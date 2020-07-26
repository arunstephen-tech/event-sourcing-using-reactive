package com.event.sourcing.order.event.model;

import java.io.Serializable;

public class CartItemDTO implements Serializable {
	private String id;
	private Double price;
	private int quantity;
	private String productId;
	private static final long serialVersionUID = 12345678000L;
	
	public CartItemDTO(String id, String productId, Double price, int quantity) {
		this.id = id;
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
		builder.append("Cart Item [cartItemId=").append(id).append(", productId=").append(productId).append(", price=")
				.append(price).append(", quantity=").append(quantity);
		return builder.toString();
	}

}
