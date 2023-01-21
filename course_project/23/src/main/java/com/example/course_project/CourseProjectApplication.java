package com.example.course_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.course_project")
public class CourseProjectApplication {

	public static void main(String[] args) throws SQLException {
		initDatabase();
		SpringApplication.run(CourseProjectApplication.class, args);
	}

	private static void initDatabase() throws SQLException {
		createDatabase();

		var url = System.getenv("DATABASE_URL");
		var user = System.getenv("DATABASE_USER");
		var password = System.getenv("DATABASE_PASSWORD");
		var connection = DriverManager.getConnection(url, user, password);

		initUsersTable(connection);
		initTweetsTable(connection);

		connection.close();
	}

	private static void createDatabase() throws SQLException {
		var url = System.getenv("DATABASE_URL");
		var splitIndex = url.lastIndexOf("/");
		var databaseUrl = url.substring(0, splitIndex);
		var databaseName = url.substring(splitIndex + 1);

		var user = System.getenv("DATABASE_USER");
		var password = System.getenv("DATABASE_PASSWORD");

		var connection = DriverManager.getConnection(databaseUrl, user, password);
		var statement = connection.createStatement();

		statement.execute("create database if not exists " + databaseName);

		statement.close();
		connection.close();
	}

	private static void initUsersTable(Connection connection) throws SQLException {
		var statement = connection.createStatement();
		statement.execute("""
			create table if not exists users (
				id bigint primary key not null auto_increment,
				username varchar(40) not null,
				registrationDate datetime not null default now(),
				followers bigint not null default 0,
				following bigint not null default 0
			);
		""");
		statement.close();
	}

	private static void initTweetsTable(Connection connection) throws SQLException {
		var statement = connection.createStatement();
		statement.execute("""
			create table if not exists tweets (
				id bigint primary key not null auto_increment,
				author bigint not null,
				date datetime not null default now(),
				text text not null default '',
				
				foreign key(author) references users(id)
			);
		""");
		statement.close();
	}

}
