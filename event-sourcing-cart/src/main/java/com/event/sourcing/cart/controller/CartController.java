package com.event.sourcing.cart.controller;

import java.util.List;

import com.event.sourcing.cart.model.Cart;
import com.event.sourcing.cart.model.CartItem;
import com.event.sourcing.cart.service.CartService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController implements AbstractController<Cart> {
	private final CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping
	@Override
	public List<Cart> getAllDetails() {
		try {
			return cartService.getAllDetails();
		} catch(Exception ex) {
			System.out.println("Error in fecth the details " + ex.getMessage());
		}
		return null;
	}
	
	@GetMapping(value="/{id}")
	@Override
	public Cart getDetailsById(@PathVariable String id) {
		try {
			return cartService.getDetailsById(id);
		} catch(Exception ex) {
			System.out.println("Error in fecth the details " + ex.getMessage());
		}
		return null;
	}
	
	@PostMapping
	@Override
	public Cart saveDetail(@RequestBody Cart value) {
		try {
			return cartService.saveDetail(value);
		} catch(Exception ex) {
			System.out.println("Error in fecth the details " + ex.getMessage());
		}
		return null;
	}
	
	@PostMapping(value="/{id}/addItem")
	public Cart saveChildItem(@PathVariable String id, @RequestBody CartItem value) {
		try {
			return cartService.saveChildItem(id, value);
		} catch (Exception ex) {
			System.out.println("Error in save the details " + ex.getMessage());
		}
		return null;
	}

	@DeleteMapping(value="/{id}")
	@Override
	public void deleteDetail(@PathVariable String id) {
		try {
			cartService.deleteDetail(id);
		} catch (Exception ex) {
			System.out.println("Error in fecth the details " + ex.getMessage());
		}
	}
	
	@DeleteMapping(value="/{id}/removeItem/{itemId}")
	public void deleteItemDetail(@PathVariable String id, @PathVariable String itemId) {
		try {
			cartService.deleteItemDetail(id, itemId);
		} catch (Exception ex) {
			System.out.println("Error in fecth the details " + ex.getMessage());
		}
	}
	
	@GetMapping(value="/{id}/checkout")
	public void checkoutItems(@PathVariable String id) {
		try {
			cartService.checkoutItems(id);
		} catch (Exception ex) {
			System.out.println("Error in fecth the details " + ex.getMessage());
		}
	}

}
