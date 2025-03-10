package com.arun.project.uber.uberApp.dto;

import com.arun.project.uber.uberApp.entities.enums.PaymentMethod;
import com.arun.project.uber.uberApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {

    private Long id;

    private RiderDto rider;

    private PointDto pickUpLocation;

    private PointDto dropOffLocation;

    private LocalDateTime requestedTime;

    private Double fare;

    private RideRequestStatus rideRequestStatus;

    private PaymentMethod paymentMethod;

}
