package com.sicae.parkingservice.dto;

// atrapamos los datos que vienen del body de postman
public class ParkingRequestDTO {
    private Integer idUsuario;
    private String placa;

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
}