package com.sicae.userservice.service;

import com.sicae.userservice.dto.UsuariosResponseDTO;
import com.sicae.userservice.repository.Usuario;

import com.sicae.userservice.dto.UsuariosRequestDTO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.sicae.userservice.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import javax.crypto.SecretKey;

@Service
public class UsuarioService {
    //esta es la llave que nos dio el integrante 1 de nuestro equipo, la tenemos aqui para verificar los tokens que llegan.
    private static final String SECRET_KEY_STRING = "LlaveSuperSecretaParangaricutirimicuaro";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    
    //metodo de prueba chicos, para probar que si esta funcionando en el 82
    public String prueba() {
        return "probando el UserServices 8082";
    }
    
    
    // En esta parte se hace uso del repository para poder hacer la consulta a la base de datos
    private final UsuarioRepository usuarioRepository; 

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
    
    //Aqui ya con el token bueno, antes que nada lo validamos por seguridad, entramos a lo que es el palyload del token, que es donde tiene su informacion. De ahi extraemos el user, conel .getSubjetc()
    public String obtenerUsuarioDelToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims.getSubject(); 
    }
    
    
    public String agregarUsuario(String token, UsuariosRequestDTO usuarioSoli) {
        
        Usuario nuevoUsuario = new Usuario();
    
        nuevoUsuario.setIdRol(usuarioSoli.getIdRol());
        nuevoUsuario.setIdTipoUsuario(usuarioSoli.getIdTipoUsuario());
        nuevoUsuario.setIdProgramaEducativo(usuarioSoli.getIdProgramaEducativo());
        nuevoUsuario.setNombre(usuarioSoli.getNombre());
        nuevoUsuario.setApellidoPaterno(usuarioSoli.getApellidoPaterno());
        nuevoUsuario.setApellidoMaterno(usuarioSoli.getApellidoMaterno());
        nuevoUsuario.setEmail(usuarioSoli.getEmail());
        nuevoUsuario.setTelefono(usuarioSoli.getTelefono());
        nuevoUsuario.setUsername(usuarioSoli.getUsername());
        nuevoUsuario.setPassword(usuarioSoli.getPassword());
        
        // Primero llamo al metodo de arriba para verificar el token que nos mando Postman
        boolean tokenValido = validarToken(token);
        if (tokenValido == false) {
            throw new RuntimeException("El token es inválido o ya expiró");
        }
        
        // Extraemos el username para traer todo el objeto despues de la base de datos
        String usernameUsuario = obtenerUsuarioDelToken(token);
        
        // Buscamos a ese usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(usernameUsuario);
        
        if (usuario == null) {
            throw new RuntimeException("El usuario del token no existe en la base de datos");
        }
        
        // Validamos si es administrador pq es 1
        if (usuario.getIdRol() != 1) {
            throw new RuntimeException("ERROR NO ERES ADMIN, NO PUEDES AGREGAR USUARIOS");
        }
        
        //Aqui nada m[as hacemos las validaciones de todos los obligatorios que nos piden en el project
        if (nuevoUsuario.getIdRol() == null){
            throw new RuntimeException("Falta asignar el idRol del usuario del nuevo usuario");
        }
        if (nuevoUsuario.getIdTipoUsuario() == null){
            throw new RuntimeException("Falta asignar el IdTipoUsuario del nuevo usuario");
        }
        if (nuevoUsuario.getNombre() == null || nuevoUsuario.getNombre().isEmpty()) {
            throw new RuntimeException("Falta asignar el nombre del nuevo usuario.");
        }
        if (nuevoUsuario.getApellidoPaterno() == null || nuevoUsuario.getApellidoPaterno().isEmpty()) {
            throw new RuntimeException("Falta asignar el apellido paterno del nuevo usuario.");
        }
        if (nuevoUsuario.getIdProgramaEducativo() == null) {
            throw new RuntimeException("Falta asignar el programa educativo del nuevo usuario.");
        }
        if (nuevoUsuario.getUsername() == null || nuevoUsuario.getUsername().isEmpty()) {
            throw new RuntimeException("Falta asignar el username del nuevo usuario.");
        }
        if (nuevoUsuario.getPassword() == null || nuevoUsuario.getPassword().isEmpty()) {
            throw new RuntimeException("Falta asignar el password del nuevo usuario.");
        }
        if (nuevoUsuario.getEmail() == null || nuevoUsuario.getEmail().isEmpty() || !nuevoUsuario.getEmail().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new RuntimeException("Falta asignar el email del nuevo usuario o no tiene el formato valido");
        }
        if (nuevoUsuario.getTelefono() == null || nuevoUsuario.getTelefono().isEmpty()) {
            throw new RuntimeException("Falta asignar el teléfono del nuevo usuario.");
        }
                                    
        
        //creamos un usuario temporal para buscar en la base de datos si el username de nuestronuevo usuario 
        //Esta en la base, si es diferente de null, tiene un nombre que coincidio y entonces avisa
        Usuario bd = usuarioRepository.findByUsername(nuevoUsuario.getUsername());
        if (bd != null) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        
        //creamos un usuario temporal para buscar en la base de datos si el correo de nuestronuevo usuario 
        //Esta en la base, si es diferente de null, tiene un correo que coincidio y entonces avisa
        bd = usuarioRepository.findByemail(nuevoUsuario.getEmail());
        if (bd != null) {
            throw new RuntimeException("El correo electrónico ya se encuentra registrado");
        }
        
        //aqui verificamos si el id proporcionado corresponde a uno que tengamos en nuestra tabla de programaEducativo
        
        if (usuarioRepository.findByidPrograma(nuevoUsuario.getIdProgramaEducativo()) == 0) {
            throw new RuntimeException("El id de Programa Educativo ingresado no se encuentra en nuestro catalogo");
        }
        
        //aqui verificamos si el id proporcionado corresponde a uno que tengamos en nuestra tabla de roles
        if (usuarioRepository.findByidrol(nuevoUsuario.getIdRol()) == 0) {
            throw new RuntimeException("El id del rol que ingresaste no se encuentra esta asignado a ningun rol");
        }
        
        //aqui verificamos si el id proporcionado está asignado a un tipo de usuario en la BD
        if (usuarioRepository.findByidTipo(nuevoUsuario.getIdTipoUsuario()) == 0) {
            throw new RuntimeException("El id que proporcionaste no está asignado a ningun tipo de usuario");
        }
        
        //Con BCrypt encriptamos la contraseña primero antes de guardarla en la BD
        String passwordPlana = nuevoUsuario.getPassword();
        String contraEncriptada = BCrypt.hashpw(passwordPlana, BCrypt.gensalt());
        nuevoUsuario.setPassword(contraEncriptada);
        
        //le asignamos su clave unica y con patron sucesivo (mi mayor obra de arte)
        Usuario ultimoUsuario = usuarioRepository.ultimoUsuario();
        String ultimaClave = ultimoUsuario.getClaveUsuario();
        char [] cadena_div = ultimaClave.toCharArray();
        String n = "";
        for (int i = 0; i < cadena_div.length; i++) {
            if(Character.isDigit(cadena_div[i])){
                n+=cadena_div[i];
            }
        }
        int ultimonumero = Integer.parseInt(n);
        ultimonumero += 1;
        String claveFinal = "PRU-" + Integer.toString(ultimonumero);
        nuevoUsuario.setClaveUsuario(claveFinal);
        
        

        // lo ponemos activo de default
        nuevoUsuario.setEstatus(true);
        
        //Asignamos el tiempo de actualizacion en null para que se active cuando haya el primer cambio
        nuevoUsuario.setTiempoActualizacion(null);
        
        //asignamos el tiempo de creacion del usuario
        nuevoUsuario.setTiempoCreacion(LocalDateTime.now());
        
        //ya todo bien lo mandamos
        usuarioRepository.registrarUsuario(nuevoUsuario);
        
        return "Operación realizada correctamente: El usuario se agregó con éxito";
    }
    
    //Metodo para ver el perfil del usuario.
    //Va a la base de datos, saca la info y la empaqueta en el DTO para postman.
    public UsuariosResponseDTO verperfil(String token, Integer idUsuario){
        
        //Verificamos que traiga el idUsuario
        if (idUsuario == null) {
            throw new RuntimeException("Falta asignar el idUsuario.");
        }
        
        // validamos el token
        boolean tokenValido = validarToken(token);
        if (tokenValido == false) {
            throw new RuntimeException("El token es inválido o ya expiró");
        }
        
        //Traemos de la base al usuario con el id que nos pasaron, si es que est[a.
        Usuario usuarioEncontrado = usuarioRepository.findByidUsuario(idUsuario);

        // Si no se encuentra
        if (usuarioEncontrado == null) {
            throw new RuntimeException("El usuario no existe");
        }

        // Ojo aquí chicos: Si el usuario se encuentra inactivo (lo entedi como dada de baja lógica)no tenemos por qué andar mostrando su perfil. Lo rebotamos por seguridad.
        if (!usuarioEncontrado.getEstatus()) {
            throw new RuntimeException("El usuario se encuentra inactivo");
        }
        
        //Este es todo el DTO con los datos que puse para su estructura.
        //Hacemos esto del DTO para no mandar toda la entidad de la base de datos cruda y proteger info sensible como el password.
        UsuariosResponseDTO perfil = new UsuariosResponseDTO();
        perfil.setIdUsuario(usuarioEncontrado.getIdUsuario());
        perfil.setRol(usuarioEncontrado.getIdRol());
        perfil.setTipoUsuario(usuarioEncontrado.getIdTipoUsuario());
        perfil.setProgramaEducativo(usuarioEncontrado.getIdProgramaEducativo());
        perfil.setNombre(usuarioEncontrado.getNombre());
        perfil.setApellidoPaterno(usuarioEncontrado.getApellidoPaterno());
        perfil.setApellidoMaterno(usuarioEncontrado.getApellidoMaterno());
        perfil.setUsuario(usuarioEncontrado.getUsername());
        perfil.setCorreo(usuarioEncontrado.getEmail());
        perfil.setTelefono(usuarioEncontrado.getTelefono());
        perfil.setEstatus(usuarioEncontrado.getEstatus());
        perfil.setClaveUsuario(usuarioEncontrado.getClaveUsuario());
        perfil.setTiempoCreacion(usuarioEncontrado.getTiempoCreacion());
        perfil.setTiempoActualizacion(usuarioEncontrado.getTiempoActualizacion());
        
        
        //ya todo bien lo mandamos
        return perfil;
        
    }
    
    //Funcion para cambiar el estado de un usuario
    //Solo un administrador puede cambiar el estado de un usuario.
    public String actualizarEstatus(String token, Integer idUsuario, Integer idRol) {
    
        //Verificamos que traiga el idUsuario
        if (idUsuario == null) {
            throw new RuntimeException("Falta asignar el idUsuario.");
        }
        
        //Verificamos que traiga el idRol
        if (idRol == null) {
            throw new RuntimeException("Falta asignar el idRol.");
        }

        //Checamos que el token
        boolean tokenValido = validarToken(token);
        if (tokenValido == false) {
            throw new RuntimeException("El token es inválido o ya expiró");
        }

        //Extraemos el username.
        String usernameUsuario = obtenerUsuarioDelToken(token);

        //Traemos de la base al usuario con el id que nos pasaron, si es que est[a.
        Usuario usuario = usuarioRepository.findByUsername(usernameUsuario);

        if (usuario == null) {
            throw new RuntimeException("El usuario del token no existe en la base de datos");
        }

        //Validamos si es administrador (nuestro id de admin es el 1).
        if (usuario.getIdRol() != 1) {
            throw new RuntimeException("ERROR NO ERES ADMIN, NO PUEDES CAMBIAR EL ESTATUS DE USUARIOS");
        }

        //Ahora= el usuario al que le vamos a cambiar el estatus
        Usuario usuarioEncontrado = usuarioRepository.findByidUsuario(idUsuario);

        // Si no se encuentra al usuario se lanza la excepción
        if (usuarioEncontrado == null) {
            throw new RuntimeException("El usuario no existe");
        }

        // Validamos que el rol que nos mandaron coincida con el rol real del usuario objetivo.
        if (usuarioEncontrado.getIdRol() != idRol) {
            throw new RuntimeException("El idRol enviado no corresponde al usuario que quieres actualizar");
        }

        //añadi que el propio admin no se pueda dar de baja a sí mismo.
        if (usuario.getIdUsuario() == idUsuario) {
            throw new RuntimeException("No puedes cambiar tu propio estatus");
        }

        String nuevoEstatus = "";

        //Si está activo(1), lo mandamos a inactivo (0). 
        // Si estaba inactivo(0), lo activamos (0).
        if (usuarioEncontrado.getEstatus() == true) {
            nuevoEstatus = "0";
        } else {
            nuevoEstatus = "1";
        }
        
        // Aqui mandamos los parametros a repository 
        // Le pasamos quién es, su nuevo estatus, y añadimos la tempoActualizacion con LocalDateTime.now() porque ya hubo una actualizacion en el usuario
        usuarioRepository.actualizarEstatus(idUsuario, nuevoEstatus, LocalDateTime.now());

        // Si sobrevivió a todos los ifs, ya lo mandamos
        return "Operación realizada correctamente: El estatus del usuario se actualizó con éxito";
    }
    
    // Ultimo metodo. Actualizar la información de un usuario.
    // Recibimos el token de quien hace la petición, el id del usuario que vamos a modificar y el DTO con los datos nuevos.
    public String editarUsuario(String token, Integer idUsuario, UsuariosRequestDTO usuarioSoli) {
    
        //Verificamos que traiga el idUsuario
        if (idUsuario == null) {
            throw new RuntimeException("Falta asignar el idUsuario.");
        }

        // Verificamos el token
        boolean tokenValido = validarToken(token);
        if (tokenValido == false) {
            throw new RuntimeException("El token es inválido o ya expiró");
        }

        //Sacamos el username
        String usernameUsuario = obtenerUsuarioDelToken(token);

         //Traemos de la base al usuario con el id que nos pasaron, si es que est[a.
        Usuario usuario = usuarioRepository.findByUsername(usernameUsuario);

        if (usuario == null) {
            throw new RuntimeException("El usuario del token no existe en la base de datos");
        }

        //Buscamos al usuario que se quiere editar
        Usuario usuarioEncontrado = usuarioRepository.findByidUsuario(idUsuario);

        if (usuarioEncontrado == null) {
            throw new RuntimeException("El usuario que quieres editar no existe");
        }

        //Creamos una instacia de usuario para asignarle todos los datos del DTO
        Usuario usuarioEditado = new Usuario();

        // Le vamos pasando los datos nuevos que llegaron en el DTO que si se modificaran
        usuarioEditado.setIdUsuario(idUsuario);
        usuarioEditado.setIdRol(usuarioSoli.getIdRol());
        usuarioEditado.setIdTipoUsuario(usuarioSoli.getIdTipoUsuario());
        usuarioEditado.setIdProgramaEducativo(usuarioSoli.getIdProgramaEducativo());
        usuarioEditado.setNombre(usuarioSoli.getNombre());
        usuarioEditado.setApellidoPaterno(usuarioSoli.getApellidoPaterno());
        usuarioEditado.setApellidoMaterno(usuarioSoli.getApellidoMaterno());
        usuarioEditado.setEmail(usuarioSoli.getEmail());
        usuarioEditado.setTelefono(usuarioSoli.getTelefono());

        //Los de aqui se pide que no se muevan, asi que no son editables desde este endpoint.
        // Así que los pasamos tal cual los sacamos del usuario original del usuarioEncontrado.
        usuarioEditado.setUsername(usuarioEncontrado.getUsername());
        usuarioEditado.setPassword(usuarioEncontrado.getPassword());
        usuarioEditado.setClaveUsuario(usuarioEncontrado.getClaveUsuario());
        usuarioEditado.setEstatus(usuarioEncontrado.getEstatus());
        usuarioEditado.setTiempoCreacion(usuarioEncontrado.getTiempoCreacion());

        // Aqui se asigna el tiempo de actualización porque evidentemente se le está metiendo mano al registro.
        usuarioEditado.setTiempoActualizacion(LocalDateTime.now());

        //Validaciones como en las anteriores
        if (usuarioEditado.getIdRol() == null) {
            throw new RuntimeException("Falta asignar el idRol del usuario.");
        }

        if (usuarioEditado.getIdTipoUsuario() == null) {
            throw new RuntimeException("Falta asignar el IdTipoUsuario del usuario.");
        }

        if (usuarioEditado.getNombre() == null || usuarioEditado.getNombre().isEmpty()) {
            throw new RuntimeException("Falta asignar el nombre del usuario.");
        }
        
        // Restringimos tamaños de caracteres
        if (usuarioEditado.getNombre().length() > 50) {
            throw new RuntimeException("El nombre es demasiado largo.");
        }

        if (usuarioEditado.getApellidoPaterno() == null || usuarioEditado.getApellidoPaterno().isEmpty()) {
            throw new RuntimeException("Falta asignar el apellido paterno del usuario.");
        }
        // Tambien limitamos el tamaño aqui
        if (usuarioEditado.getApellidoPaterno().length() > 50) {
            throw new RuntimeException("El apellido paterno es demasiado largo.");
        }
        
        // Hacemos la validación y limitación para el apellido materno, aunque este puede ser nulo, por eso el != null al principio
        if (usuarioEditado.getApellidoMaterno() != null && usuarioEditado.getApellidoMaterno().length() > 50) {
            throw new RuntimeException("El apellido materno es demasiado largo.");
        }

        if (usuarioEditado.getIdProgramaEducativo() == null) {
            throw new RuntimeException("Falta asignar el programa educativo del usuario.");
        }

        // Verificamos el correo, formato y caracteres
        if (usuarioEditado.getEmail() == null || usuarioEditado.getEmail().isEmpty() || !usuarioEditado.getEmail().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new RuntimeException("Falta asignar el email del usuario o no tiene el formato valido");
        }
        if (usuarioEditado.getEmail().length() > 100) {
            throw new RuntimeException("El correo es demasiado largo.");
        }
        
        //Validacion para el telefono
        if (usuarioEditado.getTelefono() == null || usuarioEditado.getTelefono().isEmpty()) {
            throw new RuntimeException("Falta asignar el teléfono del usuario.");
        }
        // Terminamos con limitaciones para el telefono
        if (usuarioEditado.getTelefono().length() > 15) {
            throw new RuntimeException("El teléfono es demasiado largo.");
        }

        // Validamos si el nuevo correo que quieren poner ya se lo agenció otro usuario en el sistema.
        Usuario bd = usuarioRepository.findByemail(usuarioEditado.getEmail());
        if (bd != null && bd.getIdUsuario() != idUsuario) { 
            throw new RuntimeException("El correo electrónico ya se encuentra registrado");
        }

        // Aqui verificamos el id proporcionado en los catálogos.
        if (usuarioRepository.findByidPrograma(usuarioEditado.getIdProgramaEducativo()) == 0) {
            throw new RuntimeException("El id de Programa Educativo ingresado no se encuentra en nuestro catalogo");
        }

        // Lo mismo para los roles. 
        if (usuarioRepository.findByidrol(usuarioEditado.getIdRol()) == 0) {
            throw new RuntimeException("El id del rol que ingresaste no se encuentra esta asignado a ningun rol");
        }

        // Y lo mismo para el tipo de usuario.
        if (usuarioRepository.findByidTipo(usuarioEditado.getIdTipoUsuario()) == 0) {
            throw new RuntimeException("El id que proporcionaste no está asignado a ningun tipo de usuario");
        }

        //Con todo listo, podemos hacer el update
        usuarioRepository.editarUsuario(usuarioEditado);

        return "Operación realizada correctamente: El usuario se editó con éxito";
    }
}
