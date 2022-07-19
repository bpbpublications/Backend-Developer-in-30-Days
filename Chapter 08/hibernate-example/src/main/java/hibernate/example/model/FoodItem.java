package hibernate.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FoodItemsTbl", schema="orders_app")
public class FoodItem {
    @Id
    @GeneratedValue(generator = "fooditemstbl_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "fooditemstbl_id_seq", sequenceName = "orders_app.fooditemstbl_item_id_seq")
    @Column(name = "item_id")
    int id;

    @Column(name = "item_name")
    String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id_fk")
    Order assignedOrder;

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
    public Order getAssignedOrder() {
        return assignedOrder;
    }
    public void setAssignedOrder(Order assignedOrder) {
        this.assignedOrder = assignedOrder;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, order_id: %s", this.name, this.assignedOrder.name);
    }
}
