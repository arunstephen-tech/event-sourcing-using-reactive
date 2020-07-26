package com.event.sourcing.product.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AbstractService<T> {
	Mono<T> getDetailsById(String id);
	Flux<T> getAllDetails();
}
