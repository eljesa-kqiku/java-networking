package com.example.javanetworking.HotelReservations.DatabaseModel;

import java.util.UUID;

public class Guest {
    private UUID id;
    private String firstName;
    private String lastName;

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
