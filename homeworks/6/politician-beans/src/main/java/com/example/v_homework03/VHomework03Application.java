package com.example.v_homework03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class VHomework03Application {


    public static void main(String[] args) {
        var appContext = new ClassPathXmlApplicationContext("beans.xml");

    }

}
