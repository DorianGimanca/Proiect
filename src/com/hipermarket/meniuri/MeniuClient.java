package com.hipermarket.meniuri;

import com.hipermarket.persoane.Admin;
import com.hipermarket.persoane.Casier;
import com.hipermarket.produse.CategorieProdus;
import com.hipermarket.produse.Produs;
import com.hipermarket.produse.TipCantitate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MeniuClient implements Meniu {
    private ArrayList<Produs> cos;
    private double sumaTotala;

    MeniuClient(){
        cos = new ArrayList<>();
    }


    @Override
    public Meniu interpreteazaComanda(char c) {
        Meniu meniu = this;

        switch (c){
            case '1':
                scaneazaProdus();
                break;
            case '2':
                finalizarePlata();
                break;
            case '4':
                totalPlata();
                break;
            case '5':
                stergeProdus();
                break;
            case '6':
                anulareCumparaturi();
                break;
            case '8':
                verificareCasier();
                break;
            case '9':
                verificareAdmin();
                break;
            case '0':
                meniu = new MeniuPrincipal();
                break;
            default:
                System.out.println(this.getClass().getName() + " Optiune invalida");
        }
        return meniu;
    }

    @Override
    public void afisare() {
        System.out.println("com.hipermarket.meniuri.Meniu Client");
    }

    public void scaneazaProdus(){
        System.out.println("Clientul a scanat un produs");

        int produsId = 0;
        float produsCantitate = 0;

        File messages = new File("database/messages.txt");
        try{
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();


            String []elemente = line.split(";");
            produsId = Integer.parseInt(elemente[0]);
            produsCantitate = Float.parseFloat(elemente[1]);

            System.out.println("Id-ul meu este: " + produsId);
            System.out.println("Cantitatea mea este: " + produsCantitate);
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<Produs> produse = new ArrayList<>();

        File fisierProduse = new File("database/produse.txt");
        try{
            Scanner scanner = new Scanner(fisierProduse);

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String  []elemente = line.split(";");

                int id = Integer.parseInt(elemente[0]);
                String nume = elemente[1];
                float pret = Float.parseFloat(elemente[2]);
                float cantitate = Float.parseFloat(elemente[3]);
                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs produs = new Produs(id, nume, pret, cantitate, tipCantitate, categorieProdus);
                produse.add(produs);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        for(Produs produs : produse){
            if(produs.getId() == produsId){
                System.out.println("Am gasit un produs: " +produs.toString());
                produs.setCantitate(produsCantitate);
                cos.add(produs);

                try{
                    FileWriter scrie = new FileWriter("database/output.txt");
                    scrie.write(produs.toString());
                    scrie.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public void totalPlata(){
        System.out.println("Clientul a cerut totalul de plata");
        double suma=0;
        for (Produs p:cos) {
            suma+=p.getPret()*p.getCantitate();
        }
        try{
            FileWriter scrie=new FileWriter("database/output.txt");
            scrie.write(String.valueOf(suma));
            sumaTotala = suma;
            scrie.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void stergeProdus(){
        System.out.println("Clientul a cerut stergerea unui produs");
        File messages = new File("database/messages.txt");
        int id = 0;

        try{
            Scanner scanner = new Scanner(messages);
            String value = scanner.nextLine();
            id = Integer.parseInt(value);
        }catch (IOException e){

        }
        for(Iterator it = cos.iterator();it.hasNext();){
            Produs p = (Produs) it.next();
            if(p.getId() == id){
                it.remove();
                try{
                    FileWriter scrie = new FileWriter("database/output.txt");
                    scrie.write(String.valueOf(true));
                    scrie.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void anulareCumparaturi(){
        System.out.println("Clientul a cerut anularea cumparaturilor");
        cos.clear();
        try{
            File file = new File("database/output.txt");
            FileWriter write = new FileWriter(file);
            write.write(String.valueOf(true));
            write.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void finalizarePlata() {
        System.out.println("Clientul a cerut finalizare plata");

        File message = new File("database/messages.txt");
        double value = 0;

        try {
            Scanner scanner = new Scanner(message);
            String line = scanner.nextLine();
            value = Double.parseDouble(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
        sumaTotala -= value;

        if (sumaTotala <= 0) {

            try {
                File file = new File("database/vanzari.txt");
                FileWriter write = new FileWriter(file,true);
                write.append(String.valueOf(totalSuma()) + "\n");
                cos.clear();
                write.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void verificareCasier() {
        System.out.println("Cautare si verificare casier");
        ArrayList<Casier> casieri = new ArrayList<>();

        String user = null, parola = null;

        File casier = new File("database/casieri.txt");
        try {
            Scanner s = new Scanner(casier);

            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] elemente = line.split(";");

                user = elemente[0];
                parola = elemente[1];

                Casier casier1 = new Casier(user, parola);
                casieri.add(casier1);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File mesaj = new File("database/messages.txt");
        try {
            Scanner s = new Scanner(mesaj);
            String line = s.nextLine();
            String[] elemente = line.split(";");

            user = elemente[0];
            parola = elemente[1];

            Casier casier1 = new Casier(user, parola);
            String raspuns = "false";

            for (Casier c : casieri) {
                if (casier1.equals(c)) {
                    raspuns = "true";
                    break;
                }
            }
            FileWriter scrie = new FileWriter("database/output.txt");
            scrie.write(raspuns);
            scrie.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verificareAdmin(){
        System.out.println("Cautare si verificare admin");
        ArrayList<Admin> admin = new ArrayList<>();

        String user = null;
        String parola = null;

        File mesaj = new File("database/messages.txt");
        try{
            Scanner scanner = new Scanner(mesaj);
            String line = scanner.nextLine();
            String []elemente = line.split(";");

            user = elemente[0];
            parola = elemente[1];


        }catch (Exception e){
            e.printStackTrace();
        }

        File adminFile = new File("database/admin.txt");
        try{
            Scanner scanner = new Scanner(adminFile);

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String []elemente = line.split(";");

               String username = elemente[0];
               String password = elemente[1];

                Admin admin1 = new Admin(user,parola);
                admin.add(admin1);
                System.out.println(user);
                System.out.println(parola);

                if(username.equals(user) && password.equals(parola)) {
                    try{
                        FileWriter write = new FileWriter("database/output.txt");
                        write.write(String.valueOf(true));
                        write.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public float totalSuma(){
        float suma = 0;
        for(Produs p : cos){
            suma += p.getPret() * p.getCantitate();
        }
        return suma;
    }
}
