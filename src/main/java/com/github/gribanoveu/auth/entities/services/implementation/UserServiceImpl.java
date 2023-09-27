package com.github.gribanoveu.auth.entities.services.implementation;

import com.github.gribanoveu.auth.constants.ErrorMessages;
import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.repositories.UserRepository;
import com.github.gribanoveu.auth.entities.services.contract.UserService;
import com.github.gribanoveu.auth.entities.tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Evgeny Gribanov
 * @version 28.08.2023
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new CredentialEx(ErrorMessages.USER_NOT_EXIST, HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) userRepository.deleteById(userId);
        else throw new CredentialEx(ErrorMessages.USER_NOT_EXIST, HttpStatus.NOT_FOUND);
    }

    @Override
    public Boolean userExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getAllUsers(int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public Boolean updatePassword(String email, String password) {
       return userRepository.updateUserPassword(email, password) > 0;
    }

    @Override
    public Boolean updateEmail(Long userId, String newEmail) {
        return userRepository.updateUserEmail(userId, newEmail) > 0;
    }

}
