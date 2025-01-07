package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.dto.DriverDto;
import com.arun.project.uber.uberApp.dto.RideDto;
import com.arun.project.uber.uberApp.dto.RiderDto;
import com.arun.project.uber.uberApp.entities.Driver;
import com.arun.project.uber.uberApp.entities.Ride;
import com.arun.project.uber.uberApp.entities.RideRequest;
import com.arun.project.uber.uberApp.entities.enums.RideRequestStatus;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.arun.project.uber.uberApp.entities.enums.RideRequestStatus.PENDING;
import static com.arun.project.uber.uberApp.entities.enums.RideStatus.CONFIRMED;
import static com.arun.project.uber.uberApp.entities.enums.RideStatus.ONGOING;


@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final RideRequestService rideRequestService;


    @Override
    @Transactional
    public RideDto cancelRide(Long rideId) {
        log.info("Cancelling ride with Id:{}", rideId);

        try{
            //Step-1: Checking the Existence of Ride
            Ride ride = rideService.getRideById(rideId);
            log.debug("Fetched Ride Details:{}", ride);

            //Step-2: Validate Driver and Validate Ride Status
            Driver currentDriver=getCurrentDriver();
            validateDriver(currentDriver,ride.getDriver(),ride.getId());
            validateRideStatus(CONFIRMED,ride.getRideStatus(),ride.getId());

            //Step-3: Update the Status of Ride
            Ride updatedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);

            //Step-4 : Making Driver as Available
            currentDriver.setAvailable(true);
            driverRepository.save(currentDriver);

            log.info("Successfully Cancelled the Ride with rideId: {}", rideId);
            return modelMapper.map(updatedRide,RideDto.class);
        }catch(RideNotFoundException e){
            log.error("Ride with Id:{} not found", rideId);
            throw e;
        }catch(DriverMismatchException | InvalidRideStatusException e){
            log.error("Validation Error for Ride with rideId: {}:{}", rideId,e.getMessage());
            throw e;
        }catch(Exception e){
            log.error("Error while Cancelling the Ride with rideId: {}", rideId, e);
            throw new RideProcessingException("An Error Occurred while cancelling the ride");
        }
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        log.info("Ending Ride with rideId: {}", rideId);

        try{
            //Step-1: Check the Existence of ride
            Ride rideToBeUpdated=rideService.getRideById(rideId);
            log.debug("Fetched ride Details: {}", rideToBeUpdated);

            //Step-2: Validate Driver and Validate Ride Status
            Driver currentDriver=getCurrentDriver();
            validateDriver(currentDriver,rideToBeUpdated.getDriver(),rideToBeUpdated.getId());
            validateRideStatus(ONGOING,rideToBeUpdated.getRideStatus(),rideToBeUpdated.getId());

            //Step-3: Update the Status of Ride
            rideToBeUpdated.setEndedAt(LocalDateTime.now());
            Ride updatedRide = rideService.updateRideStatus(rideToBeUpdated, RideStatus.ENDED);

            //Step-4 : Making Driver as Available
            currentDriver.setAvailable(true);
            driverRepository.save(currentDriver);

            log.info("Successfully Ended the Ride with rideId: {}", rideId);
            return modelMapper.map(updatedRide,RideDto.class);
        }catch(RideNotFoundException e){
            log.error("Ride with Id:{} not found", rideId);
            throw e;
        }catch(DriverMismatchException | InvalidRideStatusException e){
            log.error("Validation Error for Ride with rideId: {}", rideId, e);
            throw e;
        }
        catch(Exception e){
            log.error("Error while Ending the Ride with rideId: {}", rideId, e);
            throw new RideProcessingException("An Error Occurred while ending the ride");
        }
    }

    @Override
    @Transactional
    public RideDto startRide(Long rideId,String otp) {
        log.info("Starting Ride with id {}",rideId);

        try{
            //Step-1:Validate Ride Existence
            Ride ride = rideService.getRideById(rideId);
            log.debug("Ride Details fetched :{}",ride);

            //Step-2: Validate Driver and Validate Ride Status and Validate Otp
            Driver currentDriver=getCurrentDriver();
            validateDriver(currentDriver,ride.getDriver(),ride.getId());
            validateRideStatus(CONFIRMED,ride.getRideStatus(),ride.getId());
            validateOtp(otp,ride.getOtp(),ride.getId());

            ride.setStartedAt(LocalDateTime.now());
            Ride savedRide = rideService.updateRideStatus(ride, ONGOING);
            log.info("Ride Status has been updated with Status;{}",savedRide.getRideStatus());

            log.info("Successfully started the ride with Ride Id:{}",savedRide.getId());
            return modelMapper.map(savedRide,RideDto.class);
        }catch(RideNotFoundException e){
            log.error("Ride with Id:{} not found", rideId);
            throw e;
        }catch (DriverMismatchException | InvalidRideStatusException | InvalidRideOtpException e) {
            log.error("Validation error for ride ID: {} - {}", rideId, e.getMessage(), e);
            throw e;
        } catch(Exception e){
         log.error("Error while starting ride {}",e.getMessage());
         throw new RideProcessingException("Error while starting ride"+e.getMessage());
        }
    }

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        log.info("Accepting  Ride with id {}",rideRequestId);

        try{
            //Step-1: Checking Existence of Ride Request
            RideRequest rideRequest = rideRequestService.getRideRequestById(rideRequestId);
            log.debug("RideRequest Details fetched :{}",rideRequest);

            //check the rideRequest status it should be Pending indicates no driver has accepted this rideRequest
            //Step-2: Validating Ride Request Status and Driver Availability
            Driver currentDriver=getCurrentDriver();
            validateRideRequestStatus(PENDING,rideRequest.getRideRequestStatus(),rideRequest.getId());
            validateDriverAvailability(currentDriver);
            currentDriver.setAvailable(false);
            Driver driver = driverRepository.save(currentDriver);

            Ride ride=rideService.createNewRide(rideRequest,driver);
            log.info("Create new ride with rideId: {}",ride.getId());

            log.info("Successfully Accepted Ride with id {}",rideRequestId);
            return modelMapper.map(ride,RideDto.class);
        }catch(RideRequestNotFoundException e){
            log.error("RideRequest with Id:{} not found", rideRequestId);
            throw e;
        }catch(InvalidRideStatusException | DriverNotAvailableException e){
            log.error("Validation error for ride request ID: {}", rideRequestId);
            throw e;
        }catch(Exception e){
            log.error("Error while accepting ride {}",rideRequestId,e);
            throw new RideProcessingException("Error while accepting ride"+e);
        }
    }


    private void validateDriver(Driver currentDriver,Driver rideDriver,Long rideId){
        if(!currentDriver.equals(rideDriver)) {
            log.error("Driver Mismatch for ride Id:{}, Expected:{}, but found:{}",rideId,rideDriver.getId(),currentDriver.getId());
            throw new DriverMismatchException("Current Driver Does not match the rides driver");
        }
    }

    private void validateRideStatus(RideStatus expectedRideStatus, RideStatus rideStatusFound,Long rideId){
        if(!expectedRideStatus.equals(rideStatusFound)) {
            log.error("Invalid Ride Status for the rideId:{} Expected:{} but found:{}",rideId,expectedRideStatus,rideStatusFound);
            throw new InvalidRideStatusException("Ride Status is not"+expectedRideStatus);
        }
    }

    private void validateOtp(String expectedOtp,String otpFound,Long rideId){
        if(!expectedOtp.equals(otpFound)) {
            log.error("Invalid otp for the ride with Id:{} Expected:{} but found:{}", rideId, expectedOtp, otpFound);
            throw new InvalidRideOtpException("OTP does not match");
        }
    }

    private void validateDriverAvailability(Driver currentDriver){
        if(!currentDriver.getAvailable()){
            log.error("Driver with id {} is not available",currentDriver.getId());
            throw new DriverNotAvailableException("Driver is not available");
        }
    }

    private void validateRideRequestStatus(RideRequestStatus expectedStatus, RideRequestStatus rideRequestStatusFound,Long rideRequestId){
        if(!expectedStatus.equals(rideRequestStatusFound)) {
            log.error("Invalid Ride Request Status for the ride request  with Id:{} Expected Status :{} but found:{}",rideRequestId,expectedStatus,rideRequestStatusFound);
            throw new InvalidRideRequestStatusException("Ride Request Status is not PENDING");
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
