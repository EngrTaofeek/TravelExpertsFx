package com.groupfour.travelexpertsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        MainApplication.primaryStage = primaryStage; // Store the primary stage reference
        showLoginScreen(); // Start with the login screen
    }

    public static void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/groupfour/travelexpertsfx/views/LoginView.fxml"));
            BorderPane root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login - Travel Experts");
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error loading LoginView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showDashboardScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/groupfour/travelexpertsfx/views/Dashboard.fxml"));
            AnchorPane root = loader.load(); // Changed from BorderPane to AnchorPane

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Travel Experts Dashboard");
        } catch (Exception e) {
            System.err.println("Error loading Dashboard.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
