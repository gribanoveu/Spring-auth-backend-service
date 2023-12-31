package com.github.gribanoveu.cuddle.security;

import com.github.gribanoveu.cuddle.entities.repositories.UserRepository;
import com.github.gribanoveu.cuddle.entities.tables.User;
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
    private static final String USER_NOT_FOUND = "Пользователь '%s' не найден";
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));

        return new CustomUserDetails(user);
    }
}

