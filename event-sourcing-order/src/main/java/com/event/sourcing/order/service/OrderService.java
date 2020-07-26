package com.event.sourcing.order.service;

import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import com.event.sourcing.order.model.CartItem;
import com.event.sourcing.order.event.OrderEvent;
import com.event.sourcing.order.event.OrderStatus;
import com.event.sourcing.order.model.OrderHistory;
import com.event.sourcing.order.event.model.CartItemDTO;
import com.event.sourcing.order.repository.OrderHistoryRepository;

@Service
public class OrderService implements AbstractService<OrderHistory> {
	
	private OrderEvent orderEvent;
	private final RabbitMQService rabbitMQService;
	private Map<String, List<OrderEvent>> eventsMap;
	private final OrderHistoryRepository orderHistoryRepository;
	
	public OrderService(OrderHistoryRepository orderHistoryRepository, RabbitMQService rabbitMQService) {
		this.orderHistoryRepository = orderHistoryRepository;
		this.rabbitMQService = rabbitMQService;
		this.eventsMap = new HashMap<String, List<OrderEvent>>();
	}

	@Override
	public Mono<OrderHistory> getDetailsById(String id) throws Exception {
		try {
			return orderHistoryRepository.findById(id);
		} catch(Exception ex) {
			throw new Exception();
		}
	}

	@Override
	public Flux<OrderHistory> getAllDetails() throws Exception {
		try {
			return orderHistoryRepository.findAll();
		} catch(Exception ex) {
			throw new Exception();
		}
	}
	
	public Mono<List<OrderEvent>> getAllEvents(String id) throws Exception {
		try {
			if(eventsMap.containsKey(id)) {
				return Flux.fromIterable(eventsMap.get(id))
						.filter(orderEvent -> orderEvent.getOrderId().equals(id))
						.collectList();
			} else {
				throw new Exception("Event is not available for the specific order");
			}
		} catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void createOrder(OrderEvent orderEvent) throws Exception {
		if(orderEvent.getOrderStatus() == OrderStatus.ORDER_INITIATED) {
			List<CartItem> listCartItem = new ArrayList<CartItem>();
			if(orderEvent.getSubject().getListOfCartItems() != null) {
				for(CartItemDTO cartItem:orderEvent.getSubject().getListOfCartItems()) {
					CartItem saveCartItem = new CartItem(cartItem.getProductId(), cartItem.getPrice(), cartItem.getQuantity());
					listCartItem.add(saveCartItem);
				}
			}
			
			Mono.just(new OrderHistory(orderEvent.getOrderId(), orderEvent.getSubject().getTotalPrice(), new Date(), 
					OrderStatus.ORDER_CREATED, listCartItem))
			.flatMap(orderHistoryRepository::save)
			.subscribe(System.out :: println);
			System.out.println("The new order is created with orderId:"+orderEvent.getOrderId());
			publishMessageToQueue(orderEvent.getOrderId(), OrderStatus.ORDER_CREATED);
			
		} else {
			throw new Exception("Unable to create the new order");
		}
	}

	public Mono<OrderHistory> cancelledOrder(String id) throws Exception {
		try {
			Mono<OrderHistory> response = orderHistoryRepository
					.findByOrderId(id)
   					.flatMap(orderHistory -> {
   						if(orderHistory.getOrderStatus() == OrderStatus.ORDER_CREATED) {
   							orderHistory.setOrderStatus(OrderStatus.ORDER_CANCELLED);
   							orderHistory.setCancelledDate(new Date());
   							return orderHistoryRepository.save(orderHistory);
   						} else {
   							return null;
   						}
   					 });
			
			if(response != null) {
				publishMessageToQueue(id, OrderStatus.ORDER_CANCELLED);
				return response;
			} else {
				throw new Exception("Unable to cancel the order");
			}
		} catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public Mono<OrderHistory> deliveredOrder(String id) throws Exception {
		try {
			Mono<OrderHistory> response = orderHistoryRepository
										.findByOrderId(id)
					   					.flatMap(orderHistory -> {
					   						if(orderHistory.getOrderStatus() == OrderStatus.ORDER_CREATED) {
					   							orderHistory.setOrderStatus(OrderStatus.ORDER_DELIVERED);
					   							orderHistory.setDeliveredDate(new Date());
					   							return orderHistoryRepository.save(orderHistory);
					   						} else {
					   							return null;
					   						}
					   					 });
			
			if(response != null) {
				publishMessageToQueue(id, OrderStatus.ORDER_DELIVERED);
				return response;
			} else {
				throw new Exception("Unable to deliver the order");
			}
			
		} catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public Mono<OrderHistory> deliveryFailure(String id, String failureMessage) throws Exception {
		try {
			Mono<OrderHistory> response = orderHistoryRepository
											.findByOrderId(id)
						   					.flatMap(orderHistory -> {
						   						if(orderHistory.getOrderStatus() == OrderStatus.ORDER_CREATED) {
						   							orderHistory.setOrderStatus(OrderStatus.ORDER_FAILURE);
						   							orderHistory.setDeliveryFailReason(failureMessage);
						   							return orderHistoryRepository.save(orderHistory);
						   						} else {
						   							return null;
						   						}
						   					 });
			
			if(response != null) {
				publishMessageToQueue(id, OrderStatus.ORDER_FAILURE);
				return response;
			} else {
				throw new Exception("Unable to deliver the order");
			}
		} catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public void updateOrderEvent(OrderEvent orderEvent) {
		try {
			this.processOrderEvents(orderEvent);
			if(orderEvent.getOrderStatus() == OrderStatus.ORDER_INITIATED) {
				this.createOrder(orderEvent);
			} 
		} catch(Exception ex) {
			System.out.println("Error in updating the order event");
		}
	}
	
	private void processOrderEvents(OrderEvent orderEvent) {
		try {
			List<OrderEvent> listofEvents = null;
			
			if(eventsMap.containsKey(orderEvent.getOrderId())) {
				listofEvents = eventsMap.get(orderEvent.getOrderId());
				listofEvents.add(orderEvent);
			} else {
				listofEvents = new ArrayList<OrderEvent>();
				listofEvents.add(orderEvent);
				eventsMap.put(orderEvent.getOrderId(), listofEvents);
			}
		} catch(Exception ex) {
			System.out.println("Error in processing events:"+ex.getMessage());
		}
	}
	
	private void publishMessageToQueue(String orderId, OrderStatus orderStatus) {
		if(orderEvent == null) {
			orderEvent = new OrderEvent();
		}
		orderEvent.setOrderId(orderId);
		orderEvent.setOrderStatus(orderStatus);
		rabbitMQService.sendMessage(orderEvent);
	}

}
