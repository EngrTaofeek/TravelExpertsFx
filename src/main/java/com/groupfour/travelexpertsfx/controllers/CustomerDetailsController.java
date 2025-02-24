/**
 * Sample Skeleton for 'CustomerDetails.fxml' Controller Class
 */

package com.groupfour.travelexpertsfx.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Customer;
import com.groupfour.travelexpertsfx.models.CustomerDB;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CustomerDetailsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // BUTTONS
    private Button btnBack, btnSaveChanges, btnEdit, btnDelete, btnTrips; // Value injected by FXMLLoader

    @FXML // fx:id="lblCustomerName"
    private Label lblCustomerName; // Value injected by FXMLLoader

    @FXML // fx:id="lblHeader"
    private Label lblHeader; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustAddress"
    private TextField tfCustAddress; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustAgentId"
    private TextField tfCustAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustBusPhone"
    private TextField tfCustBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustCity"
    private TextField tfCustCity; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustCountry"
    private TextField tfCustCountry; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustEmail"
    private TextField tfCustEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustFirstName"
    private TextField tfCustFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustHomePhone"
    private TextField tfCustHomePhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustLastName"
    private TextField tfCustLastName; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustPostal"
    private TextField tfCustPostal; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustProvince"
    private TextField tfCustProvince; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerId"
    private TextField tfCustomerId; // Value injected by FXMLLoader


    // MOUSE EVENT METHODS

    @FXML
    void deleteCustomer(MouseEvent event) {

    }

    @FXML
    void editCustomer(MouseEvent event) {

        this.setPageMode("edit");


    }



    @FXML
    void viewTrips(MouseEvent event) {

    }

    @FXML
    void goBack(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }

    @FXML
    void saveChanges(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert btnSaveChanges != null : "fx:id=\"btnSaveChanges\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert btnTrips != null : "fx:id=\"btnTrips\" was not injected: check your FXML file 'CustomerDetails.fxml'.";

        assert lblCustomerName != null : "fx:id=\"lblCustomerName\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert lblHeader != null : "fx:id=\"lblHeader\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustAddress != null : "fx:id=\"tfCustAddress\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustAgentId != null : "fx:id=\"tfCustAgentId\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustBusPhone != null : "fx:id=\"tfCustBusPhone\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustCity != null : "fx:id=\"tfCustCity\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustCountry != null : "fx:id=\"tfCustCountry\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustEmail != null : "fx:id=\"tfCustEmail\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustFirstName != null : "fx:id=\"tfCustFirstName\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustHomePhone != null : "fx:id=\"tfCustHomePhone\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustLastName != null : "fx:id=\"tfCustLastName\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustPostal != null : "fx:id=\"tfCustPostal\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustProvince != null : "fx:id=\"tfCustProvince\" was not injected: check your FXML file 'CustomerDetails.fxml'.";
        assert tfCustomerId != null : "fx:id=\"tfCustomerId\" was not injected: check your FXML file 'CustomerDetails.fxml'.";




    }

    // VARIABLES


    String pageMode;

    Customer currentCustomer;

    // CUSTOM METHODS BY KAZI


    public String getPageMode() {
        return pageMode;
    }

    public void setPageMode(String pageMode) {
        this.pageMode = pageMode;
        if (pageMode.equalsIgnoreCase("details")) {
            setFieldsNotEditable();
            lblHeader.setText("Customer Details: ");
            lblCustomerName.setText(currentCustomer.getCustfirstname()+" "+currentCustomer.getCustlastname());
            btnSaveChanges.setVisible(false);
        } else if (pageMode.equalsIgnoreCase("edit")) {
            setFieldsEditable();
            tfCustomerId.setDisable(true);
            btnSaveChanges.setVisible(true);
            btnEdit.setVisible(false);
            btnTrips.setVisible(false);
            lblHeader.setText("Edit Customer Details: ");

        } else if (pageMode.equalsIgnoreCase("add")) {
            setFieldsEditable();
            btnSaveChanges.setVisible(true);
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
            btnTrips.setVisible(false);
            lblHeader.setText("Add Customer Details: ");
            lblCustomerName.setText("");
        }
    }

    private void setFieldsNotEditable() {
        tfCustomerId.setEditable(false);
        tfCustFirstName.setEditable(false);
        tfCustLastName.setEditable(false);
        tfCustAddress.setEditable(false);
        tfCustCity.setEditable(false);
        tfCustProvince.setEditable(false);
        tfCustPostal.setEditable(false);
        tfCustCountry.setEditable(false);
        tfCustHomePhone.setEditable(false);
        tfCustBusPhone.setEditable(false);
        tfCustEmail.setEditable(false);
        tfCustAgentId.setEditable(false);
    }

    private void setFieldsEditable() {
        tfCustomerId.setEditable(true);
        tfCustFirstName.setEditable(true);
        tfCustLastName.setEditable(true);
        tfCustAddress.setEditable(true);
        tfCustCity.setEditable(true);
        tfCustProvince.setEditable(true);
        tfCustPostal.setEditable(true);
        tfCustCountry.setEditable(true);
        tfCustHomePhone.setEditable(true);
        tfCustBusPhone.setEditable(true);
        tfCustEmail.setEditable(true);
        tfCustAgentId.setEditable(true);
    }




    public void setCustomerDetails(Customer customer){
        if (customer != null) {
            this.currentCustomer = customer;

            tfCustomerId.setText(String.valueOf(customer.getCustomerid()));
            tfCustFirstName.setText(customer.getCustfirstname());
            tfCustLastName.setText(customer.getCustlastname());
            tfCustAddress.setText(customer.getCustaddress());
            tfCustCity.setText(customer.getCustcity());
            tfCustProvince.setText(customer.getCustprov());
            tfCustCountry.setText(customer.getCustcountry());
            tfCustEmail.setText(customer.getCustemail());
            tfCustPostal.setText(customer.getCustpostal());
            tfCustHomePhone.setText(customer.getCusthomephone());
            tfCustBusPhone.setText(customer.getCustbusphone());
            tfCustAgentId.setText(String.valueOf(customer.getAgentid()));
        } else this.currentCustomer = null;

    }


}
