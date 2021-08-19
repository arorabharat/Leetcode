package com.bharat.conference_room.model;

public class Booking {

    private final int bookingID;
    private final long startTime;
    private final long endTIme;
    private final int roomId;
    private final int userId;
    private boolean isCancelled;

    public Booking(int bookingID, long startTime, long endTIme, int roomId, int userId) {
        this.bookingID = bookingID;
        this.startTime = startTime;
        this.endTIme = endTIme;
        this.roomId = roomId;
        this.userId = userId;
    }

    public int getBookingID() {
        return bookingID;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTIme() {
        return endTIme;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
