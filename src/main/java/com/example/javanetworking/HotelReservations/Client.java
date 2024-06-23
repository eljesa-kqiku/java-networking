package com.example.javanetworking.HotelReservations;

import com.example.javanetworking.HotelReservations.DatabaseModel.Room;
import com.example.javanetworking.HotelReservations.DatabaseModel.RoomType;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) throws RemoteException {
        new Client();
    }
    private HotelServerInterface server;
    Scanner scanner;

    public Client() throws RemoteException {
        scanner = new Scanner(System.in);
        initializeRMI();
        System.out.println("     Welcome to RMI Hotel!");
        System.out.println("--------------------------------");

        while (true){
            System.out.println("Start date of reservation (Write like 2024-06-17): ");
            String start_date = scanner.nextLine();
            System.out.println("End date of reservation (Write like 2024-06-17): ");
            String end_date = scanner.nextLine();

            String[] dates1 = start_date.split("-");
            Date startDate = new Date(Integer.parseInt(dates1[0]), Integer.parseInt(dates1[1]), Integer.parseInt(dates1[2]));
            String[] dates2 = end_date.split("-");
            Date endDate = new Date(Integer.parseInt(dates2[0]), Integer.parseInt(dates2[1]), Integer.parseInt(dates2[2]));

            System.out.println("\n\nThe list of available room types on selected dates: \n");
            System.out.println("-------------------------------------------");
            Map<RoomType, List<Room>> availableRooms = server.getAvailableRooms(startDate, endDate);
            for (RoomType rt : availableRooms.keySet()){
                List<Room> rooms = availableRooms.get(rt);
                System.out.println(rt.getName());
                System.out.println(rt.getDescription());
                System.out.println("Available rooms(" + rooms.size() + "): " + rooms.stream().map(Room::getName).collect(Collectors.joining(",")));
                System.out.println("Number of people: " + rt.getNumPersons());
                System.out.println("Price: " + rt.getPrice() + '\u20ac');
                System.out.println("-------------------------------------------");
            }

            System.out.println();
            System.out.println("Type room number: ");
            String roomNumber = scanner.nextLine();
            System.out.println("First Name: ");
            String first_name = scanner.nextLine();
            System.out.println("Last Name: ");
            String last_name = scanner.nextLine();

            server.createReservation(first_name, last_name, roomNumber, startDate, endDate);
            System.out.println("Reservation created successfully!");
        }
    }

    protected void initializeRMI(){
        String host = "";
        try{
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            server = (HotelServerInterface) registry.lookup("HotelServerInterfaceImplementation");
            System.out.println("Server object" + server + " found.");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
