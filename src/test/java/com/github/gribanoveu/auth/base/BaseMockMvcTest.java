package com.github.gribanoveu.auth.base;

import com.github.gribanoveu.auth.entities.enums.TokenType;
import com.github.gribanoveu.auth.entities.services.contract.TokenService;
import com.github.gribanoveu.auth.security.CustomUserDetails;
import com.github.gribanoveu.auth.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Evgeny Gribanov
 * @version 11.09.2023
 */
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseMockMvcTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected TestJsonUtils testJsonUtils;
    @Autowired private TokenService tokenService;
    @Autowired private CustomUserDetailsService usrDetailsService;

    protected String adminToken;
    protected String userToken;

    @BeforeEach
    public void setUp() { // fixme why @WithUserDetails is not working
        var admin = (CustomUserDetails) usrDetailsService.loadUserByUsername("admin@email.com");
        adminToken = "Bearer " + tokenService.generateToken(admin, TokenType.ACCESS);

        var user = (CustomUserDetails) usrDetailsService.loadUserByUsername("user@email.com");
        userToken = "Bearer " + tokenService.generateToken(user, TokenType.ACCESS);
    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15.4-alpine");

}
