package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.dto.DriverDto;
import com.arun.project.uber.uberApp.dto.RideDto;
import com.arun.project.uber.uberApp.dto.RideRequestDto;
import com.arun.project.uber.uberApp.dto.RiderDto;
import com.arun.project.uber.uberApp.entities.Driver;
import com.arun.project.uber.uberApp.entities.Ride;
import com.arun.project.uber.uberApp.entities.RideRequest;
import com.arun.project.uber.uberApp.entities.enums.RideStatus;
import com.arun.project.uber.uberApp.exceptions.*;
import com.arun.project.uber.uberApp.repositories.DriverRepository;
import com.arun.project.uber.uberApp.services.DriverService;
import com.arun.project.uber.uberApp.services.RideRequestService;
import com.arun.project.uber.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.arun.project.uber.uberApp.entities.enums.RideRequestStatus.PENDING;
import static com.arun.project.uber.uberApp.entities.enums.RideStatus.CONFIRMED;


@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final RideRequestService rideRequestService;
    
    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto endRide(Long rideId) {
        log.info("Starting the Process of Ending Ride with rideId: {}", rideId);

        try{
            //Step-1: Check the Existence of ride
            Ride rideToBeUpdated=rideService.getRideById(rideId);
            log.debug("Fetched ride Details: {}", rideToBeUpdated);

            //Step-2: Update the Status of Ride
            Ride updatedRide = rideService.updateRideStatus(rideToBeUpdated, RideStatus.ENDED);
            updatedRide.setEndedAt(LocalDateTime.now());
            Ride savedRide = rideService.saveRide(updatedRide);
            log.info("Ride Status has been successfully updated with status;{}",updatedRide.getRideStatus());

            log.info("Successfully Ended the Ride with rideId: {}", rideId);
            return modelMapper.map(savedRide,RideDto.class);
        }catch(Exception e){
            log.error("Error while Ending the Ride with rideId: {}", rideId, e);
            throw new RideProcessingException("An Error Occured while ending the ride");
        }



    }

    @Override
    public RideDto startRide(Long rideId,String otp) {
        log.info("Starting Ride Start Process for the ride with id {}",rideId);

        try{
            //Step-1:Validate Ride Existence
            Ride ride = rideService.getRideById(rideId);
            log.debug("Ride Details fetched :{}",ride);

            //Step-2: Validate Driver
            Driver currentDriver=getCurrentDriver();
            log.debug("Current Driver Details:{}",currentDriver);


            if(!currentDriver.equals(ride.getDriver())) {
                log.error("Driver Mismatch for ride Id:{}, Expected:{}, but found:{}",rideId,ride.getDriver().getId(),currentDriver.getId());
                throw new DriverMismatchException("Current Driver Does not match the rides driver");
            }

            //Step-3: Validate Ride Status
            if(!ride.getRideStatus().equals(CONFIRMED)){
                log.error("Invalid Ride Status for the rideId:{} Expected:{} but found:{}",rideId,CONFIRMED,ride.getRideStatus());
                throw new InvalidRideStatusException("Ride Status is not Confirmed");
            }

            //Step-4: Valid Otp
            if(!ride.getOtp().equals(otp)){
                log.error("Invalid otp for the ride with Id:{} Expected:{} but found:{}",rideId,otp,ride.getOtp());
                throw new InvalidRideOtpException("OTP does not match");
            }

            log.info("Starting the Ride with id {}",rideId);
            ride.setStartedAt(LocalDateTime.now());
            Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
            log.info("Ride Status has been updated with Status;{}",savedRide.getRideStatus());

            log.info("Successfully started the ride with Ride Id:{}",savedRide.getId());
            return modelMapper.map(savedRide,RideDto.class);

        }catch (DriverMismatchException | InvalidRideStatusException | InvalidRideOtpException e) {
            log.error("Validation error for ride ID: {} - {}", rideId, e.getMessage(), e);
            throw e;
        } catch(Exception e){
         log.error("Error while starting ride {}",e.getMessage());
         throw new RideProcessingException("Error while starting ride"+e.getMessage());
        }
    }

    @Override
    public RideDto acceptRide(Long rideRequestId) {
        log.info("Starting the process of accepting Ride with id {}",rideRequestId);

        try{
            //Step-1: Checking Existence of Ride Request
            RideRequest rideRequest = rideRequestService.getRideRequestById(rideRequestId);
            log.debug("RideRequest Details fetched :{}",rideRequest);

//            check the rideRequest status it should be Pending indicates no driver has accepted this rideRequest
//            Step-2: Validating Ride Request Status
            if(!rideRequest.getRideRequestStatus().equals(PENDING)){
                log.error("Invalid Ride Request Status for the ride request with Id:{} Expected:{} but Found:{}",rideRequestId,PENDING,rideRequest.getRideRequestStatus());
                throw new InvalidRideRequestStatusException("Ride request status is not PENDING");
            }

            //Step-3: Validate Driver Availability
            Driver currentDriver=getCurrentDriver();
            log.debug("Current Driver Details:{}",currentDriver);

            if(!currentDriver.getAvailable()){
                log.error("Driver with id {} is not available",currentDriver.getId());
                throw new DriverNotAvailableException("Driver is not available");
            }
            currentDriver.setAvailable(false);
            log.info("Updating the Current Driver Status as Not available");

            Driver driver = driverRepository.save(currentDriver);
            Ride ride=rideService.createNewRide(rideRequest,driver);

            log.info("Successfully Accepted Ride with id {}",rideRequestId);
            return modelMapper.map(ride,RideDto.class);
        }catch(InvalidRideStatusException | DriverNotAvailableException e){
            log.error("Validation error for ride request ID: {} - {}", rideRequestId, e.getMessage(), e);
            throw e;
        }catch(Exception e){
            log.error("Error while accepting ride {}",rideRequestId,e);
            throw new RideProcessingException("Error while accepting ride"+e);
        }
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

    private Driver getCurrentDriver() {
        return driverRepository.findById(2L).orElseThrow(()->new RuntimeException("Current Driver not Found"));
    }
}
