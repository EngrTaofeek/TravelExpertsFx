package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ProductDB implements DBConnection {

    public static ObservableList<Product> getProducts() throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        Product product;
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from products");
        while (rs.next()) {
            product = new Product(
                    rs.getInt(1),
                    rs.getString(2)
            );
            products.add(product);
        }
        return products;
    }

}
