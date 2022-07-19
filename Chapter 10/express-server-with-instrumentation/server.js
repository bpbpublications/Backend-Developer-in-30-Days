import express from "express";
import PizzaService from "./pizza-service.js";

const app = express();

const port = 8080;

const pizzaService = new PizzaService();

app.use("/static", express.static("public"));

app.get("/", (req, res) => {
  res.send("<h1>Hello World!</h1>");
});

app.post("/", function (req, res) {
  res.send("Got a POST request");
});


app.get("/orders", async (req, res) => {
  let start = (new Date()).getTime()

  const orders = await pizzaService.getOrders();

  let end = (new Date()).getTime()
  console.log(`Orders Service request time: ${(end - start)}ms`)
  
  res.json(orders);
});

app.post("/", function (req, res) {
  res.send("Got a POST request");
});

app.listen(port, () => {
  console.log(`App listening at http://localhost:${port}`);
});