package com.github.gribanoveu.cuddle.entities.repositories;

import com.github.gribanoveu.cuddle.entities.enums.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Evgeny Gribanov
 * @version 21.09.2023
 */
@Repository
public interface PermissionRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
    List<Role> findAll(Pageable pageable);
    Boolean existsByName(String name);
    void deleteByName(String name);
}
