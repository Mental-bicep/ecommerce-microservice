package com.payment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	@PostMapping("/pay")
	public ResponseEntity<Map<String, Integer>> pay(@RequestParam("orderId") Integer orderId,
			@RequestParam("userId") String userId,
			@RequestParam("amt") Double amt) {
		
		Integer id =paymentService.makePayment(orderId, userId, amt);
			
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("paymentId",id));
		
	}
	
}
