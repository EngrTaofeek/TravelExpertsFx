package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @Author DarylWxc
 * @Date 2/13/2025
 * @Description PackageDB manager
 */

public class PackageDB {
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

    public static ObservableList<Package> getPackages() throws SQLException {
        ObservableList<Package> packages = FXCollections.observableArrayList();
        Package objectPackage;
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from packages");
        while (rs.next()) {
            objectPackage = new Package(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(7),
                    rs.getDouble(6),
                    rs.getDate(4),
                    rs.getDate(3),
                    rs.getString(5)
            );
            packages.add(objectPackage);
        }
        return packages;
    }
}
