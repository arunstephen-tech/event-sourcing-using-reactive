package com.event.sourcing.order.event;

import java.io.Serializable;

public abstract class DomainEvent<T, ID> implements Serializable {
	private ID id;
	private static final long serialVersionUID = 12345000L;
	
	public DomainEvent() {
		
	}
	
	public ID getId() {
		return id;
	}
	
	public void setId(ID id) {
		this.id = id;
	}
	
	public abstract T getSubject();
	public abstract void setSubject(T subject);
	
	
	@Override
	public String toString() {
		return "Domain Event: { id: " + id + ", subject: " + getSubject() + " }";
	}
	
}
