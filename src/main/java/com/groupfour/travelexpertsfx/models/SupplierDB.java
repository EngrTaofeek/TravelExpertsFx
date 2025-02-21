package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @Author DarylWxc
 * @Date 2/17/2025
 * @Description SupplierDB manager
 */
public class SupplierDB {
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

    public static ObservableList<Supplier> getSuppliers() throws SQLException {
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        Supplier supplier;
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from suppliers");
        while (rs.next()) {
            supplier = new Supplier(
                    rs.getInt(1),
                    rs.getString(2)
            );
            suppliers.add(supplier);
        }
        return suppliers;
    }
}
