package com.groupfour.travelexpertsfx.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Supplier;
import com.groupfour.travelexpertsfx.models.SupplierDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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

    @FXML // fx:id="tcId"
    private TableColumn<Supplier, Integer> tcId; // Value injected by FXMLLoader

    @FXML // fx:id="tcName"
    private TableColumn<Supplier, String> tcName; // Value injected by FXMLLoader

    @FXML // fx:id="tvSuppliers"
    private TableView<Supplier> tvSuppliers; // Value injected by FXMLLoader

    private ObservableList<Supplier> data = FXCollections.observableArrayList();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Suppliers.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Suppliers.fxml'.";

        setupSupplierTable();
        displaySuppliers();
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
}