module com.example.javanetworking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;


    opens com.example.javanetworking to javafx.fxml;
    exports com.example.javanetworking;

    opens com.example.javanetworking.AESEncryptor to javafx.fxml;
    exports com.example.javanetworking.AESEncryptor;

    opens com.example.javanetworking.SocketChat.Server to javafx.fxml;
    exports com.example.javanetworking.SocketChat.Server;
    exports com.example.javanetworking.SocketChat.Model;
    opens com.example.javanetworking.SocketChat.Model to javafx.fxml;
    exports com.example.javanetworking.SocketChat.Client;
    opens com.example.javanetworking.SocketChat.Client to javafx.fxml;

    opens com.example.javanetworking.RMIChat.Server to javafx.fxml;
    exports com.example.javanetworking.RMIChat.Server;
    exports com.example.javanetworking.RMIChat.Model;
    opens com.example.javanetworking.RMIChat.Model to javafx.fxml;
    exports com.example.javanetworking.RMIChat.Client;
    opens com.example.javanetworking.RMIChat.Client to javafx.fxml;

    exports com.example.javanetworking.HotelReservations.DatabaseModel;
    opens com.example.javanetworking.HotelReservations.DatabaseModel to javafx.fxml;
    exports com.example.javanetworking.HotelReservations;
    opens com.example.javanetworking.HotelReservations to javafx.fxml;
}