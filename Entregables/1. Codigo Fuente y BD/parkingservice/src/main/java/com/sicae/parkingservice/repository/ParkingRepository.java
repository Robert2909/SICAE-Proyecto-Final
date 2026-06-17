/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sicae.parkingservice.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 *
 * @author belli
 */
@Mapper
public interface ParkingRepository {

    @Select("SELECT * FROM configuracion WHERE idConfiguracion = 1")
    Configuracion obtenerConfiguracion();

    // buscamos el primer espacio disponible
    @Select("SELECT idEspacio FROM cajon WHERE ocupado = b'0' LIMIT 1")
    Integer obtenerEspacioDisponible();

    // y después devolvemos una lista con todos los lugares disponibles
    @Select("SELECT idEspacio FROM cajon WHERE ocupado = b'0'")
    List<Integer> consultarEspaciosDisponibles();

    @Update("UPDATE cajon SET ocupado = b'1' WHERE idEspacio = #{idEspacio}")
    void ocuparEspacio(@Param("idEspacio") Integer idEspacio);

    @Update("UPDATE cajon SET ocupado = b'0' WHERE idEspacio = #{idEspacio}")
    void liberarEspacio(@Param("idEspacio") Integer idEspacio);

    @Select("SELECT COUNT(*) FROM ticket WHERE idUsuario = #{idUsuario} AND estatus = b'1'")
    Integer contarVehiculosAdentroPorUsuario(@Param("idUsuario") Integer idUsuario);

    @Select("SELECT * FROM ticket WHERE placa = #{placa} AND estatus = b'1'")
    Ticket buscarTicketAbiertoPorPlaca(@Param("placa") String placa);

    @Insert("INSERT INTO ticket (idUsuario, placa, horaEntrada, idEspacio, tarifaHora) VALUES (#{idUsuario}, #{placa}, #{horaEntrada}, #{idEspacio}, #{tarifaHora})")
    @Options(useGeneratedKeys = true, keyProperty = "idTicket", keyColumn = "idTicket")
    void registrarEntrada(Ticket ticket);

    @Update("UPDATE ticket SET horaSalida = #{horaSalida}, tiempoTotalMinutos = #{tiempoTotalMinutos}, " +
            "costoTotal = #{costoTotal}, horasCobradas = #{horasCobradas}, estatus = b'0' WHERE idTicket = #{idTicket}")
    void registrarSalida(Ticket ticket);
}
