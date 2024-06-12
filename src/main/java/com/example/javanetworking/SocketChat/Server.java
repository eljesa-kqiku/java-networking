package com.example.javanetworking.SocketChat;

import com.example.javanetworking.SocketChat.Model.Chat;
import com.example.javanetworking.SocketChat.Model.ClientHandler;
import com.example.javanetworking.SocketChat.Model.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Chat> chats = new ArrayList<>();
    private final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        new Thread(() -> {
            while (true){
                try {
                    ClientHandler handler = new ServerSideClientHandler(serverSocket.accept(), this);
                    clientHandlers.add(handler);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    private User getUserByIp(InetAddress address){
        for (User usr : users){
            if(usr.getIpAddress() == address)
                return usr;
        }
        return null;
    }
    public void setUser(User user){
        users.removeIf(existing_user -> existing_user.getIpAddress() == user.getIpAddress());
        users.add(user);
    }
    public ArrayList<User> getInvitations(InetAddress address){
        ArrayList<User> invitations = new ArrayList<>();
        for (Chat chat: chats){
            if(chat.isRequestAccepted() && chat.getRequestedIpAddress().getHostAddress() == address.getHostAddress()){
                invitations.add(getUserByIp(chat.getInitiatorIpAddress()));
            }
        }
        return invitations;
    }

    public void invite(InetAddress initiatorIp, InetAddress requestedIp, String formattedDateTime){
        for (Chat chat : chats) {
            if(chat.getInitiatorIpAddress() == initiatorIp && chat.getRequestedIpAddress() == requestedIp
                    || chat.getInitiatorIpAddress() == requestedIp && chat.getRequestedIpAddress() == initiatorIp){
                return;
            }
            chats.add(new Chat(initiatorIp, requestedIp, formattedDateTime));
        }

    }

    public void acceptInvitation(InetAddress requestedAddress, InetAddress initiatorAddress, String timestamp){
        for (Chat chat : chats){
            if(chat.getRequestedIpAddress() == requestedAddress && chat.getInitiatorIpAddress() == initiatorAddress){
                chat.acceptInvite(timestamp);
                break;
            }
        }
    }

    public void sendMessage(InetAddress senderIp, InetAddress receiverIp, String content) throws Exception {
        // region Making sure they have a chat in common
        boolean found = false;
        for (Chat chat : chats){
            if((chat.getRequestedIpAddress() == senderIp && chat.getInitiatorIpAddress() == receiverIp ||
                    chat.getRequestedIpAddress() == receiverIp && chat.getInitiatorIpAddress() == senderIp) && chat.isRequestAccepted()){
                found = true;
                break;
            }
        }
        if(!found)
            throw new Exception("You haven't sent an invitation or the requested user hasn't accepted that.");
        // endregion

        ClientHandler clientHandler = null;
        for (ClientHandler ch : clientHandlers){
            if(ch.getIpAddress() == receiverIp){
                clientHandler = ch;
                break;
            }
        }

        if(clientHandler != null)
            clientHandler.receiveMessage(senderIp, content);
    }

    public static void main(String[] args) throws IOException {
        new Server().start();
    }
}
