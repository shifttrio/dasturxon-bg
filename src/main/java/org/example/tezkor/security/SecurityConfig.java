package org.example.tezkor.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // CSRF o‘chiriladi
            .csrf(csrf -> csrf.disable())

            // ❌ CORS bu yerda YO‘Q
            .cors(cors -> cors.disable())

            // Session yo‘q (JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Authorization
            .authorizeHttpRequests(auth -> auth

                // OPTIONS (preflight)
                .requestMatchers(HttpMethod.OPTIONS, "/").permitAll()

                // PUBLIC endpointlar
                .requestMatchers(
                    "/api/contacts/",
                    "/api/cart/",
                    "/user/",
                    "/auth/",
                    "/error",
                    "/swagger-ui/",
                    "/v3/api-docs/**"
                ).permitAll()

                // Qolgan hammasi token talab qiladi
                .anyRequest().authenticated()
            )

            // JWT filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
