package com.example.javanetworking.RMIChat.Model;

import java.io.Serializable;

public class Message implements Serializable {
    private final String senderName;
    private final String receiverName;
    private final String content;
    private final String timestamp;

    public Message(String senderName, String receiverName, String content, String timestamp) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
