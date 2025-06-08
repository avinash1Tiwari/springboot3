package com.avinash.project.uber.uberApp.strategies;

import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Drivers> findMatchingDriver(RideRequest rideRequest);                   ///in interface, all methods are kept in public
}
