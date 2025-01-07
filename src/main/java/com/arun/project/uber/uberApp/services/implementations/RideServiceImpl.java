package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.dto.RideRequestDto;
import com.arun.project.uber.uberApp.entities.Driver;
import com.arun.project.uber.uberApp.entities.Ride;
import com.arun.project.uber.uberApp.entities.RideRequest;
import com.arun.project.uber.uberApp.entities.enums.RideStatus;
import com.arun.project.uber.uberApp.exceptions.RideNotFoundException;
import com.arun.project.uber.uberApp.repositories.RideRepository;
import com.arun.project.uber.uberApp.services.RideRequestService;
import com.arun.project.uber.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.arun.project.uber.uberApp.entities.enums.RideRequestStatus.CONFIRMED;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final ModelMapper modelMapper;
    private final RideRequestService rideRequestService;


    @Override
    public Ride getRideById(Long rideId) {
        log.info("Checking if ride exists with rideId:{}",rideId);
        return rideRepository.findById(rideId).orElseThrow(() -> {
            log.error("Ride with rideId:{} not found", rideId);
            return new RideNotFoundException("Ride with rideId:" + rideId + " not found");
        });
    }

    @Override
    public void matchWithDrivers(RideRequestDto rideRequestDto) {

    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(CONFIRMED);
        Ride rideToBeSaved = modelMapper.map(rideRequest, Ride.class);
        rideToBeSaved.setRideStatus(RideStatus.CONFIRMED);
        rideToBeSaved.setDriver(driver);
        rideToBeSaved.setOtp(generateRandomOTP());
        rideToBeSaved.setId(null);
        rideRequestService.updateRideRequest(rideRequest);
        return rideRepository.save(rideToBeSaved);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        log.info("Updating ride status with Status:{}",rideStatus);
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Long driverId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Ride saveRide(Ride ride) {
        log.info("Saving ride:{}",ride);
        return rideRepository.save(ride);
    }

    private String generateRandomOTP(){
        Random random = new Random();
        int otpInt = random.nextInt(10000);  //0 to 9999
        return String.format("%04d", otpInt);
    }
}
