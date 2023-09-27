package com.github.gribanoveu.auth.entities.services.contract;

import com.github.gribanoveu.auth.entities.tables.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Evgeny Gribanov
 * @version 28.08.2023
 */
public interface UserService {
    @Transactional void saveUser(User user);
    @Transactional void deleteUserById(Long userId);
    @Transactional Boolean updatePassword(String email, String password);
    @Transactional Boolean updateEmail(Long userId, String newEmail);
    User findUserByEmail(String email);
    Boolean userExistByEmail(String email);
    List<User> getAllUsers(int pageNumber, int pageSize);
}
