package com.groupfour.travelexpertsfx.controllers;

import com.groupfour.travelexpertsfx.MainApplication;
import com.groupfour.travelexpertsfx.models.CurrentUser;
import com.groupfour.travelexpertsfx.models.UserDb;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for handling the login functionality in the Travel Experts application.
 * Provides user authentication, input validation, and UI enhancements.
 */
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

    /**
     * Initializes the login screen, sets up UI responsiveness, and binds event listeners.
     */
    @FXML
    public void initialize() {

        // Make leftSection resize dynamically with window size
        MainApplication.getPrimaryStage().widthProperty().addListener((obs, oldVal, newVal) -> {
            leftSection.setPrefWidth(newVal.doubleValue() * 0.35);
        });

        MainApplication.getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
            leftSection.setPrefHeight(newVal.doubleValue() * 0.9);
        });

        // Ensure logo resizes properly within the left section
        imgLogo.fitWidthProperty().bind(leftSection.widthProperty().multiply(0.8));
        imgLogo.fitHeightProperty().bind(leftSection.heightProperty().multiply(0.5));

        // Stretch input fields & buttons
        txtEmail.setMaxWidth(Double.MAX_VALUE);
        txtPassword.setMaxWidth(Double.MAX_VALUE);
        txtVisiblePassword.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnRegister.setMaxWidth(Double.MAX_VALUE);

        // Ensure password fields sync visibility
        txtVisiblePassword.setManaged(false);
        txtVisiblePassword.setVisible(false);
        txtVisiblePassword.textProperty().bindBidirectional(txtPassword.textProperty());

        // Adjust eye button alignment
        StackPane.setMargin(btnShowPassword, new Insets(0, 10, 0, 0));

        // Attach button actions
        btnLogin.setOnAction(e -> handleLogin());
        btnRegister.setOnAction(e -> handleRegister());
        btnShowPassword.setOnAction(e -> togglePasswordVisibility());
    }

    /**
     * Toggles password visibility between hidden (PasswordField) and visible (TextField).
     */
    @FXML
    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;

        txtVisiblePassword.setManaged(isPasswordVisible);
        txtVisiblePassword.setVisible(isPasswordVisible);

        txtPassword.setManaged(!isPasswordVisible);
        txtPassword.setVisible(!isPasswordVisible);

        // Update eye icon based on password visibility status
        btnShowPassword.setText(isPasswordVisible ? "👁‍🗨" : "👁");
        btnShowPassword.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial Unicode MS';");
    }

    /**
     * Handles user login by validating credentials and checking authentication from the database.
     */
    @FXML
    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = txtPassword.isVisible() ? txtPassword.getText().trim() : txtVisiblePassword.getText().trim();

        // Validate if fields are empty
        if (email.isEmpty() || password.isEmpty()) {
            showMessage("⚠ Please enter email and password.", "red");
            return;
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showMessage("⚠ Invalid email format.", "red");
            return;
        }

        // Attempt authentication
        String authResult = UserDb.authenticateUser(email, password);

        // Handle authentication result
        switch (authResult) {
            case "email-not-found":
                showMessage("❌ Email not found.", "red");
                break;
            case "wrong-password":
                showMessage("❌ Incorrect password.", "red");
                break;
            case "error":
                showMessage("❌ An internal error occurred. Please try again later.", "red");
                break;
            default:
                // Successful login: Store logged-in user
                CurrentUser.setUser(email, authResult);
                System.out.println("Logged in as: " + CurrentUser.getEmail() + " | Role: " + CurrentUser.getRole());

                // Redirect to the dashboard
                DashboardController.setUserRole(authResult);
                MainApplication.showDashboardScreen();
                break;
        }
    }

    /**
     * Redirects user to the registration page.
     */
    @FXML
    private void handleRegister() {
        MainApplication.showRegisterScreen(); // Redirect to registration page
    }

    /**
     * Displays a temporary message to the user.
     *
     * @param text The message to be displayed.
     * @param color The color of the message text.
     */
    private void showMessage(String text, String color) {
        lblMessage.setText(text);
        lblMessage.setStyle("-fx-text-fill: " + color + ";");

        // Show background only when there is text
        if (!text.isEmpty()) {
            lblMessage.setStyle("-fx-text-fill: " + color + "; -fx-background-color: #FFEBEE;");
        } else {
            lblMessage.setStyle("-fx-background-color: transparent;");
        }

        // Remove message after 3 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            lblMessage.setText("");
            lblMessage.setStyle("-fx-background-color: transparent;");
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

}
