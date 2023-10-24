package com.github.gribanoveu.cuddly.entities.services.user;

import com.github.gribanoveu.cuddly.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.repositories.UserRepository;
import com.github.gribanoveu.cuddly.entities.services.user.UserService;
import com.github.gribanoveu.cuddly.entities.tables.User;
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
    public void deleteUserByEmail(String userEmail) {
        if (userRepository.existsByEmail(userEmail)) userRepository.deleteByEmail(userEmail);
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
    public void updatePasswordByEmail(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public void updateUserPasswordAndCredentialsExpiredById(User user, String password) {
        user.setPassword(password);
        user.setCredentialsNonExpired(false);
        userRepository.save(user);
    }

    @Override
    public void updateEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    @Override
    public void updateEnabled(User user, Boolean enabled) {
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    @Override
    public void updateLocked(User user, Boolean locked) {
        user.setAccountNonLocked(locked);
        userRepository.save(user);
    }

}
