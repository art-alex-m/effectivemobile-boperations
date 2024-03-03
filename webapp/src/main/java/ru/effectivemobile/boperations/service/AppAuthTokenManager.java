package ru.effectivemobile.boperations.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.CompressionAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.domain.core.boundary.response.LoginResponse;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Менеджер токенов авторизации
 *
 * <p>
 * <a href="https://www.baeldung.com/java-json-web-tokens-jjwt">Supercharge Java Authentication with JSON Web Tokens (JWTs)</a><br>
 * <a href="https://github.com/jwtk/jjwt">JJWT</a><br>
 * <a href="https://datatracker.ietf.org/doc/html/rfc7519#section-4.1">Registered Claim Names</a><br>
 * </p>
 */
@Component
@AllArgsConstructor
@Slf4j
public class AppAuthTokenManager {

    private static final String PEM_DELIMITER = ",";

    private final KeyPair keyPair;

    private final CompressionAlgorithm compressionAlgorithm;

    private final int ttl = 3600;

    public AppUserDetails validate(String rawToken) {
        String token = prepareToken(rawToken);

        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(keyPair.getPublic()).build().parseSignedClaims(token);
            String userId = claimsJws.getPayload().get(JwtClaim.UID, String.class);
            List<String> permissions =
                    Arrays.stream(claimsJws.getPayload().get(JwtClaim.PEM, String.class).split(PEM_DELIMITER)).toList();
            return new AppUserDetails(UUID.fromString(userId), userId, permissions);
        } catch (IllegalArgumentException | JwtException e) {
            log.warn("Parse JWT error: {}", e.getMessage(), e);
        }

        return null;
    }

    public String create(LoginResponse loginResponse) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + (ttl * 1000));

        String token;
        try {
            token = Jwts.builder()
                    .id(id)
                    .issuedAt(now)
                    .notBefore(now)
                    .expiration(exp)
                    .claim(JwtClaim.UID, loginResponse.getUser().getId())
                    .claim(JwtClaim.PEM, String.join(PEM_DELIMITER, loginResponse.getPermissions()))
                    .compressWith(compressionAlgorithm)
                    .signWith(keyPair.getPrivate())
                    .compact();
        } catch (Exception e) {
            log.error("Unable to create JWT #{}: {}", id, e.getMessage(), e);
            token = id;
        }

        return token;
    }

    public String prepareToken(String rawToken) {
        return rawToken != null
                ? rawToken.replace("Bearer ", "")
                : "";
    }
}
