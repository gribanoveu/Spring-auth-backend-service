package com.github.gribanoveu.cuddle.entities.tables;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gribanoveu.cuddle.dtos.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Entity
@Table(name = "users", indexes = @Index(name = "idx_is_banned", columnList = "ban_expiration"))
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "email", unique = true)
    private String email;

    @JsonProperty(access = WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ban_expiration")
    private LocalDateTime banExpiration;

    @Column(name = "restriction_reason")
    private String restrictionReason;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

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
        this.registrationDate = LocalDateTime.now().withNano(0);
    }
}

