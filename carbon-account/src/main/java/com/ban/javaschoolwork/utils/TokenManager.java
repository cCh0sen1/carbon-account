package com.ban.carbonaccount.utils;

import com.ban.carbonaccount.enums.ResultEnum;
import com.ban.carbonaccount.exception.BizException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenManager {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire-time:86400000}")
    private long expireTime;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            byte[] padded = new byte[32];
            System.arraycopy(keyBytes, 0, padded, 0, Math.min(keyBytes.length, 32));
            keyBytes = padded;
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Long userId) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expireTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long parseUserId(String token) {
        if (token == null || token.isBlank()) {
            throw new BizException(ResultEnum.TOKEN_ERROR);
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token.trim())
                    .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (ExpiredJwtException e) {
            throw new BizException(ResultEnum.TOKEN_ERROR.getCode(), "Token已过期");
        } catch (JwtException | NumberFormatException e) {
            throw new BizException(ResultEnum.TOKEN_ERROR);
        }
    }
}