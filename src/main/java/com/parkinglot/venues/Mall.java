package com.parkinglot.venues;

import com.parkinglot.ParkingVenue;
import com.parkinglot.enums.Spot;
import com.parkinglot.enums.Venue;
import com.parkinglot.exceptions.SpotNotFoundException;


import java.util.Set;

import static com.parkinglot.enums.Spot.*;
import static com.parkinglot.enums.Venue.MALL;
import static java.lang.String.format;


public class Mall extends ParkingVenue {

    private static final long RATE_FOR_MOTORCYCLE_OR_SCOOTER = 10;
    private static final long RATE_FOR_CAR_OR_SUV = 20;
    private static final long RATE_FOR_BUS_OR_TRUCK = 50;

    @Override
    public Venue getType() {
        return MALL;
    }

    @Override
    protected long calculateFee(Spot spotType, long hours) {
        switch (spotType) {
            case MOTORCYCLE_OR_SCOOTER:
                return RATE_FOR_MOTORCYCLE_OR_SCOOTER * hours;
            case CAR_OR_SUV:
                return RATE_FOR_CAR_OR_SUV * hours;
            case BUS_OR_TRUCK:
                return RATE_FOR_BUS_OR_TRUCK * hours;
            default:
                throw new SpotNotFoundException(format("Spot type [%s] is not supported", spotType));
        }
    }

    @Override
    protected Set<Spot> getSupportedSpot() {
        return Set.of(MOTORCYCLE_OR_SCOOTER, CAR_OR_SUV, BUS_OR_TRUCK);
    }
}
