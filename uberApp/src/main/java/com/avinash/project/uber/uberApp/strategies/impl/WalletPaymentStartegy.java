package com.avinash.project.uber.uberApp.strategies.impl;

import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.Payment;
import com.avinash.project.uber.uberApp.entities.Rider;
import com.avinash.project.uber.uberApp.entities.enums.PaymentMethod;
import com.avinash.project.uber.uberApp.entities.enums.PaymentStatus;
import com.avinash.project.uber.uberApp.entities.enums.TransactionMethod;
import com.avinash.project.uber.uberApp.repositories.PaymentRepository;
import com.avinash.project.uber.uberApp.services.WalletService;
import com.avinash.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Rider had 232 , Driver had 500rs
// Ride cost is rs100 and plateform-commisssion = 30rs
// Rider = 232-100 = 122
// Driver -> 500 + (ride-cost - commission) = 500 + (100-30) = 570
@Service@RequiredArgsConstructor
public class WalletPaymentStartegy implements PaymentStrategy {

    private final WalletService walletService;

    private final PaymentRepository paymentRepository;


    @Override
    @Transactional
    public void processPayment(Payment payment) {

        Drivers driver = payment.getRide().getDriver();

        Rider rider = payment.getRide().getRider();

//        deduct money from rider-wallet
        walletService.deductMoneyFromWallet(rider.getUser(),payment.getAmount(),null,payment.getRide(), TransactionMethod.RIDE);


        double driversCut = payment.getAmount() *(1-PLATFORM_COMMISSION);

        //        deduct money to driver-wallet
        walletService.addMoneyToWallet(driver.getUser(),driversCut,null,payment.getRide(),TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
