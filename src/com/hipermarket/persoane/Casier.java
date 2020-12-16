package com.hipermarket.persoane;

public class Casier {
    private String username;
    private String parola;

    public Casier(String username, String parola) {
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
    public boolean equals(Object obj) {
        if(!(obj instanceof Casier)){
            return false;
        }

        Casier c = (Casier)obj;
        return username.equals(c.username) && parola.equals(c.parola);
    }


}
