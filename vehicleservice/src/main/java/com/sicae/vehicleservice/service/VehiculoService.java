package com.sicae.vehicleservice.service;

import com.sicae.vehicleservice.repository.VehiculoRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import com.sicae.vehicleservice.dto.VehiculoResponseDTO;
import com.sicae.vehicleservice.repository.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import com.sicae.vehicleservice.dto.VehiculoRequestDTO;

@Service
public class VehiculoService {
    //esta es la llave que nos dio el integrante 1 de nuestro equipo, la tenemos aqui para verificar los tokens que llegan.
    private static final String SECRET_KEY_STRING = "LlaveSuperSecretaParangaricutirimicuaro";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    
    // En esta parte se hace uso del repository para poder hacer la consulta a la base de datos
    private final VehiculoRepository vehiculoRepository;
    
    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }
    
    //metodo de prueba chicos, para probar que si esta funcionando en el 83
    public String prueba() {
        return "probando el VehicleService 8083";
    }
    
    

    //Estos lo saque de youtube Robert, primero se decifra el token con el parser, de ahi se hace la lectura del token y le ponemos el trycatch para que si todo esta bien da true, pero si el token ya expiro, esta mal en su estructura o la firma con la que se hizo no es la misma, lo vote
    public boolean validarToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //en este metodo buscamos vehiculos por usuario recibiendo el id del usuario y el token
    public List<VehiculoResponseDTO> buscarVehiculosPorUsuario(String token, Integer idUsuario) {
    
    //vemos que nos enviaron un id de usuario valido
    if (idUsuario == null) {
        throw new RuntimeException("Falta idUsuario.");
    }
    
    //aqui validamos que el token sea valido
    boolean tokenValido = validarToken(token);
    if (tokenValido == false) {
        throw new RuntimeException("El token es inválido.");
    }
    
   
    List<Vehiculo> vehiculosEncontrados = vehiculoRepository.buscarVehiculosPorUsuario(idUsuario);

    // Creamos una lista de respuesta
    
    List<VehiculoResponseDTO> respuesta = new ArrayList<>();
    
    // Aquí vamos a guardar los vehículos ya acomodadoss
    for (Vehiculo vehiculoEncontrado : vehiculosEncontrados) {
        VehiculoResponseDTO vehiculoRespuesta = new VehiculoResponseDTO();

        vehiculoRespuesta.setIdVehiculo(vehiculoEncontrado.getIdVehiculo());
        vehiculoRespuesta.setIdUsuario(vehiculoEncontrado.getIdUsuario());
        vehiculoRespuesta.setClaveVehiculo(vehiculoEncontrado.getClaveVehiculo());
        vehiculoRespuesta.setIdMarca(vehiculoEncontrado.getIdMarca());
        vehiculoRespuesta.setMarca(vehiculoEncontrado.getMarca());
        vehiculoRespuesta.setIdModelo(vehiculoEncontrado.getIdModelo());
        vehiculoRespuesta.setModelo(vehiculoEncontrado.getModelo());
        vehiculoRespuesta.setPlaca(vehiculoEncontrado.getPlaca());
        vehiculoRespuesta.setColor(vehiculoEncontrado.getColor());
        vehiculoRespuesta.setAnio(vehiculoEncontrado.getAnio());
        vehiculoRespuesta.setEstatus(vehiculoEncontrado.getEstatus());
        vehiculoRespuesta.setDescripcion(vehiculoEncontrado.getDescripcion());

        respuesta.add(vehiculoRespuesta);
    }

    return respuesta;
}
    
    public String registrarVehiculo(String token, VehiculoRequestDTO solicitud) {

    // Aquí vamos a hacer todas las validaciones antes de meter algo en la base
    boolean tokenValido = validarToken(token);
    if (tokenValido == false) {
        throw new RuntimeException("El token es inválido.");
    }

    if (solicitud.getIdUsuario() == null) {
        throw new RuntimeException("Falta el idUsuario.");
    }

    if (solicitud.getIdModelo() == null) {
        throw new RuntimeException("Falta el idModelo.");
    }

    if (solicitud.getPlaca() == null || solicitud.getPlaca().isEmpty()) {
        throw new RuntimeException("Falta la placa del vehículo.");
    }

    if (solicitud.getColor() == null || solicitud.getColor().isEmpty()) {
        throw new RuntimeException("Falta el color del vehículo.");
    }

    if (solicitud.getAnio() == null) {
        throw new RuntimeException("Falta el año del vehículo.");
    }

    if (solicitud.getPlaca().length() > 7) {
        throw new RuntimeException("La placa no puede tener más de 7 caracteres.");
    }

    if (solicitud.getColor().length() > 20) {
        throw new RuntimeException("El color no puede tener más de 20 caracteres.");
    }

    if (solicitud.getDescripcion() != null && solicitud.getDescripcion().length() > 255) {
        throw new RuntimeException("La descripción no puede tener más de 255 caracteres.");
    }

    if (vehiculoRepository.existeModeloActivo(solicitud.getIdModelo()) == 0) {
        throw new RuntimeException("El modelo no existe o esta inactivo.");
    }

    Vehiculo vehiculoConMismaPlaca = vehiculoRepository.buscarPorPlaca(solicitud.getPlaca());
    if (vehiculoConMismaPlaca != null) {
        throw new RuntimeException("La placa ya esta registrada.");
    }

    Integer vehiculosActivos = vehiculoRepository.contarVehiculosActivosPorUsuario(solicitud.getIdUsuario());
    if (vehiculosActivos >= 4) {
        throw new RuntimeException("El usuario ya tiene cuatro vehículos activos.");
    }
    // Creamos el objeto Vehiculo que ahora sí se va a guardar en la base
    Vehiculo nuevoVehiculo = new Vehiculo();

    nuevoVehiculo.setIdUsuario(solicitud.getIdUsuario());
    nuevoVehiculo.setIdModelo(solicitud.getIdModelo());
    nuevoVehiculo.setPlaca(solicitud.getPlaca());
    nuevoVehiculo.setColor(solicitud.getColor());
    nuevoVehiculo.setAnio(solicitud.getAnio());
    nuevoVehiculo.setDescripcion(solicitud.getDescripcion());

    Vehiculo ultimoVehiculo = vehiculoRepository.ultimoVehiculo();
    
    //generamos la clave final aqui
    String claveFinal;

    if (ultimoVehiculo == null) {
        claveFinal = "V-1";
    } else {
        Integer siguienteNumero = ultimoVehiculo.getIdVehiculo() + 1;
        claveFinal = "V-" + siguienteNumero;
    }
    //agregamos la clave 
    nuevoVehiculo.setClaveVehiculo(claveFinal);

    vehiculoRepository.registrarVehiculo(nuevoVehiculo);

    return "El vehiculo se registro exitosamente";
}
    
    public String editarVehiculo(String token, Integer idVehiculo, VehiculoRequestDTO solicitud) {
    
    //hacemos todas las validaciones como las q hicimos en el metodo pasado
    if (idVehiculo == null) {
        throw new RuntimeException("Falta el idVehiculo.");
    }

    boolean tokenValido = validarToken(token);
    if (tokenValido == false) {
        throw new RuntimeException("El token es inválido");
    }

    if (solicitud.getIdUsuario() == null) {
        throw new RuntimeException("Falta el idUsuario.");
    }

    if (solicitud.getIdModelo() == null) {
        throw new RuntimeException("Falta el idModelo.");
    }

    if (solicitud.getPlaca() == null || solicitud.getPlaca().isEmpty()) {
        throw new RuntimeException("Falta la placa del vehículo.");
    }

    if (solicitud.getColor() == null || solicitud.getColor().isEmpty()) {
        throw new RuntimeException("Falta el color del vehículo.");
    }

    if (solicitud.getAnio() == null) {
        throw new RuntimeException("Falta el año del vehículo.");
    }

    if (solicitud.getPlaca().length() > 7) {
        throw new RuntimeException("La placa no puede tener más de 7 caracteres.");
    }

    if (solicitud.getColor().length() > 20) {
        throw new RuntimeException("El color no puede tener más de 20 caracteres.");
    }

    if (solicitud.getDescripcion() != null && solicitud.getDescripcion().length() > 255) {
        throw new RuntimeException("La descripción no puede tener más de 255 caracteres.");
    }

    Vehiculo vehiculoEncontrado = vehiculoRepository.buscarPorId(idVehiculo);
    //confirmamos que exista el vehiculo
    if (vehiculoEncontrado == null) {
        throw new RuntimeException("El vehículo no existe.");
    }
    //aqui vemos si el vehiculo si esta afiliado al usuario o no
    if (!vehiculoEncontrado.getIdUsuario().equals(solicitud.getIdUsuario())) {
        throw new RuntimeException("El vehículo no pertenece al usuario indicado.");
    }
    //vemos si existe el modelo
    if (vehiculoRepository.existeModeloActivo(solicitud.getIdModelo()) == 0) {
        throw new RuntimeException("El modelo no existe o esta inactivo.");
    }
    // aqui tenemos que comprobar que la placa nueva q queremos poner no este ya usada por otro
    Vehiculo placaEnOtroVehiculo = vehiculoRepository.buscarPlacaEnOtroVehiculo(solicitud.getPlaca(), idVehiculo);

    if (placaEnOtroVehiculo != null) {
        throw new RuntimeException("La placa ya está registrada en otro vehículo.");
    }

    Vehiculo vehiculoEditado = new Vehiculo();

    vehiculoEditado.setIdVehiculo(idVehiculo);
    vehiculoEditado.setIdModelo(solicitud.getIdModelo());
    vehiculoEditado.setPlaca(solicitud.getPlaca());
    vehiculoEditado.setColor(solicitud.getColor());
    vehiculoEditado.setAnio(solicitud.getAnio());
    vehiculoEditado.setDescripcion(solicitud.getDescripcion());

    vehiculoRepository.editarVehiculo(vehiculoEditado);

    return "El vehiculo se actualizo correctamente";
}
    
    public String cambiarEstatus(String token, Integer idVehiculo, Integer idUsuario) {
    //validaciones de que el vehiculo y todo esta en orden
    if (idVehiculo == null) {
        throw new RuntimeException("Falta el idVehiculo.");
    }

    if (idUsuario == null) {
        throw new RuntimeException("Falta el idUsuario.");
    }

    boolean tokenValido = validarToken(token);
    if (tokenValido == false) {
        throw new RuntimeException("El token es inválido");
    }

    Vehiculo vehiculoEncontrado = vehiculoRepository.buscarPorId(idVehiculo);

    if (vehiculoEncontrado == null) {
        throw new RuntimeException("El vehículo no existe.");
    }

    if (!vehiculoEncontrado.getIdUsuario().equals(idUsuario)) {
        throw new RuntimeException("El vehículo no pertenece al usuario.");
    }
    //llamamos para hacer el cambio de estatus mandando el id del vehiculo
    vehiculoRepository.cambiarEstatus(idVehiculo);

    return "estatus del vehiculo cambiado con exito!!";
}
    
    public VehiculoResponseDTO validarVehiculoParaParking(String token, Integer idUsuario, String placa) {
    //validaciones de rutina como en los demas
    if (idUsuario == null) {
        throw new RuntimeException("Falta el idUsuario.");
    }

    if (placa == null || placa.isEmpty()) {
        throw new RuntimeException("Falta la placa del vehículo.");
    }

    boolean tokenValido = validarToken(token);
    if (tokenValido == false) {
        throw new RuntimeException("El token es inválido");
    }
    //hacemos la llamada para validar
    Vehiculo vehiculoEncontrado = vehiculoRepository.validarVehiculoPorUsuarioYPlaca(idUsuario, placa);

    if (vehiculoEncontrado == null) {
        throw new RuntimeException("El vehículo no existe, no esta asignado al usuario o esta inactivo.");
    }
    
    VehiculoResponseDTO respuesta = new VehiculoResponseDTO();

    respuesta.setIdVehiculo(vehiculoEncontrado.getIdVehiculo());
    respuesta.setIdUsuario(vehiculoEncontrado.getIdUsuario());
    respuesta.setClaveVehiculo(vehiculoEncontrado.getClaveVehiculo());
    respuesta.setIdMarca(vehiculoEncontrado.getIdMarca());
    respuesta.setMarca(vehiculoEncontrado.getMarca());
    respuesta.setIdModelo(vehiculoEncontrado.getIdModelo());
    respuesta.setModelo(vehiculoEncontrado.getModelo());
    respuesta.setPlaca(vehiculoEncontrado.getPlaca());
    respuesta.setColor(vehiculoEncontrado.getColor());
    respuesta.setAnio(vehiculoEncontrado.getAnio());
    respuesta.setEstatus(vehiculoEncontrado.getEstatus());
    respuesta.setDescripcion(vehiculoEncontrado.getDescripcion());

    return respuesta;
}
}
    
    
    
    
    
    