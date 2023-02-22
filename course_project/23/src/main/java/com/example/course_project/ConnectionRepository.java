package com.example.course_project;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class ConnectionRepository {
    protected Connection connection;

    protected ConnectionRepository() {
        var url = System.getenv("DATABASE_URL");
        var user = System.getenv("DATABASE_USER");
        var password = System.getenv("DATABASE_PASSWORD");

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
