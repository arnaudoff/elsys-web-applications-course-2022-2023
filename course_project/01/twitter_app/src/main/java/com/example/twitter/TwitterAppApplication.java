package com.example.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.twitter")
public class TwitterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterAppApplication.class, args);
	}

}
