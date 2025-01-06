package com.arun.project.uber.uberApp.strategies.implementations;

import com.arun.project.uber.uberApp.entities.RideRequest;
import com.arun.project.uber.uberApp.services.DistanceService;
import com.arun.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public Double calculateFare(RideRequest rideRequest) {
        log.info("Starting the Default fare Calculation for Ride Request with Id:{}", rideRequest.getId());

        log.info("Calculating the Distance between Pickup Location and DropOff Location");
        Double distance=distanceService
                .calculateDistance(rideRequest.getPickUpLocation(),rideRequest.getDropOffLocation());
        log.debug("Distance between Pickup Location and DropOff Location: {}", distance);

        Double fare=distance*RIDE_FARE_MULTIPLIER;
        log.info("Fare Calculated Successfully for the Ride Request with Id:{}", rideRequest.getId());

        return fare;
    }
}
