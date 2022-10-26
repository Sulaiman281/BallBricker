module com.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.main to javafx.fxml;
    opens com.main.controller to javafx.fxml;
    exports com.main;
}