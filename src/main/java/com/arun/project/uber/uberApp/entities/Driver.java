package com.arun.project.uber.uberApp.entities;


import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private Boolean available;

    //4326 denotes it for Earth
    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;
}
