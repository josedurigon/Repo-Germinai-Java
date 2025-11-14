package com.germinai.back.service;

import com.germinai.back.entities.PasswordResetToken;
import com.germinai.back.entities.User;
import com.germinai.back.repository.interfaces.PasswordResetTokenRepository;
import com.germinai.back.repository.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${app.password.reset.token.expiration:3600000}")
    private long tokenExpirationMs;

    @Transactional
    public void requestPasswordReset(String email) {
        User user = userRepository.findByUsernameOrEmail(email);

        if (user == null) {
            return;
        }

        tokenRepository.deleteByUser(user);

        String token = generateSecureToken();
        Instant expiryDate = Instant.now().plusMillis(tokenExpirationMs);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(expiryDate);
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> optionalToken = tokenRepository.findByToken(token);

        if (optionalToken.isEmpty()) {
            throw new IllegalArgumentException("Token inválido");
        }

        PasswordResetToken resetToken = optionalToken.get();

        if (resetToken.isUsed()) {
            throw new IllegalArgumentException("Token já foi utilizado");
        }

        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("Token expirado");
        }

        User user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

    @Transactional
    public boolean validateToken(String token) {
        Optional<PasswordResetToken> optionalToken = tokenRepository.findByToken(token);

        if (optionalToken.isEmpty()) {
            return false;
        }

        PasswordResetToken resetToken = optionalToken.get();
        return !resetToken.isUsed() && !resetToken.isExpired();
    }

    @Transactional
    public void cleanupExpiredTokens() {
        tokenRepository.deleteByExpiryDateBefore(Instant.now());
    }

    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
