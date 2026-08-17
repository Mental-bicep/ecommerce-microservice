package com.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.request.OrderRequest;
import com.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired 
	OrderService orderService;
	
	@PostMapping("/purchase")
	public String purchase(@RequestBody  OrderRequest orderRequest) {
		Integer orderId = orderService.checkout(orderRequest);
		return "Order Placed Successfully with orderId = "+orderId;
	}
	
}
