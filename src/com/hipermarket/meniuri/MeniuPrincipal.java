package com.hipermarket.meniuri;

public class MeniuPrincipal implements Meniu {
    @Override
    public Meniu interpreteazaComanda(char c) {
        Meniu meniu = null;

        switch (c){
            case 'c':
                meniu = new MeniuClient();
                break;
            case 'a':
                meniu = new MeniuAdmin();
                break;
            case 'm':
                meniu = new MeniuCasier();
                break;
            default:
                System.out.println("com.hipermarket.meniuri.Meniu invalid");
        }
        return meniu;
    }

    @Override
    public void afisare() {
        System.out.println("com.hipermarket.meniuri.Meniu Principal");
    }
}
