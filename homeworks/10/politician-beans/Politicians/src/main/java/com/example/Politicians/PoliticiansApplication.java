package com.example.Politicians;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class PoliticiansApplication {

	public static void main(String[] args) {

		var appContext = new ClassPathXmlApplicationContext("beans.xml");
	}

}
