package com.api.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "segredo_super_secreto"; // Você pode pegar do application.properties se preferir

    // Método para extrair o nome de usuário do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Método para extrair o papel (tipo_acesso) do token
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class)); // Assuming role is stored in 'role' claim
    }

    // Método para extrair a data de expiração do token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Método genérico para extrair qualquer dado do token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Método para extrair todos os dados (claims) do token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para verificar se o token já expirou
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Método para gerar o token JWT
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_" + role);  // Aqui você adiciona o tipo de acesso/role ao token
        claims.put("email", username);  // Adicionando o e-mail ao token para identificação

        return createToken(claims, username);
    }


    // Método privado para criar o token JWT com claims e dados de expiração
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    // Método para validar o token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        final String extractedRole = extractRole(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
