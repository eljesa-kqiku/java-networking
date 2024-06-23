package com.example.javanetworking.SocketChat.Client;

import com.example.javanetworking.HelloApplication;
import com.example.javanetworking.SocketChat.Model.Chat;
import com.example.javanetworking.SocketChat.Model.FriendInviteStatus;
import com.example.javanetworking.SocketChat.Model.User;
import com.example.javanetworking.SocketChat.Model.Utilities;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client extends Application {
    private Stage primaryStage;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private User me;
    private ChatUI ui = new ChatUI(this);
    private final ArrayList<Chat> chats = new ArrayList<>();
    private ArrayList<User> myFriends = new ArrayList<>();

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

    private void setHandler(){
        new Thread(() -> {
            while (true){
                try {
                    // Getting the list of currently existing users on chat
                    Object obj = input.readObject();
                    if(obj.getClass() == ArrayList.class){
                        if(((ArrayList<?>) obj).size() > 0){
                            if(((ArrayList<?>) obj).get(0).getClass() == User.class){
                                System.out.print("Fetching available users...");
                                for (User u: (ArrayList<User>)obj){
                                    myFriends.add(u);
                                }
                                System.out.println("////////");
                                showFriends();
                            }
                        }
                    }
                    // New friend joined
                    if(obj.getClass() == User.class){
                        System.out.println("A new user joined the chat: " + ((User) obj).getDisplayName());
                        myFriends.add((User) obj);
                        showFriends();
                    }
                    // New/Accepted invitation
                    if(obj.getClass() == Chat.class){
                        getInvitation((Chat) obj);
                    }
                    // new message


                } catch (Exception e) {
                    e.printStackTrace();
                }
        }}  ).start();
    }

    public void showFriends(){
        ui.showFriends(myFriends);
    }

    public void sendInvitation(String username){
        System.out.println("Inviting: " + username);
        try {
            Chat newChat = new Chat(me.getDisplayName(), username, Utilities.getTimestamp());
            output.writeObject(newChat);
            chats.add(newChat);
            showFriends();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void getInvitation(Chat chat){
        if(Objects.equals(chat.getInitiatorName(), me.getDisplayName())){
            // existing invite has been accepted

            Chat ch = null;
            for (Chat item : chats){
                if(item.isRequestAccepted()){
                    chats.remove(item);
                }
                if(Objects.equals(item.getRequestedName(), chat.getRequestedName()))
                    ch = item;
            }
            ch.acceptInvite(chat.getAcceptTimestamp());
            System.out.println("Your invite has been accepted");
            ui.setActiveChatDetails(Objects.requireNonNull(getFriendByName(chat.getRequestedName())));
        }else{
            // or new invite has come
            System.out.println("You have a new invite");
            chats.add(chat);
        }
        showFriends();
    }
    public void acceptInvitation(String username){
        Chat chat = null;
        for (Chat item : chats){
            if(item.isRequestAccepted()){
                chats.remove(item);
            }
            if(Objects.equals(item.getInitiatorName(), username))
                chat = item;
        }
        assert chat != null;
        chat.acceptInvite(Utilities.getTimestamp());
        try {
            output.writeObject(chat);
            ui.setActiveChatDetails(Objects.requireNonNull(getFriendByName(username)));
            showFriends();
            System.out.println("Invite accepted");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public FriendInviteStatus getInvitationStatus(String username){
        Chat chat = null;
        for (Chat exsitingChat : chats){
            if (Objects.equals(exsitingChat.getInitiatorName(), username) || Objects.equals(exsitingChat.getRequestedName(), username)){
                chat = exsitingChat;
            }
        }

        if (chat == null)
            return FriendInviteStatus.NONE;
        else if(chat.isRequestAccepted())
            return FriendInviteStatus.ACCEPTED;
        else if(Objects.equals(chat.getInitiatorName(), username))
            return FriendInviteStatus.RECEIVED_PENDING;
        else
            return FriendInviteStatus.SENT_PENDING;
    }

    private User getFriendByName(String displayName){
        for (User usr : myFriends){
            if(Objects.equals(usr.getDisplayName(), displayName))
                return usr;
        }
        return null;
    }

    public static void main(String[] args) {
        launch();
    }
}