package com.uwaterloo.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
@SpringBootApplication
public class Server {
	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}

}
