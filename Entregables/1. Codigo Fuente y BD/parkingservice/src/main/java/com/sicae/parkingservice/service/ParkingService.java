package com.sicae.parkingservice.service;

import com.sicae.parkingservice.dto.ParkingResponseDTO;
import com.sicae.parkingservice.repository.Configuracion;
import com.sicae.parkingservice.repository.ParkingRepository;
import com.sicae.parkingservice.repository.Ticket;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ParkingService {

    // la llave súper secreta del equipo para validar tokens, nadie la puede saber
    private static final String SECRET_KEY_STRING = "LlaveSuperSecretaParangaricutirimicuaro";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    @Autowired
    private ParkingRepository parkingRepository;

    // verificación del token
    public boolean validarToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // registrro de entrada al estacionamiento de un vehiculo
    public ParkingResponseDTO registrarEntrada(Integer idUsuario, String placa, String tokenPuro) {
        
        if (!validarToken(tokenPuro)) {
            throw new RuntimeException("El token es inválido o ya expiró.");
        }

        // primero validamos la disponibilidad de los cajones
        Integer idEspacio = parkingRepository.obtenerEspacioDisponible();
        
        if (idEspacio == null) {
            throw new RuntimeException("Estacionamiento lleno. No hay cajones disponibles.");
        }

        // después validamos la regla del negocio, osea máximo dos vehiculos dentro por usuario
        Integer vehiculosAdentro = parkingRepository.contarVehiculosAdentroPorUsuario(idUsuario);
        if (vehiculosAdentro >= 2) {
            throw new RuntimeException("El usuario ya tiene el máximo permitido de 2 vehículos adentro.");
        }

        // después validamos que la placa no esté registrada como "adentro"
        Ticket ticketExistente = parkingRepository.buscarTicketAbiertoPorPlaca(placa);
        if (ticketExistente != null) {
            throw new RuntimeException("El vehículo con placa " + placa + " ya se encuentra adentro del estacionamiento.");
        }

        // después integramos otros microservicios, estos usando RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        
        // preparamos las cabeceras o Headers.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenPuro);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // consultar al UserService para verificar si el usuario existe.
        try {
            String urlUsuario = "http://host.docker.internal:8082/usuarios/verPerfil/" + idUsuario;
            restTemplate.exchange(urlUsuario, HttpMethod.GET, entity, Map.class); //excepción si el usuario no existe.
        } catch (Exception e) {
            throw new RuntimeException("Error de Integración: El usuario no existe, está inactivo o el token expiró.");
        }

        // después consulta el servicio vehicleservice para validad si existe la placa en el puerto 8083.
        try {
            String urlVehiculo = "http://host.docker.internal:8083/vehiculos/validar?idUsuario=" + idUsuario + "&placa=" + placa;
            restTemplate.exchange(urlVehiculo, HttpMethod.GET, entity, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error de Integración: El vehículo no existe, no le pertenece al usuario o está inactivo.");
        }

        // si pasa todas las validaciones, procedo a insertar el itcket
        Configuracion config = parkingRepository.obtenerConfiguracion();
        Ticket nuevoTicket = new Ticket();
        nuevoTicket.setIdUsuario(idUsuario);
        nuevoTicket.setPlaca(placa);
        nuevoTicket.setHoraEntrada(LocalDateTime.now());
        nuevoTicket.setIdEspacio(idEspacio);
        nuevoTicket.setTarifaHora(config.getCostoHora());
        
        parkingRepository.registrarEntrada(nuevoTicket);
        parkingRepository.ocuparEspacio(idEspacio);
        
        ParkingResponseDTO response = new ParkingResponseDTO();
        response.setIdMovimiento(nuevoTicket.getIdTicket());
        response.setTiempoEntrada(nuevoTicket.getHoraEntrada());
        response.setEspacioAsignado(nuevoTicket.getIdEspacio());
        response.setTarifaHora(nuevoTicket.getTarifaHora());

        return response;
    }

    // método principal para registrar salida
    public ParkingResponseDTO registrarSalida(String placa, String tokenPuro) {
        
        if (!validarToken(tokenPuro)) {
            throw new RuntimeException("El token es inválido o ya expiró.");
        }

        // primero buscamos que el ticket esté abierto para esta placa
        Ticket ticket = parkingRepository.buscarTicketAbiertoPorPlaca(placa);
        if (ticket == null) { 
            throw new RuntimeException("No se encontró ningún vehículo adentro con la placa " + placa);
        }

        // después se calcula el tiempo total que estuvo adentro
        LocalDateTime horaSalida = LocalDateTime.now();
        Duration duracion = Duration.between(ticket.getHoraEntrada(), horaSalida);
        long minutosTotales = duracion.toMinutes();

        // luego se calcula el tiempo para el costo
        Configuracion config = parkingRepository.obtenerConfiguracion();
        long horasCobradas = minutosTotales / 60;
        long minutosCobrados = minutosTotales % 60;

        double costoTotal = (horasCobradas * config.getCostoHora()) + (minutosCobrados * config.getCostoMinuto());

        // se actualiza el ticket con los nuevos datos obtenidos
        ticket.setHoraSalida(horaSalida);
        ticket.setTiempoTotalMinutos((int) minutosTotales);
        ticket.setCostoTotal(costoTotal);
        ticket.setHorasCobradas((int) horasCobradas);

        parkingRepository.registrarSalida(ticket);
        parkingRepository.liberarEspacio(ticket.getIdEspacio());

        ParkingResponseDTO response = new ParkingResponseDTO();
        response.setIdMovimiento(ticket.getIdTicket());
        response.setTiempoEntrada(ticket.getHoraEntrada());
        response.setTiempoSalida(ticket.getHoraSalida());
        response.setEspacioAsignado(ticket.getIdEspacio());
        response.setTarifaHora(ticket.getTarifaHora());
        response.setCostoTotal(ticket.getCostoTotal());
        response.setHorasCobradas(ticket.getHorasCobradas());

        return response;
    }

    public List<Integer> consultarEspacios(String tokenPuro) {
        if (!validarToken(tokenPuro)) { // validamos el token antes de consultar los espacios disponibles
            throw new RuntimeException("El token es inválido o ya expiró.");
        }
        return parkingRepository.consultarEspaciosDisponibles();
    }
}
