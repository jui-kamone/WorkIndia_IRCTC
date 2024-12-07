package com.example.irctc.config;

import com.example.irctc.security.JWTAuthenticationFilter;
import com.example.irctc.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    // Autowired bean for JWT authentication entry point
    @Autowired
    private JwtAuthenticationEntryPoint point;

    // Autowired bean for JWT authentication filter
    @Autowired
    private JWTAuthenticationFilter filter;

    // Autowired bean for user details service
    @Autowired
    private UserDetailsService userDetailsService;

    // Autowired bean for password encoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Bean definition for security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("/home/**").authenticated()
                                        .requestMatchers("/auth/login")
                                .permitAll()

                                        .requestMatchers("/auth/createUser").permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean definition for DaoAuthenticationProvider
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
      return provider;
    }
}
