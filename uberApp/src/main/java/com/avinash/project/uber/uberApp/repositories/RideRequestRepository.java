package com.avinash.project.uber.uberApp.repositories;

import com.avinash.project.uber.uberApp.entities.RideRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ride_request " +
            "(drop_off_location, fare, payment_method, pickup_location, requested_time, ride_request_status, rider_id) " +
            "VALUES (ST_GeomFromText(:dropOffLocation, 4326), :fare, :paymentMethod, ST_GeomFromText(:pickupLocation, 4326), :requestedTime, :rideRequestStatus, :riderId)",
            nativeQuery = true)
    void saveRideRequest(
            @Param("dropOffLocation") String dropOffLocation,
            @Param("fare") Double fare,
            @Param("paymentMethod") String paymentMethod,
            @Param("pickupLocation") String pickupLocation,
            @Param("requestedTime") LocalDateTime requestedTime,
            @Param("rideRequestStatus") String rideRequestStatus,
            @Param("riderId") Long riderId
    );
}
