package com.sicae.vehicleservice.controller;

import com.sicae.vehicleservice.dto.VehiculoRequestDTO;
import com.sicae.vehicleservice.dto.VehiculoResponseDTO;
import com.sicae.vehicleservice.service.VehiculoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController indica que este archivo es un controlador REST
@RestController
// @RequestMapping indica que todas las rutas empezaran con /vehiculos
@RequestMapping("/vehiculos")

public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;
    
    @GetMapping("/buscarPorUsuario/{idUsuario}")
    public ResponseEntity<?> buscarVehiculosPorUsuario(@RequestHeader("Authorization") final String authHeader, @PathVariable Integer idUsuario) {

    try {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización");
        }

        String token = authHeader.substring(7);

        List<VehiculoResponseDTO> vehiculos = vehiculoService.buscarVehiculosPorUsuario(token, idUsuario);

        return ResponseEntity.ok(vehiculos);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("No se pudo realizar: " + e.getMessage());
    }
}
    
    @PostMapping("/agregar")
    public ResponseEntity<?> registrarVehiculo(@RequestHeader("Authorization") final String authHeader, @RequestBody VehiculoRequestDTO nuevoVehiculo) {

    try {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización.");
        }

        String token = authHeader.substring(7);

        String mensajeExito = vehiculoService.registrarVehiculo(token, nuevoVehiculo);

        return ResponseEntity.ok(mensajeExito);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("No se pudo realizar: " + e.getMessage());
    }
}
    
    @PutMapping("/editar/{idVehiculo}")
    public ResponseEntity<?> editarVehiculo(@RequestHeader("Authorization") final String authHeader, @PathVariable Integer idVehiculo, @RequestBody VehiculoRequestDTO vehiculoEditado) {

    try {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización");
        }

        String token = authHeader.substring(7);

        String mensajeExito = vehiculoService.editarVehiculo(token, idVehiculo, vehiculoEditado);

        return ResponseEntity.ok(mensajeExito);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("No se pudo realizar: " + e.getMessage());
    }
}
    
    @PatchMapping("/actualizarEstatus/{idVehiculo}/{idUsuario}")
    public ResponseEntity<?> cambiarEstatus(@RequestHeader("Authorization") final String authHeader, @PathVariable Integer idVehiculo, @PathVariable Integer idUsuario) {

    try {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización");
        }

        String token = authHeader.substring(7);

        String mensajeExito = vehiculoService.cambiarEstatus(token, idVehiculo, idUsuario);

        return ResponseEntity.ok(mensajeExito);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("No se pudo realizar: " + e.getMessage());
    }
}
    
    @GetMapping("/validar")
    public ResponseEntity<?> validarVehiculoParaParking(@RequestHeader("Authorization") final String authHeader, @RequestParam Integer idUsuario, @RequestParam String placa) {

    try {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización");
        }

        String token = authHeader.substring(7);

        VehiculoResponseDTO vehiculo = vehiculoService.validarVehiculoParaParking(token, idUsuario, placa);

        return ResponseEntity.ok(vehiculo);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("No se pudo realizar: " + e.getMessage());
    }
}

}
   
