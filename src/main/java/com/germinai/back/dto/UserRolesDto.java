package com.germinai.back.dto;

import com.germinai.back.entities.User;

public record UserRolesDto(
        Long id,
        User user,
        String role

) {
}
