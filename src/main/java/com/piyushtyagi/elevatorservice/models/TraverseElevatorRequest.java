package com.piyushtyagi.elevatorservice.models;


import jakarta.validation.constraints.NotNull;

public class TraverseElevatorRequest {
    @NotNull(message = "hotelId cannot be null")
    String hotelId;

    @NotNull(message = "elevatorIdd cannot be null")
    String elevatorId;

    @NotNull(message = "floor number cannot be null")
    String floorNumber;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }
}
