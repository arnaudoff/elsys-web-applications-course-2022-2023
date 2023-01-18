package com.example.course_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.course_project")
public class CourseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseProjectApplication.class, args);
	}

}
