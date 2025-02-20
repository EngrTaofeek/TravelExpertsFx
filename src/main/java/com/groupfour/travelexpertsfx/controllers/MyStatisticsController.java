/**
 * Sample Skeleton for 'MyStatistics.fxml' Controller Class
 */

package com.groupfour.travelexpertsfx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert dtpMaxDate != null : "fx:id=\"dtpMaxDate\" was not injected: check your FXML file 'MyStatistics.fxml'.";
        assert lblBookingAmt != null : "fx:id=\"lblBookingAmt\" was not injected: check your FXML file 'MyStatistics.fxml'.";
        assert lblCommissionAmt != null : "fx:id=\"lblCommissionAmt\" was not injected: check your FXML file 'MyStatistics.fxml'.";

    }

}
