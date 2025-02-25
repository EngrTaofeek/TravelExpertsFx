package com.groupfour.travelexpertsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage primaryStage;



    @Override
    public void start(Stage primaryStage) {
        MainApplication.primaryStage = primaryStage; // Store the primary stage reference
        primaryStage.setMaximized(true);
        showLoginScreen(); // Start with the login screen
    }
    public static void showRegisterScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/groupfour/travelexpertsfx/views/RegisterView.fxml"));
            AnchorPane root = loader.load();

            // Create a new stage for Register Page
            Stage registerStage = new Stage();
            registerStage.setTitle("Register - Travel Experts");
            registerStage.setScene(new Scene(root, 600, 650)); // Set pop-up size

            // Make it a modal pop-up (blocks interaction with login window)
            registerStage.initModality(Modality.APPLICATION_MODAL);
            registerStage.initStyle(StageStyle.UTILITY);
            registerStage.setResizable(false); // Disable resizing

            registerStage.showAndWait(); // Show as pop-up and wait for it to close

        } catch (IOException e) {
            System.err.println("❌ Error loading RegisterView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/groupfour/travelexpertsfx/views/LoginView.fxml"));
            BorderPane root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login - Travel Experts");
            // Open in maximized mode
            primaryStage.setMaximized(true);

            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error loading LoginView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showDashboardScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/groupfour/travelexpertsfx/views/Dashboard.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root,primaryStage.getWidth(),primaryStage.getHeight());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Travel Experts Dashboard");

            // Open in maximized mode
            primaryStage.setMaximized(true);


            primaryStage.show();
        } catch (Exception e) {
            System.err.println("❌ Error loading Dashboard.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }







    public static Stage getPrimaryStage() {
        return primaryStage;
    }





    public static void main(String[] args) {
        launch(args);
    }
}
