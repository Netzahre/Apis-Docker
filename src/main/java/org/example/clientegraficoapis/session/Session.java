package org.example.clientegraficoapis.session;

import org.example.clientegraficoapis.model.User;

public class Session {
    private static User loggedUser;

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void clear() {
        loggedUser = null;
    }

    public static String getName(){
        return loggedUser.getUsername();
    }
}


