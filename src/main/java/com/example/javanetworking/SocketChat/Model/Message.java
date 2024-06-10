package com.example.javanetworking.SocketChat.Model;

import java.net.InetAddress;

public class Message {
    private final InetAddress ipAddress;
    private final String content;

    private final String timestamp;

    public Message(InetAddress ipAddress, String content, String timestamp) {
        this.ipAddress = ipAddress;
        this.content = content;
        this.timestamp = timestamp;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp(){
        return timestamp;
    }
}
