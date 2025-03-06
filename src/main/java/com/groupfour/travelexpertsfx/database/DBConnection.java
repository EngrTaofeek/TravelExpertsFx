package com.groupfour.travelexpertsfx.database;

/**
 * @Author: Kazi Fattah
 * @Date: 2/2025
 * @Description: Method for establishing database connection
 * @To-do-list:
 *

 * - view past trips
 * - search customer
 *
 */


import java.sql.*;


public interface DBConnection {
    public static Connection getConnection() {
        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        Connection conn;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to database", e);
        }

        return conn;
    }
}
