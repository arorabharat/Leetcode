package com.bharat.conference_room.model;

import java.util.ArrayList;
import java.util.List;

public class ConferenceRoom {

    private final int roomId;
    private final List<Booking> bookings;

    public ConferenceRoom(int roomId) {
        this.roomId = roomId;
        this.bookings = new ArrayList<>();
    }

    public int getRoomId() {
        return roomId;
    }

    public List<Booking> getBookings() {
        return bookings;
    }


    public boolean addBooking(Booking booking) {
        if(isValidBooking()) {
            this.bookings.add(booking);
        }

    }

    private boolean isValidBooking() {


    }

    public void
}
