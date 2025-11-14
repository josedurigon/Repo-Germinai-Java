package com.germinai.back.controller;

import com.germinai.back.dto.LoginRequest;
import com.germinai.back.entities.User;
import com.germinai.back.repository.interfaces.UserRepository;
import com.germinai.back.service.PasswordResetService;
import com.germinai.back.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordResetService passwordResetService;

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

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email é obrigatório");
        }

        try {
            passwordResetService.requestPasswordReset(email);
            return ResponseEntity.ok(Map.of("message", "Se um usuário com este e-mail for encontrado, um link de redefinição foi enviado."));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("message", "Se um usuário com este e-mail for encontrado, um link de redefinição foi enviado."));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Token é obrigatório"));
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Nova senha é obrigatória"));
        }

        if (newPassword.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("message", "A senha deve ter no mínimo 6 caracteres"));
        }

        try {
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Erro ao redefinir senha"));
        }
    }

    @GetMapping("/validate-reset-token")
    public ResponseEntity<?> validateResetToken(@RequestParam String token) {
        boolean isValid = passwordResetService.validateToken(token);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }

}
