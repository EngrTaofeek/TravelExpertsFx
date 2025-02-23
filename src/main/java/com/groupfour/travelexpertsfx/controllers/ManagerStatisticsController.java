/**
 * Sample Skeleton for 'ManagerStatistics.fxml' Controller Class
 */

package com.groupfour.travelexpertsfx.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import com.groupfour.travelexpertsfx.models.*;
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
    private ComboBox<AgencyDTO> cmbSelectAgencies; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSelectAgents"
    private ComboBox<AgentDTO> cmbSelectAgents; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSelectCustomers"
    private ComboBox<CustomerDTO> cmbSelectCustomers; // Value injected by FXMLLoader

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
    private LineChart<String, Number> linStats; // Value injected by FXMLLoader

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

        // Initialize combo boxes for selecting agents, agencies and customers
        loadAgents();
        loadAgencies();
        loadCustomers();

        // Disable chart animations to prevent errors with data display
        brcStats.setAnimated(false);
        pieStats.setAnimated(false);
        linStats.setAnimated(false);

        // Format the view to show agent sales on load
        dtpMaxDate.setValue(LocalDate.now());
        formatView(cmbStatsView.getSelectionModel().getSelectedItem().getValue());

        // Create event listener for stat view combobox
        cmbStatsView.setOnAction(event -> {
            Map.Entry<String, Integer> selectedStat = cmbStatsView.getSelectionModel().getSelectedItem();
            // Clear charts and combobox on change
            clearCharts();
            formatView(selectedStat.getValue());
        });

        // Create event listener for add to chart button
        btnChartAdd.setOnAction(event -> {
            // Get the chosen stat view
            Map.Entry<String, Integer> selectedStat = cmbStatsView.getSelectionModel().getSelectedItem();
            addToChart(selectedStat.getValue());
        });

        // Create event listener for clear chart button
        btnChartClear.setOnAction(event -> clearCharts());
    }

    private void clearCharts() {
        brcStats.getData().clear();
        pieStats.getData().clear();
        linStats.getData().clear();
    }

    private void addToChart(Integer value) {
        switch (value) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    private void formatView(Integer value) {
        switch (value) {
            case 1:
                // Hide other combo boxes and charts
                brcStats.setVisible(true);
                pieStats.setVisible(false);
                linStats.setVisible(false);
                cmbSelectAgencies.setVisible(false);
                cmbSelectCustomers.setVisible(false);
                // Update labels
                lblSelect.setText("Select Agent:");
                // Reset date picker to today
                dtpMaxDate.setValue(LocalDate.now());
                try {
                    // Get first agent
                    cmbSelectAgents.setValue(cmbSelectAgents.getItems().getFirst());
                    AgentDTO agent = cmbSelectAgents.getItems().getFirst();
                    // Get agent's sales until today's date
                    long agentSales = StatisticsDB.totalSalesPerAgent(agent.getAgentId(), dtpMaxDate.getValue());
                    // Convert date to string
                    String selectedDate = dtpMaxDate.getValue().toString();
                    // Format the chart
                    haxBarStats.setLabel("Date");
                    vaxBarStats.setLabel("Sales Per Agent");
                    XYChart.Series<String, Number> totalSalesSeries = new XYChart.Series<>();
                    totalSalesSeries.setName(agent.getAgentFirstName() + " " + agent.getAgentLastName());
                    // Add datapoint for agent's first sale
                    LocalDate firstSale = StatisticsDB.agentFirstSaleDate(agent.getAgentId());
                    totalSalesSeries.getData().add(new XYChart.Data<>(firstSale.toString(), 1));
                    // Add datapoint for today's date in datetimepicker
                    totalSalesSeries.getData().add(new XYChart.Data<>(selectedDate, agentSales));
                    brcStats.getData().add(totalSalesSeries);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                // Hide other combo boxes and charts
                linStats.setVisible(true);
                brcStats.setVisible(false);
                pieStats.setVisible(false);
                cmbSelectAgencies.setVisible(false);
                cmbSelectCustomers.setVisible(false);
                // Update labels
                lblSelect.setText("Select Agent:");
                // Reset date picker to today's date
                dtpMaxDate.setValue(LocalDate.now());
                try {
                    // Get first agent
                    cmbSelectAgents.setValue(cmbSelectAgents.getItems().getFirst());
                    AgentDTO agent = cmbSelectAgents.getItems().getFirst();
                    // Convert date to string
                    String today = dtpMaxDate.getValue().toString();
                    // Get agent's commission value until today's date
                    BigDecimal agentCommission = StatisticsDB.totalCommissionPerAgent(agent.getAgentId(), dtpMaxDate.getValue());
                    // Format the chart
                    haxLineStats.setLabel("Date");
                    vaxLineStats.setLabel("Commission Value Per Agent");
                    XYChart.Series<String, Number> totalCommissionSeries = new XYChart.Series<>();
                    totalCommissionSeries.setName(agent.getAgentFirstName() + " " + agent.getAgentLastName());
                    // Add datapoint for agent's first sale
                    LocalDate firstSale = StatisticsDB.agentFirstSaleDate(agent.getAgentId());
                    String convertFirst = firstSale.toString();
                    BigDecimal firstCommission = StatisticsDB.agentFirstCommissionValue(agent.getAgentId());
                    totalCommissionSeries.getData().add(new XYChart.Data<>(convertFirst, firstCommission));
                    // Add datapoint for today's date
                    totalCommissionSeries.getData().add(new XYChart.Data<>(today, agentCommission));
                    linStats.getData().add(totalCommissionSeries);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    private void loadCustomers() {
        ObservableList<CustomerDTO> customerList = null;
        try {
            customerList = FXCollections.observableArrayList(StatisticsDB.customersList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbSelectCustomers.setItems(customerList);
    }

    private void loadAgencies() {
        ObservableList<AgencyDTO> agencyList = null;
        try {
            agencyList = FXCollections.observableArrayList(StatisticsDB.agenciesList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbSelectAgencies.setItems(agencyList);
    }

    private void loadAgents() {
        ObservableList<AgentDTO> agentList = null;
        try {
            agentList = FXCollections.observableArrayList(StatisticsDB.agentsList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbSelectAgents.setItems(agentList);
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