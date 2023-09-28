package com.github.gribanoveu.auth.security;

import com.github.gribanoveu.auth.constants.ErrorMessages;
import com.github.gribanoveu.auth.entities.repositories.UserRepository;
import com.github.gribanoveu.auth.entities.tables.User;
import com.github.gribanoveu.auth.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Evgeny Gribanov
 * @version 28.08.2023
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, email)));

        return new CustomUserDetails(user);
    }
}

