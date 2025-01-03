package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.dto.DriverDto;
import com.arun.project.uber.uberApp.dto.RideDto;
import com.arun.project.uber.uberApp.dto.RideRequestDto;
import com.arun.project.uber.uberApp.dto.RiderDto;
import com.arun.project.uber.uberApp.services.RiderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiderServiceImpl implements RiderService {

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        return null;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId) {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }
}
