package com.example.javanetworking.SocketChat.Server;

import com.example.javanetworking.SocketChat.Model.Chat;
import com.example.javanetworking.SocketChat.Model.ClientHandler;
import com.example.javanetworking.SocketChat.Model.Message;
import com.example.javanetworking.SocketChat.Model.User;
import com.example.javanetworking.SocketChat.Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSideClientHandler implements ClientHandler, Runnable {
    private final Server server;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
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
                if (data.getClass() == User.class) {
                    setUserData((User) data);
                    ArrayList<User> allOtherUsers = new ArrayList<>();
                    for (User otherUser : server.getUsers()) {
                        if (!otherUser.getDisplayName().equals(currentUser.getDisplayName()))
                            allOtherUsers.add(otherUser);
                    }
                    output.writeObject(allOtherUsers);
                }
                // Invitation created/accepted
                if (data.getClass() == Chat.class) {
                    sendInvitation((Chat) data);
                }
                // New message sent
                if (data.getClass() == Message.class) {
                    sendMessage((Message) data);
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

    public void updateFriendList(User newUser) {
        try {
            if (!newUser.getDisplayName().equals(currentUser.getDisplayName()))
                output.writeObject(newUser);
        } catch (Exception e) {
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
            server.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(Message msg) {
        try {
            output.writeObject(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getDisplayName() {
        return currentUser.getDisplayName();
    }
}
