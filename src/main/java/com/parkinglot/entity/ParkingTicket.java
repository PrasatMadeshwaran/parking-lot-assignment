package com.parkinglot.entity;

import lombok.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

@Value
public class ParkingTicket {
    private static int ticketCounter = 0;
    String ticketNumber;
    int spotNumber;
    LocalDateTime entryDateTime;

    public ParkingTicket(int spotNumber, LocalDateTime entryDateTime) {
        ticketNumber = format("%03d", ++ticketCounter);
        this.spotNumber = spotNumber;
        this.entryDateTime = entryDateTime;
    }

    @Override
    public String toString() {
        return format("Parking Ticket:\n" +
                        " Ticket Number: %s\n" +
                        " Spot Number: %d\n" +
                        " Entry Date-time: %s", ticketNumber, spotNumber,
                entryDateTime.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")));
    }
}
