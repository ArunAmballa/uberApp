package com.arun.project.uber.uberApp.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private Double balance;

    @OneToMany(fetch =FetchType.LAZY, mappedBy = "wallet",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<WalletTransaction> transactions;

}
