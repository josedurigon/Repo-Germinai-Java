package com.germinai.back.service;

import com.germinai.back.dto.UserRegisterRequest;
import com.germinai.back.dto.UserResponse;
import com.germinai.back.entities.User;
import com.germinai.back.entities.UserRoles;
import com.germinai.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRegisterRequest req) {

        User u = new User();
        u.setUsername(req.username());
        u.setEmail(req.email());
        u.setPasswordHash(passwordEncoder.encode(req.password()));
        u.setEnabled(true);

        // roles

        List<UserRoles> roles =  new ArrayList<>();

        if (req.roles() == null || req.roles().isEmpty()) {
            UserRoles defaultRole = new UserRoles();
            defaultRole.setUser(u);
            defaultRole.setRole("USER");
            roles.add(defaultRole);
        } else {
            for (String r : req.roles()) {
                UserRoles ur = new UserRoles();
                ur.setUser(u);
                ur.setRole(r.toUpperCase());
                roles.add(ur);
            }
        }

        u.setRoles(roles);

        User saved = userRepository.save(u);

        return new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getRoles().stream().map(UserRoles::getRole).collect(Collectors.toSet()),
                saved.isEnabled(),
                saved.getCreatedAt()
        );
    }
}
