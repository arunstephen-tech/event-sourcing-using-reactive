package com.event.sourcing.order.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.event.sourcing.order.event.handler.OrderEventHandler;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

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

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(ORDER_EVENT_MESSAGE_QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(OrderEventHandler orderEventHandler) {
		return new MessageListenerAdapter(orderEventHandler, "receiveOrderEvent");
	}
}

