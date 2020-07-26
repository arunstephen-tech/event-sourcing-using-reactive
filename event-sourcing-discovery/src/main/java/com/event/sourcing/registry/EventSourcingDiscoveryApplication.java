package com.event.sourcing.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EventSourcingDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSourcingDiscoveryApplication.class, args);
	}

}
