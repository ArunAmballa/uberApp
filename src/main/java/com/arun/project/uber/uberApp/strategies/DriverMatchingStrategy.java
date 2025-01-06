package com.arun.project.uber.uberApp.strategies;

import com.arun.project.uber.uberApp.entities.Driver;
import com.arun.project.uber.uberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDrivers(RideRequest rideRequest);
}
