package com.groupfour.travelexpertsfx.utils;

import com.groupfour.travelexpertsfx.database.DBConnection;
import com.groupfour.travelexpertsfx.models.Product;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;

/**
 * @Author: Kazi Fattah
 * @Date: 3/2025
 * @Description: Input Validation
 * @To-do-list:
 *

 *
 *
 */

public class Validator_KF {
    public static String isEmpty(TextField textField, String textFieldName) {
        String errorMessage = "";
        if (textField.getText().equals("")) {
            errorMessage += "The field " + textFieldName + " cannot be left blank!\n";
        }

        return errorMessage;
    }

    public static String productAlreadyExists(TextField textField) {
        String errorMessage = "";
        String productName = textField.getText();
        Product product = null;
        ArrayList<Product> products =  new ArrayList<>();
        String query = "select * from products where prodname ilike '" + productName + "'";
        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                product = new Product(rs.getInt(1), rs.getString(2));
            }
            if (product!=null) {
                products.add(product);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        if (!products.isEmpty()) {
            errorMessage += "The product " + productName + " already exists!\n";
        }


        return  errorMessage;
    }
}
