package com.product.search.service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.product.search.service.entity.ProductEntity;
import com.product.search.service.repository.ProductRepository;

@Service
public class ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	ProductRepository productRepository;
	
//	@Autowired
//	MongoTemplate mongoTemplate;
	
	public ProductEntity searchProductByName(String prodName) {
//		System.out.println("--> ACTUAL CONNECTED DB: " + mongoTemplate.getDb().getName());
//	    System.out.println("--> TOTAL DOCS FOUND IN COLLECTION: " + mongoTemplate.getCollection("products").countDocuments());
		Optional<ProductEntity> ent = productRepository.findByName(prodName);
		if(ent.isPresent()) return ent.get();
		log.info("no prod found for prodName = "+prodName);
		return null;
	}
	
	
	public ProductEntity searchProductById(String prodId) {
		Optional<ProductEntity> ent = productRepository.findById(prodId);
		if(ent.isPresent()) return ent.get();
		log.info("no prod found for prodid = "+prodId);
		return null;
	}
	
}
