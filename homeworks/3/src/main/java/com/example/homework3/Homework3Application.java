package com.example.homework3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class Homework3Application {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("beans.xml");
    }

}
