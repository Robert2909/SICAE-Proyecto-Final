/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sicae.parkingservice.repository;

/**
 *
 * @author belli
 */
public class Configuracion {

    private Integer idConfiguracion;
    private Integer capacidadTotal;
    private Double costoHora;
    private Double costoMinuto;

    // Getters y Setters
    public Integer getIdConfiguracion() { return idConfiguracion; }
    public void setIdConfiguracion(Integer idConfiguracion) { this.idConfiguracion = idConfiguracion; }

    public Integer getCapacidadTotal() { return capacidadTotal; }
    public void setCapacidadTotal(Integer capacidadTotal) { this.capacidadTotal = capacidadTotal; }

    public Double getCostoHora() { return costoHora; }
    public void setCostoHora(Double costoHora) { this.costoHora = costoHora; }

    public Double getCostoMinuto() { return costoMinuto; }
    public void setCostoMinuto(Double costoMinuto) { this.costoMinuto = costoMinuto; }
}
