package com.uwaterloo.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// TODO  remove when db support is added, and Security configuration is set up
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class Server {
	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}

}
