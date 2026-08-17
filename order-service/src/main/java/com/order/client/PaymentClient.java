package com.order.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", path = "/payment")
public interface PaymentClient {
	
	@PostMapping("/pay")
	public ResponseEntity<Map<String,Integer>> pay(@RequestParam("orderId") Integer orderId,
													@RequestParam("userId") String userId,
													@RequestParam("amt") Double amt);
	
}
