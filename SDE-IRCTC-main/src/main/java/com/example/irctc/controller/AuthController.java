package com.example.irctc.controller;

import com.example.irctc.entity.JwtRequest;
import com.example.irctc.entity.JwtResponse;
import com.example.irctc.entity.User;
import com.example.irctc.security.JwtHelper;
import com.example.irctc.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Autowired bean for user details service
    @Autowired
    private UserDetailsService userDetailsService;

    // Autowired bean for authentication manager
    @Autowired
    private AuthenticationManager manager;

    // Autowired bean for password encoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Autowired bean for user service implementation
    @Autowired
    private UserServiceImpl userService;

    // Autowired bean for JWT helper
    @Autowired
    private JwtHelper helper;

    // Logger for AuthController
    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .JwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    // Method to authenticate user credentials
    private void doAuthenticate(String email, String password) {
        // Creating an authentication token with provided email and password
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            // Authenticating the user using the authentication manager
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            // Handling bad credentials exception
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    // Exception handler for BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        // Returning a message for invalid credentials
        return "Credentials Invalid !!";
    }

    // Endpoint for user registration
    @PostMapping("/createUser")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}
