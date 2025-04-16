package com.groupfour.travelexpertsfx.controllers;


import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.PackageDB;
import com.groupfour.travelexpertsfx.utils.AlertMessage;
import com.groupfour.travelexpertsfx.utils.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.groupfour.travelexpertsfx.models.Package;

public class PackageFormViewController{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnClear"
    private Button btnClear; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="dpEndDate"
    private DatePicker dpEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="dpStartDate"
    private DatePicker dpStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyCommission"
    private TextField tfAgencyCommission; // Value injected by FXMLLoader

    @FXML // fx:id="tfBasePrice"
    private TextField tfBasePrice; // Value injected by FXMLLoader

    @FXML // fx:id="tfDescription"
    private TextArea tfDescription; // Value injected by FXMLLoader

    @FXML // fx:id="tfName"
    private TextField tfName; // Value injected by FXMLLoader

    private int packageId = 0;

    public String mode;

    AlertMessage message = new AlertMessage();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'package-form-view.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'package-form-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'package-form-view.fxml'.";
        assert dpEndDate != null : "fx:id=\"dpEndDate\" was not injected: check your FXML file 'package-form-view.fxml'.";
        assert dpStartDate != null : "fx:id=\"dpStartDate\" was not injected: check your FXML file 'package-form-view.fxml'.";
        assert tfAgencyCommission != null : "fx:id=\"tfAgencyCommission\" was not injected: check your FXML file 'package-form-view.fxml'.";
        assert tfBasePrice != null : "fx:id=\"tfBasePrice\" was not injected: check your FXML file 'package-form-view.fxml'.";
        assert tfDescription != null : "fx:id=\"tfDescription\" was not injected: check your FXML file 'package-form-view.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'package-form-view.fxml'.";

        btnCancel.setOnAction((ActionEvent event) -> {
            closeWindow(event);
        });

        btnSave.setOnAction(event -> {
            savePackage(event);
        });

    }

    private void closeWindow(ActionEvent event) {
        Node node = (Node)event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }

    private void savePackage(ActionEvent event) {
        Package newPackage = getPackageFormView();
        if(newPackage != null) {
            int numRows = 0;
            if (mode.equals("add")) {
                try {
                    numRows = PackageDB.addPackage(newPackage);
                } catch (SQLException e) {
                    message.alertMessage(Alert.AlertType.ERROR,"An error occurred while Adding the package\n:"+e.getMessage());
                }
            } else {
                try {
                    numRows = PackageDB.updatePackage(newPackage);
                } catch (SQLException e) {
                    message.alertMessage(Alert.AlertType.ERROR,"An error occurred while Updating the package\n:"+e.getMessage());
                }
            }
            if(numRows == 1){
                message.alertMessage(Alert.AlertType.CONFIRMATION,"Successfully saved the package");
            } else {
                message.alertMessage(Alert.AlertType.ERROR,mode == "add" ? "Insertion" : "Update" + " failed.");
            }
            closeWindow(event);
        }
    }

    public void setMode(String mode){
        this.mode = mode;
    }

    private void checkDate(Date startDate,Date endDate) {
        if(startDate.after(endDate)) {
            throw new RuntimeException("Start date cannot be after end date");
        }
        Date now = new Date(System.currentTimeMillis());
        if(startDate.before(now)) {
            throw new RuntimeException("Start date should after now");
        }
    }

    private Package getPackageFormView() {
        try {
            Validator.validateName(tfName.getText());
            if(dpStartDate.getValue() == null) {
                throw new RuntimeException("please select start date");
            }
            if(dpEndDate.getValue() == null) {
                throw new RuntimeException("please select end date");
            }
            checkDate(Date.valueOf(dpStartDate.getValue()),Date.valueOf(dpEndDate.getValue()));
            Validator.validatePrice(tfBasePrice.getText(),"base price");
            Validator.validatePrice(tfAgencyCommission.getText(),"agency commission");
            return new Package(packageId != 0 ? packageId : 0,tfName.getText(),
                    Double.parseDouble(tfAgencyCommission.getText()),
                    Double.parseDouble(tfBasePrice.getText()),
                    Date.valueOf(dpEndDate.getValue()),
                    Date.valueOf(dpStartDate.getValue()),
                    tfDescription.getText());
        } catch (Exception e) {
            message.alertMessage(Alert.AlertType.ERROR,e.getMessage());
        }
        return null;
    }

    public void setPackageFormView(Package pack) {
        tfBasePrice.setText(String.valueOf(pack.getPkgbaseprice()));
        tfDescription.setText(pack.getPkgdesc());
        tfName.setText(pack.getPkgname());
        tfAgencyCommission.setText(String.valueOf(pack.getPkgagencycommission()));
        dpStartDate.setValue(pack.getPkgstartdate());
        dpEndDate.setValue(pack.getPkgenddate());
    }

    public void setPackageId(int PackageId) {
        this.packageId = PackageId;
    }
}
