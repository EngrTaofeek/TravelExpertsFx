package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

/**
 * @Author RyanOOSD
 * @Date 2/19/2025
 * @Description Gather statistics from the database
 */

public class StatisticsDB {
    public static Connection getConnection() {
        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return conn;
    }

    public static long totalSalesUntilDatePerAgent(int agentId, LocalDate date) throws SQLException {
        // Declare long and extract date
        long salesUntil = 0;
        Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());

        Connection conn = getConnection();
        String sql = "SELECT COUNT(*) FROM bookings" +
                " INNER JOIN customers ON bookings.customerid = customers.customerid" +
                " WHERE agentid = ? AND bookingdate <= ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, agentId);
        stmt.setTimestamp(2, timestamp);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            salesUntil = rs.getLong(1);
        }
        stmt.close();
        return salesUntil;
    }

    public static BigDecimal totalCommissionUntilDatePerAgent(int agentId, LocalDate date) throws SQLException {
        BigDecimal commission = BigDecimal.ZERO;
        Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());

        Connection conn = getConnection();
        String sql = "SELECT SUM(agencycommission) FROM bookingdetails" +
                " INNER JOIN bookings ON bookingdetails.bookingid = bookings.bookingid" +
                " INNER JOIN customers ON bookings.customerid = customers.customerid" +
                " WHERE agentid = ? AND bookingdate <= ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, agentId);
        stmt.setTimestamp(2, timestamp);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            commission = rs.getBigDecimal(1);
        }
        stmt.close();
        return commission;
    }

    public static long totalSalesPerAgency(int agencyId) throws SQLException {
        long salesPerAgency = 0;

        Connection conn = getConnection();
        String sql = "SELECT COUNT(*) FROM bookings" +
                " INNER JOIN customers ON bookings.customerid = customers.customerid" +
                " INNER JOIN agents ON customers.agentid = agents.agentid" +
                " WHERE agentid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, agencyId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            salesPerAgency = rs.getLong(1);
        }
        stmt.close();
        return salesPerAgency;
    }

    public static long totalSales() throws SQLException {
        long bookings = 0;

        Connection conn = getConnection();
        String sql = "SELECT COUNT(*) FROM bookings";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            bookings = rs.getLong(1);
        }
        stmt.close();
        return bookings;
    }

    public static long totalSalesPerCustomer(int customerId) throws SQLException {
        long salesPerCustomer = 0;

        Connection conn = getConnection();
        String sql = "SELECT COUNT(*) FROM bookings" +
                " WHERE customerid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            salesPerCustomer = rs.getLong(1);
        }
        stmt.close();
        return salesPerCustomer;
    }
}
