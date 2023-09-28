package com.github.gribanoveu.auth.entities.repositories;

import com.github.gribanoveu.auth.entities.tables.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Modifying
    @Query("UPDATE User u SET u.password = :password, u.credentialsNonExpired = true WHERE u.email = :email")
    Integer updateUserPasswordByEmail(@Param("email") String email, @Param("password") String password);

    @Modifying
    @Query("UPDATE User u SET u.password = :password, u.credentialsNonExpired = false WHERE u.id = :userId")
    Integer updateUserPasswordAndCredentialsExpiredById(@Param("userId") Long userId, @Param("password") String password);

    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
    Integer updateUserEmail(@Param("id") Long id, @Param("email") String email);

    @Modifying
    @Query("UPDATE User u SET u.enabled = :enabled WHERE u.id = :id")
    Integer updateUserEnabled(@Param("id") Long id, @Param("enabled") Boolean enabled);
}
