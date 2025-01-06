package com.arun.project.uber.uberApp.entities;

import com.arun.project.uber.uberApp.entities.enums.PaymentMethod;
import com.arun.project.uber.uberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    //4326 denotes it for Earth
    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point pickUpLocation;

    //4326 denotes it for Earth
    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point dropOffLocation;

    private Double fare;

    @CreationTimestamp
    private LocalDateTime createdTime; //when driver accepts the ride

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String otp;


    private LocalDateTime startedAt; //when driver starts the ride

    private LocalDateTime endedAt; // when driver ends the ride

}
