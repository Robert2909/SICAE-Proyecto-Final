package com.sicae.parkingservice.service;

import com.sicae.parkingservice.dto.ParkingResponseDTO;
import com.sicae.parkingservice.repository.Configuracion;
import com.sicae.parkingservice.repository.ParkingRepository;
import com.sicae.parkingservice.repository.Ticket;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    // Método principal para registrar la entrada de un vehículo
    // Recibe el token JWT para poder pasárselo a UserService y VehicleService
    public ParkingResponseDTO registrarEntrada(Integer idUsuario, String placa, String token) {
        
        // 1. Validar disponibilidad de cajones en el estacionamiento
        Integer idEspacio = parkingRepository.obtenerEspacioDisponible();
        
        if (idEspacio == null) {
            throw new RuntimeException("Estacionamiento lleno. No hay cajones disponibles.");
        }

        // 2. Validar regla de negocio: Máximo dos vehículos adentro por usuario
        Integer vehiculosAdentro = parkingRepository.contarVehiculosAdentroPorUsuario(idUsuario);
        if (vehiculosAdentro >= 2) {
            throw new RuntimeException("El usuario ya tiene el máximo permitido de 2 vehículos adentro.");
        }

        // 3. Validar que la placa no esté ya registrada como "Adentro" (evitar doble entrada)
        Ticket ticketExistente = parkingRepository.buscarTicketAbiertoPorPlaca(placa);
        if (ticketExistente != null) {
            throw new RuntimeException("El vehículo con placa " + placa + " ya se encuentra adentro del estacionamiento.");
        }

        // 4. Integración con otros Microservicios usando RestTemplate
        // Instanciamos RestTemplate para hacer las peticiones HTTP
        RestTemplate restTemplate = new RestTemplate();
        
        // Preparamos los Headers inyectando el Token JWT que recibimos del cliente (Postman)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // A. Consultar al UserService (Puerto 8082)
        try {
            // Suponemos que hay un endpoint para ver el perfil o validar usuario
            String urlUsuario = "http://host.docker.internal:8082/usuarios/verPerfil/" + idUsuario;
            // Si el usuario no existe, está inactivo o el token es inválido, esto lanzará una excepción
            restTemplate.exchange(urlUsuario, HttpMethod.GET, entity, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error de Integración: El usuario no existe, está inactivo o el token expiró.");
        }

        // B. Consultar al VehicleService (Puerto 8083)
        try {
            // Usamos el endpoint exacto que dejó nuestro compañero en la documentación de la Fase 3
            String urlVehiculo = "http://host.docker.internal:8083/vehiculos/validar?idUsuario=" + idUsuario + "&placa=" + placa;
            restTemplate.exchange(urlVehiculo, HttpMethod.GET, entity, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error de Integración: El vehículo no existe, no le pertenece al usuario o está inactivo.");
        }

        // 5. Si pasamos todas las validaciones (locales y externas), insertamos el ticket
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

    // Método principal para registrar la salida y calcular el cobro
    public ParkingResponseDTO registrarSalida(String placa) {
        
        // 1. Buscamos el ticket que siga abierto (estatus = 1) para esta placa
        Ticket ticket = parkingRepository.buscarTicketAbiertoPorPlaca(placa);
        if (ticket == null) {
            throw new RuntimeException("No se encontró ningún vehículo adentro con la placa " + placa);
        }

        // 2. Calculamos el tiempo total que estuvo adentro usando la clase Duration de Java
        LocalDateTime horaSalida = LocalDateTime.now();
        Duration duracion = Duration.between(ticket.getHoraEntrada(), horaSalida);
        long minutosTotales = duracion.toMinutes();

        // 3. Matemáticas de cobro trayendo la configuración de la base de datos
        Configuracion config = parkingRepository.obtenerConfiguracion();
        long horasCobradas = minutosTotales / 60; // División entera para sacar las horas
        long minutosCobrados = minutosTotales % 60; // El módulo nos da los minutos sobrantes

        double costoTotal = (horasCobradas * config.getCostoHora()) + (minutosCobrados * config.getCostoMinuto());

        // 4. Actualizamos el ticket con los nuevos datos (el repository lo marca como estatus = 0)
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

    public List<Integer> consultarEspacios() {
        return parkingRepository.consultarEspaciosDisponibles();
    }
}
