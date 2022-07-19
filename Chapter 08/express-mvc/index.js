import express from 'express';
import { engine } from 'express-handlebars';

const app = express();

app.engine('.hbs', engine({extname: '.hbs'}));
app.set('view engine', 'handlebars');
app.set('view engine', '.hbs');
app.set("views", "./views");

app.get('/', (req, res) => {
    res.render('home', {
        order: {
            orderName: "Pedro's order",
            ingredients: ["Cheese", "Tomato sauce"]
        },
        layout:false});
});

const port = 3000
app.listen(port, () => console.log(`App listening to port ${port}`));