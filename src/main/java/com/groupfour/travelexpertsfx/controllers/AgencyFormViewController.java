package com.groupfour.travelexpertsfx.controllers;

/**
 * @Author DarylWxc
 * @Date 2/22/2025
 * @Description Agency Form Controller
 */

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Agency;
import com.groupfour.travelexpertsfx.models.AgencyDB;
import com.groupfour.travelexpertsfx.models.Package;
import com.groupfour.travelexpertsfx.models.PackageDB;
import com.groupfour.travelexpertsfx.utils.AlertMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgencyFormViewController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="tfAddress"
    private TextField tfAddress; // Value injected by FXMLLoader

    @FXML // fx:id="tfCity"
    private TextField tfCity; // Value injected by FXMLLoader

    @FXML // fx:id="tfCountry"
    private TextField tfCountry; // Value injected by FXMLLoader

    @FXML // fx:id="tfFax"
    private TextField tfFax; // Value injected by FXMLLoader

    @FXML // fx:id="tfPhone"
    private TextField tfPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfPostal"
    private TextField tfPostal; // Value injected by FXMLLoader

    @FXML // fx:id="tfProv"
    private TextField tfProv; // Value injected by FXMLLoader

    private int agencyId = 0;

    public String mode;

    AlertMessage message = new AlertMessage();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'agency-form-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'agency-form-view.fxml'.";
        assert tfAddress != null : "fx:id=\"tfAddress\" was not injected: check your FXML file 'agency-form-view.fxml'.";
        assert tfCity != null : "fx:id=\"tfCity\" was not injected: check your FXML file 'agency-form-view.fxml'.";
        assert tfCountry != null : "fx:id=\"tfCountry\" was not injected: check your FXML file 'agency-form-view.fxml'.";
        assert tfFax != null : "fx:id=\"tfFax\" was not injected: check your FXML file 'agency-form-view.fxml'.";
        assert tfPhone != null : "fx:id=\"tfPhone\" was not injected: check your FXML file 'agency-form-view.fxml'.";
        assert tfPostal != null : "fx:id=\"tfPostal\" was not injected: check your FXML file 'agency-form-view.fxml'.";
        assert tfProv != null : "fx:id=\"tfProv\" was not injected: check your FXML file 'agency-form-view.fxml'.";

        btnCancel.setOnAction((ActionEvent event) -> {
            closeWindow(event);
        });

        btnSave.setOnAction((ActionEvent event) -> {
            saveAgency(event);
        });

    }

    private void saveAgency(ActionEvent event) {
        Agency agency = getAgencyFormView();
        int numRows = 0;
        if (mode.equals("add")) {
            try {
                numRows = AgencyDB.addAgency(agency);
            } catch (SQLException e) {
                message.alertMessage(Alert.AlertType.ERROR,"An error occurred while Adding the agency\n:"+e.getMessage());
            }
        } else {
            try {
                numRows = AgencyDB.updateAgency(agency);
            } catch (SQLException e) {
                message.alertMessage(Alert.AlertType.ERROR,"An error occurred while Updating the agency\n:"+e.getMessage());
            }
        }
        if(numRows == 1){
            message.alertMessage(Alert.AlertType.CONFIRMATION,"Successfully saved the agency");
        } else {
            message.alertMessage(Alert.AlertType.ERROR,mode == "add" ? "Insertion" : "Update" + " failed.");
        }
        closeWindow(event);
    }

    private void closeWindow(ActionEvent event) {
        Node node = (Node)event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }

    public void setMode(String mode){
        this.mode = mode;
    }

    public void setAgencyId(int AgencyId) {
        this.agencyId = AgencyId;
    }

    private Agency getAgencyFormView() {
        return new Agency(agencyId != 0 ? agencyId : 0,
                tfAddress.getText(),
                tfCity.getText(),
                tfProv.getText(),
                tfPostal.getText(),
                tfCountry.getText(),
                tfPhone.getText(),
                tfFax.getText());
    }

    public void setAgencyFormView(Agency agency) {
        tfAddress.setText(agency.getAgencyaddress());
        tfCity.setText(agency.getAgencycity());
        tfProv.setText(agency.getAgencyprov());
        tfPostal.setText(agency.getAgencypostal());
        tfCountry.setText(agency.getAgencycountry());
        tfPhone.setText(agency.getAgencyphone());
        tfFax.setText(agency.getAgencyphone());
    }
}
