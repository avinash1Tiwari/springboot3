package com.avinash.project.uber.uberApp.entities;

import com.avinash.project.uber.uberApp.entities.enums.TransactionMethod;
import com.avinash.project.uber.uberApp.entities.enums.TransactionType;
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
        @Index(name = "idx_wallat_transaction_wallet",columnList = "wallet_id"),        /// / wallet -> wallet_id
        @Index(name = "idx_wallat_transaction_ride",columnList = "ride_id")           /// / ride -> ride_id
})
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;

    @ManyToOne
    private Ride ride;                                        /// / ride -> ride_id

    private String transactionId;


    @ManyToOne
    private Wallet wallet;                                              /// / wallet -> wallet_id
    /// /multiple transaction can belong to a single Wallet


    @CreationTimestamp
    private LocalDateTime timeStamp;
}
