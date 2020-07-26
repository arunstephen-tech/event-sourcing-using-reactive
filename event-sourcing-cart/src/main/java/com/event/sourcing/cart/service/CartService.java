package com.event.sourcing.cart.service;

import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import com.event.sourcing.cart.model.Cart;
import com.event.sourcing.cart.model.CartItem;
import org.springframework.stereotype.Service;
import com.event.sourcing.cart.model.CartStatus;
import com.event.sourcing.order.event.OrderEvent;
import com.event.sourcing.order.event.OrderStatus;
import com.event.sourcing.order.event.model.OrderDTO;
import com.event.sourcing.order.event.model.CartItemDTO;

@Service
public class CartService implements AbstractService<Cart> {
	
	private OrderEvent orderEvent;
	private List<Cart> cacheCartList;
	private final RabbitMQService rabbitMQService;
	
	public CartService(RabbitMQService rabbitMqService) {
		cacheCartList = new ArrayList<Cart>();
		this.orderEvent = null;
		this.rabbitMQService = rabbitMqService;
	}

	@Override
	public Cart getDetailsById(String id) throws Exception {
		if(cacheCartList.isEmpty()) {
			throw new Exception("Cart is empty and not select any item from cart");
		} else if(cacheCartList.size() > 0) {
			for(Cart cart:cacheCartList) {
				if(cart.getId().equals(id)) {
					return cart;
				}
			}
			throw new Exception("The given cart is not available");
		}
		return null;
	}

	@Override
	public List<Cart> getAllDetails() throws Exception {
		if(cacheCartList.isEmpty()) {
			throw new Exception("The cart is empty");
		} else {
			return cacheCartList;
		}
	}

	@Override
	public Cart saveDetail(Cart value) throws Exception {
		Cart saveCart = null;
		if(value.getId() != null) {
			saveCart = new Cart(value.getId(), value.getCartStatus());
		} else {
			saveCart = new Cart();
		}
		publishMessageToQueue(OrderStatus.CART_INITIATED, null);
		
		if(value.getCartStatus() == CartStatus.CART_INITIATED || value.getCartStatus() == CartStatus.CART_CREATED) {
			cacheCartList.add(saveCart);
			publishMessageToQueue(OrderStatus.CART_CREATED, null);
			return this.getDetailsById(saveCart.getId());
		} else {
			throw new Exception("You can't able to add item to the cart");
		}
	}
	
	public Cart saveChildItem(String cartId, CartItem cartItem) throws Exception {
		Cart cart, savedCart;
		if(cartId == null || cartId.equals("null")) {
			savedCart = new Cart();
			orderEvent = null;
			cart = this.saveDetail(savedCart);
		} else {
			cart = this.getDetailsById(cartId);
		}
		
		if(cart != null) {
			return this.saveCartItem(cart, cartItem);
		} else {
			throw new Exception("The cart is not available to add cart items");
		}
	}
	
	private Cart saveCartItem(Cart cart, CartItem cartItem) throws Exception {
		CartItem saveCartItem = null;
		if(cart.getCartItems() == null) {
			cart.setCartItems(new ArrayList<CartItem>());
		}
		if(cart.getCartStatus() == CartStatus.CART_INITIATED || cart.getCartStatus() == CartStatus.CART_CREATED ||
				cart.getCartStatus() == CartStatus.CART_UPDATED) {
			if(cartItem.getId().isEmpty()) {
				saveCartItem = new CartItem(cartItem.getProductId(), cartItem.getName(), cartItem.getPrice(), cartItem.getQuantity());
			} else {
				saveCartItem = new CartItem(cartItem.getId(), cartItem.getProductId(), cartItem.getName(), cartItem.getPrice(), cartItem.getQuantity());
			}
			cart.getCartItems().add(saveCartItem);
			cart.setTotalPrice(getTotalPrice(cart.getCartItems()));
			cart.setActiveSince(new Date());
			cart.setCartStatus(CartStatus.CART_UPDATED);
			
			OrderDTO orderDto = new OrderDTO(orderEvent.getOrderId());
			orderDto.setListOfCartItems(new ArrayList<CartItemDTO>());
			CartItemDTO saveCartItemDto = new CartItemDTO(saveCartItem.getId(), saveCartItem.getProductId(), saveCartItem.getPrice(), saveCartItem.getQuantity());
			orderDto.getListOfCartItems().add(saveCartItemDto);
			orderDto.setTotalPrice(cart.getTotalPrice());
			publishMessageToQueue(OrderStatus.CART_ITEM_ADDED, orderDto);
			return this.getDetailsById(cart.getId());
		} else {
			throw new Exception("You can't able to add the item to the cart");
		}
	}

