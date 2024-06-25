package com.example.javanetworking.HotelReservations;

import com.example.javanetworking.HotelReservations.DatabaseModel.Reservation;
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
            System.out.println("What action do you want to perform? ");
            System.out.println("Type 'N' to create a new reservation");
            System.out.println("Type 'C' to cancel an existing reservation");

            String mode = scanner.nextLine();
            if(Objects.equals(mode, "N") || Objects.equals(mode, "n"))
                createReservation();
            else if(Objects.equals(mode, "C") || Objects.equals(mode, "c"))
                cancelReservation();
            else
                System.out.println("Bad input. Resetting...");

            System.out.println("-------------------------------------------\n");
        }
    }

    private void createReservation() throws RemoteException {
        System.out.println("Creating a new reservation...\n");
        System.out.println("Start date of reservation (Format: 2024-06-17): ");
        String start_date = scanner.nextLine();
        System.out.println("End date of reservation (Format: 2024-06-17): ");
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
        System.out.println("Type the room number (Physical room, like 100, 201, ..): ");
        String roomNumber = scanner.nextLine();
        System.out.println("First Name: ");
        String first_name = scanner.nextLine();
        System.out.println("Last Name: ");
        String last_name = scanner.nextLine();

        server.createReservation(first_name, last_name, roomNumber, startDate, endDate);
        System.out.println("Reservation created successfully!");
    }

    private void cancelReservation() throws RemoteException {
        System.out.println("Canceling reservation...\n");

        System.out.println("Searching for existing reservations...\n" +
                "Will need you to type the start date and end date of the reservation you want to cancel (or start and end dates that contain the reservation within): ");
        System.out.println("Start date (Format: 2024-06-17): ");
        String start_date = scanner.nextLine();
        System.out.println("End date (Format: 2024-06-17): ");
        String end_date = scanner.nextLine();

        String[] dates1 = start_date.split("-");
        Date startDate = new Date(Integer.parseInt(dates1[0]), Integer.parseInt(dates1[1]), Integer.parseInt(dates1[2]));
        String[] dates2 = end_date.split("-");
        Date endDate = new Date(Integer.parseInt(dates2[0]), Integer.parseInt(dates2[1]), Integer.parseInt(dates2[2]));

        System.out.println("Searching...");

        List<Reservation> existingReservations = server.sameDateReservations(startDate, endDate);

        if(existingReservations.isEmpty()){
            System.out.println("No reservations found on specified dates, please try other data.");
        }else{
            for (int i = 0; i < existingReservations.size(); i++) {
                Reservation res = existingReservations.get(i);

                System.out.println(STR."\{i}: \{res.getDescription()}");
            }
            System.out.println("Pick the reservation index: ");
            int index = scanner.nextInt();
            if(index >= 0 && index < existingReservations.size()){
                server.cancelReservation(existingReservations.get(index).getId());
                System.out.println("Reservation cancelled successfully!");
            }else{
                System.out.println("Invalid input. Cancelling action...");
            }
        }
    }

    protected void initializeRMI(){
        String host = "";
        try{
            Registry registry = LocateRegistry.getRegistry("localhost", 1339);
            server = (HotelServerInterface) registry.lookup("HotelServerInterfaceImplementation");
            System.out.println("Server object" + server + " found.");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
