package com.example.javanetworking.RMIChat.Model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientInterface extends Remote {
    void updateListOfFriends(ArrayList<User> friends) throws RemoteException;

    void newFriend(User user) throws RemoteException;

    void getInvitation(Chat chat) throws RemoteException;

    void showNewMessage(Message msg) throws RemoteException;

    String getDisplayName() throws RemoteException;
}
