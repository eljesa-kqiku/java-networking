package com.example.javanetworking.HotelReservations;

import com.example.javanetworking.HotelReservations.DatabaseModel.Reservation;
import com.example.javanetworking.HotelReservations.DatabaseModel.Room;
import com.example.javanetworking.HotelReservations.DatabaseModel.RoomType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface HotelServerInterface extends Remote {
    Map<RoomType, List<Room>> getAvailableRooms(Date startDate, Date endDate) throws RemoteException;
    void createReservation(String first_name, String last_name, String roomNumber, Date startDate, Date endDate) throws RemoteException;
    List<Reservation> sameDateReservations(Date startDate, Date endDate) throws RemoteException;
    void cancelReservation(UUID reservationID) throws RemoteException;
}

