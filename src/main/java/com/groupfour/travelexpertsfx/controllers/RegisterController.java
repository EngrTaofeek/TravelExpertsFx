package com.groupfour.travelexpertsfx.controllers;

import com.groupfour.travelexpertsfx.MainApplication;
import com.groupfour.travelexpertsfx.models.UserDb;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private ComboBox<String> comboRole;
    @FXML private Label lblMessage;
    @FXML private Button btnBack;

    @FXML
    private void handleRegister() {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();
        String role = comboRole.getValue();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role == null) {
            lblMessage.setText("⚠ All fields are required!");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!password.equals(confirmPassword)) {
            lblMessage.setText("❌ Passwords do not match!");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        // Get agent ID from email
        int agentId = UserDb.getAgentIdByEmail(email);
        if (agentId == -1) {
            lblMessage.setText("❌ Registration failed. No agent found with this email.");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        // Attempt registration
        boolean success = UserDb.registerUser(email, password, role);
        if (success) {
            lblMessage.setText("✅ Registration successful!");
            lblMessage.setStyle("-fx-text-fill: green;");
        } else {
            lblMessage.setText("❌ Registration failed. Email may already be in use.");
            lblMessage.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack() {
        System.out.println("🔄 Redirecting to Login Page...");
        MainApplication.showLoginScreen();
    }

    private void showMessage(String message, String color) {
        lblMessage.setText(message);
        lblMessage.setStyle("-fx-text-fill: " + color + ";");
    }
}
