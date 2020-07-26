package com.event.sourcing.order.service;

import org.springframework.stereotype.Service;
import com.event.sourcing.order.event.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConversionException;

@Service
public class RabbitMQService {
	private final RabbitTemplate rabbitTemplate;
	
	@Value("${event.sourcing.amqp.rabbit.queue}")
	private String MESSAGE_QUEUE;
	
	public RabbitMQService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendMessage(OrderEvent orderEvent) {
		try {
			System.out.println("Sending message to queue:"+orderEvent.toString());
			rabbitTemplate.convertAndSend(MESSAGE_QUEUE, orderEvent);
		} catch(MessageConversionException ex) {
			ex.printStackTrace();
		}
	}

}
