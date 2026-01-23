package com.atlassian.badminton_court;

import java.util.*;

public class BookingCourt2 {

    static class Booking {

        private final String id;
        private final long startTime;
        private final long endTime;
        private String assignedCourt;


        public Booking(String id, long startTime, long endTime) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public Booking(long startTime, long endTime) {
            this.id = UUID.randomUUID().toString();
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public String getAssignedCourt() {
            return assignedCourt;
        }

        @Override
        public String toString() {
            return "Booking{" +
                    "id='" + id + '\'' +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", assignedCourt='" + assignedCourt + '\'' +
                    "}\n";
        }

        public void setAssignedCourt(String assignedCourt) {
            this.assignedCourt = assignedCourt;
        }
    }

    static class Court {

        private final String id;
        private final List<Booking> bookings;

        public Court(String id) {
            this.id = id;
            this.bookings = new ArrayList<>();
        }

        public Court() {
            this.id = UUID.randomUUID().toString();
            this.bookings = new ArrayList<>();
        }

        public boolean isAvailable(Booking booking) {
            if(bookings.isEmpty()) {
                return true;
            } else {
                return bookings.get(bookings.size() - 1).endTime <= booking.startTime;
            }
        }

        public boolean addBooking(Booking booking) {
            if(isAvailable(booking)) {
                bookings.add(booking);
                return true;
            }
            return false;
        }
    }


    static class CourtBookingService {

        private final List<Court> courtList = new ArrayList<>();
        private final Map<String, Court> courtsDb = new HashMap<>();

        void assignCourts(List<Booking> bookings) {
            // sorted bookings
            Comparator<Booking> sortByStartTime = (b1, b2) -> {
                if (b1.startTime == b2.endTime) {
                    return Long.compare(b1.endTime, b2.endTime);
                }
                return Long.compare(b1.startTime, b2.startTime);
            };
            bookings.sort(sortByStartTime);
            for (Booking booking : bookings) {
                Court assignedCourt = null;
                if(courtList.isEmpty()) {
                    assignedCourt = addBookingToNewCourt(booking);
                } else {
                    for(Court court : courtList) {
                        if(court.addBooking(booking)) {
                            assignedCourt = court;
                        }
                    }
                    if(Objects.isNull(assignedCourt)) {
                        assignedCourt = addBookingToNewCourt(booking);
                    }
                }
                booking.setAssignedCourt(assignedCourt.id);
            }

            Comparator<Booking> sortById = Comparator.comparing(Booking::getId);
            bookings.sort(sortById);
        }

        private Court addBookingToNewCourt(Booking booking) {
            Court court = new Court(String.valueOf(courtList.size() + 1));
            courtsDb.put(court.id, court);
            court.addBooking(booking);
            courtList.add(court);
            return court;
        }

    }

    public static void main(String[] args) {
        Booking b1 = new Booking("1", 1, 2);
        Booking b2 = new Booking("2", 2, 3);
        Booking b3 = new Booking("3", 1, 3);
        Booking b4 = new Booking("4", 2, 4);
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b1);
        bookings.add(b2);
        bookings.add(b3);
        bookings.add(b4);
        CourtBookingService courtBookingService = new CourtBookingService();
        System.out.println(bookings);
        courtBookingService.assignCourts(bookings);
        System.out.println(bookings);
    }
}
