package com.germinai.back.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
        @UniqueConstraint(name = "uk_users_email", columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false, length=64)  private String username;
    @Column(nullable=false, length=120) private String email;
    @Column(nullable=false, length=120) private String passwordHash;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name="user_roles", joinColumns=@JoinColumn(name="user_id"))
//    @Column(name="role", nullable=false)
//    private Set<String> roles = Set.of("USER");

    @Column(nullable=false) private boolean enabled = true;
    @Column(nullable=false) private Instant createdAt = Instant.now();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    private Set<UserRoles> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserRoles> roles = new ArrayList<>();


}
