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

    public static Product getProductsByName(String prodName) throws SQLException {
        //ObservableList<Product> products = FXCollections.observableArrayList();
        //Product product;
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "select * from products where prodname= ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, prodName);
        ResultSet rs = preparedStatement.executeQuery();
        Product product = null;
        while (rs.next()) {
            product = new Product(
                    rs.getInt(1),
                    rs.getString(2)
            );

        }

        return product;
    }

    public static Product getProductsById(int productId) throws SQLException {
        //ObservableList<Product> products = FXCollections.observableArrayList();
        //Product product;
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "select * from products where productid= ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, productId);
        ResultSet rs = preparedStatement.executeQuery();
        Product product = null;
        while (rs.next()) {
            product = new Product(
                    rs.getInt(1),
                    rs.getString(2)
            );

        }

        return product;
    }


    // VIEW PRODUCTS AND SUPPLIERS (R)
    public static ObservableList<ProductSupplier> getProductsSuppliers() throws SQLException {
        ObservableList<ProductSupplier> productSuppliers = FXCollections.observableArrayList();
        ProductSupplier productSupplier;
        String query = "SELECT products.productid, products.prodname, suppliers.supplierid, suppliers.supname " +
                "FROM products_suppliers " +
                "full outer join products on products_suppliers.productid = products.productid " +
                "left join suppliers on products_suppliers.supplierid = suppliers.supplierid " +
                "ORDER BY products.productid ASC";
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            productSupplier = new ProductSupplier(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4)
            );
            productSuppliers.add(productSupplier);
        }
        return productSuppliers;
    }

    // ADD NEW PRODUCTS (C)

    public static int addProduct(String prodName) throws SQLException {
        Connection conn = DBConnection.getConnection();
        int affectedRows = 0;

        String query = "INSERT INTO products (prodName) VALUES (?)";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        //preparedStatement.setInt(1, product.getProductId());
        preparedStatement.setString(1, prodName);


        affectedRows = preparedStatement.executeUpdate();
        conn.close();

        return affectedRows;

    }

    // DELETE PRODUCT (D)

    public static int deleteProduct(int productId) throws SQLException {
        int affectedRows = 0;
        Connection conn = DBConnection.getConnection();
        String sqlStatement = "DELETE FROM products WHERE productid=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, productId);
        affectedRows = preparedStatement.executeUpdate();
        conn.close();
        return affectedRows;
    }

    // EDIT PRODUCT (U)

    public static int updateProduct(Product product) throws SQLException {
        Connection conn = DBConnection.getConnection();
        int affectedRows = 0;

        String sqlStatement = "UPDATE products SET prodname=? WHERE productid=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);

        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setInt(2, product.getProductId());


        affectedRows = preparedStatement.executeUpdate();
        conn.close();

        return affectedRows;
    }

}
