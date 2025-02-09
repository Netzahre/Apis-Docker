package org.example.clientegraficoapis.model;


public class User {
    private String username;

    private String password;

    private Boolean isAdmin = false;

    public User(){}

    public User(String user, String password){
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usuario) {
        this.username = usuario;
    }
}
