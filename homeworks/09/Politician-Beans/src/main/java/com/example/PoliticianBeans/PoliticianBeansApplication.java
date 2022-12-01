package com.example.PoliticianBeans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class PoliticianBeansApplication {

	public static void main(String[] args) {
//		SpringApplication.run(PoliticianBeansApplication.class, args);
		var appContext = new ClassPathXmlApplicationContext("bean.xml");
	}

}
