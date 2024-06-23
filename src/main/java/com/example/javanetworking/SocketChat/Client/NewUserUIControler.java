package com.example.javanetworking.SocketChat.Client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class NewUserUIControler {
    private Client controller;
    private  DropShadow dropShadow = new DropShadow();
    private String selectedAvatar = "1";
    @FXML
    private ImageView av1, av2, av3, av4, av5, av6, av7, av8, av9, av10, av11, av12, av13, av14, av15;
    @FXML
    private TextField displayName;

    private ImageView[] buttons = new ImageView[15];

    public NewUserUIControler(Client c){
        this.controller = c;
    }
    @FXML
    private void initialize() {
        buttons[0] = av1;
        buttons[1] = av2;
        buttons[2] = av3;
        buttons[3] = av4;
        buttons[4] = av5;
        buttons[5] = av6;
        buttons[6] = av7;
        buttons[7] = av8;
        buttons[8] = av9;
        buttons[9] = av10;
        buttons[10] = av11;
        buttons[11] = av12;
        buttons[12] = av13;
        buttons[13] = av14;
        buttons[14] = av15;

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
        controller.setUser(STR."\{displayName.getText()} - \{selectedAvatar}");
    }
    @FXML
    protected void setSelectedAvatar(MouseEvent e){
        String a = ((ImageView) e.getSource()).getId().substring(2);
        buttons[Integer.parseInt(selectedAvatar) - 1].setEffect(null);
        selectedAvatar = a;
        buttons[Integer.parseInt(selectedAvatar) - 1].setEffect(dropShadow);
    }
}
