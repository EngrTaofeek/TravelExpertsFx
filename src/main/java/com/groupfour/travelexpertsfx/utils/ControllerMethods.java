package com.groupfour.travelexpertsfx.utils;

/**
 * @Author: Kazi Fattah
 * @Date: 2/2025
 * @Description: Common methods used in the program
 * @To-do-list:
 *


 *
 */

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerMethods {
    public static void alertUser(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setHeaderText("STATUS: " + alertType.toString());
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
