package com.event.sourcing.order.controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import org.springframework.http.MediaType;
import com.event.sourcing.order.event.OrderEvent;
import com.event.sourcing.order.model.OrderHistory;
import com.event.sourcing.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController implements AbstractController<OrderHistory> {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping(value="/{id}")
	@Override
	public Mono<OrderHistory> getDetailsById(@PathVariable String id) {
		try {
			return orderService.getDetailsById(id);
		} catch(Exception ex) {
			System.out.println("Error in fetch the order:"+ex.getMessage());
		}
		return null;
	}

	@GetMapping
	@Override
	public Flux<OrderHistory> getAllDetails() {
		try {
			return orderService.getAllDetails();
		} catch(Exception ex) {
			System.out.println("Error in fetch the order:"+ex.getMessage());
		}
		return null;
	}
	
	@GetMapping(value="/{id}/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<List<OrderEvent>> getOrderEvent(@PathVariable String id) {
		try {
			return orderService.getAllEvents(id);
		} catch(Exception ex) {
			System.out.println("Error in retrieveing events:"+ex.getMessage());
		}
		return null;
	}
	
	@GetMapping(value="/{id}/delivered")
	public Mono<OrderHistory> deliveredOrder(@PathVariable String id) {
		try {
			return orderService.deliveredOrder(id);
		} catch(Exception ex) {
			System.out.println("Error in order delivery:"+ex.getMessage());
		}
		return null;
	}
	
	@GetMapping(value="/{id}/cancelled")
	public Mono<OrderHistory> cencelleddOrder(@PathVariable String id) {
		try {
			return orderService.cancelledOrder(id);
		} catch(Exception ex) {
			System.out.println("Error in order delivery:"+ex.getMessage());
		}
		return null;
	}
	
	@PostMapping(value="/{id}/deliveryFailure")
	public Mono<OrderHistory> deliveryFailure(@PathVariable String id, @RequestBody String failureMessage) {
		try {
			return orderService.deliveryFailure(id, failureMessage);
		} catch(Exception ex) {
			System.out.println("Error in order delivery:"+ex.getMessage());
		}
		return null;
	}

}
