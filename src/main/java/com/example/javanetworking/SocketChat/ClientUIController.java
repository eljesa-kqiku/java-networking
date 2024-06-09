package com.example.javanetworking.SocketChat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientUIController {
    @FXML
    private TextField displayName;

    @FXML
    protected void onSubmit() {
        System.out.println("submit");
    }
}
