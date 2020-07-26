package com.event.sourcing.order.repository;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Repository;
import com.event.sourcing.order.model.OrderHistory;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Repository
public interface OrderHistoryRepository extends ReactiveMongoRepository<OrderHistory, String> {
	@Query("{ 'orderId': ?0 }")
	Mono<OrderHistory> findByOrderId(final String orderId);
}
