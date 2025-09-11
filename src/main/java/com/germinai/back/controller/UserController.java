package com.germinai.back.controller;


import com.germinai.back.dto.UserRegisterRequest;
import com.germinai.back.dto.UserResponse;
import com.germinai.back.dto.UserRolesDto;
import com.germinai.back.entities.UserRoles;
import com.germinai.back.repository.UserRepository;
import com.germinai.back.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest req){
        UserResponse response = this.userService.register(req);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public List<UserResponse> listarTodosUsuarios() {
        return this.userRepository.findAll().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getRoles().stream()
                                .map(r -> r.getRole()) // apenas o nome do role
                                .collect(Collectors.toSet()),
                        u.isEnabled(),
                        u.getCreatedAt()
                ))
                .toList();
    }


}
