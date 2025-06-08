package com.avinash.project.uber.uberApp.services;

import com.avinash.project.uber.uberApp.entities.Payment;
import com.avinash.project.uber.uberApp.entities.Ride;
import com.avinash.project.uber.uberApp.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createPayment(Ride ride);

}
