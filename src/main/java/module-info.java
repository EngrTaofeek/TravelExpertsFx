module com.groupfour.travelexpertsfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.groupfour.travelexpertsfx.controllers to javafx.fxml;
    opens com.groupfour.travelexpertsfx.models to javafx.base;
    exports com.groupfour.travelexpertsfx;
    opens com.groupfour.travelexpertsfx.utils to javafx.fxml;
}
