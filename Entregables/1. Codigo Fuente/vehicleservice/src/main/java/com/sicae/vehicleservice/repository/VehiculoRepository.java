package com.sicae.vehicleservice.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface VehiculoRepository {
    
    //Nota: hey, no quiten los \" porque en mi caso me da un buen de errores al tratar de conectarse a la tabla usuarios.
    //vi que con robert no pasaba pero a mi si desde que meti mi primera peticion a postman
    // En esta parte se hace la consulta a la base de datos para encontrar al usuario y los vehiculos
    
    // aqui buscamos todos los vehículos que pertenecen a un usuario específico
    // Se usa @Param("idUsuario") para mandar el idusuario recibido desde el service a la consulta sql
    @Select("SELECT * FROM vehiculofullinfo WHERE idUsuario = #{idUsuario}")
    List<Vehiculo> buscarVehiculosPorUsuario(@Param("idUsuario") Integer idUsuario);

    // aqui buscamos un vehículo por su idvehiculo
    // Nos sirve para validar que el vehículo exista antes de hacerle algun cambio
    @Select("SELECT * FROM vehiculo WHERE idVehiculo = #{idVehiculo}")
    Vehiculo buscarPorId(@Param("idVehiculo") Integer idVehiculo);
    
    // aqui buscamos un vehículo por su placa
    // La usamos para validar que no se registre otro vehículo con la misma placa.
    @Select("SELECT * FROM vehiculo WHERE placa = #{placa}")
    Vehiculo buscarPorPlaca(@Param("placa") String placa);
    
    // aqui checamos si al editar una placa y asignar una nueva no exista en otro vehiculo
    // El idVehiculo se usa para ignorar el vehículo actual y no marcar su propia placa como que ya existe
    @Select("SELECT * FROM vehiculo WHERE placa = #{placa} AND idVehiculo <> #{idVehiculo}")
    Vehiculo buscarPlacaEnOtroVehiculo(@Param("placa") String placa, @Param("idVehiculo") Integer idVehiculo);
    
    // aqui checamos cuantos vehiculos activos tiene un usuario
    // Nos sirve para validar la regla de máximo cuatro vehículos activos por usuario
    @Select("SELECT COUNT(*) FROM vehiculo WHERE idUsuario = #{idUsuario} AND estatus = b'1'")
    Integer contarVehiculosActivosPorUsuario(@Param("idUsuario") Integer idUsuario);
    
    // esta es para ver si el modelo existe y si esta activado en el catalogo
    // asi no registramos vehiculos con un modelo que no existe o que no este activado
    @Select("SELECT COUNT(*) FROM modelo WHERE idModelo = #{idModelo} AND estatus = b'1'")
    Integer existeModeloActivo(@Param("idModelo") Integer idModelo);
    
    // aqui traemos al ultimo vehiculo registrado
    @Select("SELECT * FROM vehiculo ORDER BY idVehiculo DESC LIMIT 1")
    Vehiculo ultimoVehiculo();
    
    // con esta consulta insertamos un nuevo vehiculo en la tabla
    // No mandamos idVehiculo porque mysql lo genera solo y tampoco mandamos estatus porque por defecto se inserta activo
    @Insert("INSERT INTO vehiculo (idUsuario, claveVehiculo, idModelo, placa, color, anio, descripcion) VALUES (#{idUsuario}, #{claveVehiculo}, #{idModelo}, #{placa}, #{color}, #{anio}, #{descripcion})")
    void registrarVehiculo(Vehiculo vehiculo);
    
    // con esta consulta podemos editar los datos
    // No modificamos idusuario, clavevehiculo ni estatus porque esos datos no deben cambiarse desde aqui
    @Update("UPDATE vehiculo SET idModelo = #{idModelo}, placa = #{placa}, color = #{color}, anio = #{anio}, descripcion = #{descripcion} WHERE idVehiculo = #{idVehiculo}")
    Integer editarVehiculo(Vehiculo vehiculo);
    
    // aqui cambiamos el estatus del vehiculo
    // lo unico que hace es que si el vehiculo esta activado lo desactiva y si no estaba activado lo activa
    @Update("UPDATE vehiculo SET estatus = CASE WHEN estatus = b'1' THEN b'0' ELSE b'1' END WHERE idVehiculo = #{idVehiculo}")
    Integer cambiarEstatus(@Param("idVehiculo") Integer idVehiculo);
    
    // Esta consulta busca un vehículo usando idusuario y placa
    // esto lo usara el parkingservice para confirmar que la placa pertenece al usuario antes de registrar entrada o salida.
    @Select("SELECT * FROM vehiculofullinfo WHERE idUsuario = #{idUsuario} AND placa = #{placa} AND estatus = b'1'")
    Vehiculo validarVehiculoPorUsuarioYPlaca(@Param("idUsuario") Integer idUsuario, @Param("placa") String placa);
    }