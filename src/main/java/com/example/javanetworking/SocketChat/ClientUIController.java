package com.example.javanetworking.SocketChat;

import com.example.javanetworking.SocketChat.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;

public class ClientUIController {
    private Client controller;
    private  DropShadow dropShadow = new DropShadow();
    private String selectedAvatar = "1";
    @FXML
    private Button av1, av2, av3;
    @FXML
    private TextField displayName;

    private Button[] buttons = new Button[12];

    public ClientUIController(Client c){
        this.controller = c;
    }
    @FXML
    private void initialize() {
        buttons[0] = av1;
        buttons[1] = av2;
        buttons[2] = av3;


        dropShadow.setInput(null); // Setting input to null achieves "three-pass-box" effect
        dropShadow.setColor(javafx.scene.paint.Color.rgb(54,155,176));
        dropShadow.setWidth(10);
        dropShadow.setHeight(10);
        dropShadow.setRadius(10);

        buttons[Integer.parseInt(selectedAvatar) - 1].setEffect(dropShadow);
    }
    @FXML
    protected void onSubmit() {
        if(displayName.getText().isEmpty())
            return;
        controller.setUser(STR."\{displayName.getText()} | \{selectedAvatar}");
    }
    @FXML
    protected void setSelectedAvatar(ActionEvent e){
        String a = ((Button) e.getSource()).getId().substring(2);
        buttons[Integer.parseInt(selectedAvatar) - 1].setEffect(null);
        selectedAvatar = a;
        buttons[Integer.parseInt(selectedAvatar) - 1].setEffect(dropShadow);
    }
}
