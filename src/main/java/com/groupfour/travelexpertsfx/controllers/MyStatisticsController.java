/**
 * Sample Skeleton for 'MyStatistics.fxml' Controller Class
 */

package com.groupfour.travelexpertsfx.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.StatisticsDB;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javax.xml.stream.Location;

public class MyStatisticsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="dtpMaxDate"
    private DatePicker dtpMaxDate; // Value injected by FXMLLoader

    @FXML // fx:id="lblBookingAmt"
    private Label lblBookingAmt; // Value injected by FXMLLoader

    @FXML // fx:id="lblCommissionAmt"
    private Label lblCommissionAmt; // Value injected by FXMLLoader

    LocalDate date = LocalDate.now();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert dtpMaxDate != null : "fx:id=\"dtpMaxDate\" was not injected: check your FXML file 'MyStatistics.fxml'.";
        assert lblBookingAmt != null : "fx:id=\"lblBookingAmt\" was not injected: check your FXML file 'MyStatistics.fxml'.";
        assert lblCommissionAmt != null : "fx:id=\"lblCommissionAmt\" was not injected: check your FXML file 'MyStatistics.fxml'.";

        // On load, set today's date and load stats
        dtpMaxDate.setValue(date);
        updateStats(date);

        // Define event listener for DatePicker to update stats depending on date
        dtpMaxDate.setOnAction(event -> {
            date = dtpMaxDate.getValue();
            updateStats(date);
        });
    }

    private void updateStats(LocalDate date) {
        long bookings = 0;
        BigDecimal commissions;

        // Need to update methods to pull id from currently logged-in user
        try {
            bookings = StatisticsDB.totalSalesUntilDatePerAgent(1, date);
            commissions = StatisticsDB.totalCommissionUntilDatePerAgent(1, date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (bookings != 0) {
            lblBookingAmt.setText(Long.toString(bookings));
            lblCommissionAmt.setText(NumberFormat.getCurrencyInstance().format(commissions));
        } else {
            lblBookingAmt.setText("0");
            lblCommissionAmt.setText("0");
        }
    }
}
