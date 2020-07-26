package com.event.sourcing.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventSourcingProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSourcingProductApplication.class, args);
	}

}
