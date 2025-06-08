package com.avinash.project.uber.uberApp.repositories;

import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.Rating;
import com.avinash.project.uber.uberApp.entities.Ride;
import com.avinash.project.uber.uberApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Driver;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Drivers driver);

    Optional<Rating> findByRide(Ride ride);
}
