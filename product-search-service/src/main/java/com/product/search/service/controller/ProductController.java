package com.product.search.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.search.service.entity.ProductEntity;
import com.product.search.service.repository.ProductRepository;
import com.product.search.service.service.ProductService;

@RestController
@RequestMapping("/search/productby")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/name")
	public ResponseEntity<?> searchProductByName(@RequestParam("prodName") String prodName) {
		ProductEntity res = productService.searchProductByName(prodName);
		if(res == null) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Product with prodName = "+prodName +" does not exists");
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	
	@GetMapping("/id")
	public ResponseEntity<?> searchProductById(@RequestParam("productId") String productId) {
		ProductEntity res = productService.searchProductById(productId);
		if(res == null) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Product with prodId = "+productId +" does not exists");
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	
}
