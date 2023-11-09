module com.example.schedulecontacts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.schedulecontacts to javafx.fxml;
    opens com.example.schedulecontacts.model to javafx.base;

    exports com.example.schedulecontacts;
    exports com.example.schedulecontacts.controller;
    opens com.example.schedulecontacts.controller to javafx.fxml;
}
