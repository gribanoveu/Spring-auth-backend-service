package com.github.gribanoveu.cuddle.config;

import com.github.gribanoveu.cuddle.exeptions.entrypoint.AccessDeniedEntryPoint;
import com.github.gribanoveu.cuddle.exeptions.entrypoint.AuthErrorEntryPoint;
import com.github.gribanoveu.cuddle.exeptions.entrypoint.ServerErrorEntryPoint;
import com.github.gribanoveu.cuddle.security.CustomUserDetailsService;
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
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static com.github.gribanoveu.cuddle.dtos.enums.Role.ADMIN;
import static com.github.gribanoveu.cuddle.dtos.enums.Role.MODERATOR;

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
    private final AuthErrorEntryPoint authErrorEntryPoint;
    private final ServerErrorEntryPoint serverErrorEntryPoint;
    private final AccessDeniedEntryPoint accessDeniedHandler;

    // permitAll - allow auth and anonymous users
    // anonymous - allow only anonymous
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http // to make API available for requests from other domains - enable cors
                .csrf(CsrfConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("POST", "GET", "PUT", "DELETE"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(req -> req
                        // anonymous scope
                        .requestMatchers("/open/**").anonymous()

                        // anonymous scope (swagger)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // authenticated scope
                        .requestMatchers("*/moderator/**").hasAnyAuthority(ADMIN.scope(), MODERATOR.scope())
                        .requestMatchers("*/role/**").hasAnyAuthority(ADMIN.scope())
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(serverErrorEntryPoint))
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(authErrorEntryPoint));

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
