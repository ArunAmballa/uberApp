package com.arun.project.uber.uberApp.strategies;

import com.arun.project.uber.uberApp.dto.RideRequestDto;


public interface RideFareCalculationStrategy {

    Double calculateFare(RideRequestDto rideRequestDto);
}
