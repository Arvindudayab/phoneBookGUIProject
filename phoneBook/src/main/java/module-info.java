module com.example.phonebook {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.phonebook to javafx.fxml;
    exports com.example.phonebook;
}