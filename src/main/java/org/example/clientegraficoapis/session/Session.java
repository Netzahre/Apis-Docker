package org.example.clientegraficoapis.session;

import org.example.clientegraficoapis.model.User;

public class Session {
    private static User loggedUser;

    /**
     * Method to set the logged user
     * @param user the user to set
     */
    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    /**
     * Method to get the logged user
     * @return the logged user
     */
    public static User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Method to clear the session
     */
    public static void clear() {
        loggedUser = null;
    }
}


