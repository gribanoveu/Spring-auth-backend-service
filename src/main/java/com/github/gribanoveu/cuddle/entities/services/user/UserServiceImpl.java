package com.github.gribanoveu.cuddle.entities.services.user;

import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import com.github.gribanoveu.cuddle.dtos.enums.Role;
import com.github.gribanoveu.cuddle.entities.repositories.UserRepository;
import com.github.gribanoveu.cuddle.entities.tables.User;
import com.github.gribanoveu.cuddle.exeptions.CredentialEx;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<User> getAllUsers(Pageable pageable) {
        return userRepository.findAllByRoleEquals(Role.USER, pageable).orElseThrow(() ->
                new CredentialEx(ResponseCode.USERS_NOT_FOUND));
    }

    @Override
    public List<User> getAllModers(Pageable pageable) {
        return userRepository.findAllByRoleEquals(Role.MODERATOR, pageable).orElseThrow(() ->
                new CredentialEx(ResponseCode.USERS_NOT_FOUND));
    }

    @Override
    public void updatePasswordByEmail(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public void updateEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    @Override
    public void enabledUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(User user, String disableReasonCode) {
        user.setEnabled(false);
        user.setRestrictionReason(disableReasonCode);
        userRepository.save(user);
    }

    @Override
    public void unlockUser(User user) {
        user.setAccountNonLocked(true);
        user.setBanExpiration(null);
        userRepository.save(user);
    }

    @Override
    public void lockUser(User user, LocalDateTime banExpiration, String banReason) {
        user.setAccountNonLocked(false);
        user.setBanExpiration(banExpiration);
        user.setRestrictionReason(banReason);
        userRepository.save(user);
    }

    @Override
    public void updateRole(User user, Role role) {
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public int removeExpiredBans() {
        var unbannedUsers = 0;
        var now = LocalDateTime.now();
        var bannedUsers = userRepository.findBannedUsersWithExpiredBan(now);

        for (User user : bannedUsers) {
            if (user.getBanExpiration() != null && user.getBanExpiration().isBefore(now)) {
                user.setAccountNonLocked(true); // Снятие бана
                user.setBanExpiration(null); // Сброс времени истечения бана
                userRepository.save(user);
                unbannedUsers++;
            }
        }
        return unbannedUsers;
    }
}
