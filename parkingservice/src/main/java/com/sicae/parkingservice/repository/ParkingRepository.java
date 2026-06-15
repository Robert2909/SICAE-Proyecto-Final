/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sicae.parkingservice.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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

    @Select("SELECT COUNT(*) FROM ticket WHERE estatus = b'1'")
    Integer contarEspaciosOcupados();

    @Select("SELECT COUNT(*) FROM ticket WHERE idUsuario = #{idUsuario} AND estatus = b'1'")
    Integer contarVehiculosAdentroPorUsuario(@Param("idUsuario") Integer idUsuario);

    @Select("SELECT * FROM ticket WHERE placa = #{placa} AND estatus = b'1'")
    Ticket buscarTicketAbiertoPorPlaca(@Param("placa") String placa);

    @Insert("INSERT INTO ticket (idUsuario, placa, horaEntrada) VALUES (#{idUsuario}, #{placa}, #{horaEntrada})")
    void registrarEntrada(Ticket ticket);

    @Update("UPDATE ticket SET horaSalida = #{horaSalida}, tiempoTotalMinutos = #{tiempoTotalMinutos}, " +
            "costoTotal = #{costoTotal}, estatus = b'0' WHERE idTicket = #{idTicket}")
    void registrarSalida(Ticket ticket);
}
