package com.github.gribanoveu.auth.base;

import com.github.gribanoveu.auth.controllers.dtos.request.LoginDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15.4-alpine");


    // fixme why @WithUserDetails is not working
    // fixme make create test users only for tests
    // fixme mock rsa keys and run in ci/cd
    protected String issueJwtTokenAsAdmin() throws Exception {
        var result =  mockMvc.perform(post("/v1/token/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(
                                new LoginDto("admin@email.com", "Qwerty123"))))
                .andExpect(status().isOk()).andReturn();

        return "Bearer " + testJsonUtils.getJsonValueFromMvcResult(result, "accessToken");
    }

    protected String issueTokenAsUser() throws Exception {
        var result = mockMvc.perform(post("/v1/token/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(
                                new LoginDto("user@email.com", "Qwerty123"))))
                .andExpect(status().isOk()).andReturn();

        return "Bearer " + testJsonUtils.getJsonValueFromMvcResult(result, "accessToken");
    }
}
