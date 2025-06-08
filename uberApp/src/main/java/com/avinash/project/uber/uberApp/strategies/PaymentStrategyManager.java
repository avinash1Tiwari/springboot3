package com.avinash.project.uber.uberApp.strategies;


import com.avinash.project.uber.uberApp.entities.enums.PaymentMethod;
import com.avinash.project.uber.uberApp.strategies.impl.CashPaymentStrategy;
import com.avinash.project.uber.uberApp.strategies.impl.WalletPaymentStartegy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStartegy walletPaymentStartegy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod)
    {
        return switch (paymentMethod){
            case WALLET -> walletPaymentStartegy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
