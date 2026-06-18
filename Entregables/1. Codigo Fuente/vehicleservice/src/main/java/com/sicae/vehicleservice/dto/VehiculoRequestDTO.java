package com.sicae.vehicleservice.dto;

public class VehiculoRequestDTO {
    
    //Este sirve para meter todos los datos que pedimos para realizar una peticion
    //No incluimos idvehiculo, clavevehiculo, estatus, marca, modelo, idmarca porque o ya los generamos o no se ocupan o ya tienen un valor por default
    
    private Integer idUsuario;
    private Integer idModelo;
    private String placa;
    private String color;
    private Integer anio;
    private String descripcion;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}