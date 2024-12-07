package com.example.irctc.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Method to handle unauthorized access
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Set HTTP status to unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Get PrintWriter to write response message
        PrintWriter writer = response.getWriter();
        // Write response message
        writer.println("Access Denied!!" + authException.getMessage());
    }
}
