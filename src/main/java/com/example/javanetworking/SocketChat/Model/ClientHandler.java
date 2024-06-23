package com.example.javanetworking.SocketChat.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public interface ClientHandler {
    void setUserData(User userData);
    void sendInvitation(Chat chat);
    void getInvitation(Chat chat);
    void sendMessage (Message message);
    void receiveMessage(String senderIp, String content);
    void updateFriendList(User friends);
    String getDisplayName();
}
