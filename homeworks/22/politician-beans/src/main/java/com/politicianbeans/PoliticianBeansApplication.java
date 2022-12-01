package com.politicianbeans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
// @ImportResource("classpath*:beans.xml")
// alternative way to run the application
// (politician's message appears before the message for that the application has  finished starting)
public class PoliticianBeansApplication {
    public static void main(String[] args) {
//      SpringApplication.run(PoliticianBeansApplication.class, args);
//      alternative way to run the application
//      (politician's message appears before the message for that the application has  finished starting)
        new ClassPathXmlApplicationContext("beans.xml");
    }

}
