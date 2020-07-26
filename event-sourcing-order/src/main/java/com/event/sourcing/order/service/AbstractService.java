package com.event.sourcing.order.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AbstractService<T> {
	Mono<T> getDetailsById(String id) throws Exception;
	Flux<T> getAllDetails() throws Exception;
}
