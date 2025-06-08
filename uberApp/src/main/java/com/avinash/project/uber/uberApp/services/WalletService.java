package com.avinash.project.uber.uberApp.services;

import com.avinash.project.uber.uberApp.entities.Ride;
import com.avinash.project.uber.uberApp.entities.User;
import com.avinash.project.uber.uberApp.entities.Wallet;
import com.avinash.project.uber.uberApp.entities.enums.TransactionMethod;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    void withDrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createWallet(User user);

    Wallet findByUser(User user);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);
}
