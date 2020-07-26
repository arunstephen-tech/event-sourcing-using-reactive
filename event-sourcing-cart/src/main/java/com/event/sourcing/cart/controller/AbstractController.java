package com.event.sourcing.cart.controller;

import java.util.List;

public interface AbstractController<T> {
	T getDetailsById(String id);
	List<T> getAllDetails();
	T saveDetail(T value);
	void deleteDetail(String value);
}
