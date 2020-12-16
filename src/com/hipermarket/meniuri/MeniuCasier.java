package com.hipermarket.meniuri;

import com.hipermarket.produse.CategorieProdus;
import com.hipermarket.produse.Produs;
import com.hipermarket.produse.TipCantitate;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MeniuCasier extends MeniuAngajat {
    @Override
    public void afisare() {
        System.out.println("com.hipermarket.meniuri.Meniu Casier");
    }

    @Override
    public Meniu interpreteazaComanda(char c) {
        Meniu menu = this;

        switch (c){
            case '1':
                adauga();
                break;
            case '2':
                listare();
                break;
            case '3':
                sterge();
                break;
            case '9':
                comutareClient();
                break;
            case '0':
                menu = new MeniuPrincipal();
        }
        return menu;
    }

    @Override
    public void comutareClient() {

    }

    @Override
    public void adauga() {
        File messages = new File("database/messages.txt");
        Produs produs = null;
        try{
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();

            String []elemente = line.split(";");
            int id = Integer.parseInt(elemente[0])  ;
            String nume = String.valueOf(elemente[1]);
            float pret = Float.parseFloat(elemente[2]);
            float cantitate = Float.parseFloat(elemente[3]);
            TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
            CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

            produs = new Produs(id,nume,pret,cantitate,tipCantitate,categorieProdus);
        }catch (Exception e){
            e.printStackTrace();
        }

        File produsFile = new File("database/produse.txt");

        ArrayList<Produs> produse = new ArrayList<>();
        try{
            Scanner s = new Scanner(produsFile);
            while(s.hasNextLine()){
                String line = s.nextLine();
                String []elemente = line.split(";");
                int id = Integer.parseInt(elemente[0])  ;
                String nume = String.valueOf(elemente[1]);
                float pret = Float.parseFloat(elemente[2]);
                float cantitate = Float.parseFloat(elemente[3]);
                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs produs1 = new Produs(id,nume,pret,cantitate,tipCantitate,categorieProdus);
                produse.add(produs1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        boolean rezultat = true;
        for(Produs p : produse){
            if(p.equals(produs) || p.getId() == produs.getId()){
                rezultat = false;
                break;
            }
        }

        try {
            FileWriter writer = new FileWriter("database/output.txt");
            writer.write(String.valueOf(rezultat));
            writer.close();
            if(rezultat == true) {
                FileWriter produsW = new FileWriter("database/produse.txt", true);
                produsW.write(produs.toString());
                produsW.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void listare() {
        File produsFile = new File("database/produse.txt");
        ArrayList<Produs> produse = new ArrayList<>();
        try{
            Scanner s = new Scanner(produsFile);
            while(s.hasNextLine()){
                String line = s.nextLine();
                String []elemente = line.split(";");
                int id = Integer.parseInt(elemente[0])  ;
                String nume = String.valueOf(elemente[1]);
                float pret = Float.parseFloat(elemente[2]);
                float cantitate = Float.parseFloat(elemente[3]);
                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs produs1 = new Produs(id,nume,pret,cantitate,tipCantitate,categorieProdus);
                produse.add(produs1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        File messages = new File("database/messages.txt");
        CategorieProdus categorie = null;

        try{
            Scanner scanner = new Scanner(messages);
            categorie = CategorieProdus.valueOf(scanner.nextLine());

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter("database/output.txt");
            for (Produs p : produse) {
                if (p.getCategorie().equals(categorie)) {
                    writer.write(p.toString());

                }
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sterge() {
        File produsFile = new File("database/produse.txt");
        ArrayList<Produs> produse = new ArrayList<>();
        try {
            Scanner s = new Scanner(produsFile);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] elemente = line.split(";");
                int id = Integer.parseInt(elemente[0]);
                String nume = String.valueOf(elemente[1]);
                float pret = Float.parseFloat(elemente[2]);
                float cantitate = Float.parseFloat(elemente[3]);
                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs produs1 = new Produs(id, nume, pret, cantitate, tipCantitate, categorieProdus);
                produse.add(produs1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        try{
            File fisier=new File("database/messages.txt");
            Scanner scanner=new Scanner(fisier);
            int id= Integer.parseInt(scanner.nextLine());
            boolean rezultat=false;
            for(Iterator it = produse.iterator(); it.hasNext();){
                Produs p = (Produs)it.next();
                if(p.getId() == id){
                    it.remove();
                    rezultat=true;
                    break;
                }
            }

            File fisier1=new File("database/output.txt");
            FileWriter scrie=new FileWriter(fisier1);
            scrie.write(String.valueOf(rezultat));
            scrie.close();

            File produseFile=new File("database/produse.txt");
            FileWriter scrieProduse = new FileWriter(produseFile);
            for(Produs p : produse){
                scrieProduse.write(p.toString());
            }
            scrieProduse.close();
        }catch(Exception ex){
            ex.getStackTrace();
        }
    }

    public void verificareAdmin(){

    }
}
