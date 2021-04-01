package com.bharat.parkinglot.model;

public class ParkingSpotNotUnavailableException extends Exception {

    public ParkingSpotNotUnavailableException(String message) {
        super(message);
    }

    public ParkingSpotNotUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
