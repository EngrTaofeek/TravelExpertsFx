package com.groupfour.travelexpertsfx.controllers;

import com.groupfour.travelexpertsfx.MainApplication;
import com.groupfour.travelexpertsfx.models.UserDb;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtVisiblePassword;
    @FXML private Button btnShowPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnRegister;
    @FXML private Label lblMessage;

    private boolean isPasswordVisible = false;

    @FXML
    public void initialize() {
        // Ensure password fields sync
        txtVisiblePassword.setManaged(false);
        txtVisiblePassword.setVisible(false);
        txtVisiblePassword.textProperty().bindBidirectional(txtPassword.textProperty());

        // Set button actions
        btnLogin.setOnAction(e -> handleLogin());
        btnRegister.setOnAction(e -> handleRegister());
        btnShowPassword.setOnAction(e -> togglePasswordVisibility());
    }

    @FXML
    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;

        txtVisiblePassword.setManaged(isPasswordVisible);
        txtVisiblePassword.setVisible(isPasswordVisible);

        txtPassword.setManaged(!isPasswordVisible);
        txtPassword.setVisible(!isPasswordVisible);

        btnShowPassword.setText(isPasswordVisible ? "👁‍🗨" : "👁"); // Update button icon
    }

    @FXML
    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = isPasswordVisible ? txtVisiblePassword.getText().trim() : txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showMessage("⚠ Please enter email and password.", "red");
            return;
        }

        System.out.println("🔍 Attempting to authenticate user: " + email);
        String userRole = UserDb.authenticateUser(email, password);

        if (userRole != null) {
            showMessage("✅ Login successful!", "green");

            System.out.println("✅ User authenticated: " + email + " | Role: " + userRole);

            // Pass user role to DashboardController
            DashboardController.setUserRole(userRole);

            // Redirect to Dashboard
            System.out.println("🔄 Redirecting to Dashboard...");
            MainApplication.showDashboardScreen();
        } else {
            showMessage("❌ Invalid email or password.", "red");
            System.out.println("❌ Login failed for: " + email);
        }
    }

    @FXML
    private void handleRegister() {
        System.out.println("🔄 Redirecting to Registration Page...");
        MainApplication.showRegisterScreen();
    }

    /**
     * Displays a message in the label with a given color.
     * The message disappears after 3 seconds.
     */
    private void showMessage(String text, String color) {
        lblMessage.setText(text);
        lblMessage.setStyle("-fx-text-fill: " + color + ";");

        // Remove message after 3 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> lblMessage.setText("")));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
