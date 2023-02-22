package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class PoliticianApplication {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

        PoliticianClient politicianClient = applicationContext.getBean("politicianClient", PoliticianClient.class);
        politicianClient.speak();


    }
}