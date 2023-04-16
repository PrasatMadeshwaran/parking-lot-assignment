package com.parkinglot.exceptions;

public class ParkingIsFullException extends Exception{
    public ParkingIsFullException(String message) {
        super(message);
    }
}
