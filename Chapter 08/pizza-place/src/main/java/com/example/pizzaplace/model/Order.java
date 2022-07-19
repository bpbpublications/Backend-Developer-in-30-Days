package com.example.pizzaplace.model;

import java.util.LinkedList;
import java.util.List;

public class Order {
    private String orderName;
    private List<String> ingredients;

    public Order(String orderName) {
        this.setOrderName(orderName);
        ingredients = new LinkedList<>();
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }
}