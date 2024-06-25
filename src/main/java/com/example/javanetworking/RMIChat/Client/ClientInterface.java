package com.example.javanetworking.RMIChat.Client;

import com.example.javanetworking.RMIChat.Model.Chat;
import com.example.javanetworking.RMIChat.Model.Message;
import com.example.javanetworking.RMIChat.Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientInterface extends Remote {
    public void updateListOfFriends(ArrayList<User> friends) throws RemoteException;
    public void newFriend(User user) throws RemoteException;
    public void getInvitation(Chat chat) throws RemoteException;
    public void showNewMessage(Message msg) throws RemoteException;
    public String getDisplayName() throws RemoteException;
}
