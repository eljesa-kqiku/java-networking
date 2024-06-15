package com.example.javanetworking.SocketChat.Model;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public interface ClientHandler {
    void setUserData(User userData);
    ArrayList<User> getInvitations();
    void sendInvitation(InetAddress address, String formattedDatetime);
    void acceptInvitation(InetAddress address, String timestamp);
    void sendMessage (Message message);
    void receiveMessage(InetAddress senderIp, String content);
    InetAddress getIpAddress();
}
