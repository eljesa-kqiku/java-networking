package com.example.javanetworking.SocketChat.Model;

import java.io.Serializable;
import java.net.InetAddress;

public class Message implements Serializable {
    private final InetAddress senderIpAddress;
    private final InetAddress receiverIpAddress;
    private final String content;
    private final String timestamp;

    public Message(InetAddress senderIpAddress, InetAddress receiverIpAddress, String content, String timestamp) {
        this.senderIpAddress = senderIpAddress;
        this.receiverIpAddress = receiverIpAddress;
        this.content = content;
        this.timestamp = timestamp;
    }

    public InetAddress getSenderIpAddress() {
        return senderIpAddress;
    }

    public InetAddress getReceiverIpAddress() {
        return receiverIpAddress;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp(){
        return timestamp;
    }
}
