package com.avinash.project.uber.uberApp.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(indexes = {
        @Index(name = "idx_rating_rider",columnList = "rider_id"),
        @Index(name = "idx_rating_driver",columnList = "driver_id")
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ride ride;                        ////one rating belongs to only one ride

    @ManyToOne
    private Rider rider;                          //// multiple rating belongs to same single rider.


    @ManyToOne
    private Drivers driver;

    private Integer driverRating;           //// rating for driver
    private Integer riderRating;            //// rating for rider
}
