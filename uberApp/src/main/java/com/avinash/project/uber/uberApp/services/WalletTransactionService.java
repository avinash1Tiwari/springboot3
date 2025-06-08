package com.avinash.project.uber.uberApp.services;

import com.avinash.project.uber.uberApp.dto.WalletTransactionDto;
import com.avinash.project.uber.uberApp.entities.WalletTransaction;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransaction walletTransaction);
}
