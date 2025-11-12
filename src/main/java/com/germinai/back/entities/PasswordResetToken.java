package com.germinai.back.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private boolean used = false;

    public boolean isExpired() {
        return Instant.now().isAfter(expiryDate);
    }
}
