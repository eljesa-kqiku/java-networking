package com.example.javanetworking.SocketChat;

import com.example.javanetworking.SocketChat.Model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ServerSideClientHandler implements ClientHandler, Runnable{
    private final Server server;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final Socket socket;
    private User currentUser;

    public ServerSideClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());
    }
    public void run() {
        while (true) {
            try {
                Object data = input.readObject();

                // Setting user details
                if(data.getClass() == User.class){
                    setUserData((User) data);
                    ArrayList<User> allOtherUsers = new ArrayList<>();
                    for(User otherUser : server.getUsers()){
                        if(!otherUser.getDisplayName().equals(currentUser.getDisplayName()))
                            allOtherUsers.add(otherUser);
                    }
                    output.writeObject(allOtherUsers);
                }
                // Invitation created/accepted
                if(data.getClass() == Chat.class){
                    sendInvitation((Chat) data);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void setUserData(User userData) {
        this.currentUser = userData;
        server.setUser(userData);
    }

    public void updateFriendList(User newUser){
        try{
            if(!newUser.getDisplayName().equals(currentUser.getDisplayName()))
                output.writeObject(newUser);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void sendInvitation(Chat chat) {
        server.invite(chat);
    }
    @Override
    public void getInvitation(Chat chat) {
        try {
            output.writeObject(chat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    @Override
    public void sendMessage(Message message) {
        try {
            // TODO: read from input
            server.sendMessage(message.getSenderName(), message.getReceiverName(), message.getContent());
        }catch (Exception e){
            // TODO: try to send this error to client
        }
    }

    @Override
    public void receiveMessage(String senderName, String content) {
        // TODO: add timestamp to users sent time not server received time
        Message msg = new Message(senderName, currentUser.getDisplayName(), content, Utilities.getTimestamp());
        // TODO: write to output
    }


    public String getDisplayName(){
        return currentUser.getDisplayName();
    }
}
