package com.groupfour.travelexpertsfx.models;

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
                "custemail, " +
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
                    rs.getString(5)
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

    /*

     */


}
