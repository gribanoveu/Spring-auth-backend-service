package com.github.gribanoveu.cuddly.entities.repositories;

import com.github.gribanoveu.cuddly.entities.tables.Permission;
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
public interface PermissionRepository extends CrudRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
    List<Permission> findAll(Pageable pageable);
    Boolean existsByName(String name);
    void deleteByName(String name);
}
