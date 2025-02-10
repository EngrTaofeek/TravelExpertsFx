package com.groupfour.travelexpertsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the Dashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupfour/travelexpertsfx/views/Dashboard.fxml"));


            AnchorPane root = loader.load();

            // Create and set the scene
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Travel Experts Dashboard");
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error loading Dashboard.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
