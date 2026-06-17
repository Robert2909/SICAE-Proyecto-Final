package com.sicae.userservice.dto;

import java.time.LocalDateTime;

// Este DTO sirve para regresar información completa del usuario.
// No incluye password porque no se pide.
public class UsuariosResponseDTO {
    
    private Integer idUsuario;
    private Integer rol;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Integer tipoUsuario;
    private Integer programaEducativo;
    private String usuario;
    private String correo;
    private String telefono;
    private Boolean estatus;
    private String claveUsuario;
    private LocalDateTime tiempoCreacion;
    private LocalDateTime tiempoActualizacion;

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
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
    
    

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public Integer getRol() {
        return rol;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public Integer getProgramaEducativo() {
        return programaEducativo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public LocalDateTime getTiempoCreacion() {
        return tiempoCreacion;
    }

    public LocalDateTime getTiempoActualizacion() {
        return tiempoActualizacion;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }


    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setProgramaEducativo(Integer programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public void setTiempoCreacion(LocalDateTime tiempoCreacion) {
        this.tiempoCreacion = tiempoCreacion;
    }

    public void setTiempoActualizacion(LocalDateTime tiempoActualizacion) {
        this.tiempoActualizacion = tiempoActualizacion;
    }
    
    
    
}