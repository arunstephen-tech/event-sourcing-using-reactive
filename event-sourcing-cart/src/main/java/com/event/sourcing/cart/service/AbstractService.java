package com.event.sourcing.cart.service;

import java.util.List;

public interface AbstractService<T> {
	T getDetailsById(String id) throws Exception;
	List<T> getAllDetails() throws Exception;
	T saveDetail(T value) throws Exception;
	void deleteDetail(String value) throws Exception;
}
