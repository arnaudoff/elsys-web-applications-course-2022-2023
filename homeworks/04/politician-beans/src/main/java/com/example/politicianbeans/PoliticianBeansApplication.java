package com.example.politicianbeans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class PoliticianBeansApplication {

	public static void main(String[] args) {
		var appContext = new ClassPathXmlApplicationContext("bean.xml");
	}

}
