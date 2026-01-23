package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // 1. Database details
    private static final String URL = "jdbc:mysql://localhost:3306/bank_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Chathura123";

    // 2. Connection eka ganna method eka
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Driver eka load karanna (Samahara aluth Java versions wala meka one na, eth dana eka hondai)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connection eka hadanna
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Database Connected Successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println(" Driver not found! (Library eka hariyata add wela na)");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(" Connection Failed! (Database name/user/pass waradi)");
            e.printStackTrace();
        }
        return connection;
    }
}