/**
 * Sample Skeleton for 'PastTrips.fxml' Controller Class
 */

package com.groupfour.travelexpertsfx.controllers;

/**
 * @Author: Kazi Fattah
 * @Date: 3/2025
 * @Description: Controller for viewing past trips of selected customer
 * @To-do-list:
 *

 * - view past trips
 * - search customer
 *
 */

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Customer;
import com.groupfour.travelexpertsfx.models.CustomerDB;
import com.groupfour.travelexpertsfx.models.PastTrips;
import com.groupfour.travelexpertsfx.utils.ControllerMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PastTripsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnGoBack"
    private Button btnGoBack; // Value injected by FXMLLoader

    @FXML // fx:id="colBookingId"
    private TableColumn<PastTrips, Integer> colBookingId; // Value injected by FXMLLoader

    @FXML // fx:id="colClass"
    private TableColumn<PastTrips, String> colClass; // Value injected by FXMLLoader

    @FXML // fx:id="colDesc"
    private TableColumn<PastTrips, String> colDesc; // Value injected by FXMLLoader

    @FXML // fx:id="colDest"
    private TableColumn<PastTrips, String> colDest; // Value injected by FXMLLoader

    @FXML // fx:id="colEnd"
    private TableColumn<PastTrips, String> colEnd; // Value injected by FXMLLoader

    @FXML // fx:id="colPrice"
    private TableColumn<PastTrips, Double> colPrice; // Value injected by FXMLLoader

    @FXML // fx:id="colStart"
    private TableColumn<PastTrips, String> colStart; // Value injected by FXMLLoader

    @FXML // fx:id="colTT"
    private TableColumn<PastTrips, String> colTT; // Value injected by FXMLLoader

    @FXML // fx:id="lblCustName"
    private Label lblCustName; // Value injected by FXMLLoader

    @FXML
    private TableView<PastTrips> tvTrips;


    @FXML
    void goBack(MouseEvent event) {

        ControllerMethods.closeWindow(event);

    }



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnGoBack != null : "fx:id=\"btnGoBack\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert colBookingId != null : "fx:id=\"colBookingId\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert colClass != null : "fx:id=\"colClass\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert colDesc != null : "fx:id=\"colDesc\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert colDest != null : "fx:id=\"colDest\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert colEnd != null : "fx:id=\"colEnd\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert colPrice != null : "fx:id=\"colPrice\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert colStart != null : "fx:id=\"colStart\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert colTT != null : "fx:id=\"colTT\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert lblCustName != null : "fx:id=\"lblCustName\" was not injected: check your FXML file 'PastTrips.fxml'.";
        assert tvTrips != null : "fx:id=\"tvTrips\" was not injected: check your FXML file 'PastTrips.fxml'.";


        setupTripsTable();


    }



    Customer currentCustomer;
    private ObservableList<PastTrips> tripsData = FXCollections.observableArrayList();
    private int customerId;


    public void setCustomerId(int customerId) {
        this.customerId = customerId;
        //System.out.println("customerId: " + customerId);
        displayTrips();
        try {
            this.currentCustomer = CustomerDB.getCustomerById(customerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblCustName.setText(lblCustName.getText()+currentCustomer.getCustfirstname()+" "+currentCustomer.getCustlastname());

    }

    private void setupTripsTable() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<PastTrips, Integer>("bookingId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<PastTrips, String>("tripDescription"));
        colDest.setCellValueFactory(new PropertyValueFactory<PastTrips, String>("tripDestination"));
        colPrice.setCellValueFactory(new PropertyValueFactory<PastTrips, Double>("tripTotalPrice"));
        colTT.setCellValueFactory(new PropertyValueFactory<PastTrips, String>("tripType"));
        colClass.setCellValueFactory(new PropertyValueFactory<PastTrips, String>("tripClassName"));
        colStart.setCellValueFactory(new PropertyValueFactory<PastTrips, String>("tripStartDate"));
        colEnd.setCellValueFactory(new PropertyValueFactory<PastTrips, String>("tripEndDate"));

    }

    private void displayTrips() {
        tripsData.clear();
        try {
            tripsData = CustomerDB.getTripsByCustomerId(customerId);
           // System.out.println(customerId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load Trips table", e);
        }
        tvTrips.setItems(tripsData);

    }

}
