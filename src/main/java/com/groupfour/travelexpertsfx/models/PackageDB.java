package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

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

    public static ObservableList<Package> searchPackages(String pkgName, LocalDate start, LocalDate end, Double basePrice, Double agencyCommission, String desc) throws SQLException {
        ObservableList<Package> packages = FXCollections.observableArrayList();
        Package objectPackage;
        Connection conn = getConnection();

        String SQL = "SELECT * FROM packages WHERE " +
                "(? IS NULL OR ? = '' OR LOWER(pkgname) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(pkgdesc) LIKE LOWER(?)) " +
                "AND (? IS NULL OR pkgstartdate >= ?) " +
                "AND (? IS NULL OR pkgenddate <= ?)" +
                "AND (? IS NULL OR pkgbaseprice = ?) " +
                "AND (? IS NULL OR pkgagencycommission = ?)";
        PreparedStatement stmt = conn.prepareStatement(SQL);

        stmt.setString(1, pkgName.isEmpty() ? null : pkgName);
        stmt.setString(2, pkgName.isEmpty() ? null : pkgName);
        stmt.setString(3, "%" + pkgName + "%");

        stmt.setString(4, desc.isEmpty() ? null : desc);
        stmt.setString(5, desc.isEmpty() ? null : desc);
        stmt.setString(6, "%" + desc + "%");

        if(start == null) {
            stmt.setNull(7, java.sql.Types.DATE);
            stmt.setNull(8, java.sql.Types.DATE);
        } else {
            stmt.setDate(7, java.sql.Date.valueOf(start));
            stmt.setDate(8, java.sql.Date.valueOf(start));
        }

        if(end == null) {
            stmt.setNull(9, java.sql.Types.DATE);
            stmt.setNull(10, java.sql.Types.DATE);
        } else {
            stmt.setDate(9, java.sql.Date.valueOf(end));
            stmt.setDate(10, java.sql.Date.valueOf(end));
        }

        if(basePrice == null) {
            stmt.setNull(11, java.sql.Types.DOUBLE);
            stmt.setNull(12, java.sql.Types.DOUBLE);
        } else {
            stmt.setDouble(11, basePrice);
            stmt.setDouble(12, basePrice);
        }

        if(agencyCommission == null) {
            stmt.setNull(13, java.sql.Types.DOUBLE);
            stmt.setNull(14, java.sql.Types.DOUBLE);
        } else {
            stmt.setDouble(13, agencyCommission);
            stmt.setDouble(14, agencyCommission);
        }

        ResultSet rs = stmt.executeQuery();
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

    public static Integer addPackage(Package objectPackage) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "INSERT INTO packages (pkgname, pkgstartdate, pkgenddate, pkgdesc, pkgbaseprice, pkgagencycommission) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, objectPackage.getPkgname());
        stmt.setDate(2,Date.valueOf(objectPackage.getPkgstartdate()));
        stmt.setDate(3,Date.valueOf(objectPackage.getPkgenddate()));
        stmt.setString(4, objectPackage.getPkgdesc());
        stmt.setDouble(5, objectPackage.getPkgbaseprice());
        stmt.setDouble(6, objectPackage.getPkgagencycommission());

        numAffectedRows = stmt.executeUpdate();
        return numAffectedRows;
    }

    public static int updatePackage(Package objectPackage) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "Update packages " +
                "SET pkgname = ?, " +
                "pkgstartdate = ?, " +
                "pkgenddate = ?, pkgdesc = ?, pkgbaseprice = ?, " +
                "pkgagencycommission = ? " +
                "Where packageid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, objectPackage.getPkgname());
        stmt.setDate(2,Date.valueOf(objectPackage.getPkgstartdate()));
        stmt.setDate(3,Date.valueOf(objectPackage.getPkgenddate()));
        stmt.setString(4, objectPackage.getPkgdesc());
        stmt.setDouble(5, objectPackage.getPkgbaseprice());
        stmt.setDouble(6, objectPackage.getPkgagencycommission());
        stmt.setInt(7,objectPackage.getId());

        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }

    public static int deletePackage(int packageId) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "DELETE FROM packages WHERE packageid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,packageId);
        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }

}
