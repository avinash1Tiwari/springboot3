package com.avinash.project.uber.uberApp.services.Implementation;

import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.Rating;
import com.avinash.project.uber.uberApp.entities.Ride;
import com.avinash.project.uber.uberApp.entities.Rider;
import com.avinash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.avinash.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.avinash.project.uber.uberApp.repositories.DriverRepository;
import com.avinash.project.uber.uberApp.repositories.RatingRepository;
import com.avinash.project.uber.uberApp.repositories.RiderRepository;
import com.avinash.project.uber.uberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RatingServiceImplentation implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;


    @Override
    public Drivers rateDriver(Ride ride, Integer rating) {
        Rating  ratingObj = ratingRepository.findByRide(ride).orElseThrow(()->
                new ResourceNotFoundException("Rating not found for ride with id " + ride.getId()));

        if(ratingObj.getDriverRating() != null)
            throw new RuntimeConflictException("Driver has already been rated, cannot rate again");

        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Drivers driver = ride.getDriver();

        Double driverUpdatedRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)                       /////(rating1->rating1.getDriverRating())
                .average().orElse(0.0);


        driver.setRating(driverUpdatedRating);
        return driverRepository.save(driver);
    }

    @Override
    public Rider rateRider(Ride ride,Integer rating) {
        Rating  ratingObj = ratingRepository.findByRide(ride).orElseThrow(()->
                new ResourceNotFoundException("Rating not found for ride with id " + ride.getId()));


        if(ratingObj.getRiderRating() != null)
            throw new RuntimeConflictException("Rider has already been rated, cannot rate again");


        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Rider rider = ride.getRider();

        Double riderUpdatedRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)                       /////(rating1->rating1.getDriverRating())
                .average().orElse(0.0);


        rider.setRating(riderUpdatedRating);
        return riderRepository.save(rider);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();
    }
}
