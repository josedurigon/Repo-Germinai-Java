package com.germinai.back.repository.interfaces;

import com.germinai.back.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users u WHERE u.username = :username OR u.email = :username LIMIT 1", nativeQuery = true)
    User findByUsernameOrEmail(@Param("username") String username);


}
