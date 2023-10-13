package com.github.gribanoveu.cuddly.entities.repositories;

import com.github.gribanoveu.cuddly.entities.tables.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
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
}
