package com.example.instagramhomework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection{


    protected static Connection initializeDatabase() throws ClassNotFoundException, SQLException {
        String dbDriver = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql:// localhost:3306/";
        String dbName = "demoprj";
        String dbUsername =    "root";
        String dbPassword = "root";

        Class.forName(dbDriver);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagramhomework", "root", "password");
        return connection;
    }


}
