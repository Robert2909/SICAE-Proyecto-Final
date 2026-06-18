package com.sicae.authservice.security;

import com.sicae.authservice.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // En esta parte se crea la llave secreta con una frase que debe ser muy larga y compleja para que sea segura
    private static final String SECRET_KEY_STRING = "LlaveSuperSecretaParangaricutirimicuaro";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // En esta parte se define el tiempo que durará el token, le puse 2 horas
    // Dice ese numero porque 7200000 son los milisegundos, y eso equivale a 2 horas
    private static final long EXPIRATION_TIME = 7200000; 

    // En esta parte se genera el token con el usuario
    // Jwts.builder() es un método que crea un token
    public String generateToken(Usuario usuario) {
        
        // Se construye el token con el usuario y el tiempo de expiración
        return Jwts.builder()
                .setSubject(usuario.getUsername())                 // Aqui se inserta el username del usuario
                .claim("idUsuario", usuario.getIdUsuario())        // Aqui va el id del usuario
                .claim("idRol", usuario.getIdRol())                // Aqui va el id del rol
                .setIssuedAt(new Date())                           // Aqui se inserta la fecha y hora en que se genera el token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Aqui se inserta la fecha y hora en que caduca el token
                .signWith(key, SignatureAlgorithm.HS256)           // Aqui se firma el token con la llave secreta
                .compact();                                        // Aqui se convierte a JWT en un texto largo de JSON
    }
}
