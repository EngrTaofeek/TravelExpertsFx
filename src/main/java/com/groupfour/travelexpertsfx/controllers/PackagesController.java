package com.groupfour.travelexpertsfx.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.utils.AlertMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.groupfour.travelexpertsfx.models.Package;
import com.groupfour.travelexpertsfx.models.PackageDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PackagesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnClear"
    private Button btnClear; // Value injected by FXMLLoader

    @FXML // fx:id="btnSearch"
    private Button btnSearch; // Value injected by FXMLLoader

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML // fx:id="tfSearchField"
    private TextField tfSearchField; // Value injected by FXMLLoader

    @FXML // fx:id="dpEnd"
    private DatePicker dpEnd; // Value injected by FXMLLoader

    @FXML // fx:id="dpStart"
    private DatePicker dpStart; // Value injected by FXMLLoader

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

    String mode;

    Package objectPackage;

    AlertMessage message = new AlertMessage();

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Packages.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Packages.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Packages.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Packages.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Packages.fxml'.";
        assert dpEnd != null : "fx:id=\"dpEnd\" was not injected: check your FXML file 'Packages.fxml'.";
        assert dpStart != null : "fx:id=\"dpStart\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcAgencyCommission != null : "fx:id=\"tcAgencyCommission\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcBasePrice != null : "fx:id=\"tcBasePrice\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcDescription != null : "fx:id=\"tcDescription\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcEndDate != null : "fx:id=\"tcEndDate\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcName != null : "fx:id=\"tcName\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tcStartDate != null : "fx:id=\"tcStartDate\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tvPackages != null : "fx:id=\"tvPackages\" was not injected: check your FXML file 'Packages.fxml'.";
        assert tfSearchField != null : "fx:id=\"tfSearchField\" was not injected: check your FXML file 'Packages.fxml'.";

        setupPackageTable();
        displayPackages();

        btnAdd.setOnAction(event -> {
            mode = "add";
            CreateForm(null);
        });

        btnEdit.setOnAction(event -> {
            mode = "edit";
            if (objectPackage != null) {
                CreateForm(objectPackage);
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Please select a package first");
            }
        });

        btnDelete.setOnAction(event -> {
            if (objectPackage != null) {
                deletePackage(objectPackage);
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Please select a package first");
            }
        });

        btnClear.setOnAction(event -> {
            clearSearchForm();
        });

        btnSearch.setOnAction(event -> {
            searchPackages();
        });

        tvPackages.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                objectPackage = newValue;
            }
        });

    }

    private void setupPackageTable() {
        tcName.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgname"));
        tcStartDate.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgstartdate"));
        tcEndDate.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgenddate"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgdesc"));
        tcBasePrice.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgbaseprice"));
        tcAgencyCommission.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgagencycommission"));
    }

    public void displayPackages() {
        data.clear();
        try {
            data = PackageDB.getPackages();
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load package table", e);
        }
        tvPackages.setItems(data);
    }

    public void searchPackages() {
        data.clear();
        String searchWord = tfSearchField.getText();
        if (isInputDouble(searchWord)) {
            double searchNum = Double.parseDouble(searchWord);

            try {
                data = PackageDB.searchPackagesByNumber(searchNum, dpStart.getValue(), dpEnd.getValue());
            } catch (SQLException e) {
                throw new RuntimeException("Fail to load package table", e);
            }
        } else {
            if (searchWord.isEmpty() && dpEnd.getValue() == null && dpStart.getValue() == null) {
                displayPackages();
            } else {
                try {
                    data = PackageDB.searchPackagesByString(searchWord, dpStart.getValue(), dpEnd.getValue());
                } catch (SQLException e) {
                    throw new RuntimeException("Fail to load package table", e);
                }
            }
        }
        tvPackages.setItems(data);
    }

    public static boolean isInputDouble(String input) {
        boolean isInputDouble = true;
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            isInputDouble = false;
        }

        return isInputDouble;
    }

    private void CreateForm(Package objectPackage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/groupfour/travelexpertsfx/views/package-form-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PackageFormViewController controller = fxmlLoader.getController();
        controller.setMode(mode);
        if (objectPackage != null) {
            controller.setPackageFormView(objectPackage);
            controller.setPackageId(objectPackage.getId());
        }
        closeWindow(scene);
        displayPackages();
    }

    private void closeWindow(Scene scene) {
        Stage stage = new Stage();
        stage.setTitle("Package" + mode);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    private void deletePackage(Package objectPackage) {
        int numRows = 0;
        int packageId = objectPackage.getId();

        try {
            numRows = PackageDB.deletePackage(packageId);
            if (numRows == 1) {
                message.alertMessage(Alert.AlertType.CONFIRMATION, "The package has been deleted successfully.");
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Delete package failed.");
            }
        } catch (SQLException e) {
            message.alertMessage(Alert.AlertType.ERROR, "An error occurred while deleting the package\n" + e.getMessage());
        }
        displayPackages();
    }

    private void clearSearchForm() {
        tfSearchField.clear();
        dpStart.setValue(null);
        dpEnd.setValue(null);
        displayPackages();
    }
}
