package com.hipermarket;

import com.hipermarket.meniuri.Meniu;
import com.hipermarket.meniuri.MeniuPrincipal;
import com.hipermarket.scriere.CitireFisier;
import com.hipermarket.scriere.ScriereFisier;

import java.io.File;
public class Main {

    public static void main(String[] args) {



        File fisier = new File("database/input.txt");
        CitireFisier citire = new CitireFisier(fisier);
        ScriereFisier scriere = new ScriereFisier(fisier);

        Meniu meniu = new MeniuPrincipal();
        while (true) {
            char c = citire.citireCaracter();

            if (c != 'x') {
                System.out.println("==================================");
                System.out.println("Am citit din fisier comanda: " + c);
                meniu.afisare();
                meniu = meniu.interpreteazaComanda(c);
                scriere.scrieCaracter('x');
                System.out.println("==================================");
            }
        }

    }
}
