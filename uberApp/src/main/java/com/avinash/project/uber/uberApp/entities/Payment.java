package com.avinash.project.uber.uberApp.entities;


import com.avinash.project.uber.uberApp.entities.enums.PaymentMethod;
import com.avinash.project.uber.uberApp.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_payment_status",columnList = "paymentStatus")
})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne
    private Ride ride;                                                    /////(fetch = FetchType.LAZY)

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Double amount;

    @CreationTimestamp
    private LocalDateTime paymentTime;
}
