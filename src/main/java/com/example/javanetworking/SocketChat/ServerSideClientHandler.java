package com.example.javanetworking.SocketChat;

import com.example.javanetworking.SocketChat.Model.ClientHandler;
import com.example.javanetworking.SocketChat.Model.Message;
import com.example.javanetworking.SocketChat.Model.User;
import com.example.javanetworking.SocketChat.Model.Utilities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSideClientHandler implements ClientHandler {
    private final Server server;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private User currentUser;

    public ServerSideClientHandler(Socket socket, Server server) throws IOException {
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.server = server;
    }
    @Override
    public void setUserData(User userData) {
        this.currentUser = userData;
        server.setUser(userData);
    }

    // TODO: is this necessary
    @Override
    public ArrayList<User> getInvitations() {
        if(this.currentUser == null)
            return null;
        return server.getInvitations(this.currentUser.getIpAddress());
    }

    @Override
    public void sendInvitation(InetAddress address, String formattedDateTime) {
        // TODO: read from input
        server.invite(currentUser.getIpAddress(), address, formattedDateTime);
    }

    @Override
    public void acceptInvitation(InetAddress address, String timestamp) {
        // TODO: read from input
        server.acceptInvitation(currentUser.getIpAddress(), address, timestamp);
    }

    @Override
    public void sendMessage(Message message) {
        try {
            // TODO: read from input
            server.sendMessage(message.getSenderIpAddress(), message.getReceiverIpAddress(), message.getContent());
        }catch (Exception e){
            // TODO: try to send this error to client
        }
    }

    @Override
    public void receiveMessage(InetAddress senderIp, String content) {
        // TODO: add timestamp to users sent time not server received time
        Message msg = new Message(senderIp, getIpAddress(), content, Utilities.getTimestamp());
        // TODO: write to output
    }

    @Override
    public InetAddress getIpAddress() {
        return currentUser.getIpAddress();
    }
}
