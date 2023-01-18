package com.example.twitterapi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static java.lang.Thread.sleep;

public class Main
{
	public static void main(String[] args)
	{
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		UserDAO userDAO = (UserDAO)context.getBean("userDAO");

		User user1 = new User("Test User");
		int count = userDAO.addUser(user1);
		System.out.println(count + " records added");
	}
}
