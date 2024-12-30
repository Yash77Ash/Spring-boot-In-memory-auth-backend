package com.spring.security.inmemoryauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.spring.security.inmemoryauthentication")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