	@Override
	public void deleteDetail(String id) throws Exception {
		if(cacheCartList.size() > 0) {
			boolean isRemoved = false;
			Iterator<Cart> cartIterator = cacheCartList.iterator();
			while(cartIterator.hasNext()) {
				Cart deleteCart = cartIterator.next();
				if(deleteCart.getId().equals(id)) {
					if(deleteCart.getCartStatus() != CartStatus.CART_CHECKOUT) {
						cartIterator.remove();
						isRemoved = true;
					}
				}
			}
			
			if(!isRemoved) {
				throw new Exception("The cart is not found to remove");
			} else {
				publishMessageToQueue(OrderStatus.CART_REMOVED, null);
			}
			
		} else {
			throw new Exception("The cart is empty");
		}
	}
	
	public Cart deleteItemDetail(String cartId, String cartItemId) throws Exception {
		if(cartId == null || cartId.equals("null")) {
			throw new Exception("The cart id should not be null");
		} else {
			System.out.println(cacheCartList.toString());
		}
		Cart cart = this.getDetailsById(cartId);
		if(cart != null) {
			if(cart.getCartStatus() != CartStatus.CART_CHECKOUT) {
				if(cart.getCartItems().isEmpty()) {
					throw new Exception("Cart is empty and cannot remove any item");
				} else {
					boolean isRemoved = false;
					CartItem removeCartItem = null;
					Iterator<CartItem> cartItemIterator = cart.getCartItems().iterator();
					while(cartItemIterator.hasNext()) {
						CartItem cartItem = cartItemIterator.next();
						if(cartItem.getId().equals(cartItemId)) {
							removeCartItem = cartItem;
							cartItemIterator.remove();
							isRemoved = true;
						}
					}
					
					if(!isRemoved) {
						throw new Exception("The cart item is not found to remove from the cart");
					} else {
						cart.setActiveSince(new Date());
						cart.setTotalPrice(getTotalPrice(cart.getCartItems()));
						
						OrderDTO orderDto = new OrderDTO(orderEvent.getId());
						orderDto.setListOfCartItems(new ArrayList<CartItemDTO>());
						CartItemDTO removeCartItemDto = new CartItemDTO(removeCartItem.getId(), removeCartItem.getProductId(), removeCartItem.getPrice(), removeCartItem.getQuantity());
						orderDto.getListOfCartItems().add(removeCartItemDto);
						orderDto.setTotalPrice(cart.getTotalPrice());
						publishMessageToQueue(OrderStatus.CART_ITEM_REMOVED, orderDto);
					}
				}
				
				return this.getDetailsById(cart.getId());
			} else {
				throw new Exception("You can't able to remove the item from the cart");
			}
		} else {
			throw new Exception("The cart is not available to remove the cart items");
		}
	}
	
	public void checkoutItems(String cartId) throws Exception {
		try {
			Cart cart = this.getDetailsById(cartId);
			if(cart != null) {
				cart.setActiveSince(new Date());
				cart.setCartStatus(CartStatus.CART_CHECKOUT);
				
				OrderDTO orderDto = new OrderDTO(orderEvent.getId());
				orderDto.setListOfCartItems(new ArrayList<CartItemDTO>());
				orderDto.setTotalPrice(cart.getTotalPrice());
				CartItemDTO cartItemDto = null;
				for(CartItem cartItem:cart.getCartItems()) {
					cartItemDto = new CartItemDTO(cartItem.getId(), cartItem.getProductId(), cartItem.getPrice(), cartItem.getQuantity());
					orderDto.getListOfCartItems().add(cartItemDto);
				}
				publishMessageToQueue(OrderStatus.ORDER_INITIATED, orderDto);
			} else {
				throw new Exception("The cart is empty");
			}
		} catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	private Double getTotalPrice(List<CartItem> listCartItems) {
		Double totalPrice = 0.0;
		
		if(!listCartItems.isEmpty() && listCartItems.size() > 0) {
			for(CartItem cartItem: listCartItems) {
				totalPrice += cartItem.getQuantity() * cartItem.getPrice();
			}
		} 
		return totalPrice;
	}
	
	private void publishMessageToQueue(OrderStatus orderStatus, OrderDTO orderDto) {
		if(orderEvent == null) {
			orderEvent = new OrderEvent();
		}
		orderEvent.setOrderStatus(orderStatus);
		orderEvent.setSubject(orderDto);
		rabbitMQService.sendMessage(orderEvent);
	}

}
