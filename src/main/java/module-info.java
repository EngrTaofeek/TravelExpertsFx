module com.groupfour.travelexpertsfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.groupfour.travelexpertsfx.controllers to javafx.fxml;
    exports com.groupfour.travelexpertsfx;
}
