package com.product.search.service.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.product.search.service.entity.ProductEntity;
import java.util.List;


@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String>{

	Optional<ProductEntity>  findByName(String name);
	
}
