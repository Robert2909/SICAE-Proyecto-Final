package com.sicae.parkingservice.controller;

import com.sicae.parkingservice.dto.ParkingRequestDTO;
import com.sicae.parkingservice.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController indica que este archivo es un controlador REST
@RestController
// @RequestMapping indica que todas las rutas empezaran con /parking
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    // Endpoint de prueba 
    @GetMapping("/prueba")
    public String probarParkingService() {
        return "probando el ParkingService 8084";
    }

    // Primer endpoint: Registrar la entrada de un vehículo al estacionamiento
    // Usamos el DTO en el RequestBody para recibir idUsuario y placa
    @PostMapping("/entrada")
    public ResponseEntity<?> registrarEntrada(@RequestHeader("Authorization") final String authHeader, @RequestBody ParkingRequestDTO solicitud) {
        try {
            // Primero revisamos si nos mandaron el token en la cabecera (La seguridad es primero)
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización o el formato es incorrecto");
            }

            // Le quitamos la palabra "Bearer " con el substring(7) para dejar el token limpio (validación)
            String tokenPuro = authHeader.substring(7);

            // Llamamos a ParkingService para la lógica de negocio y la integración
            String resultado = parkingService.registrarEntrada(solicitud.getIdUsuario(), solicitud.getPlaca(), authHeader);

            // En nuestro servicio controlamos los errores regresando un String que empieza con "Error:"
            if (resultado.startsWith("Error:")) {
                return ResponseEntity.badRequest().body(resultado);
            }

            // Si todo sale bien, regresamos el mensaje de éxito
            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            // Por si truena la conexión o pasa algo inesperado
            return ResponseEntity.badRequest().body("No pudo completarse: " + e.getMessage());
        }
    }

    // Segundo endpoint: Registrar la salida y cobrar
    // Como solo necesitamos la placa, la pedimos directamente en la URL con @PathVariable
    @PostMapping("/salida/{placa}")
    public ResponseEntity<?> registrarSalida(@RequestHeader("Authorization") final String authHeader, @PathVariable String placa) {
        try {
            // Verificamos el token (aunque el proceso es local, mantenemos la capa de seguridad)
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización o el formato es incorrecto");
            }

            // Llamamos al servicio para sacar al vehículo y calcular cuánto debe
            String resultado = parkingService.registrarSalida(placa);

            if (resultado.startsWith("Error:")) {
                return ResponseEntity.badRequest().body(resultado);
            }

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No pudo completarse: " + e.getMessage());
        }
    }
}
