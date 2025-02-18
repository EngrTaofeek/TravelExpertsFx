package com.groupfour.travelexpertsfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {
    @FXML
    private Label lblWelcome;

    public void initialize() {
        lblWelcome.setText("Welcome to Travel Experts Dashboard!");
    }
}
