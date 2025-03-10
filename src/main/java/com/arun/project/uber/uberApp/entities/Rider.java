package com.arun.project.uber.uberApp.entities;


import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
