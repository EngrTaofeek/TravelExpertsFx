package com.groupfour.travelexpertsfx.controllers;

import com.groupfour.travelexpertsfx.MainApplication;
import com.groupfour.travelexpertsfx.models.UserDb;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtVisiblePassword;
    @FXML private Button btnShowPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnRegister;
    @FXML private Label lblMessage;
    @FXML private ImageView imgLogo;
    @FXML private VBox leftSection;
    @FXML private HBox passwordContainer; // Holds password field + eye button

    private boolean isPasswordVisible = false;

    @FXML
    public void initialize() {

        // Dynamically resize logo within the left section
        imgLogo.fitWidthProperty().bind(leftSection.widthProperty().multiply(0.9));
        imgLogo.fitHeightProperty().bind(leftSection.heightProperty().multiply(0.6));

        // Ensure input fields & buttons stretch properly
        txtEmail.setMaxWidth(Double.MAX_VALUE);
        txtPassword.setMaxWidth(Double.MAX_VALUE);
        txtVisiblePassword.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnRegister.setMaxWidth(Double.MAX_VALUE);

        // Ensure password fields sync visibility
        txtVisiblePassword.setManaged(false);
        txtVisiblePassword.setVisible(false);
        txtVisiblePassword.textProperty().bindBidirectional(txtPassword.textProperty());

        // Align the eye icon within the password field (no need for an external container)
        StackPane.setMargin(btnShowPassword, new Insets(0, 10, 0, 0)); // Adjust right padding

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

        // Update the eye icon to show password visibility status
        btnShowPassword.setText(isPasswordVisible ? "👁‍🗨" : "👁");
        btnShowPassword.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial Unicode MS';");

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
            DashboardController.setUserRole(userRole);
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

    private void showMessage(String text, String color) {
        lblMessage.setText(text);
        lblMessage.setStyle("-fx-text-fill: " + color + ";");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> lblMessage.setText("")));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
