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
                    rs.getString(5),
                    rs.getString(8)
            );
            packages.add(objectPackage);
        }
        return packages;
    }

    public static ObservableList<Package> searchPackagesByString(String searchWord, LocalDate start, LocalDate end) throws SQLException {
        ObservableList<Package> packages = FXCollections.observableArrayList();
        Package objectPackage;
        Connection conn = getConnection();

        String SQL = "SELECT * FROM packages WHERE " +
                "((? IS NULL OR ? = '' OR pkgname ILIKE ?) " +
                "OR (? IS NULL OR ? = '' OR pkgdesc ILIKE ?)) " +
                "AND (?::timestamp IS NULL OR pkgstartdate >= ?::timestamp) " +
                "AND (?::timestamp IS NULL OR pkgenddate <= ?::timestamp)";
        PreparedStatement stmt = conn.prepareStatement(SQL);

        stmt.setString(1, searchWord.isEmpty() ? null : searchWord);
        stmt.setString(2, searchWord.isEmpty() ? null : searchWord);
        stmt.setString(3, "%" + searchWord + "%");

        stmt.setString(4, searchWord.isEmpty() ? null : searchWord);
        stmt.setString(5, searchWord.isEmpty() ? null : searchWord);
        stmt.setString(6, "%" + searchWord + "%");

        if (start == null) {
            stmt.setNull(7, Types.DATE);
            stmt.setNull(8, Types.DATE);
        } else {
            stmt.setDate(7, Date.valueOf(start));
            stmt.setDate(8, Date.valueOf(start));
        }

        if (end == null) {
            stmt.setNull(9, Types.DATE);
            stmt.setNull(10, Types.DATE);
        } else {
            stmt.setDate(9, Date.valueOf(end));
            stmt.setDate(10, Date.valueOf(end));
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
                    rs.getString(5),
                    rs.getString(8)
            );
            packages.add(objectPackage);
        }
        return packages;
    }

    public static ObservableList<Package> searchPackagesByNumber(double searchWord, LocalDate start, LocalDate end) throws SQLException {
        ObservableList<Package> packages = FXCollections.observableArrayList();
        Package objectPackage;
        Connection conn = getConnection();

        String SQL = "SELECT * FROM packages WHERE " +
                "(CAST(pkgbaseprice as TEXT) ilike '%" + searchWord + "%' " +
                "or CAST(pkgagencycommission as TEXT) ilike '%" + searchWord + "%' " +
                "or pkgdesc ilike '%" + searchWord + "%' " +
                "or pkgname ilike '%" + searchWord + "%') " +
                "AND (?::timestamp IS NULL OR pkgstartdate >= ?::timestamp) " +
                "AND (?::timestamp IS NULL OR pkgenddate <= ?::timestamp)";
        PreparedStatement stmt = conn.prepareStatement(SQL);

        if (start == null) {
            stmt.setNull(1, Types.DATE);
            stmt.setNull(2, Types.DATE);
        } else {
            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(start));
        }

        if (end == null) {
            stmt.setNull(3, Types.DATE);
            stmt.setNull(4, Types.DATE);
        } else {
            stmt.setDate(3, Date.valueOf(end));
            stmt.setDate(4, Date.valueOf(end));
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
                    rs.getString(5),
                    rs.getString(8)
            );
            packages.add(objectPackage);
        }
        return packages;
    }

    public static Integer addPackage(Package objectPackage) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "INSERT INTO packages (pkgname, pkgstartdate, pkgenddate, pkgdesc, pkgbaseprice, pkgagencycommission, image_url, destination) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, objectPackage.getPkgname());
        stmt.setDate(2, Date.valueOf(objectPackage.getPkgstartdate()));
        stmt.setDate(3, Date.valueOf(objectPackage.getPkgenddate()));
        stmt.setString(4, objectPackage.getPkgdesc());
        stmt.setDouble(5, objectPackage.getPkgbaseprice());
        stmt.setDouble(6, objectPackage.getPkgagencycommission());
        stmt.setString(7, "/images/packages/default.jpg");
        stmt.setString(8, objectPackage.getDestination());

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
        stmt.setDate(2, Date.valueOf(objectPackage.getPkgstartdate()));
        stmt.setDate(3, Date.valueOf(objectPackage.getPkgenddate()));
        stmt.setString(4, objectPackage.getPkgdesc());
        stmt.setDouble(5, objectPackage.getPkgbaseprice());
        stmt.setDouble(6, objectPackage.getPkgagencycommission());
        stmt.setInt(7, objectPackage.getId());

        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }

    public static int deletePackage(int packageId) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "DELETE FROM packages WHERE packageid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, packageId);
        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }

}
