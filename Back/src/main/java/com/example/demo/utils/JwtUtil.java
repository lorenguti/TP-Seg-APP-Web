package com.example.demo.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Método para obtener una clave secreta de 256 bits usando SHA-256
    private Key generateSecretKey(String secret) throws Exception {
        // Usamos SHA-256 para generar una clave de 256 bits a partir de la clave secreta "TPSEGWEB"
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(secret.getBytes());
        return new javax.crypto.spec.SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    // Genera un token JWT utilizando la clave secreta generada
    public String generateToken(String email) {
        try {
            // Generar la clave secreta con "TPSEGWEB"
            Key secretKey = generateSecretKey("TPSEGWEB");
            Map<String, Object> customHeader = Map.of(
                    "alg", "HS256", // Algoritmo de firma
                    "typ", "JWT"   // Tipo de token
            );

            // Generar el JWT con campos personalizados y header predeterminado (HS256, JWT)
            return Jwts.builder()
                    .claim("apikey", "TPSEGWEB")  // Aquí se añade el campo "apikey"
                    .claim("mail", email)
                    .setHeader(customHeader)  // Establecer el header personalizado
                    .signWith(secretKey)          // Firmar con la clave secreta
                    .compact();                   // Generar el token
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
