package com.databasir.core.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtTokens {

    // 15 minutes
    private static final long ACCESS_EXPIRE_TIME = 1000 * 60 * 15;

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String ISSUER = "Databasir";

    private static final String SECRET = "Databasir2022";

    public String accessToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        return JWT.create()
                .withExpiresAt(new Date(new Date().getTime() + ACCESS_EXPIRE_TIME))
                .withIssuer(ISSUER)
                .withClaim("username", username)
                .sign(algorithm);
    }

    public boolean verify(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer(ISSUER)
                .build();
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.warn("verify jwt token failed " + e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) {
        return JWT.decode(token).getClaim("username").asString();
    }

    public LocalDateTime expireAt(String token) {
        long time = JWT.decode(token).getExpiresAt().getTime();
        return Instant.ofEpochMilli(time)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
