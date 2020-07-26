package com.event.sourcing.product.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import com.event.sourcing.product.model.Product;
import com.event.sourcing.product.repository.ProductRepository;

@Service
public class ProductService implements AbstractService<Product> {
	
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Mono<Product> getDetailsById(String id) {
		return productRepository.findById(id);
	}

	@Override
	public Flux<Product> getAllDetails() {
		return productRepository.findAll();
	}

}
