package com.example.javanetworking.SocketChat.Client;

import com.example.javanetworking.HelloApplication;
import com.example.javanetworking.SocketChat.Model.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends Application {
    private Stage primaryStage;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private User me;
    private ChatUI ui = new ChatUI();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chat/name-avatar-screen.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> {
            try {
                return controllerClass.getConstructor(Client.class).newInstance(this);
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
        try {
            Socket socket = new Socket("localhost", 8002);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            String[] myData = userData.split("-");
            me = new User(socket.getInetAddress(), myData[0].trim(), myData[1].trim());
            output.writeObject(me);

            StackPane box = new StackPane();
            Label connectionInfo = new Label("Connected. Fetching data.");
            box.getChildren().add(connectionInfo);
            primaryStage.setScene(new Scene(box, 800, 800));

            primaryStage.setScene(new Scene(ui, 1200, 900));
            ui.showMyData(me);
            this.setHandler();

            primaryStage.centerOnScreen();
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            StackPane box = new StackPane();
            box.getChildren().add(new Label("Couldn't connect to server"));
            primaryStage.setScene(new Scene(box, 400, 400));
        }
    }
    public void showFriends(ArrayList<User> friends){
        // TODO: fix ip address
//        List<User> filteredFriends = friends.stream().filter(i -> i.getIpAddress() != me.getIpAddress()).toList();
        ui.showFriends(friends);
    }

    private void setHandler(){
        new Thread(() -> {
            while (true){
                try {
                    Object obj = input.readObject();
                    // new friend joined
                    if(obj.getClass() == ArrayList.class){
                        if(((ArrayList<?>) obj).size() > 0){
                            if(((ArrayList<?>) obj).get(0).getClass() == User.class){
                                System.out.print("New friend joined");
                                for (User u: (ArrayList<User>)obj){
                                    System.out.print(u.getDisplayName() + ", ");
                                }
                                System.out.println("////////");
                                showFriends((ArrayList<User>) obj);
                            }
                        }
                    }
                    // new invitation
                    // new message


                } catch (Exception e) {
                    e.printStackTrace();
                }
        }}  ).start();
    }

    public static void main(String[] args) {
        launch();
    }
}