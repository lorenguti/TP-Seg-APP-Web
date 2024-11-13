package com.example.demo.utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtilGen {


    private Key generateSecretKey(String secret) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(secret.getBytes());
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    // Genera un token JWT utilizando la clave secreta generada con expiración de 5 días
    public String generateToken(String id, String email) {
        try {
            Map<String, Object> customHeader = Map.of(
                    "alg", "HS256",
                    "typ", "JWT"
            );

            Key kc = generateSecretKey("GEN_TP");

            // Establecer la fecha de expiración (5 días desde ahora)
            Date expirationDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(5));

            // Incluye ambos valores en los claims
            return Jwts.builder()
                    .setHeader(customHeader)
                    .setClaims(Map.of("id_contract", id, "email", email))
                    .setExpiration(expirationDate)  // Configura la expiración del token
                    .signWith(kc)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Error generando el token", e);
        }
    }

    // Valida el token y verifica su expiración
    public Boolean validateTokenGen(String token) {
        try {
            Key secretKey = generateSecretKey("GEN_TP");

            var claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String id_contract = claims.get("id_contract", String.class);
            String email = claims.get("email", String.class);
            Date expiration = claims.getExpiration();

            // Verifica si el token tiene el campo de expiración y si aún es válido
            return id_contract != null && email != null && expiration != null && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            Key secretKey = generateSecretKey("GEN_TP");

            var claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("email", String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo el email del token", e);
        }
    }

    public String getIdContractFromToken(String token) {
        try {
            Key secretKey = generateSecretKey("GEN_TP");

            var claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("id_contract", String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo el email del token", e);
        }
    }


}
