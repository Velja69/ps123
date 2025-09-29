/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import baza.DBBroker;
import forme.ServerskaForma;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Igra;
import model.RezultatIgre;

/**
 *
 * @author munja
 */
public class Controller {
    private static Controller instance;
    
    private ServerskaForma sf;
    
    private ArrayList<Igra> igre;
    private RezultatIgre rezultat;
    
    private DBBroker dbb;
    

    public Controller() {
        
        igre = new ArrayList<>();
        rezultat = new RezultatIgre(-1, new Date(), null, -1, "izgubio", igre);
        dbb = new DBBroker();
    }

    public static Controller getInstance() {
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }

    public Igra pogadjaj(Igra igra) {

        if(igre.size() > 5){
            return igra;
        }
        
        String kombinacijaNaServeru = sf.vratiKombinaciju();
        String kombinacijaNaKlijentu = igra.getKombinacija();
        System.out.println(kombinacijaNaServeru); //2-1-0-5
        System.out.println(kombinacijaNaKlijentu); //2-5-3-4
        
        int brojacNaMestu = 0;
        int brojacVanMesta = 0;
        
        String[] niz1 = kombinacijaNaServeru.split("-"); // {"2", "1", "0", "5"}
        String[] niz2 = kombinacijaNaKlijentu.split("-"); // {"2", "5", "3", "4"}
        
        if(niz1[0].equals(niz2[0])){
            brojacNaMestu++;
        }else{
            if(kombinacijaNaKlijentu.contains(niz1[0])){
                brojacVanMesta++;
            }
        }
        
        if(niz1[1].equals(niz2[1])){
            brojacNaMestu++;
        }else{
            if(kombinacijaNaKlijentu.contains(niz1[1])){
                brojacVanMesta++;
            }
        }
        
        if(niz1[2].equals(niz2[2])){
            brojacNaMestu++;
        }else{
            if(kombinacijaNaKlijentu.contains(niz1[2])){
                brojacVanMesta++;
            }
        }
        
        if(niz1[3].equals(niz2[3])){
            brojacNaMestu++;
        }else{
            if(kombinacijaNaKlijentu.contains(niz1[3])){
                brojacVanMesta++;
            }
        }
        
        igra.setBrPogodjenihNaMestu(brojacNaMestu);
        igra.setBrPogodjenihNisuNaMestu(brojacVanMesta);
        
        if(brojacNaMestu == 4){
            rezultat.setRezultat("pobedio");
        }
        
        igre.add(igra);
        return igra;
    }

    public void setSf(ServerskaForma sf) {
        this.sf = sf;
    }

    public ArrayList<Igra> getIgre() {
        return igre;
    }

    public void setIgre(ArrayList<Igra> igre) {
        this.igre = igre;
    }

    public RezultatIgre getRezultat() {
        return rezultat;
    }

    public void setRezultat(RezultatIgre rezultat) {
        this.rezultat = rezultat;
    }

    public void sacuvajIgru() {
        rezultat.setDobitnaKomb(sf.vratiKombinaciju());
        rezultat.setBrojPokusaja(igre.size());
        dbb.sacuvajIgru(rezultat);
    }

    public ArrayList<RezultatIgre> vratiRezultate() {
        return dbb.vratiRezultate();
    }

    public ArrayList<Igra> vratiIgre(int id) {
        return dbb.vratiIgre(id);
    }
    
        
    
    
}
