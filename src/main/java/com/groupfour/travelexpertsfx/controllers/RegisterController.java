
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
    @FXML private Label lblRole;
    @FXML private Button btnBack;
    @FXML private TextField txtVisiblePassword;
    @FXML private Button btnShowPassword;

    @FXML
    public void initialize() {
        btnShowPassword.setOnAction(e -> togglePasswordVisibility(txtPassword, txtVisiblePassword, btnShowPassword));
        btnShowConfirmPassword.setOnAction(e -> togglePasswordVisibility(txtConfirmPassword, txtVisibleConfirmPassword, btnShowConfirmPassword));

        // Fetch role dynamically when email is entered
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            updateRoleDisplay(newValue);
        });
    }

    // Method to fetch role and update label
    private void updateRoleDisplay(String email) {
        if (email.isEmpty()) {
            lblRole.setText("Enter email to fetch role");
            lblRole.setStyle("-fx-text-fill: gray;");
            return;
        }

        String role = UserDb.getAgentRoleByEmail(email);
        if (role == null) {
            lblRole.setText("❌ No agent found");
            lblRole.setStyle("-fx-text-fill: red;");
        } else {
            lblRole.setText("Role: " + role.substring(0, 1).toUpperCase() + role.substring(1)); // Capitalize first letter
            lblRole.setStyle("-fx-text-fill: blue;");
        }
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

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            lblMessage.setText("⚠ All fields are required!");
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

        // ✅ Fetch the role from the agents table
        String role = UserDb.getAgentRoleByEmail(email);
        if (role == null) {
            lblMessage.setText("❌ Registration failed: No agent found with this email.");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // ✅ Register user with fetched role
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