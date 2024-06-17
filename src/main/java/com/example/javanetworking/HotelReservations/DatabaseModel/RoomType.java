package com.example.javanetworking.HotelReservations.DatabaseModel;

import java.io.Serializable;
import java.util.UUID;

public class RoomType implements Serializable {
    private final UUID id;
    private final String name;
    private final int price;
    private final int numPersons;
    private final String description;

    public RoomType(String name, int price, int numPersons, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.numPersons = numPersons;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public String getDescription() {
        return description;
    }
}
