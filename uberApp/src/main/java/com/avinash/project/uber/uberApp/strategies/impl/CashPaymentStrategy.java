package com.avinash.project.uber.uberApp.strategies.impl;

import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.Payment;
import com.avinash.project.uber.uberApp.entities.enums.PaymentStatus;
import com.avinash.project.uber.uberApp.entities.enums.TransactionMethod;
import com.avinash.project.uber.uberApp.services.Implementation.PaymentServiceImpl1;
import com.avinash.project.uber.uberApp.services.WalletService;
import com.avinash.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Let Rider pays 100rs in cash
//then => Driver gets 70 as 30rs(platform - commission) wiil be deducted from driver's-wallet by uberApp.


@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {                          //// COD => Cash On Delivery

private final WalletService walletService;
private final PaymentServiceImpl1 paymentServiceImpl1;

    @Override
    public void processPayment(Payment payment) {

        Drivers driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(),platformCommission,null
                ,payment.getRide() , TransactionMethod.RIDE);

        paymentServiceImpl1.updatePaymentStatus(payment, PaymentStatus.CONFIRMED);

    }
}
