package com.sicae.userservice.dto;

public class UsuariosRequestDTO {
    
    //Este sirve para meter todos los datos que pedimos para realizar una peticion
    //No incluimos idUsuario, claveUsuario, estatus ni los tiempos porque ya los generamos nosotros
    
    private Integer idRol;
    private Integer idTipoUsuario;
    private Integer idProgramaEducativo;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String telefono;
    private String username;
    private String password;

    public Integer getIdRol() {
        return idRol;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public Integer getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public void setIdProgramaEducativo(Integer idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}