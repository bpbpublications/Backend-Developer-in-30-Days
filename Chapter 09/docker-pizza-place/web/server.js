import express from 'express';
import { engine } from 'express-handlebars';
import os from 'os';

const app = express();

app.engine('.hbs', engine({extname: '.hbs'}));
app.set('view engine', 'handlebars');
app.set('view engine', '.hbs');
app.set("views", "./views");

app.use(express.static('./public'));

app.get('/', (req, res) => {
    res.render('home', {app: {
      system: {
        os: os.hostname(),
      }
    }, layout:false});
});

const port = 3000
app.listen(port, () => console.log(`App listening to port ${port}`));