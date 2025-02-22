package com.groupfour.travelexpertsfx.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.CustomerDB;
import com.groupfour.travelexpertsfx.models.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * @Author: Kazi Fattah
 * @Date: 2/2025
 * @Description: Controller for the performing CRUD on Customers
 * @To-do-list:
 *
 * - READ - view customer details in a table
 * - CREATE - add new customer
 * - UPDATE - edit customer details
 * - DELETE - delete customer
 * - view past trips
 * - search customer
 * - need new form for add/edit/delete
 */

public class CustomersController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="HBoxSearchBar"
    private HBox HBoxSearchBar; // Value injected by FXMLLoader

    @FXML
    private Button btnDetails, btnAdd, btnEdit, btnTrips, btnDelete, btnSearch, btnClearSearch; // Value injected by FXMLLoader

    @FXML // fx:id="colCustID"
    private TableColumn<CustomerDTO, Integer> colCustID; // Value injected by FXMLLoader

    @FXML // fx:id="colAgent"
    private TableColumn<CustomerDTO, String> colAgent; // Value injected by FXMLLoader

    @FXML // fx:id="colCustName"
    private TableColumn<CustomerDTO, String> colCustName; // Value injected by FXMLLoader

    @FXML // fx:id="colEmail"
    private TableColumn<CustomerDTO, String> colEmail; // Value injected by FXMLLoader

    @FXML // fx:id="colPhone"
    private TableColumn<CustomerDTO, String> colPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfSearchBar"
    private TextField tfSearchBar; // Value injected by FXMLLoader

    @FXML // fx:id="tvCustomer"
    private TableView<CustomerDTO> tvCustomer; // Value injected by FXMLLoader

    @FXML
    void addProduct(MouseEvent event) {

    }

    @FXML
    void clearSearch(MouseEvent event) {

    }

    @FXML
    void deleteProduct(MouseEvent event) {

    }

    @FXML
    void editProduct(MouseEvent event) {

    }

    @FXML
    void searchCustomer(MouseEvent event) {

    }

    private ObservableList<CustomerDTO> customerData = FXCollections.observableArrayList();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert HBoxSearchBar != null : "fx:id=\"HBoxSearchBar\" was not injected: check your FXML file 'Customers.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Customers.fxml'.";
        assert btnClearSearch != null : "fx:id=\"btnClearSearch\" was not injected: check your FXML file 'Customers.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Customers.fxml'.";
        assert btnDetails != null : "fx:id=\"btnDetails\" was not injected: check your FXML file 'Customers.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Customers.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Customers.fxml'.";
        assert btnTrips != null : "fx:id=\"btnTrips\" was not injected: check your FXML file 'Customers.fxml'.";
        assert colCustID != null : "fx:id=\"colCustID\" was not injected: check your FXML file 'Customers.fxml'.";
        assert colAgent != null : "fx:id=\"colAgent\" was not injected: check your FXML file 'Customers.fxml'.";
        assert colCustName != null : "fx:id=\"colCustName\" was not injected: check your FXML file 'Customers.fxml'.";
        assert colEmail != null : "fx:id=\"colEmail\" was not injected: check your FXML file 'Customers.fxml'.";
        assert colPhone != null : "fx:id=\"colPhone\" was not injected: check your FXML file 'Customers.fxml'.";
        assert tfSearchBar != null : "fx:id=\"tfSearchBar\" was not injected: check your FXML file 'Customers.fxml'.";
        assert tvCustomer != null : "fx:id=\"tvCustomer\" was not injected: check your FXML file 'Customers.fxml'.";

        setupCustomerTable();
        displayCustomer();

    }

    private void setupCustomerTable() {
        colCustID.setCellValueFactory(new PropertyValueFactory<CustomerDTO,Integer>("customerId"));
        colCustName.setCellValueFactory(new PropertyValueFactory<CustomerDTO, String>("customerName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<CustomerDTO, String>("customerPhone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<CustomerDTO, String>("customerEmail"));
        colAgent.setCellValueFactory(new PropertyValueFactory<CustomerDTO, String>("customerAgent"));

    }

    public void displayCustomer(){
        customerData.clear();
        try {
            customerData = CustomerDB.getCustomer();
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load Products table", e);
        }
        tvCustomer.setItems(customerData);
    }
}

/*
Notes for Kazi:

TASKS:
Status: INCOMPLETE

    * CREATE - add new customer
    * UPDATE - edit customer details
    * DELETE - delete customer
    * view past trips
    * search customer
    * need new form for add/edit/delete

 Status: COMPLETE
    * READ - view customer details in a table

 */