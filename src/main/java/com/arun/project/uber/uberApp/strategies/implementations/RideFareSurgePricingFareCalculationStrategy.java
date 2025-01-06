package com.arun.project.uber.uberApp.strategies.implementations;

import com.arun.project.uber.uberApp.entities.RideRequest;
import com.arun.project.uber.uberApp.services.DistanceService;
import com.arun.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {


    private final DistanceService distanceService;
    private static final double SURGE_FACTOR=2;

    @Override
    public Double calculateFare(RideRequest rideRequest) {
        Double distance=distanceService.calculateDistance(rideRequest.getPickUpLocation(),rideRequest.getDropOffLocation());
        return distance*RIDE_FARE_MULTIPLIER*SURGE_FACTOR;
    }
}
