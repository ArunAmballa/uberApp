package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.entities.User;
import com.arun.project.uber.uberApp.entities.Wallet;
import com.arun.project.uber.uberApp.repositories.WalletRepository;
import com.arun.project.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet createWallet(User user) {
        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(0.0)
                .transactions(new ArrayList<>())
                .build();
        return walletRepository.save(wallet);
    }
}
