package com.example.javanetworking.SocketChat.Model;

import java.io.Serializable;

public class Chat implements Serializable {
    private final String initiatorName;
    private final String requestedName;
    private boolean requestAccepted;
    private final String requestTimestamp;
    private String acceptTimestamp;

    public Chat(String initiatorName, String requestedName, String requestTimestamp) {
        this.initiatorName = initiatorName;
        this.requestedName = requestedName;
        this.requestTimestamp = requestTimestamp;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public String getRequestedName() {
        return requestedName;
    }

    public boolean isRequestAccepted() {
        return requestAccepted;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public String getAcceptTimestamp() {
        return acceptTimestamp;
    }

    public void acceptInvite(String acceptTimestamp) {
        this.requestAccepted = true;
        this.acceptTimestamp = acceptTimestamp;
    }
}
