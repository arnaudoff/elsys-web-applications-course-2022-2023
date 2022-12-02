package com.politicans;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;



    @SpringBootApplication
    public class PoliticiansBeansApplication {

        public static void main(String[] args) {
            new ClassPathXmlApplicationContext("Beans.xml");
        }

    }

