package com.techcorp.securedocs.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // clave fija de al menos 32 caracteres (256 bits) para HS256
    // en producción esto debe venir de una variable de entorno, no hardcodeado
    private static final String SECRET = "securedocs-clave-secreta-para-tecsup-2026-cambiar-en-produccion";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private final long EXPIRATION_MS = 3600000;

    public String generarToken(String correo, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    public Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extraerCorreo(String token) {
        return extraerClaims(token).getSubject();
    }

    public boolean esTokenValido(String token) {
        try {
            extraerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
