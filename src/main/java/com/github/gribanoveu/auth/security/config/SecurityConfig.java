package com.github.gribanoveu.auth.security.config;

import com.github.gribanoveu.auth.controllers.exeptions.CustomAccessDeniedHandler;
import com.github.gribanoveu.auth.controllers.exeptions.ServerErrorEntryPoint;
import com.github.gribanoveu.auth.security.userdetails.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.github.gribanoveu.auth.entities.enums.Permissions.*;

/**
 * @author Evgeny Gribanov
 * @version 28.08.2023
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetails;
    private final ServerErrorEntryPoint serverErrorEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http // to make API available for requests from other domains - enable cors
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("*/permission").hasAuthority(AU_PERMISSIONS_MANAGEMENT.scope())
                        .requestMatchers("*/user-manage/**").hasAuthority(AU_USERS_MANAGEMENT.scope())
                        .requestMatchers("*/token/**").permitAll()
                        .requestMatchers("*/account/generate-code").permitAll()
                        .requestMatchers("*/account/restore-password").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(serverErrorEntryPoint))
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(serverErrorEntryPoint));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetails);
        return new ProviderManager(authProvider);
    }
}
