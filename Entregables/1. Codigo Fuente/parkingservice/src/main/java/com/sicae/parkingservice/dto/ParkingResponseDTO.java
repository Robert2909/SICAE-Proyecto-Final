package com.sicae.parkingservice.dto;

import java.time.LocalDateTime;

public class ParkingResponseDTO {
    private Integer idMovimiento;
    private LocalDateTime tiempoEntrada;
    private LocalDateTime tiempoSalida;
    private Integer espacioAsignado;
    private Double tarifaHora;
    private Double costoTotal;
    private Integer horasCobradas;

    // Getters y Setters
    public Integer getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Integer idMovimiento) { this.idMovimiento = idMovimiento; }

    public LocalDateTime getTiempoEntrada() { return tiempoEntrada; }
    public void setTiempoEntrada(LocalDateTime tiempoEntrada) { this.tiempoEntrada = tiempoEntrada; }

    public LocalDateTime getTiempoSalida() { return tiempoSalida; }
    public void setTiempoSalida(LocalDateTime tiempoSalida) { this.tiempoSalida = tiempoSalida; }

    public Integer getEspacioAsignado() { return espacioAsignado; }
    public void setEspacioAsignado(Integer espacioAsignado) { this.espacioAsignado = espacioAsignado; }

    public Double getTarifaHora() { return tarifaHora; }
    public void setTarifaHora(Double tarifaHora) { this.tarifaHora = tarifaHora; }

    public Double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(Double costoTotal) { this.costoTotal = costoTotal; }

    public Integer getHorasCobradas() { return horasCobradas; }
    public void setHorasCobradas(Integer horasCobradas) { this.horasCobradas = horasCobradas; }
}