package com.github.gribanoveu.cuddle.entities.services;

import com.github.gribanoveu.cuddle.dtos.enums.TokenType;
import com.github.gribanoveu.cuddle.security.CustomUserDetails;

import java.util.Date;
import java.util.List;

/**
 * @author Evgeny Gribanov
 * @version 26.09.2023
 */
public interface TokenService {
    String generateToken(CustomUserDetails usrDetails, TokenType tokenType);
    String extractSubject(String token);
    List<String> extractClaimsAsList(String token, String claim);
    String extractClaim(String token, String claim);
    Date extractExpire(String token);
}
