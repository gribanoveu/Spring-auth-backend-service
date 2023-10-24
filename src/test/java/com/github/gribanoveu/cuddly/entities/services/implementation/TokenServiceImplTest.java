package com.github.gribanoveu.cuddly.entities.services.implementation;

import com.github.gribanoveu.cuddly.base.BaseMockMvcTest;
import com.github.gribanoveu.cuddly.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddly.entities.services.token.TokenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Evgeny Gribanov
 * @version 02.10.2023
 */
@SpringBootTest
class TokenServiceImplTest extends BaseMockMvcTest {
    @Autowired private TokenService tokenService;

    @Test
    public void testExtractSubject_ValidToken_ReturnsSubject() {
        var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJKb2huIERvZSJ9.ufwLbxhkmtZKhNS3NBv4Wqv1E6rvwdVAHmc6Co5zaU4";
        var subject = tokenService.extractSubject(token);
        Assertions.assertThat(subject).isEqualTo("John Doe");
    }

    @Test
    public void testExtractClaimsAsList_ValidToken_ReturnsClaimsAsList() {
        var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJBVV9VU0VSU19NQU5BR0VNRU5UIiwiQVVfTUFJTl9JTkZPX1ZJRVciXX0.e_2nud7VKBwY5DO-vMsfoAONnzQQQZzz8TKF1LYZ7SI";
        var claims = tokenService.extractClaimsAsList(token, "scope");
        Assertions.assertThat(claims).hasSize(2);
    }

    @Test
    public void testExtractClaim_ValidToken_ReturnsClaim() {
        var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6IlNDT1BFX0FETUlOIn0.ZvhZz3oBkoN0iSatyXwDJXnItT-rAqol8Ar6-LqVmyc";
        var claim = tokenService.extractClaim(token, "scope");
        Assertions.assertThat(claim).isEqualTo("SCOPE_ADMIN");
    }

    @Test
    public void testExtractExpiresAt_ValidToken_ReturnsExpiresAt() {
        var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzI1MzEyMDB9.BM498J3ulaG6v69S6qMEz6ETWgUeXZ7dpb-ZE4VOons";
        var expectedDate = "2023-01-01T00:00:00Z";
        var expiresAt = tokenService.extractExpire(token).toInstant().toString();
        Assertions.assertThat(expiresAt).isEqualTo(expectedDate);
    }

    @Test
    public void testExtractSubject_InvalidToken_ThrowsCredentialEx() {
        assertThrows(CredentialEx.class,
                () -> tokenService.extractSubject("invalid_token"));
    }

    @Test
    public void testExtractSubject_NullToken_ThrowsCredentialEx() {
        assertThrows(CredentialEx.class,
                () -> tokenService.extractSubject(null));
    }

    @Test
    public void testExtractClaimsAsList_InvalidToken_ThrowsCredentialEx() {
        assertThrows(CredentialEx.class,
                () -> tokenService.extractClaimsAsList("invalid_token", "scope"));
    }

    @Test
    public void testExtractClaim_InvalidToken_ThrowsCredentialEx() {
        assertThrows(CredentialEx.class,
                () -> tokenService.extractClaim("invalid_token", "scope"));
    }
    @Test
    public void testExtractExpiresAt_InvalidToken_ThrowsCredentialEx() {
        assertThrows(CredentialEx.class,
                () -> tokenService.extractExpire("invalid_token"));
    }

    @Test
    public void testExtractExpiresAt_NullToken_ThrowsCredentialEx() {
        assertThrows(CredentialEx.class,
                () -> tokenService.extractExpire(null));
    }

    @Test
    public void testExtractExpiresAt_EmptyToken_ThrowsCredentialEx() {
        assertThrows(CredentialEx.class,
                () -> tokenService.extractExpire(""));
    }

}