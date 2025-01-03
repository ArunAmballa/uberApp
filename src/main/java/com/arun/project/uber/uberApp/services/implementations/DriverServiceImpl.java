package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.dto.DriverDto;
import com.arun.project.uber.uberApp.dto.RideDto;
import com.arun.project.uber.uberApp.dto.RideRequestDto;
import com.arun.project.uber.uberApp.dto.RiderDto;
import com.arun.project.uber.uberApp.services.DriverService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DriverServiceImpl implements DriverService {


    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto endRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto startRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto acceptRide(RideRequestDto rideRequestDto) {
        return null;
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public DriverDto getMyProfile() {
        return null;
    }
}
