package org.elsysbg.twitterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.elsysbg.twitterapi"
public class TwitterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApiApplication.class, args);
	}

}
