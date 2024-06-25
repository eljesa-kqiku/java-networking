package com.example.javanetworking.RMIChat.Server;

import com.example.javanetworking.RMIChat.Model.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Objects;

public class Server extends UnicastRemoteObject implements ServerInterface {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Chat> chats = new ArrayList<>();
    private final ArrayList<ClientInterface> clients = new ArrayList<>();

    protected Server() throws RemoteException {
    }

    public synchronized void setUser(User user, ClientInterface i) throws RemoteException {
        updateAllFriendLists(user);
        i.updateListOfFriends(this.users);
        users.add(user);
        this.clients.add(i);
    }

    private void updateAllFriendLists(User user) throws RemoteException {
        System.out.println("Updating friends list " + user.getDisplayName());
        for (ClientInterface u : clients) {
            u.newFriend(user);
        }
    }

    public synchronized void invite(Chat newChat) throws RemoteException {
        for (Chat chat : chats) {
            if (Objects.equals(chat.getInitiatorName(), newChat.getInitiatorName()) && Objects.equals(chat.getRequestedName(), newChat.getRequestedName())
                    || Objects.equals(chat.getInitiatorName(), newChat.getRequestedName()) && Objects.equals(chat.getRequestedName(), newChat.getInitiatorName())) {
                if (!chat.isRequestAccepted()) {
                    // if the invitation exists just accept it and notify the initiator
                    chat.acceptInvite(newChat.getRequestTimestamp());
                    ClientInterface initiatorHandler = getHandlerByName(newChat.getInitiatorName());
                    assert initiatorHandler != null;
                    initiatorHandler.getInvitation(newChat);
                }
                return;
            }
        }
        // if no invitation existed before add it and notify the other user
        chats.add(newChat);
        ClientInterface initiatorHandler = getHandlerByName(newChat.getRequestedName());
        if (initiatorHandler != null)
            initiatorHandler.getInvitation(newChat);
    }

    public synchronized void sendMessage(Message msg) throws RemoteException {
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
            System.out.println("You haven't sent an invitation or the requested user hasn't accepted that.");
        // endregion

        ClientInterface clientHandler = null;
        for (ClientInterface ch : clients) {
            if (Objects.equals(ch.getDisplayName(), msg.getReceiverName())) {
                clientHandler = ch;
                break;
            }
        }

        if (clientHandler != null)
            clientHandler.showNewMessage(msg);
    }

    private ClientInterface getHandlerByName(String displayName) throws RemoteException {
        for (ClientInterface usr : clients) {
            if (Objects.equals(usr.getDisplayName(), displayName))
                return usr;
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            ServerInterface server = new Server();
            Registry registry = LocateRegistry.createRegistry(1338);
            registry.rebind("Server", server);
            System.out.println("Chat server started.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
