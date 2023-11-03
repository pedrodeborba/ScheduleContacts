module com.example.schedulecontacts {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.schedulecontacts to javafx.fxml;
    exports com.example.schedulecontacts;
}