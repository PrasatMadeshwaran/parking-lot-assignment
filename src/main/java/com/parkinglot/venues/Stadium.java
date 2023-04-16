package com.parkinglot.venues;



import com.parkinglot.ParkingVenue;
import com.parkinglot.enums.Spot;
import com.parkinglot.enums.Venue;
import com.parkinglot.exceptions.SpotNotFoundException;

import java.util.Set;

import static com.parkinglot.enums.Spot.CAR_OR_SUV;
import static com.parkinglot.enums.Spot.MOTORCYCLE_OR_SCOOTER;
import static com.parkinglot.enums.Venue.STADIUM;
import static java.lang.String.format;


public class Stadium extends ParkingVenue {

    private static final long FIRST_FLAT_RATE_INTERVAL_UPPER_BOUND = 4;
    private static final long SECOND_FLAT_RATE_INTERVAL_UPPER_BOUND = 12;

    private static final long FIRST_FLAT_RATE_FOR_MOTORCYCLE_OR_SCOOTER = 30;
    private static final long SECOND_FLAT_RATE_FOR_MOTORCYCLE_OR_SCOOTER = 60;
    private static final long HOURLY_RATE_FOR_MOTORCYCLE_OR_SCOOTER = 100;


    @Override
    public Venue getType() {
        return STADIUM;
    }

    @Override
    protected long calculateFee(Spot spotType, long hours) {
        switch (spotType) {
            case MOTORCYCLE_OR_SCOOTER:
                return calculateFeeForMotorcycleOrScooter(hours);
            case CAR_OR_SUV:
                return calculateFeeForCarOrSUV(hours);
            default:
                throw new SpotNotFoundException(format("Spot type [%s] is not supported", spotType));
        }
    }

    private long calculateFeeForMotorcycleOrScooter(long hours) {
        long totalFee = FIRST_FLAT_RATE_FOR_MOTORCYCLE_OR_SCOOTER;

        if (hours > FIRST_FLAT_RATE_INTERVAL_UPPER_BOUND) {
            totalFee += SECOND_FLAT_RATE_FOR_MOTORCYCLE_OR_SCOOTER;
        }

        if (hours > SECOND_FLAT_RATE_INTERVAL_UPPER_BOUND) {
            totalFee += HOURLY_RATE_FOR_MOTORCYCLE_OR_SCOOTER  * (hours - 12);
        }

        return totalFee;
    }

    private long calculateFeeForCarOrSUV(long hours) {
        return 2 * calculateFeeForMotorcycleOrScooter(hours);
    }

    @Override
    protected Set<Spot> getSupportedSpot() {
        return Set.of(MOTORCYCLE_OR_SCOOTER, CAR_OR_SUV);
    }
}
