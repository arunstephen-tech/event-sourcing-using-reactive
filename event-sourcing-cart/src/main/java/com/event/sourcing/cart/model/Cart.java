package com.event.sourcing.cart.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Cart {
	
	private String id;
	private Date activeSince;
	private Double totalPrice;
	private CartStatus cartStatus;
	private List<CartItem> cartItems;
	
	public Cart() {
		this.id = UUID.randomUUID().toString();
		this.activeSince = new Date();
		totalPrice = 0.0;
		this.cartStatus = CartStatus.CART_INITIATED;
	}
	
	public Cart(String id) {
		this.id = id;
		this.activeSince = new Date();
		this.cartStatus = CartStatus.CART_CREATED;
	}
	
	public Cart(String id, CartStatus cartStatus) {
		this.id = id;
		this.cartStatus = cartStatus;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public CartStatus getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(CartStatus cartStatus) {
		this.cartStatus = cartStatus;
	}
	
	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	public Date getActiveSince() {
		return activeSince;
	}

	public void setActiveSince(Date activeSince) {
		this.activeSince = activeSince;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cart [id=").append(id).append(", cartItems=").append(cartItems.toString())
			   .append(", totalPrice=").append(totalPrice)
			   .append(", activeFrom=").append(activeSince).append("]");
		return builder.toString();
	}

}
