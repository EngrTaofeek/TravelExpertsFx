package com.groupfour.travelexpertsfx.controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private HBox topButtonContainer;
    @FXML private AnchorPane mainContent; // Main content area for loading views
    @FXML private AnchorPane sideMenu; // Sidebar for navigation
    @FXML private Label lblMenu; // Menu toggle label
    @FXML private ImageView exitIcon; // Exit button icon
    @FXML private Button btnHome, btnAdd, btnEdit, btnDelete; // Top buttons
    @FXML private Button btnAgents, btnAgencies, btnPackages, btnSuppliers, btnCustomers, btnProducts; // Sidebar buttons

    private boolean isMenuOpen = false; // Sidebar initially hidden

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Ensure sidebar starts hidden
        sideMenu.setLayoutX(-200);

        // Set event listener for "☰ Menu" label
        lblMenu.setOnMouseClicked(event -> toggleSidebar());

        // Set event for Exit button
        exitIcon.setOnMouseClicked(event -> System.exit(0));

        // Load the Home page by default
        loadPage("Home.fxml");

        // Set navigation button actions
        btnHome.setOnAction(event -> {
            loadPage("Home.fxml");
            setTopButtonsVisibility(false); // Hide buttons on Home
        });
        btnAgents.setOnAction(event -> loadPage("Agents.fxml"));
        btnAgencies.setOnAction(event -> loadPage("Agencies.fxml"));
        btnPackages.setOnAction(event -> loadPage("Packages.fxml"));
        btnSuppliers.setOnAction(event -> loadPage("Suppliers.fxml"));
        btnCustomers.setOnAction(event -> loadPage("Customers.fxml"));
        btnProducts.setOnAction(event -> loadPage("Products.fxml"));

        // Initially hide the top buttons
        setTopButtonsVisibility(false);
    }

    // Sidebar toggle function with layout adjustment
    private void toggleSidebar() {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.4), sideMenu);

        if (isMenuOpen) {
            slide.setToX(-200); // Hide sidebar
            slide.setOnFinished(event -> sideMenu.setVisible(false));
            mainContent.setLayoutX(0); // Adjust main content position
        } else {
            sideMenu.setVisible(true);
            slide.setToX(0); // Show sidebar
            mainContent.setLayoutX(200); // Push content when sidebar is visible
        }

        slide.play();
        isMenuOpen = !isMenuOpen;
    }

    // Function to load a new FXML file inside `mainContent`
    private void loadPage(String fxmlFile) {
        try {
            String fullPath = "/com/groupfour/travelexpertsfx/views/" + fxmlFile;
            URL fileUrl = getClass().getResource(fullPath);

            if (fileUrl == null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Node page = loader.load();
            mainContent.getChildren().clear(); // Clear previous content
            mainContent.getChildren().add(page); // Load new content

            // Show buttons only when a section is selected
            boolean isHome = fxmlFile.equals("Home.fxml");
            setTopButtonsVisibility(!isHome);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to show/hide top buttons
    private void setTopButtonsVisibility(boolean visible) {
        btnAdd.setVisible(visible);
        btnEdit.setVisible(visible);
        btnDelete.setVisible(visible);
    }
}
