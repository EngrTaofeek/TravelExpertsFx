package com.groupfour.travelexpertsfx.controllers;

import com.groupfour.travelexpertsfx.models.UserDb;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    public Button btnShowConfirmPassword;
    public TextField txtVisibleConfirmPassword;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private ComboBox<String> comboRole;
    @FXML private Label lblMessage;
    @FXML private Button btnBack;
    @FXML private TextField txtVisiblePassword;
    @FXML private Button btnShowPassword;

    @FXML
    public void initialize() {
        btnShowPassword.setOnAction(e -> togglePasswordVisibility(txtPassword, txtVisiblePassword, btnShowPassword));
        btnShowConfirmPassword.setOnAction(e -> togglePasswordVisibility(txtConfirmPassword, txtVisibleConfirmPassword, btnShowConfirmPassword));
    }



    @FXML
    private void togglePasswordVisibility(PasswordField passwordField, TextField visibleTextField, Button toggleButton) {
        boolean isVisible = visibleTextField.isVisible();

        if (!isVisible) {
            // Show text field (plain text) and hide password field
            visibleTextField.setText(passwordField.getText());
            visibleTextField.setVisible(true);
            visibleTextField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            toggleButton.setText("👁‍🗨"); // Change icon to indicate hiding
            toggleButton.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial Unicode MS';"); // Change icon to indicate hiding

        } else {
            // Show password field and hide text field
            passwordField.setText(visibleTextField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            visibleTextField.setVisible(false);
            visibleTextField.setManaged(false);
            toggleButton.setText("👁"); // Change icon back
            toggleButton.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial Unicode MS';");
        }
    }




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
        System.out.println("🔄 Closing Register Page and Returning to Login...");

        // Get the current stage (Register window) and close it
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    private void showMessage(String message, String color) {
        lblMessage.setText(message);
        lblMessage.setStyle("-fx-text-fill: " + color + ";");
    }
}
