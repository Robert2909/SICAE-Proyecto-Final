package com.sicae.userservice.repository;

import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UsuarioRepository {
    
    //Nota: hey, no quiten los \" porque en mi caso me da un buen de errores al tratar de conectarse a la tabla usuarios.
    //vi que con robert no pasaba pero a mi si desde que meti mi primera peticion a postman
    
    // En esta parte se hace la consulta a la base de datos para encontrar al usuario
    // Se utiliza el @Select para poder pasar el username como parametro a la consulta SQL
    // Luego en "@Param("username") String username" es donde se hace la asignacion con el "username" de la consulta SQL
    @Select("SELECT * FROM \"usuario\" WHERE \"username\" = #{username}")
    Usuario findByUsername(@Param("username") String username);
    
    // Para buscar al usuario por id
    @Select("SELECT * FROM \"usuario\" WHERE \"idUsuario\" = #{idUsuario}")
    Usuario findByidUsuario(@Param("idUsuario") Integer idUsuario);
    
    // Para buscarlo por clave
    @Select("SELECT * FROM \"usuario\" WHERE \"claveUsuario\" = #{claveUsuario}")
    Usuario findByclaveUsuario(@Param("claveUsuario") String claveUsuario);

    // Para buscarlo por correo
    @Select("SELECT * FROM \"usuario\" WHERE \"email\" = #{email}")
    Usuario findByemail(@Param("email") String email);
    
    @Insert("INSERT INTO \"usuario\" (\"idRol\", \"idTipoUsuario\", \"nombre\", \"apellidoPaterno\", \"apellidoMaterno\", \"claveUsuario\", \"email\", \"telefono\", \"username\", \"password\", \"estatus\", \"idProgramaEducativo\", \"tiempoCreacion\", \"tempoActualizacion\") " +
            "VALUES (#{idRol}, #{idTipoUsuario}, #{nombre}, #{apellidoPaterno}, #{apellidoMaterno}, #{claveUsuario}, #{email}, #{telefono}, #{username}, #{password}, CASE WHEN #{estatus} THEN B'1' ELSE B'0' END, #{idProgramaEducativo}, #{tiempoCreacion}, #{tiempoActualizacion})")
    void registrarUsuario(Usuario usuario);
    
    
    @Select("SELECT * FROM \"usuario\" ORDER BY \"idUsuario\" DESC LIMIT 1;")
    Usuario ultimoUsuario();
    
    @Select("SELECT COUNT(*) FROM \"programaEducativo\" WHERE \"idPrograma\" = #{idPrograma}")
    Integer findByidPrograma(@Param("idPrograma") Integer idPrograma);
    
    @Select("SELECT COUNT(*) FROM \"rol\" WHERE \"idrol\" = #{idrol}")
    Integer findByidrol(@Param("idrol") Integer idRol);
    
    @Select("SELECT COUNT(*) FROM \"tipoUsuario\" WHERE \"idTipo\" = #{idTipo}")
    Integer findByidTipo(@Param("idTipo") Integer idTipo);
    
    @Update("UPDATE \"usuario\" SET \"estatus\" = CAST(#{nuevoEstatus} AS bit), \"tempoActualizacion\" = #{tiempoActualizacion} WHERE \"idUsuario\" = #{idUsuario}")
    Integer actualizarEstatus(@Param("idUsuario") Integer idUsuario, @Param("nuevoEstatus") String nuevoEstatus, @Param("tiempoActualizacion") LocalDateTime tiempoActualizacion);
    
    @Update("UPDATE \"usuario\" SET \"idRol\" = #{idRol}, \"idTipoUsuario\" = #{idTipoUsuario}, \"nombre\" = #{nombre}, \"apellidoPaterno\" = #{apellidoPaterno}, \"apellidoMaterno\" = #{apellidoMaterno}, \"email\" = #{email}, \"telefono\" = #{telefono}, \"idProgramaEducativo\" = #{idProgramaEducativo}, \"tempoActualizacion\" = #{tiempoActualizacion} WHERE \"idUsuario\" = #{idUsuario}")
    Integer editarUsuario(Usuario usuario);
    
    

}