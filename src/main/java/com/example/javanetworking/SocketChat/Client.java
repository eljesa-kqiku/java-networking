package com.example.javanetworking.SocketChat;

import com.example.javanetworking.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Application {
    Stage primaryStage;
    ObjectInputStream input;
    ObjectOutputStream output;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chat/name-avatar-screen.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> {
            try {
                return controllerClass.getConstructor(com.example.javanetworking.SocketChat.Client.class).newInstance(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("SocketChat");
        stage.setScene(scene);
        stage.show();
        primaryStage = stage;
    }

    public void setUser(String userData){
        System.out.println(userData);
        try {
            Socket socket = new Socket("localhost", 8002);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            setHandler();
            StackPane box = new StackPane();
            box.getChildren().add(new Label("Connected. Fetching data."));
            primaryStage.setScene(new Scene(box, 800, 800));
        }catch (Exception e){
            VBox box = new VBox();
            box.getChildren().add(new Label("Couldn't connect to server"));
            primaryStage.setScene(new Scene(box, 400, 400));
        }
    }

    private void setHandler(){
        // read logic
    }

    public static void main(String[] args) {
        launch();
    }
}