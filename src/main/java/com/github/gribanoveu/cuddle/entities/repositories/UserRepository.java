package com.github.gribanoveu.cuddle.entities.repositories;

import com.github.gribanoveu.cuddle.dtos.enums.Role;
import com.github.gribanoveu.cuddle.entities.tables.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Evgeny Gribanov
 * @version 28.08.2023
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findUserById(Long id);
    List<User> findAll(Pageable pageable);
    Optional<List<User>> findAllByRoleEquals(Role role, Pageable pageable);
    void deleteByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.accountNonLocked = false AND u.banExpiration < :now")
    List<User> findBannedUsersWithExpiredBan(LocalDateTime now);
}
