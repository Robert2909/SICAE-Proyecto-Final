package com.sicae.authservice.controller;

import com.sicae.authservice.dto.LoginRequestDTO;
import com.sicae.authservice.dto.LoginResponseDTO;
import com.sicae.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController indica que este archivo es un controlador REST
@RestController
// @RequestMapping indica que todas las rutas empezaran con /auth
@RequestMapping("/auth")
// Es que se me olvidan jeje
public class AuthController {

    // Mandamos a llamar al servicio de autenticación
    private final AuthService authService;

    // Constructor para inyectar el servicio
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // @PostMapping indica que este método responderá a una petición POST
    // @RequestBody indica que el parámetro se tomará del cuerpo de la petición
    // Aqui utilizamos RequestBody y no el @PathVariable porque estamos esperando datos en formato JSON en el cuerpo de la petición
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            // Aqui le pasamos el username y el password al servicio y nos devuelve una respuesta
            LoginResponseDTO respuesta = authService.login(request.getUsername(), request.getPassword());
            
            // Si todo sale bien devolvemos la respuesta con un status 200 OK
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            // Si hay un error, devolvemos el mensaje de error con un status 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
