/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sicae.parkingservice.repository;

import java.time.LocalDateTime;

/**
 *
 * @author belli
 */
public class Ticket {

    private Integer idTicket;
    private Integer idUsuario;
    private String placa;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private Integer tiempoTotalMinutos;
    private Integer idEspacio;
    private Double tarifaHora;
    private Integer horasCobradas;
    private Double costoTotal;
    private Boolean estatus; 

    // Getters y Setters
    public Integer getIdTicket() { return idTicket; }
    public void setIdTicket(Integer idTicket) { this.idTicket = idTicket; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public LocalDateTime getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(LocalDateTime horaEntrada) { this.horaEntrada = horaEntrada; }

    public LocalDateTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalDateTime horaSalida) { this.horaSalida = horaSalida; }

    public Integer getTiempoTotalMinutos() { return tiempoTotalMinutos; }
    public void setTiempoTotalMinutos(Integer tiempoTotalMinutos) { this.tiempoTotalMinutos = tiempoTotalMinutos; }

    public Integer getIdEspacio() { return idEspacio; }
    public void setIdEspacio(Integer idEspacio) { this.idEspacio = idEspacio; }

    public Double getTarifaHora() { return tarifaHora; }
    public void setTarifaHora(Double tarifaHora) { this.tarifaHora = tarifaHora; }

    public Integer getHorasCobradas() { return horasCobradas; }
    public void setHorasCobradas(Integer horasCobradas) { this.horasCobradas = horasCobradas; }

    public Double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(Double costoTotal) { this.costoTotal = costoTotal; }

    public Boolean getEstatus() { return estatus; }
    public void setEstatus(Boolean estatus) { this.estatus = estatus; }
}
