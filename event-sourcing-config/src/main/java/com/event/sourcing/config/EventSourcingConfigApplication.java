package com.event.sourcing.config;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableConfigServer
@SpringBootApplication
public class EventSourcingConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSourcingConfigApplication.class, args);
	}

}
