package com.germinai.back.controller;

import com.germinai.back.dto.LoginRequest;
import com.germinai.back.entities.User;
import com.germinai.back.repository.interfaces.UserRepository;
import com.germinai.back.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        User user = userRepository.findByUsernameOrEmail(request.username());

        if (user == null)
            throw new RuntimeException("Usuario nao encontrado");
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash()))
            throw new RuntimeException("Credenciais invalidas");

        String token = jwtUtil.generateToken(user);





        return ResponseEntity.ok(token);
    }


}
