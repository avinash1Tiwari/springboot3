package com.avinash.project.uber.uberApp.entities;

import com.avinash.project.uber.uberApp.entities.enums.PaymentMethod;
import com.avinash.project.uber.uberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_rider",columnList = "rider_id"),
        @Index(name = "idx_driver",columnList = "driver_id")
})
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    @JdbcTypeCode(SqlTypes.GEOMETRY)
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    @JdbcTypeCode(SqlTypes.GEOMETRY)
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Drivers driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private String otp;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", pickupLocation=" + (pickupLocation != null ? pickupLocation.toText() : "null") +
                ", dropOffLocation=" + (dropOffLocation != null ? dropOffLocation.toText() : "null") +
                ", createdTime=" + createdTime +
                ", riderId=" + (rider != null ? rider.getId() : "null") +
                ", driverId=" + (driver != null ? driver.getId() : "null") +
                ", paymentMethod=" + paymentMethod +
                ", rideStatus=" + rideStatus +
                ", otp='" + otp + '\'' +
                ", fare=" + fare +
                ", startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                '}';
    }

}
