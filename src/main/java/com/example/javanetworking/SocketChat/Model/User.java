package com.example.javanetworking.SocketChat.Model;

import java.net.InetAddress;

public class User {
    private final InetAddress ipAddress;
    private final String displayName;
    private final String avatar;
    private UserState state;
    public User(InetAddress ipAddress, String displayName, String avatar) {
        this.ipAddress = ipAddress;
        this.displayName = displayName;
        this.avatar = avatar;
        this.state = UserState.ACTIVE;
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

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }
}
