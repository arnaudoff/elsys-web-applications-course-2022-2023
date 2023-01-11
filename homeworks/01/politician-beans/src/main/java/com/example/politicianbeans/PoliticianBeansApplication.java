package com.example.politicianbeans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class PoliticianBeansApplication {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("BeanConfig.xml");
	}

}
