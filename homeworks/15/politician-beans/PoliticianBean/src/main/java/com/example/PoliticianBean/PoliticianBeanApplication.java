package com.example.PoliticianBean;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class PoliticianBeanApplication {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("beans.xml");
	}
}