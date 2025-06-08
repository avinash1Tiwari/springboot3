package com.avinash.project.uber.uberApp.entities;


import com.avinash.project.uber.uberApp.entities.enums.PaymentMethod;
import com.avinash.project.uber.uberApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_rider",columnList = "rider_id")
})
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "GEOMETRY(Point,4326)")
    @JdbcTypeCode(SqlTypes.GEOMETRY)
    private Point pickupLocation;

    @Column(columnDefinition = "GEOMETRY(Point,4326)")
    @JdbcTypeCode(SqlTypes.GEOMETRY)
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime requestedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;             ///// hibernate converts rider -> rider_id

    private Double fare;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;

}
