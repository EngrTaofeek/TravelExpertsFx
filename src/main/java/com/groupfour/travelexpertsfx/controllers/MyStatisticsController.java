package com.groupfour.travelexpertsfx.controllers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;

import com.groupfour.travelexpertsfx.models.CurrentUser;
import com.groupfour.travelexpertsfx.models.StatisticsDB;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class MyStatisticsController {

    @FXML private DatePicker dtpMaxDate;
    @FXML private Label lblBookingAmt;
    @FXML private Label lblCommissionAmt;

    // Initialize a LocalDate object with its value set to today
    private LocalDate date = LocalDate.now();

    @FXML
    void initialize() {
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

        try {
            // Get id of logged-in user
            int currentId = StatisticsDB.resolveAgentId(CurrentUser.getEmail());
            bookings = StatisticsDB.totalSalesPerAgent(currentId, date);
            commissions = StatisticsDB.totalCommissionPerAgent(currentId, date);
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
