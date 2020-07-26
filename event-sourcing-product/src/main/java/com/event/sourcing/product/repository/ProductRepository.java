package com.event.sourcing.product.repository;

import com.event.sourcing.product.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

}
