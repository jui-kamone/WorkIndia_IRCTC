package com.example.irctc.repository;

import com.example.irctc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByUsername(String username);/**/
    public Optional<User> findByUsername(String username);
}
