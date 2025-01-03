package com.arun.project.uber.uberApp.strategies.implementations;

import com.arun.project.uber.uberApp.dto.RideRequestDto;
import com.arun.project.uber.uberApp.entities.Driver;
import com.arun.project.uber.uberApp.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {
    @Override
    public List<Driver> findMatchingDrivers(RideRequestDto rideRequestDto) {
        return List.of();
    }
}
