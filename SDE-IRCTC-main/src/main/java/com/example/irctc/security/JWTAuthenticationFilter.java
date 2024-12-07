package com.example.irctc.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    // Logger for JWTAuthenticationFilter
    private final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    // Autowired bean for JwtHelper
    @Autowired
    private JwtHelper jwtHelper;

    // Autowired bean for UserDetailsService
    @Autowired
    private UserDetailsService userDetailsService;

    // Method to filter incoming requests
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, MalformedJwtException {

        // Get authorization header from request
        String requestHeader = request.getHeader("Authorization");

        // Check if the authorization header is present and starts with "Bearer"
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            // Extract token from the authorization header
            token = requestHeader.substring(7);
            try {
                // Extract username from token
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Invalid Header Value !! ");
        }

        // If username is not null and there is no existing authentication in SecurityContextHolder
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Fetch user details from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // Validate token
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {
                // Set the authentication in SecurityContextHolder
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("Validation fails !!");
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

}
