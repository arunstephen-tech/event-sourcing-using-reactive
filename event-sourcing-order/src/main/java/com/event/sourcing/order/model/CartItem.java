package com.event.sourcing.order.model;

public class CartItem {
	private Double price;
	private Integer quantity;
	private String productId;
	
	public CartItem() {
		
	}
	
	public CartItem(String productId, Double price, int quantity) {
		this.price = price;
		this.quantity = quantity;
		this.productId = productId;
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

}
