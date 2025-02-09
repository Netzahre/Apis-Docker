package org.example.api_clientes.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @Column(nullable = false, name = "usuario")
    private String username;

    @Column(nullable = false, name = "password")
    private byte[] password;

    @Column(nullable = false, name = "esAdmin")
    private Boolean isAdmin = false;

    public User(){}

    public User (String user, byte[] password){
        this.username = user;
        this.password = password;
        this.isAdmin = false;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean esAdmin) {
        this.isAdmin = esAdmin;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usuario) {
        this.username = usuario;
    }
}
