package com.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.order.DTO.ProductResponseDTO;

@FeignClient(name = "product-search-service", path = "/search/productby", dismiss404 =true)
public interface ProductSearchServiceClient {
	
	@GetMapping("/name")
	public ResponseEntity<ProductResponseDTO> searchProductByName(@RequestParam("prodName") String prodName);
	
	@GetMapping("/id")
	public ResponseEntity<ProductResponseDTO> searchProductById(@RequestParam("productId") String productId);
}
