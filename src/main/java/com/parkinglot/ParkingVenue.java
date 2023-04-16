package com.parkinglot;

import com.parkinglot.entity.ParkingReceipt;
import com.parkinglot.entity.ParkingTicket;
import com.parkinglot.entity.SpotDetails;
import com.parkinglot.enums.Spot;
import com.parkinglot.enums.Venue;
import com.parkinglot.exceptions.ParkingIsFullException;
import com.parkinglot.exceptions.SpotNotFoundException;
import com.parkinglot.exceptions.VenueNotFoundException;
import com.parkinglot.venues.Airport;
import com.parkinglot.venues.Mall;
import com.parkinglot.venues.Stadium;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

import static com.parkinglot.ParkingVenue.SpotAvailability.AVAILABLE;
import static com.parkinglot.ParkingVenue.SpotAvailability.OCCUPIED;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public abstract class ParkingVenue {

    private static final Map<Spot, Map<SpotAvailability, List<SpotDetails>>> SPOTS = new HashMap<>();

    protected abstract Set<Spot> getSupportedSpot();

    public abstract Venue getType();

    protected abstract long calculateFee(Spot spotType, long hours);

    public static ParkingVenue createParkingVenue(Venue venue, Map<Spot, Integer> spots) {
        ParkingVenue parkingVenue = createParkingVenue(venue);
        spots.forEach(parkingVenue::addParkingSpots);
        return parkingVenue;

    }
    private static ParkingVenue createParkingVenue(Venue venue) {
        switch (venue) {
            case MALL:
                return new Mall();
            case STADIUM:
                return new Stadium();
            case AIRPORT:
                return new Airport();
        }
        throw new VenueNotFoundException(format("Venue is [%s] not having parking", venue));
    }

    private void addParkingSpots(Spot spot, int noOfSpots) {
        if (!getSupportedSpot().contains(spot)) {
            throw new SpotNotFoundException(format("Spot [%s] is not available in the venue [%s]", spot, getType()));
        }

        List<SpotDetails> availableSpots = IntStream.range(1, noOfSpots + 1)
                .boxed()
                .map(spotNumber -> new SpotDetails(spot, spotNumber))
                .collect(toList());

        SPOTS.put(
                spot,
                Map.of(AVAILABLE, availableSpots, OCCUPIED, new ArrayList<>()));
    }

    public synchronized ParkingTicket park(Spot spot, LocalDateTime entryDateTime)
            throws ParkingIsFullException {
        if (SPOTS.get(spot).get(AVAILABLE).isEmpty()) {
            throw new ParkingIsFullException("Parking is Full");
        }

        SpotDetails allocatedSpotDetails = SPOTS.get(spot).get(AVAILABLE).remove(0);

        SPOTS.get(spot).get(OCCUPIED).add(allocatedSpotDetails);

        return new ParkingTicket(allocatedSpotDetails.getSpotNumber(), entryDateTime);
    }

    public synchronized ParkingReceipt unpark(Spot spot, ParkingTicket ticket,
                                              LocalDateTime exitDateTime) {

        SpotDetails spotDetails = new SpotDetails(spot, ticket.getSpotNumber());

        SPOTS.get(spot).get(OCCUPIED).remove(spot);

        SPOTS.get(spot).get(AVAILABLE).add(spotDetails);

        long fee = calculateFee(spot, ChronoUnit.HOURS.between(ticket.getEntryDateTime(), exitDateTime) + 1);

        return new ParkingReceipt(ticket.getEntryDateTime(), exitDateTime, fee);
    }


    protected enum SpotAvailability {
        AVAILABLE,
        OCCUPIED
    }
}