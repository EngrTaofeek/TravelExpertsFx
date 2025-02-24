package com.groupfour.travelexpertsfx.utils;

import javafx.scene.control.Alert;

public interface ControllerMethods {
    public static void alertUser(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setHeaderText("STATUS: " + alertType.toString());
        alert.setContentText(message);
        alert.showAndWait();
    }
}
