package com.example.javanetworking.RMIChat.Model;

import java.io.Serializable;

public class User implements Serializable {
    private final String displayName;
    private final String avatar;
    private UserState state;

    public User(String displayName, String avatar) {
        this.displayName = displayName;
        this.avatar = avatar;
        this.state = UserState.ACTIVE;
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
