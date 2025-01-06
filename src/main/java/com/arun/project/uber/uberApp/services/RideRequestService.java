package com.arun.project.uber.uberApp.services;


import com.arun.project.uber.uberApp.entities.RideRequest;


public interface RideRequestService {

    RideRequest getRideRequestById(Long rideRequestId);

    void updateRideRequest(RideRequest rideRequest);
}
