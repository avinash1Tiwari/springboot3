package com.avinash.project.uber.uberApp.services.Implementation;

import com.avinash.project.uber.uberApp.entities.Ride;
import com.avinash.project.uber.uberApp.entities.User;
import com.avinash.project.uber.uberApp.entities.Wallet;
import com.avinash.project.uber.uberApp.entities.WalletTransaction;
import com.avinash.project.uber.uberApp.entities.enums.TransactionMethod;
import com.avinash.project.uber.uberApp.entities.enums.TransactionType;
import com.avinash.project.uber.uberApp.repositories.WalletRepository;
import com.avinash.project.uber.uberApp.services.WalletService;
import com.avinash.project.uber.uberApp.services.WalletTransactionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);

        wallet.setBalance(wallet.getBalance() + amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();


        walletTransactionService.createNewWalletTransaction(walletTransaction);

      return walletRepository.save(wallet);

    }

    @Override
    public void withDrawAllMyMoneyFromWallet() {


    }

    @Override
    public Wallet findWalletById(Long walletId) {
       return walletRepository.findById(walletId).orElseThrow(()-> new RuntimeException("wallet with given walletId : " + walletId + " not found "));
    }

    @Override
    public Wallet createWallet(User user) {

        Wallet wallet = new Wallet();
        wallet.setUser(user);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("wallet not found for the userId : " + user.getId()));
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);

        wallet.setBalance(wallet.getBalance() - amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();


//        walletTransactionService.createNewWalletTransaction(walletTransaction);

        wallet.getTransactions().add(walletTransaction);

        return walletRepository.save(wallet);
    }
}
