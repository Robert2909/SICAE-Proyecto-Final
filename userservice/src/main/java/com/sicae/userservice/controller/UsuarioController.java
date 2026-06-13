package com.sicae.userservice.controller;

import com.sicae.userservice.dto.UsuariosRequestDTO;
import com.sicae.userservice.dto.UsuariosResponseDTO;
import com.sicae.userservice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController indica que este archivo es un controlador REST
@RestController
// @RequestMapping indica que todas las rutas empezaran con /auth
@RequestMapping("/usuarios")

public class UsuarioController {
     
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/prueba")
    public String probarUserService() {
        return usuarioService.prueba();
    }
    
    //esta es el primer endpoint del microservicio. Toma la petición hecha y analiza el header.
    //como necesitamos validar el token, dejamos la restricción "Authorization" que verifica que desde postman de mande con un Authtype de tipo Bearer el token
    //Despues, pasamos al un objeto UsuarioRequestDTO que contiene toda la informacion para crear a nuestro nuevo usuario, este posteriormente se mapeara a un objeto Usuario que es el molde de nuestra base de datos.
    //Finalmente, se hace la autenticacion del token, se llama a usuarioService para que se encargue de aplicar la logica del negocio y finalmente si todo esta bien se realiza la carga a la base de datos y el usuario en postman ve un mensaje de exito, o uno de error en caso de que algo este mal.
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarNuevoUsuario(@RequestHeader("Authorization") final String authHeader,@RequestBody UsuariosRequestDTO nuevoUsuario) {
        
        try {
            // Primero revisamos si nos mandaron el token en la cabecera
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización o el formato es incorrecto");
            }
            
            // Le quitamos la palabra "Bearer " para dejar el puro token
            String tokenPuro = authHeader.substring(7);
            
            // Se lo mandamos al servicio junto con los datos que llegaron en el Body
            String mensajeExito = usuarioService.agregarUsuario(tokenPuro, nuevoUsuario);
            
            // Si todo sale bien, regresamos el mensaje de éxito
            return ResponseEntity.ok(mensajeExito);
            
        } catch (Exception e) {
            // Si alguna validación falló en el servicio (por ejemplo, no era admin o faltaban datos), 
            // cachamos el error aquí y mandamos el mensaje que especificamos.
            return ResponseEntity.badRequest().body("No pudo completarse: " + e.getMessage());
        }
    }
    
   // Este es nuestro segundo endpoint chicos. Sirve para editar algun usuario.
    // solo jala si le pasas estrictamente el token del admin en el header (nuestro cadenero no deja pasar a cualquiera)
    // y necesitamos mandarle por la URL el id del usuario que vamos a editar, además del body con los datos nuevos.
    @PutMapping("/editar/{idUsuario}")
    public ResponseEntity<?> editarUsuario(@RequestHeader("Authorization") final String authHeader, @PathVariable Integer idUsuario, @RequestBody UsuariosRequestDTO nuevoUsuario) {

        try {
            // primero revisamos si nos mandaron el token en la cabecera
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización o el formato es incorrecto");
            }

            // Le quitamos la palabra "Bearer " con el substring(7) para dejar el token limpio
            String tokenPuro = authHeader.substring(7);

            // Aquí llamamos a usuarioService, le mandamos el token para validar que sea admin, tambien a quién queremos editar y los datos nuevos.
            // El servicio se encarga de la l[ogica de negocio
            String mensajeExito = usuarioService.editarUsuario(tokenPuro, idUsuario, nuevoUsuario);

            // Si todo sale bien, regresamos un 200 OK y el mensaje de éxito para que Postman nos confirme que ya quedó
            return ResponseEntity.ok(mensajeExito);

        } catch (Exception e) {
            // Si algo tronó en el servicio (tipo, el usuario no era admin o no existía el id), 
            // cachamos el error aquí y mandamos el mensaje que especificamos.
            return ResponseEntity.badRequest().body("No pudo completarse: " + e.getMessage());
        }
    }
    
    // Este el tercer endpoint, es para ver el perfil de usuario 
    // Usamos GetMapping porque solo vamos a consultar datos, no le vamos a mover a nada en la BD.
    // Solo necesitamos el idUsuario en la ruta (@PathVariable) para saber a quién buscar.
    @GetMapping("/verPerfil/{idUsuario}")
    public ResponseEntity<?> verPerfil(@RequestHeader("Authorization") final String authHeader,@PathVariable Integer idUsuario) {
        
        try {
            // Otra vez, verificamos token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización o el formato es incorrecto");
            }
            
            // Le quitamos la palabra "Bearer " con el substring(7) para dejar el token limpio
            String tokenPuro = authHeader.substring(7);
            
            //llamamos a UsuarioService para armar el paquete (DTO) con todos los datos del usuario.
            //Le mandamos el token para que valide quién lo está pidiendo y el id del perfil que queremos ver.
            UsuariosResponseDTO mensajePerfil = usuarioService.verperfil(tokenPuro, idUsuario);
            
            // Si lo encontró y el usuario está activo, lo empaquetamos en un 200 OK y va de regreso.
            return ResponseEntity.ok(mensajePerfil);
            
        } catch (Exception e) {
            // Si el usuario no existe, está dado de baja o el token ya expiró, 
            // cachamos el error aquí y mandamos el mensaje que especificamos.
            return ResponseEntity.badRequest().body("No pudo completarse: " + e.getMessage());
        }
    }
    
    //Ultimo endpoint, sirve para actualizar o, bueno, cambiar el estatus de un usuario
    // Necesitamos pedir el id del usuario al que le vamos a mover el estatus, y el id del rol para hacer las validaciones.
    @PatchMapping("/actualizarEstatus/{idUsuario}/{idRol}")
    public ResponseEntity<?> actualizarEstatus(@RequestHeader("Authorization") final String authHeader,@PathVariable Integer idUsuario, @PathVariable Integer idRol) {

        try {
            // Otra vez, verificamos token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token de autorización o el formato es incorrecto");
            }

            // Le quitamos la palabra "Bearer " con el substring(7) para dejar el token limpio
            String tokenPuro = authHeader.substring(7);

            // Mandamos a llamar a usuarioService para que haga el update en la base.
            String mensajeExito = usuarioService.actualizarEstatus(tokenPuro, idUsuario, idRol);

            //Si todo bien devolvemos el mensajito de éxito.
            return ResponseEntity.ok(mensajeExito);

        } catch (Exception e) {
            // Si nos quisieron hackear con un token falso, no eran admin, o el idUsuario no existe, 
             // cachamos el error aquí y mandamos el mensaje que especificamos.
            return ResponseEntity.badRequest().body("No pudo completarse: " + e.getMessage());
        }
    }
       
}
