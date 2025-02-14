
package com.groupfour.travelexpertsfx.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Package;
import com.groupfour.travelexpertsfx.models.PackageDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;

public class PackagesController {

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

    @FXML // fx:id="tcAgencyCommission"
    private TableColumn<Package, Double> tcAgencyCommission; // Value injected by FXMLLoader

    @FXML // fx:id="tcBasePrice"
    private TableColumn<Package, Double> tcBasePrice; // Value injected by FXMLLoader

    @FXML // fx:id="tcDescription"
    private TableColumn<Package, String> tcDescription; // Value injected by FXMLLoader

    @FXML // fx:id="tcEndDate"
    private TableColumn<Package, Date> tcEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="tcName"
    private TableColumn<Package, String> tcName; // Value injected by FXMLLoader

    @FXML // fx:id="tcStartDate"
    private TableColumn<Package, Date> tcStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="tvPackages"
    private TableView<Package> tvPackages; // Value injected by FXMLLoader

    private ObservableList<Package> data = FXCollections.observableArrayList();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Packages.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Packages.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcAgencyCommission != null : "fx:id=\"tcAgencyCommission\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcBasePrice != null : "fx:id=\"tcBasePrice\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcDescription != null : "fx:id=\"tcDescription\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcEndDate != null : "fx:id=\"tcEndDate\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcName != null : "fx:id=\"tcName\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcStartDate != null : "fx:id=\"tcStartDate\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tvPackages != null : "fx:id=\"tvPackages\" was not injected: check your FXML file 'Packages.fxml'.";

        setupPackageTable();
        displayAgents();
    }

    private void setupPackageTable() {
        tcName.setCellValueFactory(new PropertyValueFactory<Package,String>("pkgname"));
        tcStartDate.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgstartdate"));
        tcEndDate.setCellValueFactory(new PropertyValueFactory<Package,Date>("pkgenddate"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<Package,String>("pkgdesc"));
        tcBasePrice.setCellValueFactory(new PropertyValueFactory<Package,Double>("pkgbaseprice"));
        tcAgencyCommission.setCellValueFactory(new PropertyValueFactory<Package,Double>("pkgagencycommission"));
    }

    public void displayAgents(){
        data.clear();
        try {
            data = PackageDB.getPackages();
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load agents table", e);
        }
        tvPackages.setItems(data);
    }
}
