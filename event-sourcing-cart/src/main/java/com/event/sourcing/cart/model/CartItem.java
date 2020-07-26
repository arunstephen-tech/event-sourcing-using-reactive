package com.event.sourcing.cart.model;

import java.util.UUID;

public class CartItem {
	private String id;
	private String name;
	private Double price;
	private int quantity;
	private String productId;
	
	public CartItem() {
		this.id = UUID.randomUUID().toString();
	}
	
	public CartItem(String productId, String name, Double price, int quantity) {
		this();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.setProductId(productId);
	}
	
	public CartItem(String id, String productId, String name, Double price, int quantity) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.productId = productId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cart Item [cartItemId=").append(id).append(", Name=").append(name).append(", price=")
				.append(price).append(", quantity=").append(quantity);
		return builder.toString();
	}

}
