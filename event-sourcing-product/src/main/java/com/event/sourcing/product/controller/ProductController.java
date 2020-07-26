package com.event.sourcing.product.controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.event.sourcing.product.model.Product;
import com.event.sourcing.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController implements AbstractController<Product> {
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(value="/{id}")
	@Override
	public Mono<Product> getDetailsById(@PathVariable String id) {
		return productService.getDetailsById(id);
	}
	
	@GetMapping
	@Override
	public Flux<Product> getAllDetails() {
		return productService.getAllDetails();
	}

}
