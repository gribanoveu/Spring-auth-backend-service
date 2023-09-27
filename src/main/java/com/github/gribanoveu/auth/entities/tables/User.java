package com.github.gribanoveu.auth.entities.tables;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.gribanoveu.auth.constants.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "position")
    private String position;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Permission> permissionCollection;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired = true;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @PrePersist
    private void setDefaultAccountStatus() {
        this.registrationDate = LocalDateTime.now();
        this.position = Constants.DEFAULT_USER_POSITION;
    }
}

