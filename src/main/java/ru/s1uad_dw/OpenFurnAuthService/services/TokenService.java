package ru.s1uad_dw.OpenFurnAuthService.services;


import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;
import ru.s1uad_dw.OpenFurnAuthService.dtos.TokensResponseDto;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.TokenLifetimeExpiredException;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.UnknownTokenException;
import ru.s1uad_dw.OpenFurnAuthService.reporitories.ClientRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @Value("${secret-key}")
    private String secretKey;

    public String genToken(UUID userId, List<String> roles, int expirationTimeInHours) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("roles", roles)
                .expiration(new Date(System.currentTimeMillis() + expirationTimeInHours * 3600000L)) // 1 hour
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public TokensResponseDto genTokenPair(UUID userId, List<String> roles) {
        return new TokensResponseDto(
                genToken(userId, roles, 1),
                genToken(userId, roles, 24)
        );
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = getTokenBody(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true; // В случае ошибки при парсинге токена считаем его просроченным
        }
    }

    public TokensResponseDto updateTokens(String token) {
        checkToken(token);
        Claims claims = getTokenBody(token);
        UUID userId = UUID.fromString(claims.getSubject());
        List<String> roles = (List<String>) claims.get("roles");
        TokensResponseDto tokenPair = genTokenPair(userId, roles);
        clientService.save(new Client(userId, List.of(tokenPair.getRefreshToken())));
        return tokenPair;
    }

    public Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenContains(String token) {
        Claims claims = getTokenBody(token);
        UUID userId = UUID.fromString(claims.getSubject());
        Optional<Client> client = clientRepository.findById(userId);
        return client.isPresent() && client.get().getRefreshTokens().contains(token);
    }

    public void checkToken(String token) {
        if (isTokenExpired(token))
            throw new TokenLifetimeExpiredException("Token expired");
        if (!isTokenContains(token)) {
            throw new UnknownTokenException("Unknown token");
        }
    }
}
