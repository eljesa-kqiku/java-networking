package com.example.javanetworking.SocketChat.Server;

import com.example.javanetworking.SocketChat.Model.Chat;
import com.example.javanetworking.SocketChat.Model.ClientHandler;
import com.example.javanetworking.SocketChat.Model.Message;
import com.example.javanetworking.SocketChat.Model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Server {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Chat> chats = new ArrayList<>();
    private final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8002);
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            ServerSideClientHandler handler = new ServerSideClientHandler(socket, this);
                            System.out.println(STR."A new user joined:\{socket.getInetAddress()}");
                            clientHandlers.add(handler);
                            handler.run();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void setUser(User user) {
//        users.removeIf(existing_user -> existing_user.getIpAddress() == user.getIpAddress());
        updateAllFriendLists(user);
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    private void updateAllFriendLists(User user) {
        System.out.println("Updating friends list " + user.getDisplayName());
        for (ClientHandler u : clientHandlers) {
            u.updateFriendList(user);
        }
    }

    public void invite(Chat newChat) {
        for (Chat chat : chats) {
            if (Objects.equals(chat.getInitiatorName(), newChat.getInitiatorName()) && Objects.equals(chat.getRequestedName(), newChat.getRequestedName())
                    || Objects.equals(chat.getInitiatorName(), newChat.getRequestedName()) && Objects.equals(chat.getRequestedName(), newChat.getInitiatorName())) {
                if (!chat.isRequestAccepted()) {
                    // if the invitation exists just accept it and notify the initiator
                    chat.acceptInvite(newChat.getRequestTimestamp());
                    ClientHandler initiatorHandler = getHandlerByName(newChat.getInitiatorName());
                    assert initiatorHandler != null;
                    initiatorHandler.getInvitation(newChat);
                }
                return;
            }
        }
        // if no invitation existed before add it and notify the other user
        chats.add(newChat);
        ClientHandler initiatorHandler = getHandlerByName(newChat.getRequestedName());
        assert initiatorHandler != null;
        initiatorHandler.getInvitation(newChat);
    }

    private User getUserByName(String displayName) {
        for (User usr : users) {
            if (Objects.equals(usr.getDisplayName(), displayName))
                return usr;
        }
        return null;
    }

    private ClientHandler getHandlerByName(String displayName) {
        for (ClientHandler usr : clientHandlers) {
            if (Objects.equals(usr.getDisplayName(), displayName))
                return usr;
        }
        return null;
    }

    public void sendMessage(Message msg) throws Exception {
        // region Making sure they have a chat in common
        boolean found = false;
        for (Chat chat : chats) {
            if ((Objects.equals(chat.getRequestedName(), msg.getSenderName()) && Objects.equals(chat.getInitiatorName(), msg.getReceiverName()) ||
                    Objects.equals(chat.getRequestedName(), msg.getReceiverName()) && Objects.equals(chat.getInitiatorName(), msg.getSenderName())) && chat.isRequestAccepted()) {
                found = true;
                break;
            }
        }
        if (!found)
            throw new Exception("You haven't sent an invitation or the requested user hasn't accepted that.");
        // endregion

        ClientHandler clientHandler = null;
        for (ClientHandler ch : clientHandlers) {
            if (Objects.equals(ch.getDisplayName(), msg.getReceiverName())) {
                clientHandler = ch;
                break;
            }
        }

        if (clientHandler != null)
            clientHandler.receiveMessage(msg);
    }

    public static void main(String[] args) throws IOException {
        new Server().start();
    }
}
