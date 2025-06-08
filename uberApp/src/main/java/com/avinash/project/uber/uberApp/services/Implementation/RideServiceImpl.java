package com.avinash.project.uber.uberApp.services.Implementation;


import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.Ride;
import com.avinash.project.uber.uberApp.entities.RideRequest;
import com.avinash.project.uber.uberApp.entities.Rider;
import com.avinash.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.avinash.project.uber.uberApp.entities.enums.RideStatus;
import com.avinash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.avinash.project.uber.uberApp.repositories.RideRepository;
import com.avinash.project.uber.uberApp.services.RideRequestService;
import com.avinash.project.uber.uberApp.services.RideService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Data
@RequiredArgsConstructor
@Service
public class RideServiceImpl implements RideService {

    private final RideRequestService rideRequestService;

    private final ModelMapper modelMapper;

    private final RideRepository rideRepository;


    @Override
    public Ride getRideById(Long rideId) {
        Ride ride =  rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("ride with given rideId not present"));
        System.out.println(ride.toString());
        return ride;
    }


    @Override
    public Ride createNewRide(RideRequest rideRequest, Drivers driver) {

        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        Ride ride = modelMapper.map(rideRequest, Ride.class);

        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateRandomOTP());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        System.out.println(ride.toString());
        Ride response =  rideRepository.save(ride);
        System.out.println(response.toString());
        return response;
    }


    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);

    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider,pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Drivers driver, PageRequest pageRequest) {
       return rideRepository.findByDriver(driver,pageRequest);
    }

    private String generateRandomOTP() {
        Random random = new Random();

        int otpInt = random.nextInt(10000);          /// generate a random number in range(0,10000);

        return String.format("%04d", otpInt);             //// it converts otpInt generate to always in four chars   Ex => otpInt = 12 =>   String.format("%04d",otpInt);  => "0014"
    }
}
