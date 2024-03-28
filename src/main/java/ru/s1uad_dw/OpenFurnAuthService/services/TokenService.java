package ru.s1uad_dw.OpenFurnAuthService.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.s1uad_dw.OpenFurnAuthService.dtos.TokensResponseDto;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.InvalidDataException;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.TokenExpiredException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {
    @Value("${secret-key}")
    private String secretKey;

    public String genToken(UUID userId, List<String> roles, int expirationTimeInHours) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("roles", roles)
                .expiration(new Date(System.currentTimeMillis() + expirationTimeInHours * 3600000)) // 1 hour
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    public TokensResponseDto genTokenPair(UUID userId, List<String> roles) {
        return new TokensResponseDto(
                genToken(userId, roles, 1),
                genToken(userId, roles, 24)
        );
    }

    public void checkTokenExpiration(String token){
        try {
            Claims claims = getTokenBody(token);
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())){
                throw new TokenExpiredException("Token expired");
            }
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidDataException("Invalid token");
        }
    }

    public UUID getSubject(String token){
        Claims claims = getTokenBody(token);
        return UUID.fromString(claims.getSubject());
    }

    public Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
