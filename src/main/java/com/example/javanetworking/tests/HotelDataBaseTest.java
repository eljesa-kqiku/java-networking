package com.example.javanetworking.tests;

import com.example.javanetworking.HotelReservations.DatabaseModel.Database;
import com.example.javanetworking.HotelReservations.DatabaseModel.Room;

import java.util.ArrayList;
import java.util.Date;

public class HotelDataBaseTest {
    public static void main(String[] args) {
        Database db = Database.getInstance();
        ArrayList<Room> availableRooms = db.getAvailableRooms(new Date("2024-02-02"), new Date("2024-02-02"));

//        for (int i = 1; i <= 38; i++) {
//            db.createReservation("John", "Doe", availableRooms.get(i).getId(), new Date("2024-02-02"), new Date(2024, 2, 3));
//        }
//
//        ArrayList<Room> availableRooms2 = db.getAvailableRooms(new Date("2024-02-02"), new Date(2024, 2, 3));
//        System.out.println(availableRooms2.size());
//
//        ArrayList<Room> availableRooms3 = db.getAvailableRooms(new Date(2024, 2, 5), new Date(2024, 2, 7));
//        System.out.println(availableRooms3.size());
//
//        ArrayList<Room> availableRooms4 = db.getAvailableRooms(new Date(2024, 2, 1), new Date(2024, 2, 7));
//        System.out.println(availableRooms4.size());
    }
}
