package com.arun.project.uber.uberApp.services;

import com.arun.project.uber.uberApp.entities.User;
import com.arun.project.uber.uberApp.entities.Wallet;

public interface WalletService {

    Wallet createWallet(User user);
}
