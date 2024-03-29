package com.example.domashno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class can be used to initialize the database connection
public class DatabaseConnection {
    protected static Connection initializeDatabase()
            throws SQLException, ClassNotFoundException
    {
        // Initialize all the information regarding
        // Database Connection
        String dbDriver = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql:// localhost:3306/";
        // Database name to access
        String dbName = "instagram";
        String dbUsername = "root";
        String dbPassword = "123456789a";

        Class.forName(dbDriver);
        Connection con = DriverManager.getConnection(dbURL,
                dbUsername,
                dbPassword);
        return con;
    }
}
