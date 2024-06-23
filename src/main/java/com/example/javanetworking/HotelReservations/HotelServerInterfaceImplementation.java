package com.example.javanetworking.HotelReservations;

import com.example.javanetworking.HotelReservations.DatabaseModel.Database;
import com.example.javanetworking.HotelReservations.DatabaseModel.Room;
import com.example.javanetworking.HotelReservations.DatabaseModel.RoomType;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

    public static void main(String[] args) throws RemoteException {
        HotelServerInterfaceImplementation server = new HotelServerInterfaceImplementation();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("HotelServerInterfaceImplementation", server);
        System.out.println("Chat server started.");
//        new HotelServerInterfaceImplementation();
    }
}
