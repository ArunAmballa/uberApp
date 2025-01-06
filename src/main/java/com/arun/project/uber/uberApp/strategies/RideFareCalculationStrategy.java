package com.arun.project.uber.uberApp.strategies;

import com.arun.project.uber.uberApp.entities.RideRequest;


public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER=10;
    Double calculateFare(RideRequest rideRequest);
}
