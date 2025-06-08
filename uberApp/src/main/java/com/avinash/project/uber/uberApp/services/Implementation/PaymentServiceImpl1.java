package com.avinash.project.uber.uberApp.services.Implementation;


import com.avinash.project.uber.uberApp.entities.Payment;
import com.avinash.project.uber.uberApp.entities.enums.PaymentStatus;
import com.avinash.project.uber.uberApp.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl1 {
    private final PaymentRepository paymentRepository;

    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }

}
