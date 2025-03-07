package com.groupfour.travelexpertsfx.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Agency;
import com.groupfour.travelexpertsfx.models.AgencyDB;
import com.groupfour.travelexpertsfx.utils.AlertMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AgenciesController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnClear"
    private Button btnClear; // Value injected by FXMLLoader

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML // fx:id="btnSearch"
    private Button btnSearch; // Value injected by FXMLLoader

    @FXML // fx:id="tcAddress"
    private TableColumn<Agency, String> tcAddress; // Value injected by FXMLLoader

    @FXML // fx:id="tcCity"
    private TableColumn<Agency, String> tcCity; // Value injected by FXMLLoader

    @FXML // fx:id="tcCountry"
    private TableColumn<Agency, String> tcCountry; // Value injected by FXMLLoader

    @FXML // fx:id="tcFax"
    private TableColumn<Agency, String> tcFax; // Value injected by FXMLLoader

    @FXML // fx:id="tcPhone"
    private TableColumn<Agency, String> tcPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tcPostal"
    private TableColumn<Agency, String> tcPostal; // Value injected by FXMLLoader

    @FXML // fx:id="tcProvince"
    private TableColumn<Agency, String> tcProvince; // Value injected by FXMLLoader

    @FXML // fx:id="tvAgency"
    private TableView<Agency> tvAgency; // Value injected by FXMLLoader

    @FXML
    private TextField tfSearchField;

    private ObservableList<Agency> data = FXCollections.observableArrayList();

    String mode;

    Agency agency;

    AlertMessage message = new AlertMessage();

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert tcAddress != null : "fx:id=\"tcAddress\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert tcCity != null : "fx:id=\"tcCity\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert tcCountry != null : "fx:id=\"tcCountry\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert tcFax != null : "fx:id=\"tcFax\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert tcPhone != null : "fx:id=\"tcPhone\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert tcPostal != null : "fx:id=\"tcPostal\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert tcProvince != null : "fx:id=\"tcProvince\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert tvAgency != null : "fx:id=\"tvPackages\" was not injected: check your FXML file 'Agencies.fxml'.";
        assert tfSearchField != null : "fx:id=\"tfSearchField\" was not injected: check your FXML file 'Agencies.fxml'.";


        setupPackageTable();
        displayAgencies();

        tvAgency.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                agency = newValue;
            }
        });

        btnAdd.setOnAction(event -> {
            mode = "add";
            CreateForm(null);
        });

        btnEdit.setOnAction(event -> {
            mode = "edit";
            if (agency != null) {
                CreateForm(agency);
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Please select a agency first");
            }
        });

        btnDelete.setOnAction(event -> {
            if (agency != null) {
                deleteAgency(agency);
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Please select a agency first");
            }
        });

        btnClear.setOnAction(event -> {
            clearSearchForm();
        });

        btnSearch.setOnAction(event -> {
            searchAgencies();
        });
    }

    private void setupPackageTable() {
        tcAddress.setCellValueFactory(new PropertyValueFactory<Agency, String>("agencyaddress"));
        tcCity.setCellValueFactory(new PropertyValueFactory<Agency, String>("agencycity"));
        tcProvince.setCellValueFactory(new PropertyValueFactory<Agency, String>("agencyprov"));
        tcPostal.setCellValueFactory(new PropertyValueFactory<Agency, String>("agencypostal"));
        tcCountry.setCellValueFactory(new PropertyValueFactory<Agency, String>("agencycountry"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<Agency, String>("agencyphone"));
        tcFax.setCellValueFactory(new PropertyValueFactory<Agency, String>("agencyfax"));
    }

    public void displayAgencies() {
        data.clear();
        try {
            data = AgencyDB.getAgencies();
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load package table", e);
        }
        tvAgency.setItems(data);
    }

    private void CreateForm(Agency agency) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/groupfour/travelexpertsfx/views/agency-form-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AgencyFormViewController controller = fxmlLoader.getController();
        controller.setMode(mode);
        if (agency != null) {
            controller.setAgencyFormView(agency);
            controller.setAgencyId(agency.getId());
        }
        closeWindow(scene);
        displayAgencies();
    }

    private void closeWindow(Scene scene) {
        Stage stage = new Stage();
        stage.setTitle("Agency" + mode);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    private void deleteAgency(Agency agency) {
        int numRows = 0;
        int agencyId = agency.getId();

        try {
            numRows = AgencyDB.deleteAgency(agencyId);
            if (numRows == 1) {
                message.alertMessage(Alert.AlertType.CONFIRMATION, "The agency has been deleted successfully.");
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Delete agency failed.");
            }
        } catch (SQLException e) {
            message.alertMessage(Alert.AlertType.ERROR, "An error occurred while deleting the agency\n" + e.getMessage());
        }
        displayAgencies();
    }

    private void clearSearchForm() {
        tfSearchField.clear();
        displayAgencies();
    }

    private void searchAgencies() {
        data.clear();
        String searchWord = tfSearchField.getText();
        try {
            data = AgencyDB.searchAgenciesByString(searchWord);
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load agency table", e);
        }
        tvAgency.setItems(data);
    }
}
