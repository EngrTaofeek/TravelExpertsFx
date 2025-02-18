package com.groupfour.travelexpertsfx.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private HBox topButtonContainer;
    @FXML private AnchorPane mainContent;
    @FXML private Button btnHome, btnAdd, btnEdit, btnDelete;
    @FXML private Button btnAgents, btnAgencies, btnPackages, btnSuppliers, btnCustomers, btnProducts;
    @FXML private VBox sideMenu;
    @FXML private Button btnMenuToggle;  // Changed from Label to Button

    private Object currentController;
    private static String userRole;
    private boolean isSidebarVisible = false;

    public static void setUserRole(String role) {
        userRole = role;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        // Ensure home is initialized

        loadPage("Home.fxml");


        // Apply role-based restrictions
        applyRolePermissions();

        // Sidebar initially hidden
        sideMenu.setVisible(false);
        sideMenu.setManaged(false);

        // Attach menu toggle
        btnMenuToggle.setOnAction(event -> toggleSidebar());

        // Attach Home button action
        btnHome.setOnAction(event -> loadPage("Home.fxml"));

        // Navigation button actions
        btnAgents.setOnAction(event -> loadPage("Agents.fxml"));
        btnAgencies.setOnAction(event -> loadPage("Agencies.fxml"));
        btnPackages.setOnAction(event -> loadPage("Packages.fxml"));
        btnSuppliers.setOnAction(event -> loadPage("Suppliers.fxml"));
        btnCustomers.setOnAction(event -> loadPage("Customers.fxml"));
        btnProducts.setOnAction(event -> loadPage("Products.fxml"));



        // Hide top buttons initially
        setTopButtonsVisibility(false);

        applyStylesheet();
    }

    @FXML
    public void toggleSidebar() {
        isSidebarVisible = !isSidebarVisible;
        sideMenu.setVisible(isSidebarVisible);
        sideMenu.setManaged(isSidebarVisible);
    }

    private void applyRolePermissions() {
        if (userRole != null && userRole.equalsIgnoreCase("agent")) {
            btnAgents.setVisible(false);
            btnAgents.setManaged(false);
            btnAgencies.setVisible(false);
            btnAgencies.setManaged(false);
        }
    }

    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupfour/travelexpertsfx/views/" + fxmlFile));
            Node page = loader.load();
            mainContent.getChildren().clear();
            mainContent.getChildren().add(page);

            currentController = loader.getController();

            boolean isHome = fxmlFile.equals("Home.fxml");
            setTopButtonsVisibility(!isHome);

            if (userRole != null && userRole.equalsIgnoreCase("agent")) {
                restrictCrudAccess();
            }

        } catch (Exception e) {
            System.err.println("❌ Error loading " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void restrictCrudAccess() {
        if (currentController instanceof AgentsController || currentController instanceof AgenciesController) {
            setTopButtonsVisibility(false);
        }
    }



    private void setTopButtonsVisibility(boolean visible) {
        btnHome.setVisible(visible);
        btnAdd.setVisible(visible);
        btnEdit.setVisible(visible);
        btnDelete.setVisible(visible);
    }
    private void applyStylesheet() {
        if (mainContent.getScene() != null) {
            Scene scene = mainContent.getScene();
            scene.getStylesheets().add(getClass().getResource("/com/groupfour/travelexpertsfx/styles/dashboard.css").toExternalForm());
        } else {
            // Wait until the scene is available
            mainContent.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.getStylesheets().add(getClass().getResource("/com/groupfour/travelexpertsfx/styles/dashboard.css").toExternalForm());
                }
            });
        }
    }


}
