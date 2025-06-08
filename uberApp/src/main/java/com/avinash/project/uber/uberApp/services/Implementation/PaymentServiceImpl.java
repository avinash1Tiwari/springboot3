package com.avinash.project.uber.uberApp.services.Implementation;

import com.avinash.project.uber.uberApp.entities.Payment;
import com.avinash.project.uber.uberApp.entities.Ride;
import com.avinash.project.uber.uberApp.entities.enums.PaymentStatus;
import com.avinash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.avinash.project.uber.uberApp.repositories.PaymentRepository;
import com.avinash.project.uber.uberApp.services.PaymentService;
import com.avinash.project.uber.uberApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Ride ride) {

        Payment payment = paymentRepository.findByRide(ride).orElseThrow(()-> new ResourceNotFoundException("Payment not found for ride with id: " + ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);

    }

    @Override
    public Payment createPayment(Ride ride) {

        Payment payment = Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        return paymentRepository.save(payment);

    }

}
