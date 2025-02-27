package com.groupfour.travelexpertsfx.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Supplier;
import com.groupfour.travelexpertsfx.models.SupplierDB;
import com.groupfour.travelexpertsfx.utils.AlertMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

public class SuppliersController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML // fx:id="btnClear"
    private Button btnClear; // Value injected by FXMLLoader

    @FXML // fx:id="btnSearch"
    private Button btnSearch; // Value injected by FXMLLoader

    @FXML // fx:id="tcId"
    private TableColumn<Supplier, Integer> tcId; // Value injected by FXMLLoader

    @FXML // fx:id="tcName"
    private TableColumn<Supplier, String> tcName; // Value injected by FXMLLoader

    @FXML // fx:id="tfName"
    private TextField tfName; // Value injected by FXMLLoader

    @FXML // fx:id="tvSuppliers"
    private TableView<Supplier> tvSuppliers; // Value injected by FXMLLoader

    private ObservableList<Supplier> data = FXCollections.observableArrayList();

    AlertMessage message = new AlertMessage();

    Supplier selectedSupplier;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert tcId != null : "fx:id=\"tcId\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert tcName != null : "fx:id=\"tcName\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert tvSuppliers != null : "fx:id=\"tvSuppliers\" was not injected: check your FXML file 'Suppliers.fxml'.";

        setupSupplierTable();
        displaySuppliers();

        btnAdd.setOnAction(event -> {
            try {
                addSupplier();
            } catch (SQLException e) {
               message.alertMessage(Alert.AlertType.ERROR, e.getMessage());
            }
        });

        btnDelete.setOnAction(event -> {
            deleteSupplier();
        });

        btnEdit.setOnAction(event -> {
            editSupplier();
        });

        btnClear.setOnAction(event -> {
            tfName.clear();
        });

        btnSearch.setOnAction(event -> {
           searchSupplier();
        });

        tvSuppliers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedSupplier = newValue;
                tfName.setText(selectedSupplier.getSupname());
            }
        });
    }

    private void setupSupplierTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<Supplier,Integer>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<Supplier,String>("supname"));
    }


    public void displaySuppliers(){
        data.clear();
        try {
            data = SupplierDB.getSuppliers();
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load suppliers table", e);
        }
        tvSuppliers.setItems(data);
    }

    public void addSupplier() throws SQLException {
        Supplier supplier = new Supplier(0,tfName.getText());
        int numRows = 0;
        try {
            numRows = SupplierDB.addSupplier(supplier);
        } catch (Exception e) {
             message.alertMessage(Alert.AlertType.ERROR,"An error occurred while adding the supplier\n:" + e.getMessage());
        }
        if (numRows == 1) {
            message.alertMessage(Alert.AlertType.CONFIRMATION, "Successfully added the supplier");
        } else {
            message.alertMessage(Alert.AlertType.ERROR, "Insertion failed.");
        }
        displaySuppliers();
    }

    public void deleteSupplier(){
        int numRows = 0;
        Integer supplierId = selectedSupplier.getId();

        try {
            numRows = SupplierDB.deleteSupplier(supplierId);
            if (numRows == 1) {
                message.alertMessage(Alert.AlertType.CONFIRMATION, "The supplier has been deleted successfully.");
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Delete supplier failed.");
            }
        } catch (SQLException e) {
            message.alertMessage(Alert.AlertType.ERROR, "An error occurred while deleting the supplier\n" + e.getMessage());
        }
        tfName.clear();
        displaySuppliers();
    }

    public void editSupplier(){
        int numRows = 0;
        Supplier supplier = new Supplier(selectedSupplier.getId(),tfName.getText());
        try {
            numRows = SupplierDB.updateSupplier(selectedSupplier.getId(), supplier);
            if (numRows == 1) {
                message.alertMessage(Alert.AlertType.CONFIRMATION, "The supplier has been updated successfully.");
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Update supplier failed.");
            }
        } catch (SQLException e) {
            message.alertMessage(Alert.AlertType.ERROR, "An error occurred while updating the supplier\n:" + e.getMessage());
        }
        tfName.clear();
        displaySuppliers();
    }

    public void searchSupplier(){
        if(tfName.getText().isEmpty()){
            displaySuppliers();
        } else {
            data.clear();
            try {
                data = SupplierDB.getSearchedSuppliers(tfName.getText());
            } catch (SQLException e) {
                throw new RuntimeException("Fail to load suppliers table", e);
            }
            tvSuppliers.setItems(data);
        }
    }
}