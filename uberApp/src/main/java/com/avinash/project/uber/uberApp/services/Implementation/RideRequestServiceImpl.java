package com.avinash.project.uber.uberApp.services.Implementation;

import com.avinash.project.uber.uberApp.entities.RideRequest;
import com.avinash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.avinash.project.uber.uberApp.repositories.RideRequestRepository;
import com.avinash.project.uber.uberApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {


    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long RideRequestId) {

        try {
            RideRequest response = rideRequestRepository.findById(RideRequestId).orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with Id :" + RideRequestId));
//        .orElseThrow(()-> new ResourceNotFoundException("RideRequest not found with Id :" + RideRequestId));
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("something went wrong");
        }
    }

    @Override
    public void update(RideRequest rideRequest) {

        rideRequestRepository.findById(rideRequest.getId()).
                orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id : " + rideRequest.getId()));


        rideRequestRepository.save(rideRequest);

    }
}
