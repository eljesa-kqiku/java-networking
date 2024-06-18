package com.example.javanetworking.HotelReservations.DatabaseModel;

import java.util.*;
import java.util.stream.Collectors;

public class Database {
    private final ArrayList<RoomType> roomTypes;
    private final ArrayList<Room> rooms;
    private final ArrayList<Guest> guests;
    private final ArrayList<Reservation> reservations;
    private static Database instance = null;
    private Database(){
        //region Room Types
            this.roomTypes = new ArrayList<>();
            roomTypes.add(new RoomType("Single Standard Room", 55, 1, "Equipped with modern amenities including TV, WiFi and coffee maker, our Standard Room ensures you stay connected and refreshed throughout your stay. Whether you're traveling solo or as a couple, this room type is perfect for those seeking comfort and affordability without compromising on quality."));
            roomTypes.add(new RoomType("Queen Room with balcony", 75, 2, "Step out onto your private balcony to savor panoramic views, creating a perfect backdrop for a morning coffee or evening tea. Inside, modern amenities such as a flat-screen TV, complimentary WiFi, and a cozy seating area provide everything you need for a comfortable stay."));
            roomTypes.add(new RoomType("Deluxe King Room with pool", 80, 2, "Experience luxury and relaxation in our Deluxe King Room with pool access, designed to elevate your stay to new heights of comfort and convenience. Indulge in the lavish comfort of a king-sized bed, adorned with premium linens and plush pillows, ensuring a restful night's sleep. The room is elegantly appointed with contemporary furnishings and tasteful decor, creating a serene ambiance that complements the tranquil poolside setting."));
            roomTypes.add(new RoomType("Pod Style Triple Room", 150, 3, "Welcome to our innovative Pod Style Triple Room, where modern design meets efficiency to maximize comfort and space. This unique accommodation is perfect for a small group of friends or a family seeking a cozy yet functional stay."));
            roomTypes.add(new RoomType("Double Studio Room", 230, 4, "Enjoy the convenience of two comfortable double beds, each fitted with soft linens and ample pillows for a rejuvenating night's sleep. The room is equipped with a small kitchenette area, complete with a microwave, mini-fridge, and coffee maker, ideal for preparing light meals or snacks at your convenience."));
        // endregion

        // region Rooms
            rooms = new ArrayList<>();
            for (int i = 0; i < 10; i++)
                rooms.add(new Room(roomTypes.get(0).getId(), 10 + "i"));
            for (int i = 0; i < 20; i++)
                rooms.add(new Room(roomTypes.get(1).getId(), 20 + "i"));
            for (int i = 0; i < 5; i++)
                rooms.add(new Room(roomTypes.get(2).getId(), 30 + "i"));
            for (int i = 0; i < 3; i++)
                rooms.add(new Room(roomTypes.get(3).getId(), 40 + "i"));
            for (int i = 0; i < 2; i++)
                rooms.add(new Room(roomTypes.get(4).getId(), 50 + "i"));
        // endregion

        reservations = new ArrayList<>();
        guests = new ArrayList<>();
    }
    public static synchronized Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }
    public void createReservation(String clientFirstName, String clientLastName, String roomNumber, Date startDate, Date endDate){
        ArrayList<Room> rooms = getAvailableRooms(startDate, endDate);
        UUID roomId = rooms.stream().filter(item -> Objects.equals(item.getName(), roomNumber)).findFirst().get().getId();

        if(!isRoomAvailable(startDate, endDate, roomId))
            return;

        Guest newGuest = new Guest(clientFirstName, clientLastName);
        guests.add(newGuest);
        reservations.add(new Reservation(newGuest.getId(), roomId, startDate, endDate));
    }
    public void createReservation(UUID clientId, UUID roomId, Date startDate, Date endDate){
        if(!isRoomAvailable(startDate, endDate, roomId))
            return;

        reservations.add(new Reservation(clientId, roomId, startDate, endDate));
    }
    private boolean isRoomAvailable(Date startDate, Date endDate, UUID roomId){
        List<Reservation> sameDateReservations = sameDateReservations(startDate, endDate);
        for (Reservation res : sameDateReservations)
            if(res.getRoomId().compareTo(roomId) == 0)
                return false;

        return true;
    }
    public void cancelReservation(UUID reservationID){
        reservations.removeIf(item -> item.getId().compareTo(reservationID) == 0);
    }
    public ArrayList<Room> getAvailableRooms(Date startDate, Date endDate){
        ArrayList<Room> availableRooms = new ArrayList<>(rooms);

        // if there is no reservation that means that all the rooms are available
        if(this.reservations.isEmpty()){
            return availableRooms;
        }
        // finding all reservations between the same dates
        List<Reservation> sameDateReservations = sameDateReservations(startDate, endDate);

        // the rooms of existing reservations should be excluded
        for (Reservation res : sameDateReservations){
            availableRooms.removeIf(item -> item.getId().compareTo(res.getRoomId()) == 0);
        }

        return availableRooms;
    }
    private List<Reservation> sameDateReservations(Date startDate, Date endDate){
        return reservations.stream().filter(item ->
                (item.getStartDate()).compareTo(startDate) >= 0 && (item.getEndDate()).compareTo(startDate) < 0 ||
                        (item.getStartDate()).compareTo(endDate) > 0 && (item.getEndDate()).compareTo(endDate) <= 0  ||
                        (item.getStartDate()).compareTo(startDate) >= 0 && (item.getEndDate()).compareTo(endDate) <= 0
        ).collect(Collectors.toList());
    }

    public Map<RoomType, List<Room>> getStructuredAvailableRooms(Date startDate, Date endDate){
        ArrayList<Room> availableRooms = this.getAvailableRooms(startDate, endDate);
        LinkedHashMap<RoomType, List<Room>> structuredAvailableRooms = new LinkedHashMap<>();

        for (RoomType rt : roomTypes) {
            List<Room> rooms = availableRooms.stream().filter(item -> item.getId().compareTo(rt.getId()) == 0).collect(Collectors.toList());
            if(!rooms.isEmpty()){
                structuredAvailableRooms.put(rt, rooms);
            }
        }

        return structuredAvailableRooms;
    }


}
