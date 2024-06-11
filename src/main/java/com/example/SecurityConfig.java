package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(customizer -> customizer
            .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
            .requestMatchers("/", "/logout", "/toInsert", "/login", "/insert").permitAll()
            .requestMatchers("/employee/**", "/administrator/**").permitAll()
            .anyRequest().authenticated()
    );
    return http.build();
  }
}