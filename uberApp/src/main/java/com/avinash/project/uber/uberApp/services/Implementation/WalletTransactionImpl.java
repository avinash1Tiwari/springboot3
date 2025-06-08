package com.avinash.project.uber.uberApp.services.Implementation;

import com.avinash.project.uber.uberApp.dto.WalletTransactionDto;
import com.avinash.project.uber.uberApp.entities.WalletTransaction;
import com.avinash.project.uber.uberApp.repositories.WalletTransactionRepository;
import com.avinash.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WalletTransactionImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
//        WalletTransaction walletTransaction = modelMapper.map(walletTransaction,WalletTransaction.class);
         walletTransactionRepository.save(walletTransaction);
    }
}
