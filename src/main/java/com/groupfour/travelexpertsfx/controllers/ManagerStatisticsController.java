/**
 * Sample Skeleton for 'ManagerStatistics.fxml' Controller Class
 */

package com.groupfour.travelexpertsfx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class ManagerStatisticsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="brcStats"
    private BarChart<?, ?> brcStats; // Value injected by FXMLLoader

    @FXML // fx:id="btnChartAdd"
    private Button btnChartAdd; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSelect"
    private ComboBox<?> cmbSelect; // Value injected by FXMLLoader

    @FXML // fx:id="cmbStatsView"
    private ComboBox<?> cmbStatsView; // Value injected by FXMLLoader

    @FXML // fx:id="dtpMaxDate"
    private DatePicker dtpMaxDate; // Value injected by FXMLLoader

    @FXML // fx:id="haxStats"
    private CategoryAxis haxStats; // Value injected by FXMLLoader

    @FXML // fx:id="lblHorizontalAxis"
    private Label lblHorizontalAxis; // Value injected by FXMLLoader

    @FXML // fx:id="lblSelect"
    private Label lblSelect; // Value injected by FXMLLoader

    @FXML // fx:id="lblStatLabelOne"
    private Label lblStatLabelOne; // Value injected by FXMLLoader

    @FXML // fx:id="lblStatLabelTwo"
    private Label lblStatLabelTwo; // Value injected by FXMLLoader

    @FXML // fx:id="lblStatValueOne"
    private Label lblStatValueOne; // Value injected by FXMLLoader

    @FXML // fx:id="lblStatValueTwo"
    private Label lblStatValueTwo; // Value injected by FXMLLoader

    @FXML // fx:id="lblVerticalAxis"
    private Label lblVerticalAxis; // Value injected by FXMLLoader

    @FXML // fx:id="vaxStats"
    private NumberAxis vaxStats; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert brcStats != null : "fx:id=\"brcStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert btnChartAdd != null : "fx:id=\"btnChartAdd\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert cmbSelect != null : "fx:id=\"cmbSelect\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert cmbStatsView != null : "fx:id=\"cmbStatsView\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert dtpMaxDate != null : "fx:id=\"dtpMaxDate\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert haxStats != null : "fx:id=\"haxStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert lblHorizontalAxis != null : "fx:id=\"lblHorizontalAxis\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert lblSelect != null : "fx:id=\"lblSelect\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert lblStatLabelOne != null : "fx:id=\"lblStatLabelOne\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert lblStatLabelTwo != null : "fx:id=\"lblStatLabelTwo\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert lblStatValueOne != null : "fx:id=\"lblStatValueOne\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert lblStatValueTwo != null : "fx:id=\"lblStatValueTwo\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert lblVerticalAxis != null : "fx:id=\"lblVerticalAxis\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";
        assert vaxStats != null : "fx:id=\"vaxStats\" was not injected: check your FXML file 'ManagerStatistics.fxml'.";

    }

}
