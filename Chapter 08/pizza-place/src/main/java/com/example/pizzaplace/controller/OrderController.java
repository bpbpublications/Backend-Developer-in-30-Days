package com.example.pizzaplace.controller;

import com.example.pizzaplace.model.Order;
import com.example.pizzaplace.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;

	@GetMapping("/order")
	public String getOrderHandler(Model model) {
		Order currentOrder = orderService.getOrder();
		
		model.addAttribute("orderName", currentOrder.getOrderName());
		model.addAttribute("ingredients", currentOrder.getIngredients());
		return "order";
	}

}