package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.entities.RideRequest;
import com.arun.project.uber.uberApp.exceptions.RideRequestNotFoundException;
import com.arun.project.uber.uberApp.repositories.RideRequestRepository;
import com.arun.project.uber.uberApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;
    private final ModelMapper modelMapper;


    @Override
    public RideRequest getRideRequestById(Long rideRequestId) {
        log.info("Checking if RideRequest with ID:{} Exists",rideRequestId);
        return rideRequestRepository.findById(rideRequestId).orElseThrow(()->{
            log.error("RideRequest with ID:{} does not exists",rideRequestId);
            return new RideRequestNotFoundException("RideRequest with ID:"+rideRequestId+"does not exist");
        });
    }

    @Override
    public void updateRideRequest(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId()).orElseThrow(() -> new RideRequestNotFoundException("RideRequest with id " + rideRequest.getId() + " not found"));
        rideRequestRepository.save(rideRequest);
    }
}
