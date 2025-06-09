package com.mopix.Mopix.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

//    @Value("${jwt.secret}")
//    private String JWT_SECRET;
    private final String JWT_SECRET = "8f2a233d56c9b97bbf86db54d6b2df61ff13e487f37b5bba7a1d1b9859f6d9e38db22a87c93d7b71a7fcd0d19cb77b0c5c4e2bf27b3fd69bae1e0f7b41a6e8003fe42afd979bdb0d8469809b6ae546f961bc417ba3c93a089498a5f6adfe8a6a42763a93ce6a3fb999f5ac978057c864cff8c68bfb5bf3876a840d8d986de22841bd4475c451cd72a341d9ddf6a9300eec666ecfe3057ff02a46cc04cd6a75246be88889ab36b935789b09179c36d622c3779d11d2f7b98e63e4beaa25e3d7e834d7a300d813b1f41636ac1b00ba16244ac27eefb93841daa9e27daa28d376511c1ea0afd4fbcefe4ed7c5870b48f28d5da916ab8cb250061ac81fb92b2b4343";
    private final long JWT_EXPIRATION = 86400000;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(
                            "8f2a233d56c9b97bbf86db54d6b2df61ff13e487f37b5bba7a1d1b9859f6d9e38db22a87c93d7b71a7fcd0d19cb77b0c5c4e2bf27b3fd69bae1e0f7b41a6e8003fe42afd979bdb0d8469809b6ae546f961bc417ba3c93a089498a5f6adfe8a6a42763a93ce6a3fb999f5ac978057c864cff8c68bfb5bf3876a840d8d986de22841bd4475c451cd72a341d9ddf6a9300eec666ecfe3057ff02a46cc04cd6a75246be88889ab36b935789b09179c36d622c3779d11d2f7b98e63e4beaa25e3d7e834d7a300d813b1f41636ac1b00ba16244ac27eefb93841daa9e27daa28d376511c1ea0afd4fbcefe4ed7c5870b48f28d5da916ab8cb250061ac81fb92b2b4343"
                                    .getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
