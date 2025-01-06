package com.arun.project.uber.uberApp.dto;


import com.arun.project.uber.uberApp.entities.enums.PaymentMethod;
import com.arun.project.uber.uberApp.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideDto {

    private Long id;

    private RiderDto rider;

    private DriverDto driver;

    private PointDto pickUpLocation;

    private PointDto dropOffLocation;

    private Double fare;

    private LocalDateTime createdTime; //when driver accepts the ride

    private RideStatus rideStatus;

    private PaymentMethod paymentMethod;

    private String otp;

    private LocalDateTime startedAt; //when driver starts the ride

    private LocalDateTime endedAt; // when driver ends the ride

}
