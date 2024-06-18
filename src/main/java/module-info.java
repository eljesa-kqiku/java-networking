module com.example.javanetworking {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javanetworking to javafx.fxml;
    exports com.example.javanetworking;

    opens com.example.javanetworking.AESEncryptor to javafx.fxml;
    exports com.example.javanetworking.AESEncryptor;

    opens com.example.javanetworking.SocketChat to javafx.fxml;
    exports com.example.javanetworking.SocketChat;
    exports com.example.javanetworking.SocketChat.Model;
    opens com.example.javanetworking.SocketChat.Model to javafx.fxml;
    exports com.example.javanetworking.SocketChat.Client;
    opens com.example.javanetworking.SocketChat.Client to javafx.fxml;

    exports com.example.javanetworking.HotelReservations;
    opens com.example.javanetworking.HotelReservations to javafx.fxml;
}