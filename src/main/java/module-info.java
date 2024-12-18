module com.example.oopproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oopproject to javafx.fxml;
    exports com.example.oopproject;
}