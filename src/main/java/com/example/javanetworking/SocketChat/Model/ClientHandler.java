package com.example.javanetworking.SocketChat.Model;

public interface ClientHandler {
    void setUserData(User userData);

    void sendInvitation(Chat chat);

    void getInvitation(Chat chat);

    void sendMessage(Message message);

    void receiveMessage(Message message);

    void updateFriendList(User friends);

    String getDisplayName();
}
