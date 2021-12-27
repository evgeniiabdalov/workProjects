module com.example.test_task {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.test_task to javafx.fxml;
    exports com.example.test_task;

}