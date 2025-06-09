package com.mopix.Mopix.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {

//    @Value("${jwt.secret}")
//    private String JWT_SECRET;

    private final String SECRET_KEY = "8f2a233d56c9b97bbf86db54d6b2df61ff13e487f37b5bba7a1d1b9859f6d9e38db22a87c93d7b71a7fcd0d19cb77b0c5c4e2bf27b3fd69bae1e0f7b41a6e8003fe42afd979bdb0d8469809b6ae546f961bc417ba3c93a089498a5f6adfe8a6a42763a93ce6a3fb999f5ac978057c864cff8c68bfb5bf3876a840d8d986de22841bd4475c451cd72a341d9ddf6a9300eec666ecfe3057ff02a46cc04cd6a75246be88889ab36b935789b09179c36d622c3779d11d2f7b98e63e4beaa25e3d7e834d7a300d813b1f41636ac1b00ba16244ac27eefb93841daa9e27daa28d376511c1ea0afd4fbcefe4ed7c5870b48f28d5da916ab8cb250061ac81fb92b2b4343";

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
