package com.event.sourcing.order.model;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import com.event.sourcing.order.event.OrderStatus;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="order_history")
public class OrderHistory {
	@Id
	private String id;
	@NotNull
	private String orderId;
	private Double totalPrice;
	private Date creationDate;
	private Date deliveredDate;
	private Date cancelledDate;
	private OrderStatus orderStatus;
	private String deliveryFailReason;
	private List<CartItem> cartItems;
	
	public OrderHistory() {
		
	}
	
	public OrderHistory(String orderId) {
		this();
		this.orderId = orderId;
	}
	
	public OrderHistory(String orderId, Double totalPrice, Date creationDate, OrderStatus orderStatus, List<CartItem> cartItems) {
		this();
		this.orderId = orderId;
		this.totalPrice = totalPrice;
		this.creationDate = creationDate;
		this.orderStatus = orderStatus;
		this.cartItems = cartItems;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getDeliveredDate() {
		return deliveredDate;
	}
	
	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}
	
	public Date getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getDeliveryFailReason() {
		return deliveryFailReason;
	}
	
	public void setDeliveryFailReason(String deliveryFailReason) {
		this.deliveryFailReason = deliveryFailReason;
	}
	
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=").append(id).append(", orderId=").append(orderId).append(", orderStatus=")
				.append(orderStatus).append(", cartItems=[").append(cartItems.toString()).append("], totalPrice=")
				.append(totalPrice.toString()).append(", creationDate=").append(creationDate).append(", deliveredDate=")
				.append(deliveredDate).append(", cancelledDate=").append(cancelledDate).append(", deliveryFailReason=")
				.append(deliveryFailReason).append("]");
		return builder.toString();
	}

}
