package com.parkinglot.exceptions;

public class VenueNotFoundException extends RuntimeException{
    public VenueNotFoundException(String message) {
        super(message);
    }
}
