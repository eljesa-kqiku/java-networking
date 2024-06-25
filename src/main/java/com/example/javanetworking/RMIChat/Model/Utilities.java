package com.example.javanetworking.RMIChat.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utilities {
    public static String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
