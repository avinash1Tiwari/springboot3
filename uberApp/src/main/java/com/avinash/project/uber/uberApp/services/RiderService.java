package com.avinash.project.uber.uberApp.services;

//import com.avinash.project.uber.uberApp.dto.DriverDto;

import com.avinash.project.uber.uberApp.advices.ApiResponse;
import com.avinash.project.uber.uberApp.dto.DriverDto;
import com.avinash.project.uber.uberApp.dto.RideDto;
import com.avinash.project.uber.uberApp.dto.RideRequestDto;
import com.avinash.project.uber.uberApp.dto.RiderDto;
import com.avinash.project.uber.uberApp.entities.Rider;
import com.avinash.project.uber.uberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface RiderService {

    ResponseEntity<ApiResponse<RideRequestDto>> requestRide(RideRequestDto rideRequestDTO);

    RideDto cancelRide(Long rideId);


    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();
}
