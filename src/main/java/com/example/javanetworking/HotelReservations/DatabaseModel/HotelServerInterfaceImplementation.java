package com.example.javanetworking.HotelReservations.DatabaseModel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class HotelServerInterfaceImplementation extends UnicastRemoteObject implements HotelServerInterface {
    Database database;
    public HotelServerInterfaceImplementation() throws RemoteException {
        super();
        this.database = Database.getInstance();
    }
    public Map<RoomType, List<Room>> getAvailableRooms(Date startDate, Date endDate) throws RemoteException {
        return database.getStructuredAvailableRooms(startDate, endDate);
    }
    public void createReservation(String first_name, String last_name, String roomNumber, Date startDate, Date endDate) throws RemoteException {
        database.createReservation(first_name, last_name, roomNumber, startDate, endDate);
    }
}
