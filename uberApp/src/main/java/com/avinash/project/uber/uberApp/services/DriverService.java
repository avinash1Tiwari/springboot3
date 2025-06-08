package com.avinash.project.uber.uberApp.services;

import com.avinash.project.uber.uberApp.dto.DriverDto;
import com.avinash.project.uber.uberApp.dto.RideDto;
import com.avinash.project.uber.uberApp.dto.RiderDto;
import com.avinash.project.uber.uberApp.entities.Drivers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    RideDto acceptRide(Long rideReqId);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId, String otp);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Drivers getCurrentDriver();

    void updateDriverAvailability(Drivers driver,boolean available);

    Drivers createNewDriver(Drivers createDriver);
}
