package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;

/**
 * @Author RyanOOSD
 * @Date 2/19/2025
 * @Description Gather statistics from the database as well as lists for combo boxes
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

    public static long totalSalesPerAgent(int agentId, LocalDate date) throws SQLException {
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

    public static LocalDate agentFirstSaleDate(int agentId) throws SQLException {
        Timestamp firstSale = Timestamp.from(Instant.now());

        Connection conn = getConnection();
        String sql = "SELECT bookingdate FROM bookingdetails" +
                " INNER JOIN bookings ON bookingdetails.bookingid = bookings.bookingid" +
                " INNER JOIN customers ON bookings.customerid = customers.customerid" +
                " WHERE agentid = ?" +
                " ORDER BY bookingdate ASC" +
                " LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, agentId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            firstSale = rs.getTimestamp(1);
        }
        stmt.close();

        // Convert to LocalDate before returning
        return firstSale.toLocalDateTime().toLocalDate();
    }

    public static BigDecimal totalCommissionPerAgent(int agentId, LocalDate date) throws SQLException {
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

    public static BigDecimal agentFirstCommissionValue(int agentId) throws SQLException {
        BigDecimal commission = BigDecimal.ZERO;

        Connection conn = getConnection();
        String sql = "SELECT agencycommission FROM bookingdetails" +
                " INNER JOIN bookings ON bookingdetails.bookingid = bookings.bookingid" +
                " INNER JOIN customers ON bookings.customerid = customers.customerid" +
                " WHERE agentid = ?" +
                " ORDER BY bookingdate ASC" +
                " LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, agentId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            commission = rs.getBigDecimal(1);
        }
        stmt.close();
        return commission;
    }

    public static long totalSales(LocalDate date) throws SQLException {
        long sales = 0;
        Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());

        Connection conn = getConnection();
        String sql = "SELECT COUNT(*) FROM bookings" +
                " WHERE bookingdate <= ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setTimestamp(1, timestamp);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            sales = rs.getLong(1);
        }
        stmt.close();
        return sales;
    }

    public static long totalSalesPerAgency(int agencyId, LocalDate date) throws SQLException {
        long salesPerAgency = 0;
        Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());

        Connection conn = getConnection();
        String sql = "SELECT COUNT(*) FROM bookings" +
                " INNER JOIN customers ON bookings.customerid = customers.customerid" +
                " INNER JOIN agents ON customers.agentid = agents.agentid" +
                " WHERE agents.agencyid = ? AND bookingdate <= ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, agencyId);
        stmt.setTimestamp(2, timestamp);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            salesPerAgency = rs.getLong(1);
        }
        stmt.close();
        return salesPerAgency;
    }

    public static long totalSalesPerCustomer(int customerId, LocalDate date) throws SQLException {
        long salesPerCustomer = 0;
        Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());

        Connection conn = getConnection();
        String sql = "SELECT COUNT(*) FROM bookings" +
                " WHERE customerid = ? AND bookingdate <= ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, customerId);
        stmt.setTimestamp(2, timestamp);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            salesPerCustomer = rs.getLong(1);
        }
        stmt.close();
        return salesPerCustomer;
    }

    public static LocalDate customerFirstSaleDate(int customerId) throws SQLException {
        Timestamp firstSale = Timestamp.from(Instant.now());

        Connection conn = getConnection();
        String sql = "SELECT bookingdate FROM bookings" +
                " WHERE bookings.customerid = ?" +
                " ORDER BY bookingdate ASC" +
                " LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            firstSale = rs.getTimestamp(1);
        }
        stmt.close();

        // Convert to LocalDate before returning
        return firstSale.toLocalDateTime().toLocalDate();
    }

    public static ObservableList<AgentDTO> agentsList() throws SQLException {
        ObservableList<AgentDTO> agents = FXCollections.observableArrayList();
        AgentDTO agent;

        Connection conn = getConnection();
        String sql = "SELECT agentid, agtfirstname, agtmiddleinitial, agtlastname, agncycity FROM agents" +
                " INNER JOIN agencies ON agents.agencyid = agencies.agencyid" +
                " ORDER BY agents.agencyid ASC";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            agent = new AgentDTO(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
            );
            agents.add(agent);
        }
        stmt.close();
        return agents;
    }

    public static ObservableList<AgencyDTO> agenciesList() throws SQLException {
        ObservableList<AgencyDTO> agencies = FXCollections.observableArrayList();
        AgencyDTO agency;

        Connection conn = getConnection();
        String sql = "SELECT agencyid, agncyaddress, agncycity FROM agencies";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            agency = new AgencyDTO(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3)
            );
            agencies.add(agency);
        }
        stmt.close();
        return agencies;
    }

    public static ObservableList<CustomerDTO> customersList() throws SQLException {
        ObservableList<CustomerDTO> customers = FXCollections.observableArrayList();
        CustomerDTO customer;

        Connection conn = getConnection();
        String sql = "SELECT customerid, custfirstname, custlastname FROM customers" +
                " ORDER BY custfirstname ASC";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            customer = new CustomerDTO(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3)
            );
            customers.add(customer);
        }
        stmt.close();
        return customers;
    }
}
