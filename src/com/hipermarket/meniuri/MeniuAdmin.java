package com.hipermarket.meniuri;

import com.hipermarket.persoane.Casier;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MeniuAdmin extends MeniuAngajat{

    @Override
    public void afisare() {
        System.out.println("com.hipermarket.meniuri.Meniu Admin");
    }

    @Override
    public Meniu interpreteazaComanda(char c) {
       Meniu menu = this;

       switch (c){
           case '1':
               adauga();
               break;
           case '2':
               sterge();
               break;
           case '3':
               listare();
               break;
           case '0':
               menu = new MeniuPrincipal();
               break;
       }
       return menu;

    }

    @Override
    public void comutareClient() {

    }

    @Override
    public void adauga() {
        System.out.println("Am intrat in adauga casier");
        Casier casier = null;

        File messages = new File("database/messages.txt");
        try {
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();


            String[] elemente = line.split(";");
            String user = elemente[0];
            String pass = elemente[1];
            casier = new Casier(user, pass);
            System.out.println("Casierul din message: " + casier.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ArrayList<Casier> casieri = new ArrayList<>();
        File casieriFile = new File("database/casieri.txt");
        try {
            Scanner scanner = new Scanner(casieriFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");
                String user = elemente[0];
                String pass = elemente[1];

                Casier c = new Casier(user, pass);
                casieri.add(c);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        boolean rezutat = true;
        for (Casier c: casieri) {
            System.out.println("Casier: " + c.toString());
            if (c.equals(casier)) {
                System.out.println("Casierul nu exista");
                rezutat = false;

            }
        }

        if(rezutat) {
            try {
                FileWriter writer = new FileWriter(casieriFile, true);
                writer.write(casier.toString());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Rezultat este: " + rezutat);

        try {
            File output = new File("database/output.txt");
            FileWriter writer = new FileWriter(output);
            writer.write(String.valueOf(rezutat));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listare() {
        File file = new File("database/casieri.txt");
        ArrayList<Casier> casiers = new ArrayList<>();
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String []elemente = line.split(";");

                String username = elemente[0];
                String parola = elemente[1];

                Casier casier = new Casier(username, parola);
                casiers.add(casier);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter("database/output.txt");
            for (Casier c : casiers) {
                writer.write(c.toString());

            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sterge() {
        System.out.println("Am intrat in adauga casier");
        Casier casier = null;

        File messages = new File("database/messages.txt");
        try {
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();


            String[] elemente = line.split(";");
            String user = elemente[0];
            String pass = elemente[1];
            casier = new Casier(user, pass);
            System.out.println("Casierul din message: " + casier.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ArrayList<Casier> casieri = new ArrayList<>();
        File casierFile = new File("database/casieri.txt");
        try {
            Scanner scanner = new Scanner(casierFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");
                String user = elemente[0];
                String pass = elemente[1];

                Casier c = new Casier(user, pass);
                casieri.add(c);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        boolean rezutat = false;
        for (Casier c: casieri) {
            System.out.println("Casier: " + c.toString());
            if (! c.equals(casier)) {
                System.out.println("Casierul nu exista");
                rezutat = true;

                try {
                    FileWriter writer = new FileWriter(casierFile, true);
                    writer.write(casier.toString());
                    writer.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Rezultat este: " + rezutat);

        try {
            File output = new File("database/output.txt");
            FileWriter writer = new FileWriter(output);
            writer.write(String.valueOf(rezutat));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verificareCasier(){

    }
}
