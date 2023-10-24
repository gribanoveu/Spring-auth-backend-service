package com.github.gribanoveu.cuddly.base;

import com.github.gribanoveu.cuddly.entities.enums.TokenType;
import com.github.gribanoveu.cuddly.entities.services.token.TokenService;
import com.github.gribanoveu.cuddly.security.CustomUserDetails;
import com.github.gribanoveu.cuddly.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Evgeny Gribanov
 * @version 11.09.2023
 */
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location=classpath:application-properties.yml"})
public abstract class BaseMockMvcTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected TestJsonUtils testJsonUtils;
    @Autowired private TokenService tokenService;
    @Autowired private CustomUserDetailsService usrDetailsService;

    protected String adminToken;
    protected String userToken;

    private static final String DATABASE_NAME = "auth_service_db";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15.4-alpine")
                    .withReuse(true)
                    .withDatabaseName(DATABASE_NAME);

    @DynamicPropertySource
    static void dataSources(DynamicPropertyRegistry registry) {
        registry.add("CONTAINER.URL", postgres::getJdbcUrl);
        registry.add("CONTAINER.USERNAME", postgres::getUsername);
        registry.add("CONTAINER.PASSWORD", postgres::getPassword);
    }

    @BeforeEach
    public void setUp() { // fixme why @WithUserDetails is not working
        var admin = (CustomUserDetails) usrDetailsService.loadUserByUsername("admin@email.com");
        adminToken = "Bearer " + tokenService.generateToken(admin, TokenType.ACCESS);

        var user = (CustomUserDetails) usrDetailsService.loadUserByUsername("user@email.com");
        userToken = "Bearer " + tokenService.generateToken(user, TokenType.ACCESS);
    }

}
