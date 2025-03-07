package com.groupfour.travelexpertsfx.models;

/**
 * @Author: Kazi Fattah
 * @Date: 2/2025
 * @Description Java class that handles database operations for CustomersController
 * @To-do-list:
 *
 */

import com.groupfour.travelexpertsfx.database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CustomerDB {

    // VIEW CUSTOMER (R)

    public static ObservableList<CustomerDTO> getCustomer() throws SQLException {
        ObservableList<CustomerDTO> customers = FXCollections.observableArrayList();
        CustomerDTO customer;
        String query = "select customerid, " +
                "concat(custfirstname,' ',custlastname), " +
                "custhomephone, " +
                "custemail, concat(custaddress, ', ', custcity, ', ', custprov), "+
                "concat(agtfirstname, ' ',agtlastname) " +
                "from customers join agents on customers.agentid = agents.agentid " +
                "order by customerid asc";
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            customer = new CustomerDTO(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
            );
            customers.add(customer);
        }
        return customers;
    }

    public static Customer getCustomerById(int customerid) throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "select * from customers where customerid = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, customerid);
        ResultSet rs = preparedStatement.executeQuery();
        Customer customer = null;
        while (rs.next()) {
            customer = new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11),
                    rs.getInt(12)

            );
        }
        return customer;
    }

    // ADD

    public static int addCustomer(Customer customer) throws SQLException {
        Connection conn = DBConnection.getConnection();
        int affectedRows = 0;

        String query = "INSERT INTO customers (custfirstname, " +
                "custlastname, " +
                "custaddress, " +
                "custcity, " +
                "custprov, " +
                "custpostal, " +
                "custcountry, " +
                "custhomephone, " +
                "custbusphone, " +
                "custemail, " +
                "agentid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setString(1, customer.getCustfirstname());
        preparedStatement.setString(2, customer.getCustlastname());
        preparedStatement.setString(3, customer.getCustaddress());
        preparedStatement.setString(4, customer.getCustcity());
        preparedStatement.setString(5, customer.getCustprov());
        preparedStatement.setString(6, customer.getCustpostal());
        preparedStatement.setString(7, customer.getCustcountry());
        preparedStatement.setString(8, customer.getCusthomephone());
        preparedStatement.setString(9, customer.getCustbusphone());
        preparedStatement.setString(10, customer.getCustemail());
        preparedStatement.setInt(11, customer.getAgentid());

        affectedRows = preparedStatement.executeUpdate();
        conn.close();

        return affectedRows;
    }

    // EDIT

    public static int updateCustomer(Customer customer) throws SQLException {
        Connection conn = DBConnection.getConnection();
        int affectedRows = 0;
        String query = "UPDATE customers SET " +
                "custfirstname=?, " +
                "custlastname=?, " +
                "custaddress=?, " +
                "custcity=?, " +
                "custprov=?, " +
                "custpostal=?, " +
                "custcountry=?, " +
                "custhomephone=?, " +
                "custbusphone=?, " +
                "custemail=?, " +
                "agentid=? WHERE customerid=?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setString(1, customer.getCustfirstname());
        preparedStatement.setString(2, customer.getCustlastname());
        preparedStatement.setString(3, customer.getCustaddress());
        preparedStatement.setString(4, customer.getCustcity());
        preparedStatement.setString(5, customer.getCustprov());
        preparedStatement.setString(6, customer.getCustpostal());
        preparedStatement.setString(7, customer.getCustcountry());
        preparedStatement.setString(8, customer.getCusthomephone());
        preparedStatement.setString(9, customer.getCustbusphone());
        preparedStatement.setString(10, customer.getCustemail());
        preparedStatement.setInt(11, customer.getAgentid());
        preparedStatement.setInt(12, customer.getCustomerid());

        affectedRows = preparedStatement.executeUpdate();
        conn.close();


        return affectedRows;
    }

    // DELETE

    public static int deleteCustomer(int customerId) throws SQLException {
        int affectedRows = 0;
        Connection conn = DBConnection.getConnection();
        String sqlStatement = "DELETE FROM customers WHERE customerid=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, customerId);
        affectedRows = preparedStatement.executeUpdate();
        conn.close();
        return affectedRows;
    }

    // SEARCH FUNCTION

    public static ObservableList<CustomerDTO> getCustomerBySearch(String searchword) throws SQLException {
        ObservableList<CustomerDTO> customers = FXCollections.observableArrayList();
        CustomerDTO customer;
        String query = "select customerid, " +
                "concat(custfirstname,' ',custlastname), " +
                "custhomephone, " +
                "custemail, concat(custaddress, ', ', custcity, ', ', custprov), "+
                "concat(agtfirstname, ' ',agtlastname) " +
                "from customers join agents on customers.agentid = agents.agentid " +
                "where custfirstname ilike '%" + searchword + "%' " +
                "or custlastname ilike '%" + searchword + "%' " +
                "or custaddress ilike '%" + searchword + "%' " +
                "or custcity ilike '%" + searchword + "%' " +
                "or custprov ilike '%" + searchword + "%' " +
                "or custpostal ilike '%" + searchword + "%' " +
                "or custemail ilike '%" + searchword + "%' " +
                "or custcountry ilike '%" + searchword + "%' " +
                "or custhomephone ilike '%" + searchword + "%' " +
                "or custbusphone ilike '%" + searchword + "%' " +
                "or agtfirstname ilike '%" + searchword + "%' " +
                "or agtlastname ilike '%" + searchword + "%' " +

                " order by customerid asc";
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            customer = new CustomerDTO(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
            );
            customers.add(customer);
        }
        return customers;

    }

    public static ObservableList<CustomerDTO> getCustomerBySearch(int searchNum) throws SQLException {
        ObservableList<CustomerDTO> customers = FXCollections.observableArrayList();
        CustomerDTO customer;
        String query = "select customerid, " +
                "concat(custfirstname,' ',custlastname), " +
                "custhomephone, " +
                "custemail, concat(custaddress, ', ', custcity, ', ', custprov), "+
                "concat(agtfirstname, ' ',agtlastname) " +
                "from customers join agents on customers.agentid = agents.agentid " +
                "where customerid = "+searchNum+
                " or custhomephone ilike '%" + searchNum + "%' " +
                "or custbusphone ilike '%" + searchNum + "%' " +
                "or custaddress ilike '%" + searchNum + "%' " +
                " order by customerid asc";
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            customer = new CustomerDTO(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
            );
            customers.add(customer);
        }
        return customers;

    }

    public static ObservableList<PastTrips> getTripsByCustomerId(int customerId) throws SQLException {
        ObservableList<PastTrips> trips = FXCollections.observableArrayList();
        PastTrips trip;
        String query = "select bookings.bookingid, " +
                "description, " +
                "destination, " +
                "baseprice+agencycommission, " +
                "ttname, " +
                "classname, " +
                "tripstart, " +
                "tripend " +
                "from bookingdetails " +
                "join bookings on bookingdetails.bookingid=bookings.bookingid " +
                "join triptypes on triptypes.triptypeid = bookings.triptypeid " +
                "join classes on classes.classid = bookingdetails.classid " +
                "where customerid = "+customerId;
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            trip = new PastTrips(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getDate(7),
                    rs.getDate(8)
            );
            trips.add(trip);
        }
        return trips;
    }

    /*


     */


}
