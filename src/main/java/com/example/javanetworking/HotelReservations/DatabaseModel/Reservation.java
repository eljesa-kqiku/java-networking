package com.example.javanetworking.HotelReservations.DatabaseModel;

import java.util.Date;
import java.util.UUID;

public class Reservation {
    private UUID id;
    private UUID clientId;
    private UUID roomId;
    private Date startDate;
    private Date endDate;

    public Reservation(UUID clientId, UUID roomId, Date startDate, Date endDate) {
        this.id = UUID.randomUUID();
        this.clientId = clientId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
