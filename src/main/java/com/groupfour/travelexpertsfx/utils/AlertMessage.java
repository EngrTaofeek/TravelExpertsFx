package com.groupfour.travelexpertsfx.utils;

/**
 * @Author DarylWxc
 * @Date 2/21/2025
 * @Description Alert Class
 */
public class AlertMessage {
    /**
     * Alert Message to User
     * @param alertType
     * @param message
     */
    public void alertMessage(javafx.scene.control.Alert.AlertType alertType, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
