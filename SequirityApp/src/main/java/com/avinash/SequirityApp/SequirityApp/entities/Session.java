package com.avinash.SequirityApp.SequirityApp.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime lastUsedAt;


    @ManyToOne                                         //// many session can have for 1 user.
    private User user;

}
