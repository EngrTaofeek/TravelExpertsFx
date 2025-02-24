module com.groupfour.travelexpertsfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jbcrypt;
    requires java.sql;
    requires java.desktop;

    opens com.groupfour.travelexpertsfx.controllers to javafx.fxml;
    opens com.groupfour.travelexpertsfx.models to javafx.base;
  
    opens com.groupfour.travelexpertsfx.utils to javafx.fxml;

    exports com.groupfour.travelexpertsfx;
    exports com.groupfour.travelexpertsfx.controllers;

}
