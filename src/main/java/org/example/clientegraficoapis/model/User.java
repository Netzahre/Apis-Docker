package org.example.clientegraficoapis.model;


public class User {
    private String username;

    private String password;

    private Boolean isAdmin = false;

    /**
     * No args constructor
     */
    public User(){}

    /**
     * Args constructor
     * @param user username
     * @param password password
     */
    public User(String user, String password){
        this.username = user;
        this.password = password;
        this.isAdmin = false;
    }

    /**
     * Gets the admin status of the user
     * @return true if the user is an admin, false otherwise
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets the admin status of the user
     * @param esAdmin true if the user is an admin, false otherwise
     */
    public void setIsAdmin(Boolean esAdmin) {
        this.isAdmin = esAdmin;
    }

    /**
     * Gets the password of the user
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user
     * @param password password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the username of the user
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user
     * @param usuario username of the user
     */
    public void setUsername(String usuario) {
        this.username = usuario;
    }
}
