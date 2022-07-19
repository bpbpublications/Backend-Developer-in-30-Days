package com.example.pizzaplace.service;

import com.example.pizzaplace.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	public Order getOrder() {
		Order newOrder = new Order("Pedro's order");

		newOrder.addIngredient("Cheese");
		newOrder.addIngredient("Tomato sauce");

		return newOrder;
	}

}