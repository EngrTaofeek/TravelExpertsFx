package com.groupfour.travelexpertsfx.controllers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.groupfour.travelexpertsfx.models.CurrentUser;
import com.groupfour.travelexpertsfx.models.StatisticsDB;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;

public class MyStatisticsController {

    @FXML private BarChart<String, Number> brcStats;
    @FXML private CategoryAxis haxBarStats;
    @FXML private DatePicker dtpMaxDate;
    @FXML private NumberAxis vaxBarStats;

    // Initialize a LocalDate object with its value set to today
    private LocalDate date = LocalDate.now();

    // Create flag to determine if chart is being loaded for the first time
    private boolean isFirstLoad = true;

    @FXML
    void initialize() {
        // On load, set today's date and load stats
        dtpMaxDate.setValue(date);
        brcStats.setAnimated(false);
        loadStats(date);

        // Define event listener for DatePicker to update stats depending on date
        dtpMaxDate.setOnAction(event -> {
            date = dtpMaxDate.getValue();
            loadStats(date);
        });
    }

    private void loadStats(LocalDate date) {
        try {
            // Get the agent's statistics
            int currentAgentId = StatisticsDB.resolveAgentId(CurrentUser.getEmail());
            long bookings = StatisticsDB.totalSalesPerAgent(currentAgentId, date);
            BigDecimal commissions = StatisticsDB.totalCommissionPerAgent(currentAgentId, date);
            String dateString = date.toString();
            if (isFirstLoad) {
                // Setup bar chart
                vaxBarStats.setLabel("Number of Sales");
                haxBarStats.setLabel("Date");
                 // Create a new series
                XYChart.Series<String, Number> salesSeries = new XYChart.Series<>();
                salesSeries.setName("Your Sales");
                // Add datapoint for today's date
                XYChart.Data<String, Number> data = new XYChart.Data<>(dateString, bookings);
                salesSeries.getData().add(data);
                brcStats.getData().add(salesSeries);
                // Add tooltip for initial date added
                Tooltip tooltip = new Tooltip("Your Sales for " + dateString + ": " + bookings + "\nCommission: " + NumberFormat.getCurrencyInstance().format(commissions));
                tooltip.setStyle("-fx-font-size: 14px");
                Tooltip.install(data.getNode(), tooltip);
                // Change the flag
                isFirstLoad = false;
            } else {
                checkDuplicateDate(brcStats, dateString, bookings, commissions);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkDuplicateDate(BarChart<String, Number> chart, String dateString, long bookings, BigDecimal commissions) {
        // Get the series from the chart
        XYChart.Series<String, Number> userSeries = chart.getData().getFirst();
        boolean dateAlreadyAdded = false;
        // Check if date has already been added to the series
        for (XYChart.Data<String, Number> data : userSeries.getData()) {
            if (data.getXValue().equals(dateString)) {
                displayMessage();
                dateAlreadyAdded = true;
                break;
            }
        }
        if (!dateAlreadyAdded) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(dateString, bookings);
            userSeries.getData().add(data);
            // Add tooltip with commission data
            Tooltip tooltip = new Tooltip("Your Sales for " + dateString + ": " + bookings + "\nCommission: " + NumberFormat.getCurrencyInstance().format(commissions));
            tooltip.setStyle("-fx-font-size: 14px");
            Tooltip.install(data.getNode(), tooltip);
            // Sort the dates
            sortDataByDate(userSeries);
        }
    }

    private void sortDataByDate(XYChart.Series<String, Number> userSeries) {
        // Specify date format for parser
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Get the data from the series and convert it into a list
        List<XYChart.Data<String, Number>> dateList = new ArrayList<>(userSeries.getData());
        dateList.sort(Comparator.comparing(data -> LocalDate.parse(data.getXValue(), formatter)));
        // Refresh the data
        userSeries.getData().setAll(dateList);
    }

    private void displayMessage() {
        Alert.AlertType type = Alert.AlertType.WARNING;
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setContentText("This date has already been added to the chart!");
        alert.showAndWait();
    }
}
