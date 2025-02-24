package com.groupfour.travelexpertsfx.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Customer;
import com.groupfour.travelexpertsfx.models.CustomerDB;
import com.groupfour.travelexpertsfx.models.CustomerDTO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    String pageMode;

    @FXML
    void addCustomer(MouseEvent event) {
        pageMode = "Add";
        Customer newCustomer = null;
        openCustomerDetailsPage(newCustomer,  pageMode);

    }

    @FXML
    void clearSearch(MouseEvent event) {

    }

    @FXML
    void deleteCustomer(MouseEvent event) {
        int id = tvCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        int numRows = 0;
        try {
            numRows = CustomerDB.deleteCustomer(id);
        } catch (SQLException e) {
            ControllerMethods.alertUser(Alert.AlertType.ERROR, "Error deleting Customer\n" + e.getMessage());
        }

        if (numRows==1) {
            ControllerMethods.alertUser(Alert.AlertType.CONFIRMATION, "Customer has been deleted successfully");
        } else {
            ControllerMethods.alertUser(Alert.AlertType.ERROR, "Error while deleting the customer");
        }
        displayCustomer();

    }

    @FXML
    void editCustomer(MouseEvent event) {
        if (tvCustomer.getSelectionModel().getSelectedItem() != null) {
            int selectedId = tvCustomer.getSelectionModel().getSelectedItem().getCustomerId();
            pageMode = "Edit";
            Customer selectedCustomer;
            try {
                selectedCustomer = CustomerDB.getCustomerById(selectedId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            openCustomerDetailsPage(selectedCustomer,  pageMode);
        } else
        {
            ControllerMethods.alertUser(Alert.AlertType.ERROR, "Select a Customer");
        }

    }

    @FXML
    void searchCustomer(MouseEvent event) {

    }

    @FXML
    void viewCustomerDetails(MouseEvent event) {
        if (tvCustomer.getSelectionModel().getSelectedItem() != null) {
            int selectedId = tvCustomer.getSelectionModel().getSelectedItem().getCustomerId();
            pageMode = "Details";
            Customer selectedCustomer;
            try {
                selectedCustomer = CustomerDB.getCustomerById(selectedId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            openCustomerDetailsPage(selectedCustomer,  pageMode);
        } else
        {
            ControllerMethods.alertUser(Alert.AlertType.ERROR, "Select a Customer");
        }
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

    private void openCustomerDetailsPage(Customer passedCustomerDetails, String pageMode){

        String fullPath = "/com/groupfour/travelexpertsfx/views/CustomerDetails.fxml";

        URL fileUrl = getClass().getResource(fullPath);
        if (fileUrl == null) {
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fileUrl);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new  RuntimeException("Fail to load CustomerDetailsPage.fxml", e);
        }

        // CONTROLLER
        CustomerDetailsController controller = fxmlLoader.getController();

        controller.setCustomerDetails(passedCustomerDetails);
        controller.setPageMode(pageMode);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Customer "+pageMode);
        stage.setScene(scene);
        stage.showAndWait();
        displayCustomer();

    }





}

/*
Notes for Kazi:

TASKS:
Status: INCOMPLETE


    * view past trips
    * search customer
    * validation (yuck)

 Status: COMPLETE
    * READ - view customer details in a table
    * details page
    * need new form for add/edit/delete
    * CREATE - add new customer
    * UPDATE - edit customer details
    * DELETE - delete customer

 */