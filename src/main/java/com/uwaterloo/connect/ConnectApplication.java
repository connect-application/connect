package com.uwaterloo.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// TODO  remove when db support is added, and Security configuration is set up
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
public class ConnectApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConnectApplication.class, args);
	}

}
