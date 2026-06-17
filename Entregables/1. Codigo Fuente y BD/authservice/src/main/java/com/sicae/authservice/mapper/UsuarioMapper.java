package com.sicae.authservice.mapper;

import com.sicae.authservice.model.Usuario;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsuarioMapper {

    // En esta parte se hace la consulta a la base de datos para encontrar al usuario
    // Se utiliza el @Select para poder pasar el username como parametro a la consulta SQL
    // Luego en "@Param("username") String username" es donde se hace la asignacion con el "username" de la consulta SQL
    @Select("SELECT * FROM usuario WHERE username = #{username}")
    Usuario findByUsername(@Param("username") String username);

}
