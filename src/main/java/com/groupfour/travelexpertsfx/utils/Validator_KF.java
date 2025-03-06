package com.groupfour.travelexpertsfx.utils;

import com.groupfour.travelexpertsfx.database.DBConnection;
import com.groupfour.travelexpertsfx.models.Product;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            errorMessage += "The \"" + textFieldName + "\" field cannot be left blank.\n";
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
            System.out.println(e.getMessage());
        }

        if (!products.isEmpty()) {
            errorMessage += "The product " + productName + " already exists.\n";
        }

        return  errorMessage;
    }

    public static String isValidPhoneNumber(TextField textField,  String textFieldName) {
        String errorMessage = "";
        // Define the regex pattern for a 10-digit phone number
        String regex = "^\\d{10}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(textField.getText());

        if(!matcher.matches()) {
            errorMessage = "Entered "+ textFieldName +" is not a valid phone number." +
                    "\nPhone Number must be a valid 10 digit number.";
        }
        return  errorMessage;
    }

    public static String isValidEmail(TextField textField) {
        String errorMessage = "";
        // Define the regex pattern for email address
        String regex = "^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(textField.getText());

        if(!matcher.matches()) {
            errorMessage =  textField.getText() +" is not a valid Email" +
                    "\nEmail address must be in format: johndoe@email.com." +
                    "\nValid special characters for email include \".\" and \"_\"";
        }
        return  errorMessage;
    }

    public static String isValidPostalCode(TextField textField) {
        String errorMessage = "";
        // Define the regex pattern for email address
        String regex = "^[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(textField.getText());

        if(!matcher.matches()) {
            errorMessage =  textField.getText() +" is not a valid Postal Code." +
                    "\nPostal Code must be in format: A1A 1A1";
        }
        return  errorMessage;
    }

    public static String isValidProvince(TextField textField) {
        String errorMessage = "";
        String[] provinceCodes = {
                "AB", // Alberta
                "BC", // British Columbia
                "MB", // Manitoba
                "NB", // New Brunswick
                "NL", // Newfoundland and Labrador
                "NS", // Nova Scotia
                "ON", // Ontario
                "PE", // Prince Edward Island
                "QC", // Quebec
                "SK", // Saskatchewan
                "NT", // Northwest Territories
                "NU", // Nunavut
                "YT"  // Yukon
        };
        boolean isValidProvince = false;
        for (String provinceCode : provinceCodes) {
            if (provinceCode.equalsIgnoreCase(textField.getText())) {
                isValidProvince = true;
            }
        }

        if (!isValidProvince) {
            errorMessage =  textField.getText() +" is not a valid province!\n" +
                    "Province must be a valid Canadian province and formatted in its 2 character province code.";
        }
        return  errorMessage;
    }
}
