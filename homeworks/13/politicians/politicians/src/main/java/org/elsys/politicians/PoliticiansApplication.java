package org.elsys.politicians;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class PoliticiansApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliticiansApplication.class, args);
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
	}
}
