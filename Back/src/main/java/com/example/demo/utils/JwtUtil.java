package com.example.demo.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private Key generateSecretKey(String secret) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(secret.getBytes());
        return new javax.crypto.spec.SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    // Genera un token JWT utilizando la clave secreta generada
    public String generateToken(String email) {
        try {
            Map<String, Object> customHeader = Map.of(
                    "alg", "HS256",
                    "typ", "JWT"
            );

            Key kc = generateSecretKey("TPSEGWEB");


            //////Key secretKey = new javax.crypto.spec.SecretKeySpec("TPSEGWEB".getBytes(), SignatureAlgorithm.HS256.getJcaName());
            return Jwts.builder()
                    .setHeader(customHeader)           // Establecer el header personalizado
                    .setClaims(Map.of("mail", "ejemplo@gmail.com"))  // Establecer el payload con un Map
                    .signWith(kc)               // Firmar con la clave secreta
                    .compact();                // Generar el token
        } catch (Exception e) {
            throw new RuntimeException("Error generando el token", e);
        }
    }

    // Valida el token
    public String validateToken(String token) {
        try {
            // Generar la clave secreta para la validación
            Key secretKey = generateSecretKey("TPSEGWEB");

            // Decodificar y obtener las claims (campos) del JWT
            var claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Obtener los campos personalizados del token
            String apikey = claims.get("apikey", String.class);
            String mail = claims.get("mail", String.class);

            // Imprimir o devolver los valores
            System.out.println("apikey: " + apikey);
            System.out.println("mail: " + mail);

            return mail; // Devuelvo el mail como ejemplo
        } catch (Exception e) {
            return null; // Token inválido o expirado
        }
    }
}
