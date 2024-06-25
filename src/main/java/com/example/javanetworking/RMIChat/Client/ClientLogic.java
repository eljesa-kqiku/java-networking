package com.example.javanetworking.RMIChat.Client;

import com.example.javanetworking.RMIChat.Model.Chat;
import com.example.javanetworking.RMIChat.Model.Message;
import com.example.javanetworking.RMIChat.Model.User;
import com.example.javanetworking.RMIChat.Server.ServerInterface;

import java.lang.reflect.Array;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientLogic extends UnicastRemoteObject implements ClientInterface{
    public ServerInterface server;
    private ClientUIController uiController;
    protected ClientLogic(ClientUIController uiController) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1338);
        server = (ServerInterface) registry.lookup("Server");
        this.uiController = uiController;
    }

    public void setUserData(User user) throws RemoteException {
        server.setUser(user, this);
    }
    public void sendInvitation(Chat chat) throws RemoteException {
        server.invite(chat);
    }
    public void sendMessage(Message msg) throws RemoteException {
        server.sendMessage(msg);
    }

    // can bee seen from server -- used as input
    @Override
    public void updateListOfFriends(ArrayList<User> friends){
        uiController.updateListOfFriends(friends);
    }
    @Override
    public void newFriend(User user){
        uiController.newFriend(user);
    }
    @Override
    public void getInvitation(Chat chat){
        uiController.getInvitation(chat);
    }
    @Override
    public void showNewMessage(Message msg){
        uiController.showNewMessage(msg);
    }
    @Override
    public String getDisplayName(){
        System.out.println("my name: " + uiController.getName());
        return uiController.getName();
    }
}
