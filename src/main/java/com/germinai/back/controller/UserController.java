package com.germinai.back.controller;


import com.germinai.back.dto.UserRegisterRequest;
import com.germinai.back.dto.UserResponse;
import com.germinai.back.repository.UserRepository;
import com.germinai.back.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest req){
        UserResponse response = this.userService.register(req);
        return ResponseEntity.ok(response);
    }
}
