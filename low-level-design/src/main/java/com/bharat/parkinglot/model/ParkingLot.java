package com.bharat.parkinglot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@Singleton
public class ParkingLot {

    private final List<ParkingSpot> parkingSpots;
    private ParkingStrategy parkingStrategy;

    private ParkingLot() {
        this.parkingSpots = new ArrayList<>();
    }

    public Optional<ParkingSpot> hasFreeSpot(VehicleType vehicleType, int gateNumber) {
        return this.parkingStrategy.getParkingSpot(this, vehicleType, gateNumber);
    }

    public void addParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpots.add(parkingSpot);
    }

    public List<ParkingSpot> getParkingSpots() {
        return Collections.unmodifiableList(parkingSpots);
    }

    public ParkingSpot assignParkingSpot(Vehicle vehicle, int gateNumber) throws ParkingSpotNotUnavailableException {
        Optional<ParkingSpot> parkingSpot = this.parkingStrategy.getParkingSpot(this, vehicle.getType(), gateNumber);
        if (parkingSpot.isPresent()) {
            parkingSpot.get().setVehicle(vehicle);
            return parkingSpot.get();
        } else {
            throw new ParkingSpotNotUnavailableException("Parking is Unavailable");
        }
    }
}
