package com.sicae.authservice.dto;

public class LoginRequestDTO {
    
    // Estos son los datos que nos envia el cliente
    private String username;
    private String password;

    // Y otra vez, los getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
