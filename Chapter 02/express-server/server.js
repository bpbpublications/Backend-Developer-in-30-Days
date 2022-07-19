const express = require("express");
const app = express();
const port = 8080;

app.use("/static", express.static("public"));

app.get("/", (req, res) => {
  res.send("<h1>Hello World!</h1>");
});

app.post("/", function (req, res) {
  res.send("Got a POST request");
});

app.listen(port, () => {
  console.log(`App listening at http://localhost:${port}`);
});
