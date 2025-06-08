package com.avinash.project.uber.uberApp.repositories;

import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//1. ST_Distance(point1,point2) => to find distance between point1 and point2.
//2. ST_DWithin(point1,d) => to find all those point which are within distance d from point point1.


@Repository
public interface DriverRepository extends JpaRepository<Drivers, Long> {

//    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickupLocation) AS distance " +
//            "FROM drivers d " +
//            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 10000) " +
//            "ORDER BY distance " +
//            "LIMIT 10", nativeQuery = true)
//
//    List<Drivers> findTenNearestDrivers(Point pickupLocation);


    @Query(value = "SELECT d.* " +
            "FROM drivers d " +
            "WHERE d.is_available = true AND ST_DWithin(d.current_location, :pickUpLocation, 15000) " +
            "ORDER BY d.rating DESC limit 10", nativeQuery = true)
    List<Drivers> findTenTopRatedDriversNearBy(Point pickUpLocation);

    /// /    we consider nearBy 15km = 15000m


    @Query(value = "SELECT d.*, ST_Distance(d.current_location, ST_GeomFromText('POINT(18.567889 21.456734)', 4326)) AS distance " +
            "FROM drivers d " +
            "WHERE d.available = true " +
            "AND ST_DWithin(d.current_location, ST_GeomFromText('POINT(18.567889 21.456734)', 4326), 10000) " +
            "ORDER BY distance " +
            "LIMIT 10", nativeQuery = true)
    List<Drivers> findTenNearestDrivers(@Param("pickupLocation") Point pickupLocation);


    Optional<Drivers> findByUser(User user);
}




