package com.github.gribanoveu.cuddly.config;

import com.github.gribanoveu.cuddly.controllers.exeptions.entrypoint.AccessDeniedEntryPoint;
import com.github.gribanoveu.cuddly.controllers.exeptions.entrypoint.AuthErrorEntryPoint;
import com.github.gribanoveu.cuddly.controllers.exeptions.entrypoint.ServerErrorEntryPoint;
import com.github.gribanoveu.cuddly.entities.enums.Role;
import com.github.gribanoveu.cuddly.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

import static com.github.gribanoveu.cuddly.entities.enums.Role.*;

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
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // anonymous scope
                        .requestMatchers( HttpMethod.POST, "*/auth").anonymous() // issue token
                        .requestMatchers( HttpMethod.PATCH, "*/auth").anonymous() // refresh token
                        .requestMatchers(HttpMethod.POST, "*/user").anonymous() // register
                        .requestMatchers(HttpMethod.POST, "*/account/generate-code").anonymous()
                        .requestMatchers(HttpMethod.POST, "*/account/restore-password").anonymous()

                        // authenticated scope
                        .requestMatchers("*/users/**").hasAnyAuthority(ADMIN.scope(), MODERATOR.scope())
                        .requestMatchers("*/moderator/**").hasAnyAuthority(ADMIN.scope(), MODERATOR.scope())
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
