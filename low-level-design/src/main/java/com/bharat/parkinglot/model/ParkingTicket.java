package com.bharat.parkinglot.model;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class ParkingTicket {

    private int ticketNumber;
    private long entryTime;
    private long exitTime;
    private double payedAmount;
    private PaymentMode paymentMode;
    private ParkingTicketStatus parkingTicketStatus;
    private Vehicle vehicle;

    public int getTicketNumber() {
        return ticketNumber;
    }

    public ParkingTicket setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
        return this;
    }

    private String formattedTime(Long epoch) {
        return formattedTime(Instant.ofEpochMilli(epoch));
    }

    private String formattedTime(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.MILLIS)
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getEntryTime() {
        return formattedTime(entryTime);
    }

    public ParkingTicket setEntryTime(long entryTime) {
        this.entryTime = entryTime;
        return this;
    }

    public String getExitTime() {
        return formattedTime(exitTime);
    }

    public ParkingTicket setExitTime(long exitTime) {
        this.exitTime = exitTime;
        return this;
    }

    public double getPayedAmount() {
        return payedAmount;
    }

    public ParkingTicket setPayedAmount(double payedAmount) {
        this.payedAmount = payedAmount;
        return this;
    }

    public ParkingTicketStatus getParkingTicketStatus() {
        return parkingTicketStatus;
    }

    public ParkingTicket setParkingTicketStatus(ParkingTicketStatus parkingTicketStatus) {
        this.parkingTicketStatus = parkingTicketStatus;
        return this;
    }
}
