package com.event.sourcing.order.controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AbstractController<T> {
	Mono<T> getDetailsById(String id);
	Flux<T> getAllDetails();
}
