package com.example.javanetworking.SocketChat;

import java.net.InetAddress;

public class User {
    private final InetAddress ipAddress;
    private final String displayName;
    private final String avatar;

    public User(InetAddress ipAddress, String displayName, String avatar) {
        this.ipAddress = ipAddress;
        this.displayName = displayName;
        this.avatar = avatar;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatar() {
        return avatar;
    }
}
