package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
