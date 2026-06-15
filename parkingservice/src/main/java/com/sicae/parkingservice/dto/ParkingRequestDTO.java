package com.sicae.parkingservice.dto;

// Este DTO es el molde exacto de los datos que nos van a mandar desde Postman en el Body
// para poder registrar la entrada de un vehículo.
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