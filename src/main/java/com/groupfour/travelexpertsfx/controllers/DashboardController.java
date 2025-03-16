package com.groupfour.travelexpertsfx.controllers;

import com.groupfour.travelexpertsfx.MainApplication;
import com.groupfour.travelexpertsfx.models.CurrentUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private HBox topButtonContainer;
    @FXML private Button btnHome;
    @FXML private Button btnAgents, btnAgencies, btnPackages, btnSuppliers, btnCustomers, btnProducts, btnMyStatistics, btnManagerStatistics;
    @FXML private VBox sideMenu;
    @FXML private Button btnMenuToggle;
    @FXML private StackPane mainContent;
    @FXML private Label lblUserEmail;
    @FXML private ImageView menuIcon;
    private Object currentController;
    private static String userRole;
    private boolean isSidebarVisible = false;

    public static void setUserRole(String role) {
        userRole = role;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Display logged-in user's email
        String userEmail = CurrentUser.getEmail();
        if (userEmail != null) {
            lblUserEmail.setText("Welcome, " + userEmail);
        } else {
            lblUserEmail.setText("Welcome, Guest");
        }
        // Show the welcome message (as the default view)
        showWelcomeMessage();

        // Apply role-based restrictions
        applyRolePermissions();

        // Sidebar initially hidden
        sideMenu.setVisible(false);
        sideMenu.setManaged(false);

        // Attach menu toggle
        btnMenuToggle.setOnAction(event -> toggleSidebar());

        // Attach Home button behavior
        btnHome.setOnAction(event -> showWelcomeMessage());
        btnHome.setVisible(false); // Initially hidden

        // Navigation button actions
        btnAgents.setOnAction(event -> { loadPage("Agents.fxml", btnAgents); toggleHomeButton(true); });
        btnAgencies.setOnAction(event -> { loadPage("Agencies.fxml", btnAgencies); toggleHomeButton(true); });
        btnPackages.setOnAction(event -> { loadPage("Packages.fxml", btnPackages); toggleHomeButton(true); });
        btnSuppliers.setOnAction(event -> { loadPage("Suppliers.fxml", btnSuppliers); toggleHomeButton(true); });
        btnCustomers.setOnAction(event -> { loadPage("Customers.fxml", btnCustomers); toggleHomeButton(true); });
        btnProducts.setOnAction(event -> { loadPage("Products.fxml", btnProducts); toggleHomeButton(true); });
        btnMyStatistics.setOnAction(event -> { loadPage("MyStatistics.fxml", btnMyStatistics); toggleHomeButton(true); });
        btnManagerStatistics.setOnAction(event -> { loadPage("ManagerStatistics.fxml", btnManagerStatistics); toggleHomeButton(true); });

        // Apply default styles
        applyStylesheet();

    }

    // Corrected Java Code
    @FXML
    private void toggleSidebar() {
        isSidebarVisible = !isSidebarVisible;
        sideMenu.setVisible(isSidebarVisible);
        sideMenu.setManaged(isSidebarVisible);

        // Define icon path
        String iconPath = isSidebarVisible ? "/com/groupfour/travelexpertsfx/images/menu-close.png" : "/com/groupfour/travelexpertsfx/images/menu-open.png";

        // Load image safely
        URL iconUrl = getClass().getResource(iconPath);
        if (iconUrl != null) {
            menuIcon.setImage(new Image(iconUrl.toExternalForm()));
        } else {
            System.out.println("Error: Menu icon not found at " + iconPath);
        }

        // Restore welcome message only if no page is open
        if (!isSidebarVisible && mainContent.getChildren().isEmpty()) {
            showWelcomeMessage();
        }
    }





    private void applyRolePermissions() {
        if (userRole != null && userRole.equalsIgnoreCase("agent")) {
            btnAgents.setVisible(false);
            btnAgents.setManaged(false);
            btnAgencies.setVisible(false);
            btnAgencies.setManaged(false);
            btnManagerStatistics.setVisible(false);
            btnManagerStatistics.setManaged(false);
        }
    }

    private void loadPage(String fxmlFile, Button clickedButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupfour/travelexpertsfx/views/" + fxmlFile));
            Node page = loader.load();

            //  Ensure the main content is cleared before loading a new page
            mainContent.getChildren().clear();
            mainContent.getChildren().add(page);

            currentController = loader.getController();

            // Highlight the clicked button
            highlightSidebarButton(clickedButton);

            if (userRole != null && userRole.equalsIgnoreCase("agent")) {
                restrictCrudAccess();
            }

            // Show Home button when loading a new page
            btnHome.setVisible(true);

        } catch (Exception e) {
            System.err.println("❌ Error loading " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void restrictCrudAccess() {
        if (currentController instanceof AgentsController || currentController instanceof AgenciesController) {
            // If needed, restrict certain actions based on role
        }
    }

    private void showWelcomeMessage() {
        mainContent.getChildren().clear();
        Label welcomeLabel = new Label("Welcome to Travel Experts Dashboard!");
        welcomeLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #222831;");
        mainContent.getChildren().add(welcomeLabel);
    }

    private void highlightSidebarButton(Button clickedButton) {
        btnAgents.setStyle("");
        btnAgencies.setStyle("");
        btnPackages.setStyle("");
        btnSuppliers.setStyle("");
        btnCustomers.setStyle("");
        btnProducts.setStyle("");
        btnMyStatistics.setStyle("");
        btnManagerStatistics.setStyle("");

    }

    private void toggleHomeButton(boolean visible) {
        btnHome.setVisible(visible);
    }

    private void applyStylesheet() {
        if (mainContent.getScene() != null) {
            Scene scene = mainContent.getScene();
            scene.getStylesheets().add(getClass().getResource("/com/groupfour/travelexpertsfx/styles/dashboard.css").toExternalForm());
        } else {
            mainContent.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.getStylesheets().add(getClass().getResource("/com/groupfour/travelexpertsfx/styles/dashboard.css").toExternalForm());
                }
            });
        }
    }

    @FXML
    private void handleLogout() {
        // Show a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                // Clear current user session
                CurrentUser.clearUser();

                // Redirect to Log in Screen after logout
                MainApplication.showLoginScreen();
            }
        });
    }

}
