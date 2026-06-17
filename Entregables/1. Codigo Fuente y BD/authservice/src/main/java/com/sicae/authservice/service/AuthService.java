package com.sicae.authservice.service;

import com.sicae.authservice.dto.LoginResponseDTO;
import com.sicae.authservice.mapper.UsuarioMapper;
import com.sicae.authservice.model.Usuario;
import com.sicae.authservice.security.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // En esta parte se hace uso del mapper para poder hacer la consulta a la base de datos
    private final UsuarioMapper usuarioMapper;
    // Agregamos nuestra máquina de hacer tokens
    private final JwtUtil jwtUtil; 

    // En el constructor se reciben el mapper y el jwtUtil y se asignan a las variables
    public AuthService(UsuarioMapper usuarioMapper, JwtUtil jwtUtil) {
        this.usuarioMapper = usuarioMapper;
        this.jwtUtil = jwtUtil;
    }

    // Cambiamos el tipo de retorno para que devuelva el DTO LoginResponseDTO
    public LoginResponseDTO login(String username, String passwordPlana) {
        
        // Se busca al usuario en la base de datos
        Usuario usuarioEncontrado = usuarioMapper.findByUsername(username);

        // Si no se encuentra al usuario, se lanza una excepción
        if (usuarioEncontrado == null) {
            throw new RuntimeException("El usuario no existe");
        }

        // Si el usuario se encuentra inactivo, se lanza una excepción
        if (!usuarioEncontrado.getEstatus()) {
            throw new RuntimeException("El usuario se encuentra inactivo");
        }

        // Se compara la contraseña ingresada con la contraseña hasheada en la base de datos
        // BCrypt.checkpw() es un método que compara la contraseña ingresada con la contraseña hasheada en la base de datos
        // Para ese método los parámetros son el String de la contraseña y el String de la contraseña hasheada
        // Y nos retorna true si la contraseña es correcta, false si no lo es
        boolean passwordCorrecta = BCrypt.checkpw(passwordPlana, usuarioEncontrado.getPassword());
        
        // Si la contraseña es incorrecta, se lanza una excepción
        if (!passwordCorrecta) {
            throw new RuntimeException("La contraseña es incorrecta"); 
        }

        // Si se llega hasta aqui, entonces la contraseña si fue correcta
        // Ahora usamos la herramienta para generar el gafete JWT
        String tokenGenerado = jwtUtil.generateToken(usuarioEncontrado);

        // Ahora voy a armar el paquete de entrega (DTO) con los datos por secciones
        // Aqui van los datos del usuario
        LoginResponseDTO respuesta = new LoginResponseDTO();
        respuesta.setIdUsuario(usuarioEncontrado.getIdUsuario());
        respuesta.setIdRol(usuarioEncontrado.getIdRol());
        respuesta.setUsuario(usuarioEncontrado.getUsername());
        
        // Aqui conecto el nombre y apellido para armar el nombre completo
        String nombreCompleto = usuarioEncontrado.getNombre() + " " + usuarioEncontrado.getApellidoPaterno();
        respuesta.setNombreCompleto(nombreCompleto);
        
        // Aqui les pongo unos textos temporales para rol y tipo
        respuesta.setIdTipoUsuario(usuarioEncontrado.getIdTipoUsuario());
        respuesta.setRol("ROL_ID_" + usuarioEncontrado.getIdRol()); 
        respuesta.setTipoUsuario("TIPO_ID_" + usuarioEncontrado.getIdTipoUsuario());
        
        // Y finalmente le pongo el token
        respuesta.setToken(tokenGenerado);

        // Ahora que ya tiene todos los datos que pide el DTO, ya lo libero
        return respuesta;
    }
}
