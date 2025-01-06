package com.arun.project.uber.uberApp.controllers;

import com.arun.project.uber.uberApp.dto.RideDto;
import com.arun.project.uber.uberApp.dto.StartRideDto;
import com.arun.project.uber.uberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping(path="/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){
        return new ResponseEntity<>(driverService.acceptRide(rideRequestId), HttpStatus.OK);
    }

    @PostMapping(path="/startRide/{rideId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideId, @RequestBody StartRideDto startRideDto){
        return new ResponseEntity<>(driverService.startRide(rideId,startRideDto.getOtp()), HttpStatus.OK);
    }

    @PostMapping(path="/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
        return new ResponseEntity<>(driverService.endRide(rideId), HttpStatus.OK);
    }


}
