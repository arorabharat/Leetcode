package com.bharat.parkinglot.model;

import java.util.Optional;

public class EasyParkingStrategy implements ParkingStrategy {

    @Override
    public Optional<ParkingSpot> getParkingSpot(ParkingLot parkingLot, VehicleType vehicleType, int gateNumber) {
        for (ParkingSpot parkingSpot : parkingLot.getParkingSpots()) {
            if (parkingSpot.getParkingType() == ParkingType.SMALL && parkingSpot.isFree()) {
                return Optional.of(parkingSpot);
            }
        }
        return Optional.empty();
    }
}
