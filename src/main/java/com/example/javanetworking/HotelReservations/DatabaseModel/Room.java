package com.example.javanetworking.HotelReservations.DatabaseModel;

import java.io.Serializable;
import java.util.UUID;

public class Room implements Serializable {
    private final UUID id;
    private final UUID roomTypeId;
    private final String name;

    public Room(UUID roomTypeId, String name) {
        this.id = UUID.randomUUID();
        this.roomTypeId = roomTypeId;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRoomTypeId() {
        return roomTypeId;
    }

    public String getName() {
        return name;
    }
}
