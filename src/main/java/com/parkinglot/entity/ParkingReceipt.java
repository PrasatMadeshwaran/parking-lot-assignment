package com.parkinglot.entity;

import lombok.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

@Value
public class ParkingReceipt {
    private static int receiptCounter = 0;
    String receiptNumber;
    LocalDateTime entryDateTime;
    LocalDateTime exitDateTime;
    long fees;
    public ParkingReceipt(LocalDateTime entryDateTime, LocalDateTime exitDateTime, long fees) {
        this.receiptNumber = format("R-%03d", ++receiptCounter);
        this.entryDateTime = entryDateTime;
        this.exitDateTime = exitDateTime;
        this.fees = fees;
    }

    @Override
    public String toString() {
        return format("Parking Receipt:\n" +
                        " Receipt Number: %s\n" +
                        " Entry Date-time: %s\n" +
                        " Exit Date-time: %s\n" +
                        " Fees: %d", receiptNumber,
                entryDateTime.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")),
                exitDateTime.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")),
                fees);
    }
}
