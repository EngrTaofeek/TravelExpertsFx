
package com.groupfour.travelexpertsfx.controllers;

import com.groupfour.travelexpertsfx.models.UserDb;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

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
            // Show plain text field and hide password field
            visibleTextField.setText(passwordField.getText());
            visibleTextField.setVisible(true);
            visibleTextField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            toggleButton.setText("👁‍🗨");
        } else {
            // Ensure password field gets updated with the latest value
            passwordField.setText(visibleTextField.getText());

            // Show password field and hide plain text field
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            visibleTextField.setVisible(false);
            visibleTextField.setManaged(false);
            toggleButton.setText("👁");
        }
    }



    @FXML
    private void handleRegister() {
        String email = txtEmail.getText().trim();
        String password = txtPassword.isVisible() ? txtPassword.getText().trim() : txtVisiblePassword.getText().trim();
        String confirmPassword = txtConfirmPassword.isVisible() ? txtConfirmPassword.getText().trim() : txtVisibleConfirmPassword.getText().trim();
        String role = comboRole.getValue().trim().toLowerCase();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role == null) {
            lblMessage.setText("⚠ All fields are required!");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!role.equals("agent") && !role.equals("manager")) {
            lblMessage.setText("❌ Invalid role selected!");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!password.equals(confirmPassword)) {
            lblMessage.setText("❌ Passwords do not match!");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        if (UserDb.isEmailTaken(email)) {
            lblMessage.setText("❌ Registration failed: Email is already in use.");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Register user and capture possible failures
        boolean success = UserDb.registerUser(email, hashedPassword, role);
        if (success) {
            lblMessage.setText("✅ Registration successful!");
            lblMessage.setStyle("-fx-text-fill: green;");
        } else {
            lblMessage.setText("❌ Registration failed due to an internal error. Check logs for details.");
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