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

    public static Integer addSupplier(Supplier supplier) throws SQLException {
        if(checkUniqueName(supplier.getSupname())) {
            throw new RuntimeException("Supplier already exists");
        } else {
            int numAffectedRows = 0;
            String maxIdQuery = "SELECT COALESCE(MAX(supplierid), 0) + 1 FROM suppliers";
            String sql = "INSERT INTO suppliers (supplierid, supname) VALUES (?, ?)";
            Connection conn = getConnection();
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(maxIdQuery);
            int newSupplierId = 1;
            if (rs1.next()) {
                newSupplierId = rs1.getInt(1);
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newSupplierId);
            stmt.setString(2, supplier.getSupname());
            numAffectedRows = stmt.executeUpdate();
            return numAffectedRows;
        }
    }

    public static int updateSupplier(Integer supplierId, Supplier supplier) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "Update suppliers SET supname = ? Where supplierId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, supplier.getSupname());
        stmt.setInt(2, supplier.getId());
        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }

    public static int deleteSupplier(Integer supplierId) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "DELETE FROM suppliers WHERE supplierId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, supplierId);
        numAffectedRows = stmt.executeUpdate();
        return numAffectedRows;
    }

    public static boolean checkUniqueName(String supplierName) throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT supname FROM suppliers WHERE supname = ?";

        boolean var5;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplierName);

            try (ResultSet rs = stmt.executeQuery()) {
                var5 = rs.next();
            }
        }
        return var5;
    }

    public static ObservableList<Supplier> getSearchedSuppliers(String searchValue) throws SQLException {
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        Supplier supplier;
        Connection conn = getConnection();
        String SQL = "select * from suppliers where supname like ?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1,"%"+searchValue+"%");
        ResultSet rs = stmt.executeQuery();
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
