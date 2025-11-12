package com.germinai.back.repository.interfaces;

import com.germinai.back.entities.PasswordResetToken;
import com.germinai.back.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUserAndUsedFalseAndExpiryDateAfter(User user, Instant now);

    void deleteByExpiryDateBefore(Instant now);

    void deleteByUser(User user);
}
