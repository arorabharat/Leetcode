package com.bharat.parkinglot.model;

import java.util.Optional;

public interface ParkingStrategy {
    Optional<ParkingSpot> getParkingSpot(ParkingLot parkingLot, VehicleType vehicleType, int gateNumber);
}
