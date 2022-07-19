var express = require("express");
var app = express();
var jwt = require("express-jwt");
var jwks = require("jwks-rsa");

var port = process.env.PORT || 8080;

var jwtCheck = jwt({
  secret: jwks.expressJwtSecret({
    cache: true,
    rateLimit: true,
    jwksRequestsPerMinute: 5,
    jwksUri: "https://pfernandom.auth0.com/.well-known/jwks.json",
  }),
  audience: "http://localhost:3010",
  issuer: "https://pfernandom.auth0.com/",
  algorithms: ["RS256"],
});

app.use(jwtCheck);

app.get("/authorized", function (req, res) {
  res.send(
    JSON.stringify({
      sensitive_info: "This is very sensitive info",
      ...req.user,
    })
  );
});

app.listen(port);
