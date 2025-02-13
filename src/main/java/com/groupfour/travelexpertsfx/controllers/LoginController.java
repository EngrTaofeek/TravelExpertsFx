package com.groupfour.travelexpertsfx.controllers;

import com.groupfour.travelexpertsfx.MainApplication;
import com.groupfour.travelexpertsfx.models.UserDb;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMessage;

    @FXML
    private Button btnlogin; // Reference to the login button

    @FXML
    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            lblMessage.setText("⚠ Please enter email and password.");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        if (UserDb.authenticateUser(email, password)) {
            lblMessage.setText("✅ Login successful!");
            lblMessage.setStyle("-fx-text-fill: green;");

            // Delay a little for UX improvement before switching to the Dashboard
            btnlogin.setDisable(true); // Disable button temporarily to prevent multiple clicks
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // 1-second delay
                    javafx.application.Platform.runLater(MainApplication::showDashboardScreen);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            lblMessage.setText("❌ Invalid email or password.");
            lblMessage.setStyle("-fx-text-fill: red;");
        }
    }
}
