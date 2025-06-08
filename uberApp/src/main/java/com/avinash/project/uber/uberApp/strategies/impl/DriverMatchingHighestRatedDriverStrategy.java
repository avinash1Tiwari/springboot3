package com.avinash.project.uber.uberApp.strategies.impl;


import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.RideRequest;
import com.avinash.project.uber.uberApp.repositories.DriverRepository;
import com.avinash.project.uber.uberApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {


    private final DriverRepository driverRepository;

    @Override
    public List<Drivers> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenTopRatedDriversNearBy(rideRequest.getPickupLocation());
    }
}
