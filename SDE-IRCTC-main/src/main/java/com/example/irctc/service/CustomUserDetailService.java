package com.example.irctc.service;

import com.example.irctc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    // Autowired bean for UserRepository
    @Autowired
    private UserRepository userRepository;

    // Method to load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Loading user from database
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}
