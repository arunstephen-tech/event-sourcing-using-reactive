package com.event.sourcing.order.event.model;

import java.util.List;
import java.io.Serializable;

public class OrderDTO implements Serializable {
	private String id;
	private Double totalPrice;
	private List<CartItemDTO> listOfCartItems;
	private static final long serialVersionUID = 1234567000L;
	
	public OrderDTO(String id) {
		this.id = id;
	}
	
	public OrderDTO(String id, List<CartItemDTO> listOfCartItems) {
		this.id = id;
		this.listOfCartItems = listOfCartItems;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public List<CartItemDTO> getListOfCartItems() {
		return listOfCartItems;
	}
	
	public void setListOfCartItems(List<CartItemDTO> listOfCartItems) {
		this.listOfCartItems = listOfCartItems;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDTO [id=").append(id).append(", listOfCartItems=").append(listOfCartItems.toString());
		return builder.toString();
	}

}
