package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.dto.DriverDto;
import com.arun.project.uber.uberApp.dto.RideDto;
import com.arun.project.uber.uberApp.dto.RideRequestDto;
import com.arun.project.uber.uberApp.dto.RiderDto;
import com.arun.project.uber.uberApp.entities.Driver;
import com.arun.project.uber.uberApp.entities.RideRequest;
import com.arun.project.uber.uberApp.entities.Rider;
import com.arun.project.uber.uberApp.entities.User;
import com.arun.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.arun.project.uber.uberApp.exceptions.RideRequestException;
import com.arun.project.uber.uberApp.repositories.RideRequestRepository;
import com.arun.project.uber.uberApp.repositories.RiderRepository;
import com.arun.project.uber.uberApp.services.RiderService;
import com.arun.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final RideStrategyManager rideStrategyManager;
    private final RiderRepository riderRepository;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        log.info("Starting Ride Request Process for :{}",rideRequestDto);

        try{
            log.debug("Mapping RideRequestDto to RideRequest");
            RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);

            log.info("Calculating fare for the Ride Request");
            Double fare=rideStrategyManager.getRideFareStrategy().calculateFare(rideRequest);
            log.debug("Calculated fare for the Ride Request:{}",fare);

            //Retrieve the current Rider
            Rider rider=getCurrentRider();
            rideRequest.setFare(fare);
            rideRequest.setRider(rider);
            rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
            log.debug("Ride Request Entity has been updated with status and fare:{}",rideRequest);

            log.info("Saving Ride Request to DataBase");
            RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
            log.info("Ride Request Saved Successfully with Id:{}",savedRideRequest.getId());

            log.info("Finding Matching Drivers for the Ride Request");
            List<Driver> matchingDrivers = rideStrategyManager
                    .getDriverMatchingStrategy(rider.getRating())
                    .findMatchingDrivers(rideRequest);
            log.debug("Matching Drivers for the Ride Request:{}",matchingDrivers);

            log.info("Notifying Matched Drivers about the Ride Request");
            //TODO Send Emails to all the matching drivers

            log.info("Ride Request Process is Successfully Completed with Id:{}",savedRideRequest.getId());
            return modelMapper.map(savedRideRequest,RideRequestDto.class);
        }catch(Exception e){
            log.error("Error Occurred while Processing the Ride Request:{}",e.getMessage());
            throw new RideRequestException("An Error Occurred while Processing the Ride Request:",e);
        }
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId,Integer rating) {
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

    @Override
    public Rider createRider(User user) {
        Rider rider = Rider.builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    public Rider getCurrentRider() {
        //TODO Implement Spring Security
        return riderRepository.findById(1L).orElseThrow(()-> new RuntimeException("Rider not found"));
    }

}
