package com.sicae.parkingservice.controller;

import com.sicae.parkingservice.dto.ParkingRequestDTO;
import com.sicae.parkingservice.dto.ParkingResponseDTO;
import com.sicae.parkingservice.service.ParkingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

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
            ParkingResponseDTO resultado = parkingService.registrarEntrada(solicitud.getIdUsuario(), solicitud.getPlaca(), tokenPuro);

            // Si todo sale bien, regresamos el mensaje de éxito
            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            // Por si truena la conexión o pasa algo inesperado
            return ResponseEntity.badRequest().body("No pudo completarse: " + e.getMessage());
        }
    }

    // Segundo endpoint se registra la salida de un vehiculo y el costo total
    @PostMapping("/salida/{placa}")
    public ResponseEntity<?> registrarSalida(@RequestHeader("Authorization") final String authHeader, @PathVariable String placa) {
        try {
            // después verificamos el token de forma local pero segura
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización o el formato es incorrecto");
            }

            String tokenPuro = authHeader.substring(7);

            // mandamos a llamar el servicio para registrar la salida y cuanto debe
            ParkingResponseDTO resultado = parkingService.registrarSalida(placa, tokenPuro);

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No pudo completarse: " + e.getMessage());
        }
    }

    // tercer endpont de lugares disponibles
    @GetMapping("/espacios")
    public ResponseEntity<?> consultarEspacios(@RequestHeader("Authorization") final String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización o el formato es incorrecto");
            }
            
            String tokenPuro = authHeader.substring(7);
            List<Integer> espaciosDisponibles = parkingService.consultarEspacios(tokenPuro);
            
            return ResponseEntity.ok(espaciosDisponibles);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No pudo completarse: " + e.getMessage());
        }
    }
}
