package com.event.sourcing.order.event.handler;

import org.springframework.stereotype.Component;
import com.event.sourcing.order.event.OrderEvent;
import com.event.sourcing.order.service.OrderService;

@Component
public class OrderEventHandler {

	private final OrderService orderService;
	
	public OrderEventHandler(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public void receiveOrderEvent(OrderEvent orderEvent) {
		System.out.println("Recieved Order Event from Stream:"+orderEvent.toString());
		orderService.updateOrderEvent(orderEvent);
	}
	
}
