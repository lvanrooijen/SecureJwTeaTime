package com.example.SecureJwTeaTime.security.config;

import com.example.SecureJwTeaTime.security.jwt.JwtAuthFilter;
import com.example.SecureJwTeaTime.security.jwt.UnAuthorizedEntryPoint;
import com.example.SecureJwTeaTime.util.routes.SecurityRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class CustomSecurityFilterChain {
  private final JwtAuthFilter jwtAuthFilter;
  private final CorsConfig corsConfig;
  private final UnAuthorizedEntryPoint unAuthorizedEntryPoint;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfig.corsConfiguration()))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(SecurityRoutes.getOpenRoutes())
                    .permitAll()
                    .anyRequest()
                    .authenticated());

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    http.exceptionHandling(ex -> ex.authenticationEntryPoint(unAuthorizedEntryPoint));

    return http.build();
  }
}
