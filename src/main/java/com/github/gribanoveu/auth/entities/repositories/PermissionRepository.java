package com.github.gribanoveu.auth.entities.repositories;

import com.github.gribanoveu.auth.entities.tables.Permission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Query("UPDATE Permission p SET p.name =:name WHERE p.id = :id")
    Integer updatePermissionName(@Param("id") Long id, @Param("name") String name);
}
