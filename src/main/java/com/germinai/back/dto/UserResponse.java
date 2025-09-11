package com.germinai.back.dto;

import java.time.Instant;
import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        Set<String> roles,
        boolean enabled,
        Instant createdAt
) {
}

