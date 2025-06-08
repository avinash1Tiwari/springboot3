package com.avinash.project.uber.uberApp.services;

import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.Ride;
import com.avinash.project.uber.uberApp.entities.Rider;

public interface RatingService {

    Drivers rateDriver(Ride ride, Integer rating);
    Rider rateRider(Ride ride, Integer rating);
    void createNewRating(Ride ride);
}
