package com.example.javanetworking.SocketChat.Model;

import java.net.InetAddress;

public class Chat {
    private final InetAddress initiatorIpAddress;
    private final InetAddress requestedIpAddress;
    private boolean requestAccepted;
    private final String requestTimestamp;
    private String acceptTimestamp;

    public Chat(InetAddress initiatorIpAddress, InetAddress requestedIpAddress, String requestTimestamp) {
        this.initiatorIpAddress = initiatorIpAddress;
        this.requestedIpAddress = requestedIpAddress;
        this.requestTimestamp = requestTimestamp;
    }

    public InetAddress getInitiatorIpAddress() {
        return initiatorIpAddress;
    }

    public InetAddress getRequestedIpAddress() {
        return requestedIpAddress;
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

    public void setRequestAccepted(boolean requestAccepted) {
        this.requestAccepted = requestAccepted;
    }

    public void setAcceptTimestamp(String acceptTimestamp) {
        this.acceptTimestamp = acceptTimestamp;
    }
}
