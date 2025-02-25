package com.groupfour.travelexpertsfx.controllers;

import com.groupfour.travelexpertsfx.models.UserDb;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Controller for the Register View.
 * Handles user registration by validating inputs, checking database constraints, 
 * and registering new users if they meet all criteria.
 */
public class RegisterController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private Label lblMessage;
    @FXML private Label lblRole;
    @FXML private Button btnBack;
    @FXML private Button btnRegister;
    @FXML private TextField txtVisiblePassword;
    @FXML private TextField txtVisibleConfirmPassword;
    @FXML private Button btnShowPassword;
    @FXML private Button btnShowConfirmPassword;

    /**
     * Initializes the controller after the FXML is loaded.
     * - Sets up password visibility toggling.
     * - Listens for changes in email field to fetch role dynamically.
     */
    @FXML
    public void initialize() {
        btnShowPassword.setOnAction(e -> togglePasswordVisibility(txtPassword, txtVisiblePassword, btnShowPassword));
        btnShowConfirmPassword.setOnAction(e -> togglePasswordVisibility(txtConfirmPassword, txtVisibleConfirmPassword, btnShowConfirmPassword));

        // Fetch role dynamically when email is entered
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            updateRoleDisplay(newValue);
        });
    }

    /**
     * Fetches the agent's role based on the entered email and updates the label accordingly.
     * @param email The email input used to retrieve the role.
     */
    @FXML
    private void updateRoleDisplay(String email) {
        if (email.isEmpty()) {
            lblRole.setText("Enter email to fetch role");
            lblRole.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
            return;
        }

        String role = UserDb.getAgentRoleByEmail(email);
        if (role == null) {
            lblRole.setText("❌ No agent found");
            lblRole.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else {
            lblRole.setText("Role: " + role.substring(0, 1).toUpperCase() + role.substring(1));

            // Set color based on role
            if (role.equalsIgnoreCase("agent")) {
                lblRole.setStyle("-fx-text-fill: #007bff; -fx-font-weight: bold;"); // Blue for Agent
            } else {
                lblRole.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold;"); // Purple for Manager
            }
        }
    }

    /**
     * Toggles the visibility of a password field.
     * - If hidden, it shows the plain text field.
     * - If visible, it restores the PasswordField.
     *
     * @param passwordField The hidden password field.
     * @param visibleTextField The visible text field (plain text version of the password).
     * @param toggleButton The button that toggles visibility.
     */
    @FXML
    private void togglePasswordVisibility(PasswordField passwordField, TextField visibleTextField, Button toggleButton) {
        boolean isVisible = visibleTextField.isVisible();

        if (!isVisible) {
            visibleTextField.setText(passwordField.getText());
            visibleTextField.setVisible(true);
            visibleTextField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            toggleButton.setText("👁‍🗨");
        } else {
            passwordField.setText(visibleTextField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            visibleTextField.setVisible(false);
            visibleTextField.setManaged(false);
            toggleButton.setText("👁");
        }
    }

    /**
     * Handles user registration process.
     * - Validates required fields.
     * - Ensures password and confirm password match.
     * - Checks if the email is already registered.
     * - Fetches role dynamically from the database.
     * - Registers the user and provides feedback.
     */
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

        // Fetch role from database based on email
        String role = UserDb.getAgentRoleByEmail(email);
        if (role == null) {
            lblMessage.setText("❌ Registration failed: No agent found with this email.");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        // Pass raw password, role, and email to `registerUser()`
        boolean success = UserDb.registerUser(email, password, role);

        if (success) {
            lblMessage.setText("✅ Registration successful!");
            lblMessage.setStyle("-fx-text-fill: green;");
        } else {
            lblMessage.setText("❌ Registration failed due to an internal error. Check logs for details.");
            lblMessage.setStyle("-fx-text-fill: red;");
        }
    }


    /**
     * Closes the registration window and returns the user to the login screen.
     */
    @FXML
    private void handleBack() {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to display.
     * @param color The color of the message (red for errors, green for success).
     */
    private void showMessage(String message, String color) {
        lblMessage.setText(message);
        lblMessage.setStyle("-fx-text-fill: " + color + ";");
    }
}
