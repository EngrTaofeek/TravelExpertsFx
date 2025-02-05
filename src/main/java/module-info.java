module com.groupfour.travelexpertsfx.travelexpertsfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.groupfour.travelexpertsfx.travelexpertsfx to javafx.fxml;
    exports com.groupfour.travelexpertsfx.travelexpertsfx;
}