package com.avinash.project.uber.uberApp.services;

import com.avinash.project.uber.uberApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long RideRequestId);

    void update(RideRequest rideRequest);
}
