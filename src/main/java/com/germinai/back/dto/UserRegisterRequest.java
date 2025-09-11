package com.germinai.back.dto;

import java.util.Set;

public record UserRegisterRequest(
        String username,
        String email,
        String password,
        Set<String> roles // ex: ["USER", "ADMIN"]
) {

}