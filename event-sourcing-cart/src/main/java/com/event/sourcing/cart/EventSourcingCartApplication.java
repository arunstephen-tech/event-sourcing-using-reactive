package com.event.sourcing.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventSourcingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSourcingCartApplication.class, args);
	}

}
