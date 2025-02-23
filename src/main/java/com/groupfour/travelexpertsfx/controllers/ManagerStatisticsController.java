/**
 * Sample Skeleton for 'ManagerStatistics.fxml' Controller Class
 */

package com.groupfour.travelexpertsfx.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import com.groupfour.travelexpertsfx.models.AgentDTO;
import com.groupfour.travelexpertsfx.models.StatisticsDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

public class ManagerStatisticsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="brcStats"
    private BarChart<String, Number> brcStats; // Value injected by FXMLLoader

    @FXML // fx:id="btnChartAdd"
    private Button btnChartAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnChartClear"
    private Button btnChartClear; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSelectAgencies"
    private ComboBox<?> cmbSelectAgencies; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSelectAgents"
    private ComboBox<AgentDTO> cmbSelectAgents; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSelectCustomers"
    private ComboBox<?> cmbSelectCustomers; // Value injected by FXMLLoader

    @FXML // fx:id="cmbStatsView"
    private ComboBox<Map.Entry<String, Integer>> cmbStatsView; // Value injected by FXMLLoader

    @FXML // fx:id="dtpMaxDate"
    private DatePicker dtpMaxDate; // Value injected by FXMLLoader

    @FXML // fx:id="haxBarStats"
    private CategoryAxis haxBarStats; // Value injected by FXMLLoader

    @FXML // fx:id="haxLineStats"
    private CategoryAxis haxLineStats; // Value injected by FXMLLoader

    @FXML // fx:id="lblSelect"
    private Label lblSelect; // Value injected by FXMLLoader

    @FXML // fx:id="linStats"
    private LineChart<?, ?> linStats; // Value injected by FXMLLoader

    @FXML // fx:id="pieStats"
    private PieChart pieStats; // Value injected by FXMLLoader

    @FXML // fx:id="vaxBarStats"
    private NumberAxis vaxBarStats; // Value injected by FXMLLoader

    @FXML // fx:id="vaxLineStats"
    private NumberAxis vaxLineStats; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert brcStats != null : "fx:id=\"brcStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert btnChartAdd != null : "fx:id=\"btnChartAdd\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert btnChartClear != null : "fx:id=\"btnChartClear\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert cmbSelectAgencies != null : "fx:id=\"cmbSelectAgencies\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert cmbSelectAgents != null : "fx:id=\"cmbSelectAgents\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert cmbSelectCustomers != null : "fx:id=\"cmbSelectCustomers\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert cmbStatsView != null : "fx:id=\"cmbStatsView\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert dtpMaxDate != null : "fx:id=\"dtpMaxDate\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert haxBarStats != null : "fx:id=\"haxBarStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert haxLineStats != null : "fx:id=\"haxLineStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert lblSelect != null : "fx:id=\"lblSelect\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert linStats != null : "fx:id=\"linStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert pieStats != null : "fx:id=\"pieStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert vaxBarStats != null : "fx:id=\"vaxBarStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert vaxLineStats != null : "fx:id=\"vaxLineStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";

        // On load, populate the statistic combobox
        List<Map.Entry<String, Integer>> statList = new ArrayList<>();
        statList.add(new AbstractMap.SimpleEntry<>("Sales Per Agent", 1));
        statList.add(new AbstractMap.SimpleEntry<>("Commissions Per Agent", 2));
        statList.add(new AbstractMap.SimpleEntry<>("Sales Per Agency", 3));
        statList.add(new AbstractMap.SimpleEntry<>("Sales Per Customer", 4));
        statList.add(new AbstractMap.SimpleEntry<>("Total Bookings", 5));
        ObservableList<Map.Entry<String, Integer>> showList = FXCollections.observableArrayList(statList);
        // Use AbstractMap to store key value pairs and format, set default statistic view
        cmbStatsView.setItems(showList);
        cmbStatsView.setConverter(formatStatList());
        cmbStatsView.setValue(showList.getFirst());

        // Format the view to show agent sales on load
        dtpMaxDate.setValue(LocalDate.now());
        formatView(cmbStatsView.getSelectionModel().getSelectedItem().getValue());

        // Create event listener for stat view combobox
        cmbStatsView.setOnAction(event -> {
            Map.Entry<String, Integer> selectedStat = cmbStatsView.getSelectionModel().getSelectedItem();
            formatView(selectedStat.getValue());
        });

        // Create event listener for add to chart button
        btnChartAdd.setOnAction(event -> {
            // Get the chosen stat view
            Map.Entry<String, Integer> selectedStat = cmbStatsView.getSelectionModel().getSelectedItem();
            addToChart(selectedStat.getValue());
        });

        // Create event listener for clear chart button
        btnChartClear.setOnAction(event -> {
            brcStats.getData().clear();
            pieStats.getData().clear();
            linStats.getData().clear();
        });
    }

    private void addToChart(Integer value) {
        switch (value) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
        }
    }

    private void formatView(Integer value) {
        switch (value) {
            case 1:
                // Hide other comboboxes and charts
                pieStats.setVisible(false);
                linStats.setVisible(false);
                cmbSelectAgencies.setVisible(false);
                cmbSelectCustomers.setVisible(false);
                // Update labels
                lblSelect.setText("Select Agent:");
                // Populate and format selection combobox
                try {
                    ObservableList<AgentDTO> agentList = FXCollections.observableArrayList(StatisticsDB.agentsList());
                    // Get first agent
                    AgentDTO agent = agentList.getFirst();
                    long agentSales = StatisticsDB.totalSalesPerAgent(agent.getAgentId(), dtpMaxDate.getValue());
                    cmbSelectAgents.setItems(agentList);
                    cmbSelectAgents.setValue(agent);
                    // Get date and convert to String
                    String selectedDate = dtpMaxDate.getValue().toString();
                    // Format the chart
                    haxBarStats.setLabel("Date");
                    vaxBarStats.setLabel("Sales Per Agent");
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    series.setName(agent.getAgentFirstName() + " " + agent.getAgentLastName());
                    // Add datapoint for agent's first sale
                    LocalDate firstSale = StatisticsDB.agentFirstSaleDate(agent.getAgentId());
                    series.getData().add(new XYChart.Data<>(firstSale.toString(), 1));
                    // Add datapoint for date in datetimepicker
                    series.getData().add(new XYChart.Data<>(selectedDate, agentSales));
                    brcStats.getData().add(series);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            case 2:
            case 3:
            case 4:
            case 5:
        }
    }

    private StringConverter<Map.Entry<String, Integer>> formatStatList() {
        return new StringConverter<>() {
            @Override
            public String toString(Map.Entry<String, Integer> entry) {
                if (entry == null) {
                    return "";
                }
                return entry.getKey();
            }

            @Override
            public Map.Entry<String, Integer> fromString(String s) {
                return null;
            }
        };
    }
}