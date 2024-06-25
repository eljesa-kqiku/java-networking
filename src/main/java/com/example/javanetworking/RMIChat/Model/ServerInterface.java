package com.example.javanetworking.RMIChat.Model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void setUser(User user, ClientInterface i) throws RemoteException;

    void invite(Chat newChat) throws RemoteException;

    void sendMessage(Message msg) throws RemoteException;
}
