package com.event.sourcing.order.event;

import java.util.UUID;
import com.event.sourcing.order.event.model.OrderDTO;

public class OrderEvent extends DomainEvent<OrderDTO, String> {
	private String orderId;
	private OrderDTO orderDto;
	private OrderStatus orderStatus;
	private static final long serialVersionUID = 123456789L;
	
	public OrderEvent() {
		this.orderId = UUID.randomUUID().toString();
		this.orderStatus = OrderStatus.ORDER_INITIATED;
	}
	
	public OrderEvent(OrderDTO orderDto) {
		this();
		this.orderDto = orderDto;
	}
	
	public OrderEvent(String orderId, OrderDTO orderDto) {
		this.orderId = orderId;
		this.orderDto = orderDto;
		this.orderStatus = OrderStatus.ORDER_INITIATED;
	}
	
	public OrderEvent(String orderId, OrderDTO orderDto, OrderStatus orderStatus) {
		this.orderId = orderId;
		this.orderDto = orderDto;
		this.orderStatus = orderStatus;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public OrderStatus getOrderStatus()
	{
		return this.orderStatus;
	}
	
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Override
	public void setSubject(OrderDTO subject) {
		this.orderDto = subject;
	}
	
	@Override
	public OrderDTO getSubject() {
		return this.orderDto;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderEvent [orderId=").append(orderId)
			   .append(", orderStatus=").append(orderStatus);
		if(orderDto != null) {
			builder.append(", orderDto=").append(orderDto.toString());
		} else {
			builder.append(", orderDto= null");
		}
		return builder.toString();
	}
}
