package com.example.javanetworking.HotelReservations.DatabaseModel;

import java.util.UUID;

public class Guest {
    private final UUID id;
    private final String firstName;
    private final String lastName;

    public Guest(String firstName, String lastName) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
