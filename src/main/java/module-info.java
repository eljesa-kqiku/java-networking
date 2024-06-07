module com.example.javanetworking {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javanetworking to javafx.fxml;
    exports com.example.javanetworking;
    opens com.example.javanetworking.AESEncryptor to javafx.fxml;
    exports com.example.javanetworking.AESEncryptor;
}