package com.hipermarket.persoane;

public class Admin {
    private String username;
    private String parola;

    public Admin(String username, String parola) {
        this.username = username;
        this.parola = parola;
    }

    public String getUsername() {
        return username;
    }

    public String getParola() {
        return parola;
    }

    @Override
    public String toString() {
        return username + ";" + parola + "\n";

    }

    @Override
    public boolean equals(Object o) {
            if (! (o instanceof Admin)) {
                return false;
            }

            Admin a = (Admin) o;

            return username.equals(((Admin) o).username) && parola.equals(((Admin) o).parola);

    }

}
