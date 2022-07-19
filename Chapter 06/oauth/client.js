const express = require("express");
const app = express();
const cors = require("cors");
require("dotenv").config();
const path = require("path");

var request = require("request");

const jwt_decode = require("jwt-decode");

var options = {
  method: "POST",
  url: "https://pfernandom.auth0.com/oauth/token",
  headers: { "content-type": "application/json" },
  body: '{"client_id":"2PI1rqZ1R2D2PICzC9O2li4UYItJDgnA","client_secret":"-PwrMwM__qPATBLSZ7ucm8oM9h_9rwzGau-wGTeReTwdd20uZnX7MbFl7qVPmA7Y","audience":"http://localhost:3010","grant_type":"client_credentials"}',
};

const { auth, requiresAuth, claimIncludes } = require("express-openid-connect");

const config = {
  authorizationParams: {
    response_type: "code",
    audience: "http://localhost:3010",
    scope: "openid profile email roles",
  },
  authRequired: false,
  auth0Logout: true,
  secret: "a long, randomly-generated string stored in env",
  baseURL: "http://localhost:3010",
  clientID: "MONl3JC4qN8zQTG4sxG5j8vCrGK1Sy5U",
  issuerBaseURL: "https://pfernandom.auth0.com",

  clientSecret:
    "k9aOt5g2Z8CYrLYY0TcXNQNSX2Y9Jq1GDHqZinAPoEttW-SW0o9_GTKy_imemHZc",
  //  "-PwrMwM__qPATBLSZ7ucm8oM9h_9rwzGau-wGTeReTwdd20uZnX7MbFl7qVPmA7Y",
  afterCallback: (req, res, session) => {
    const claims = jwt_decode(session.id_token, { header: true }); // using jose library to decode JWT
    console.log({ claims });
    /*
    if (claims.org_id !== "Required Organization") {
      throw new Error("User is not a part of the Required Organization");
    }*/
    return session;
  },
};

// auth router attaches /login, /logout, and /callback routes to the baseURL
app.use(auth(config));

app.get("/writer", claimIncludes("kid"), (req, res) =>
  res.send(`Hello ${req.oidc.user.sub}, this is the sales managers section.`)
);

// req.isAuthenticated is provided from the auth router
app.get("/", (req, res) => {
  if (!req.oidc.isAuthenticated()) {
    res.send(`
      <a href="/login">Log in</href>
    `);
  } else {
    res.send(`
      <h1>Welcome. ${req.oidc.user.name}</h1>
      <br>
      <a href="/profile">Profile</a>
      <br>
      <a href="/logout"/>Log out</a>
    `);
  }
});

app.get("/profile", requiresAuth(), (req, res) => {
  let { token_type, access_token } = req.oidc.accessToken;

  request(
    {
      method: "GET",
      url: "http://localhost:8080/authorized",
      headers: { authorization: `Bearer ${access_token}` },
    },
    function (error, response, body) {
      if (error) throw new Error(error);

      request(
        {
          method: "GET",
          url: "https://pfernandom.auth0.com/userinfo",
          headers: { authorization: `Bearer ${access_token}` },
        },
        function (error, response, body2) {
          if (error) throw new Error(error);
          console.log(body2);
          res.send(
            JSON.stringify(
              Object.assign(
                {},
                req.oidc.user,
                JSON.parse(body),
                JSON.parse(body2)
              )
            )
          );
        }
      );
    }
  );
});

/*
app.use(function (err, req, res, next) {
  console.error(err.stack);
  return res.set(err.headers).status(err.status).json({ message: err.message });
});
*/

app.listen(3010);
console.log("Listening on http://localhost:3010");
