package com.example.javanetworking.RMIChat.Server;

import com.example.javanetworking.RMIChat.Client.ClientInterface;
import com.example.javanetworking.RMIChat.Model.Chat;
import com.example.javanetworking.RMIChat.Model.Message;
import com.example.javanetworking.RMIChat.Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public void setUser(User user, ClientInterface i) throws RemoteException;
    public void invite(Chat newChat) throws RemoteException;
    public void sendMessage(Message msg) throws RemoteException;
}
