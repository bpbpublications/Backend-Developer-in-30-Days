package hibernate.example.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.List;

@Entity
@Table(name = "OrdersTbl", schema="orders_app")
public class Order {
    @Id
    @GeneratedValue(generator = "order_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_id_seq", sequenceName = "orders_app.orderstbl_order_id_seq")
    @Column(name = "order_id")
    int id;

    @Column(name = "order_name")
    String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    List<FoodItem> ingredients;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FoodItem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<FoodItem> ingredients) {
        this.ingredients = ingredients;
    }


}
