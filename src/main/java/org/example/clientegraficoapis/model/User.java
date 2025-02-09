package org.example.clientegraficoapis.model;


public class User {
    private String username;

    private byte[] password;

    private Boolean isAdmin = false;

    public User(){}

    public User(String user, byte[] password){
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
