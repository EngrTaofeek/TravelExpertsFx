module com.groupfour.travelexpertsfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jbcrypt;
    requires java.sql;

    opens com.groupfour.travelexpertsfx.controllers to javafx.fxml;
    opens com.groupfour.travelexpertsfx.models to javafx.base;

    exports com.groupfour.travelexpertsfx;
    exports com.groupfour.travelexpertsfx.controllers;
}
