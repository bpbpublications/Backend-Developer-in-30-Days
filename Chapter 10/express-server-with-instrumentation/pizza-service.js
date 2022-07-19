const item = ["Pasta", "Pizza", "Lasagna"]
const specials = ["Double Cheese", "Triple Cheese", "Amatriciana"]
const combo = ["(no combo)", "single combo", "familiar combo"]

function randomFloat(min, max) {
    return (Math.random() * (max - min) + min).toFixed(2);
}

function randomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}



export default class PizzaService {

    menu = []

    constructor() {
        const min_price = 10
        const max_price = 30

        for(let i of item){
            for(let s of specials) {
                for(let c of combo) {
                    this.menu.push({
                        item: `${i} ${s} ${c}`,
                        price: randomFloat(min_price, max_price),
                    })
                }
            }
        }
    }
    
    async getOrders() {
        const orders = []

        const ordersNumber = randomInt(0, 20);

        for(let o=0; o<ordersNumber; o++) {
            const itemsNumber = randomInt(0, 5);

            const items = [];

            for(let i = 0; i < itemsNumber; i++) {
                items.push(this.menu[randomInt(0, this.menu.length)])
            }

            orders.push({
                description: `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.`,
                createdOn: new Date(),
                items,
                total: items.reduce((acc, cur) => acc + cur.price , 0)
            })
        } 

        
        const waitTime = randomFloat(1000, 3000)
        console.log({waitTime})
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve(orders)
            }, waitTime)
        })
    }
}