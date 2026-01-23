package com.atlassian.dsa.badminton_court;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BadmintonCourt1 {

    /**
     * Implement a function that given a list of tennis court bookings with start and finish times,
     * returns a plan assigning each booking to a specific court,
     * ensuring each court is used by only one booking at a time and using the minimum amount of
     * courts with unlimited number of courts available.
     * <p>
     * An example of the booking record might look like:
     * class BookingRecord:
     * id: int // ID of the booking.
     * start_time: int
     * finish_time: int
     * <p>
     * and our function is going to look like:
     * List<Court> assignCourts(List<BookingRecord> bookingRecords)
     * <p>
     * b1 - 1,3
     * b2 - 1,3
     * b3 - 2,4
     * b4 - 5,6
     * <p>
     * c1 - b1, b4
     * c2 - b2
     * c3 - b3
     * number of unique end time
     * 1. key iteration O(B)
     * 2. O(1), LogB
     * 3. (B + LogB) * B
     * O(B*B)
     * 3 - {c1, c2}
     * 4 - {c3}
     * b4 - 5
     * <p>
     * <p>
     * 4-6
     * SortedMap <endTime ,Set<Courts>>
     * 1,2,3,5,6,7
     * <p>
     * <p>
     * <p>
     * BlogB
     * B, C (max concurrent bookings)
     * O(B+BlogB)
     * O(B*C + BlogB)
     * <p>
     * <p>
     * Court - List<br>, id
     */

    static class BookingRecord {
        int id;
        int start_time;
        int finish_time;

        BookingRecord(int id, int start_time, int finish_time) {
            this.id = id;
            this.start_time = start_time;
            this.finish_time = finish_time;
        }

        // assuming all ints positive;
        @Override
        public String toString() {
            return id + "";
        }
    }

    static class Court {
        String uuid;
        List<BookingRecord> courtBookings;
        int rest_time;
        int restFactor = 3;

        Court() {
            uuid = UUID.randomUUID().toString();
            courtBookings = new ArrayList<>();
        }

        public void addBooking(BookingRecord booking) {
            courtBookings.add(booking);
        }

        public boolean isCourtAvailable(BookingRecord booking) {
            try {
                BookingRecord lastBooking = courtBookings.get(courtBookings.size() - 1);
                return lastBooking.finish_time <= booking.start_time;
            } catch (Exception e) {
                return true;
            }
        }

        @Override
        public String toString() {
            return uuid + " : " + courtBookings.toString();
        }
    }

    public void sortBookingInAscendingTime(List<BookingRecord> bookingRecords) {
        bookingRecords.sort((b1, b2) -> {
            // sort by start time first
            int startTimeDiff = b1.start_time - b2.start_time;
            if (startTimeDiff != 0) {
                return startTimeDiff;
            } else {
                return b1.finish_time - b2.finish_time;
            }
        });
    }

    /**
     * * b1 - 1,3
     * * b2 - 1,3
     * * b3 - 2,4
     * * b4 - 5,6
     * *
     * * c1 - b1, b4
     * * c2 - b2
     * * c3 - b3
     *
     * @param bookingRecords
     * @return
     */
    List<Court> assignCourts(List<BookingRecord> bookingRecords) {
        sortBookingInAscendingTime(bookingRecords);

        // For each booking:
        // 1. if we have no courts then add a court and assign booking to it
        // 2. if we have a court but the booking overlaps then move to next
        // 3. if no overlap then add booking ot this court
        List<Court> courts = new ArrayList<>();
        for (var bookingRequest : bookingRecords) {
            // first court added
            if (courts.isEmpty()) {
                courts.add(createCourtWithBooking(bookingRequest));
            } else {
                boolean isServed = false;
                for (var court : courts) {
                    if (court.isCourtAvailable(bookingRequest)) {
                        court.addBooking(bookingRequest);
                        isServed = true;
                        break;
                    }
                }
                if (!isServed) {
                    courts.add(createCourtWithBooking(bookingRequest));
                }

            }
        }
        return courts;
    }

    private Court createCourtWithBooking(BookingRecord bookingRecord) {
        Court newCourt = new Court();
        if (newCourt.isCourtAvailable(bookingRecord)) {
            newCourt.addBooking(bookingRecord);
        } else {
            throw new RuntimeException("new court should be free");
        }
        return newCourt;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        BookingRecord br1 = new BookingRecord(1, 1, 3);
        BookingRecord br2 = new BookingRecord(2, 3, 4);
        BookingRecord br3 = new BookingRecord(3, 2, 4);
        BookingRecord br4 = new BookingRecord(4, 5, 6);


        BadmintonCourt1 main = new BadmintonCourt1();
        System.out.println(main.assignCourts(new ArrayList<>(List.of(br1, br2))));
    }

}
