package com.atlassian.badminton_court;


import java.util.*;

public class BadmintonCourt {

    static class Booking {

        private final int id;
        private final long startTime;
        private final long endTime;
        private int assignedCourt;

        public Booking(int id, long startTime, long endTime) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
            this.assignedCourt = -1;
        }

        public int getId() {
            return id;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setAssignedCourt(int assignedCourt) {
            this.assignedCourt = assignedCourt;
        }

        @Override
        public String toString() {
            return "Booking{" +
                    "id=" + id +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", assignedCourt=" + assignedCourt +
                    "}\n" ;
        }
    }


    static class Court {

        private final int id;
        private final List<Booking> bookings = new ArrayList<>();

        public Court(int id) {
            this.id = id;
        }

        public boolean isAvailable(Booking bookingRecord) {
            if (bookings.isEmpty()) {
                return true;
            }
            return bookings.get(bookings.size() - 1).endTime <= bookingRecord.startTime;
        }

        public boolean addBooking(Booking bookingRecord) {
            if (isAvailable(bookingRecord)) {
                bookings.add(bookingRecord);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Court{" +
                    "id='" + id + '\'' +
                    ", bookings=" + bookings +
                    '}';
        }
    }

    static class CourtBookingService {

        private final List<Court> courts = new ArrayList<>();
        private final Map<Integer, Court> courtsDb = new HashMap<>();

        String addBooking(Booking booking) {
            return "";
        }

        void addBooking(List<Booking> bookings) {
            Comparator<Booking> sortByStartTime = (b1, b2) -> {
                if (b1.getStartTime() == b2.getStartTime()) {
                    return Long.compare(b1.getEndTime(), b2.getEndTime());
                }
                return Long.compare(b1.getStartTime(), b2.getStartTime());
            };
            bookings.sort(sortByStartTime);
            for (Booking booking : bookings) {
                Court assignedCourt = null;
                if (courts.isEmpty()) {
                    assignedCourt = addBookingToNewCourt(booking);
                } else {
                    for (Court court : courts) {
                        if (court.isAvailable(booking)) {
                            court.addBooking(booking);
                            assignedCourt = court;
                            break;
                        }
                    }
                    if (Objects.isNull(assignedCourt)) {
                        assignedCourt = addBookingToNewCourt(booking);
                    }
                }
                booking.setAssignedCourt(assignedCourt.id);
            }
            Comparator<Booking> sortByStartId = Comparator.comparingInt(Booking::getId);
            bookings.sort(sortByStartId);
        }

        private Court addBookingToNewCourt(Booking booking) {
            Court court = new Court(courts.size()+1);
            court.addBooking(booking);
            courts.add(court);
            courtsDb.put(court.id, court);
            return court;
        }
    }

    public static void main(String[] args) {
        Booking b1 = new Booking(1, 1,2);
        Booking b2 = new Booking(2, 2,3);
        Booking b3 = new Booking(3, 1,3);
        Booking b4 = new Booking(4, 2,4);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(b1);
        bookingList.add(b2);
        bookingList.add(b3);
        bookingList.add(b4);
        CourtBookingService courtBookingService = new CourtBookingService();
        courtBookingService.addBooking(bookingList);
        System.out.println(bookingList);
    }
}
