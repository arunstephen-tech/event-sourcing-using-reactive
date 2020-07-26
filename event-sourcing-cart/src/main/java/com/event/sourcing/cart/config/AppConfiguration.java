package com.event.sourcing.cart.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

	@Value("${event.sourcing.amqp.rabbit.queue}")
	private String ORDER_EVENT_MESSAGE_QUEUE;
	
	@Value("${event.sourcing.amqp.rabbit.exchange}")
	private String ORDER_EVENT_MESSAGE_EXCHANGE;

	@Bean
	Queue queue() {
		return new Queue(ORDER_EVENT_MESSAGE_QUEUE, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(ORDER_EVENT_MESSAGE_EXCHANGE);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ORDER_EVENT_MESSAGE_QUEUE);
	}
	
}
