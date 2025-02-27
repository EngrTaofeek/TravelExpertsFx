package com.groupfour.travelexpertsfx.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.groupfour.travelexpertsfx.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class ManagerStatisticsController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private BarChart<String, Number> brcStats;
    @FXML private Button btnChartAdd, btnChartClear;
    @FXML private ComboBox<AgencyStatsDTO> cmbSelectAgencies;
    @FXML private ComboBox<AgentStatsDTO> cmbSelectAgents;
    @FXML private ComboBox<CustomerStatsDTO> cmbSelectCustomers;
    @FXML private ComboBox<Map.Entry<String, Integer>> cmbStatsView;
    @FXML private DatePicker dtpMaxDate;
    @FXML private CategoryAxis haxBarStats, haxLineStats;
    @FXML private Label lblSelect, lblCumulativeSales;
    @FXML private LineChart<String, Number> linStats;
    @FXML private PieChart pieStats;
    @FXML private NumberAxis vaxBarStats, vaxLineStats;

    @FXML
    void initialize() {
        // On load, populate the statistic combobox
        List<Map.Entry<String, Integer>> statList = new ArrayList<>();
        statList.add(new AbstractMap.SimpleEntry<>("Sales Per Agent", 1));
        statList.add(new AbstractMap.SimpleEntry<>("Commissions Per Agent", 2));
        statList.add(new AbstractMap.SimpleEntry<>("Sales Per Agency", 3));
        statList.add(new AbstractMap.SimpleEntry<>("Sales Per Customer", 4));
        statList.add(new AbstractMap.SimpleEntry<>("Total Sales", 5));
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

        // Create event listener for DatePicker to only trigger when viewing pie graph
        dtpMaxDate.setOnAction(event -> {
            Map.Entry<String, Integer> selectedStat = cmbStatsView.getSelectionModel().getSelectedItem();
            LocalDate date = dtpMaxDate.getValue();
            if (selectedStat.getValue() == 3) {
                pieStats.getData().clear();
                pieStats.setTitle("Sales Per Agency (Until " + date + ")");
                updateCumulativeLabel(date);
            }
        });
    }

    private void clearCharts() {
        brcStats.getData().clear();
        haxBarStats.getCategories().clear();
        pieStats.getData().clear();
        linStats.getData().clear();
        haxLineStats.getCategories().clear();
    }

    private void addToChart(Integer value) {
        // Get the selected date and store it separately as a string
        LocalDate date = dtpMaxDate.getValue();
        String stringDate = date.toString();
        // Get the agent selected in the combobox
        AgentStatsDTO agent = cmbSelectAgents.getSelectionModel().getSelectedItem();
        String agentName = agent.toString();
        switch (value) {
            case 1:
                long sales = 0;
                // Get the agent's sales
                try {
                    sales = StatisticsDB.totalSalesPerAgent(agent.getAgentId(), date);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                checkXYSeriesDuplicates(brcStats, agentName, stringDate, sales);
                break;
            case 2:
                BigDecimal commission = BigDecimal.ZERO;
                // Get the agent's commission
                try {
                    commission = StatisticsDB.totalCommissionPerAgent(agent.getAgentId(), date);
                    // If the agent has no commission, set to zero
                    if (commission == null) {
                        commission = BigDecimal.ZERO;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                checkXYSeriesDuplicates(linStats, agentName, stringDate, commission);
                break;
            case 3:
                // Get the agency selected in the combobox
                AgencyStatsDTO agency = cmbSelectAgencies.getSelectionModel().getSelectedItem();
                String agencyName = agency.toString();
                break;
            case 4:
                // Get the customer selected in the combobox
                CustomerStatsDTO customer = cmbSelectCustomers.getSelectionModel().getSelectedItem();
                String customerName = customer.toString();
                long bookings = 0;
                // Get sales per customer
                try {
                    bookings = StatisticsDB.totalSalesPerCustomer(customer.getCustomerId(), date);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                checkXYSeriesDuplicates(brcStats, customerName, stringDate, bookings);
                break;
            case 5:
                long totalSales = 0;
                // Get all sales
                try {
                    totalSales = StatisticsDB.totalSales(date);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                checkXYSeriesDuplicates(brcStats, "Total Sales", stringDate, totalSales);
                break;
        }
    }

    private void checkXYSeriesDuplicates(XYChart<String, Number> chart, String seriesName, String stringDate, Number seriesData) {
        // Initialize a new series and a long
        XYChart.Series<String, Number> findSeries = null;
        // Check if the agent's data series has already been added to the chart
        for (XYChart.Series<String, Number> series : chart.getData()) {
            if (series.getName().equals(seriesName)) {
                findSeries = series;
                break;
            }
        }
        // Check if the date selected already exists in the series only if the series exists
        if (findSeries != null) {
            boolean dataExists = false;
            for (XYChart.Data<String, Number> data : findSeries.getData()) {
                if (data.getXValue().equals(stringDate)) {
                    displayMessage(Alert.AlertType.ERROR, stringDate + " for " + seriesName + " has already been added to the chart!");
                    dataExists = true;
                    break;
                }
            }
            if (!dataExists) {
                // If the series exists but the selected date has not yet been added. add it
                findSeries.getData().add(new XYChart.Data<>(stringDate, seriesData));
                // Call method to re-sort the data of the series
                sortDataByDate(chart);
            }
        } else {
            // If a series for the agent does not exist, create a new one and add data before adding to the chart
            findSeries = new XYChart.Series<>();
            findSeries.setName(seriesName);
            findSeries.getData().add(new XYChart.Data<>(stringDate, seriesData));
            chart.getData().add(findSeries);
            // Call method to re-sort the data of the series
            sortDataByDate(chart);
        }
    }

    private void sortDataByDate(XYChart<String, Number> chart) {
        // Use a dummy category to force a refresh of x-axis labels before sorting
        if (chart instanceof BarChart) {
            haxBarStats.setCategories(FXCollections.observableArrayList("dummy"));
        } else {
            haxLineStats.setCategories(FXCollections.observableArrayList("dummy"));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Sort the data in each series
        for (XYChart.Series<String, Number> series : chart.getData()) {
            // Get each data object and convert it to a list
            List<XYChart.Data<String, Number>> dataList = new ArrayList<>(series.getData());
            // Re-sort the dates
            dataList.sort(Comparator.comparing(data -> LocalDate.parse(data.getXValue(), formatter)));
            // Refresh the data
            series.getData().setAll(dataList);
        }

        // Sort all the dates from all the series
        TreeSet<String> datesToSort = new TreeSet<>(Comparator.comparing(dateStr -> LocalDate.parse(dateStr, formatter)));
        for (XYChart.Series<String, Number> series : chart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                datesToSort.add(data.getXValue());
            }
        }

        // Convert back to an ObservableList
        ObservableList<String> sortedDates = FXCollections.observableArrayList(datesToSort);
        if (chart instanceof BarChart) {
            haxBarStats.setCategories(sortedDates);
        } else {
            // Manually purge and re-add the data to show data in the LineChart
            ObservableList<XYChart.Series<String, Number>> tempList = FXCollections.observableArrayList(chart.getData());
            chart.getData().clear();
            chart.getData().addAll(tempList);
            // Disable auto ranging before setting the sorted categories
            haxLineStats.setAutoRanging(false);
            haxLineStats.setCategories(sortedDates);
            haxLineStats.setAutoRanging(true);
        }
        chart.layout();
    }

    private void formatView(Integer value) {
        switch (value) {
            case 1:
                // Hide other combo boxes and charts
                brcStats.setVisible(true);
                cmbSelectAgents.setVisible(true);
                pieStats.setVisible(false);
                linStats.setVisible(false);
                cmbSelectAgencies.setVisible(false);
                cmbSelectCustomers.setVisible(false);
                lblCumulativeSales.setVisible(false);
                // Update labels
                lblSelect.setText("Select Agent:");
                // Reset date picker to today
                dtpMaxDate.setValue(LocalDate.now());
                try {
                    // Get first agent
                    cmbSelectAgents.setValue(cmbSelectAgents.getItems().getFirst());
                    AgentStatsDTO agent = cmbSelectAgents.getValue();
                    // Get agent's sales until today's date
                    long agentSales = StatisticsDB.totalSalesPerAgent(agent.getAgentId(), dtpMaxDate.getValue());
                    // Convert date to string
                    String selectedDate = dtpMaxDate.getValue().toString();
                    // Format the chart
                    haxBarStats.setLabel("Date");
                    vaxBarStats.setLabel("Sales Per Agent");
                    XYChart.Series<String, Number> totalSalesSeries = new XYChart.Series<>();
                    totalSalesSeries.setName(agent.toString());
                    // Add datapoint for agent's first sale
                    LocalDate firstSale = StatisticsDB.agentFirstSaleDate(agent.getAgentId());
                    totalSalesSeries.getData().add(new XYChart.Data<>(firstSale.toString(), 1));
                    // Add datapoint for today's date in datetimepicker
                    totalSalesSeries.getData().add(new XYChart.Data<>(selectedDate, agentSales));
                    brcStats.getData().add(totalSalesSeries);
                    // Add tooltip
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                // Hide other combo boxes and charts
                linStats.setVisible(true);
                cmbSelectAgents.setVisible(true);
                brcStats.setVisible(false);
                pieStats.setVisible(false);
                cmbSelectAgencies.setVisible(false);
                cmbSelectCustomers.setVisible(false);
                lblCumulativeSales.setVisible(false);
                // Update labels
                lblSelect.setText("Select Agent:");
                // Reset date picker to today's date
                dtpMaxDate.setValue(LocalDate.now());
                try {
                    // Get first agent
                    cmbSelectAgents.setValue(cmbSelectAgents.getItems().getFirst());
                    AgentStatsDTO agent = cmbSelectAgents.getValue();
                    // Convert date to string
                    String today = dtpMaxDate.getValue().toString();
                    // Get agent's commission value until today's date
                    BigDecimal agentCommission = StatisticsDB.totalCommissionPerAgent(agent.getAgentId(), dtpMaxDate.getValue());
                    // Format the chart
                    haxLineStats.setLabel("Date");
                    vaxLineStats.setLabel("Commission Value Per Agent");
                    XYChart.Series<String, Number> totalCommissionSeries = new XYChart.Series<>();
                    totalCommissionSeries.setName(agent.toString());
                    // Add datapoint for agent's first sale
                    LocalDate firstSale = StatisticsDB.agentFirstSaleDate(agent.getAgentId());
                    String convertFirst = firstSale.toString();
                    BigDecimal firstCommission = StatisticsDB.agentFirstCommissionValue(agent.getAgentId());
                    totalCommissionSeries.getData().add(new XYChart.Data<>(convertFirst, firstCommission));
                    // Add datapoint for today's date
                    totalCommissionSeries.getData().add(new XYChart.Data<>(today, agentCommission));
                    linStats.getData().add(totalCommissionSeries);
                    // Add tooltip
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                // Hide other controls
                pieStats.setVisible(true);
                cmbSelectAgencies.setVisible(true);
                lblCumulativeSales.setVisible(true);
                linStats.setVisible(false);
                brcStats.setVisible(false);
                cmbSelectAgents.setVisible(false);
                cmbSelectCustomers.setVisible(false);
                // Update label
                lblSelect.setText("Select Agency: ");
                // Reset date picker
                dtpMaxDate.setValue(LocalDate.now());
                try {
                    cmbSelectAgencies.setValue(cmbSelectAgencies.getItems().getFirst());
                    AgencyStatsDTO agency = cmbSelectAgencies.getValue();
                    String agencyName = agency.toString();
                    LocalDate date = dtpMaxDate.getValue();
                    // Get sales of first agency
                    long agencySales = StatisticsDB.totalSalesPerAgency(agency.getAgencyid(), date);
                    // Format and add data to the chart
                    pieStats.setTitle("Sales Per Agency (Until " + date + ")");
                    List<Map.Entry<String, Long>> sales = new ArrayList<>();
                    sales.add(new AbstractMap.SimpleEntry<>(agencyName, agencySales));
                    PieChart.Data firstAgency = new PieChart.Data(agencyName, agencySales);
                    pieStats.getData().add(firstAgency);
                    // Add a tooltip to show the value of the pie slice
                    Tooltip tooltip = new Tooltip(agencyName + ": " + agencySales);
                    tooltip.setStyle("-fx-font-size: 14px");
                    Tooltip.install(firstAgency.getNode(), tooltip);
                    // Update cumulative sales label
                    updateCumulativeLabel(date);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 4:
                // Hide other controls
                brcStats.setVisible(true);
                cmbSelectCustomers.setVisible(true);
                linStats.setVisible(false);
                pieStats.setVisible(false);
                cmbSelectAgencies.setVisible(false);
                cmbSelectAgents.setVisible(false);
                lblCumulativeSales.setVisible(false);
                // Update label
                lblSelect.setText("Select Customer: ");
                // Reset date picker
                dtpMaxDate.setValue(LocalDate.now());
                try {
                    cmbSelectCustomers.setValue(cmbSelectCustomers.getItems().getFirst());
                    CustomerStatsDTO customer = cmbSelectCustomers.getValue();
                    // Get number of bookings for first customer until today's date
                    long customerBookings = StatisticsDB.totalSalesPerCustomer(customer.getCustomerId(), dtpMaxDate.getValue());
                    String selectedDate = dtpMaxDate.getValue().toString();
                    // Update bar chart vertical label and load data
                    vaxBarStats.setLabel("Sales Per Customer");
                    XYChart.Series<String, Number> customerSales = new XYChart.Series<>();
                    customerSales.setName(customer.toString());
                    // Add datapoint for customer's first booking
                    LocalDate firstSale = StatisticsDB.customerFirstSaleDate(customer.getCustomerId());
                    customerSales.getData().add(new XYChart.Data<>(firstSale.toString(), 1));
                    // Add datapoint for customer's total sales
                    customerSales.getData().add(new XYChart.Data<>(selectedDate, customerBookings));
                    brcStats.getData().add(customerSales);
                    // Add tooltip
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 5:
                // Hide controls
                brcStats.setVisible(true);
                linStats.setVisible(false);
                pieStats.setVisible(false);
                cmbSelectAgencies.setVisible(false);
                cmbSelectAgents.setVisible(false);
                cmbSelectCustomers.setVisible(false);
                lblSelect.setVisible(false);
                lblCumulativeSales.setVisible(false);
                // Reset date picker and store the date as string
                dtpMaxDate.setValue(LocalDate.now());
                String selectedDate = dtpMaxDate.getValue().toString();
                try {
                    // Get total number of sales until today's date
                    long totalSales = StatisticsDB.totalSales(dtpMaxDate.getValue());
                    // Update bar chart vertical label
                    vaxBarStats.setLabel("Number of Sales");
                    XYChart.Series<String, Number> sales = new XYChart.Series<>();
                    sales.setName("Total Sales");
                    // Add datapoint for the agency's first sale
                    LocalDate date = StatisticsDB.firstSaleDate();
                    sales.getData().add(new XYChart.Data<>(date.toString(), 1));
                    // Add datapoint for all sales until today's date
                    sales.getData().add(new XYChart.Data<>(selectedDate, totalSales));
                    brcStats.getData().add(sales);
                    // Add tooltip
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private void updateCumulativeLabel(LocalDate date) {
        long cumulativeSales = 0;
        try {
            cumulativeSales = StatisticsDB.totalSales(date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblCumulativeSales.setText("Cumulative Sales: " + cumulativeSales);
    }

    private void loadCustomers() {
        ObservableList<CustomerStatsDTO> customerList = null;
        try {
            customerList = FXCollections.observableArrayList(StatisticsDB.customersList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbSelectCustomers.setItems(customerList);
    }

    private void loadAgencies() {
        ObservableList<AgencyStatsDTO> agencyList = null;
        try {
            agencyList = FXCollections.observableArrayList(StatisticsDB.agenciesList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbSelectAgencies.setItems(agencyList);
    }

    private void loadAgents() {
        ObservableList<AgentStatsDTO> agentList = null;
        try {
            agentList = FXCollections.observableArrayList(StatisticsDB.agentsList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbSelectAgents.setItems(agentList);
    }

    private void displayMessage(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setContentText(message);
        alert.showAndWait();
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