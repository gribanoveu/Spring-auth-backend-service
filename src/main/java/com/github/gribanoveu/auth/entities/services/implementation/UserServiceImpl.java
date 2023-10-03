package com.github.gribanoveu.auth.entities.services.implementation;

import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.repositories.UserRepository;
import com.github.gribanoveu.auth.entities.services.contract.UserService;
import com.github.gribanoveu.auth.entities.tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
                new CredentialEx(ResponseCode.USER_NOT_EXIST));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() ->
                new CredentialEx(ResponseCode.USER_NOT_EXIST));
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) userRepository.deleteById(userId);
        else throw new CredentialEx(ResponseCode.USER_NOT_EXIST);
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
    public Boolean updatePasswordByEmail(String email, String password) {
       return userRepository.updateUserPasswordByEmail(email, password) > 0;
    }

    @Override
    public Boolean updateUserPasswordAndCredentialsExpiredById(Long userId, String password) {
        return userRepository.updateUserPasswordAndCredentialsExpiredById(userId, password) > 0;
    }

    @Override
    public Boolean updateEmail(Long userId, String newEmail) {
        return userRepository.updateUserEmail(userId, newEmail) > 0;
    }

    @Override
    public Boolean updateEnabled(Long userId, Boolean enabled) {
        return userRepository.updateUserEnabled(userId, enabled) > 0;
    }

}
