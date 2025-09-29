/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import java.util.ArrayList;
import model.Igra;
import model.RezultatIgre;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author munja
 */
public class DBBroker {


    public void sacuvajIgru(RezultatIgre rezultat) {
        try {
            System.out.println(rezultat);
            int idRez = -1;
            
            String upit = "INSERT INTO rezultat (datumVreme, dobitnaKomb, brPokusaja, rezultatIgre) VALUES (?, ?, ?, ?)";
            
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);
            
            Timestamp sqlDatum = new Timestamp(rezultat.getDatumVreme().getTime());
            ps.setTimestamp(1, sqlDatum);
            ps.setString(2, rezultat.getDobitnaKomb());
            ps.setInt(3, rezultat.getBrojPokusaja());
            ps.setString(4, rezultat.getRezultat());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                idRez = rs.getInt(1);
            }
            System.out.println("ID je " + idRez);
            
            
            String upit1 = "INSERT INTO igra (kombinacija, naMestu, vanMesta, rezultat) VALUES (?, ?, ?, ?)";
            
            PreparedStatement ps1 = Konekcija.getInstance().getConnection().prepareStatement(upit1);
            for (Igra igra : rezultat.getIgra()) {
                ps1.setString(1, igra.getKombinacija());
                ps1.setInt(2, igra.getBrPogodjenihNaMestu());
                ps1.setInt(3, igra.getBrPogodjenihNisuNaMestu());
                ps1.setInt(4, idRez);
                ps1.addBatch();
            }
            ps1.executeBatch();
            
            Konekcija.getInstance().getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<RezultatIgre> vratiRezultate() {
        
        ArrayList<RezultatIgre> lista = new ArrayList<RezultatIgre>();
        
        String upit = "SELECT * FROM rezultat";
        
        try {
            Statement st = Konekcija.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(upit);
            while(rs.next()){
                int id = rs.getInt("id");
                Timestamp datumVreme = rs.getTimestamp("datumVreme");
                String dobitnaKomb = rs.getString("dobitnaKomb");
                int brojPokusaja = rs.getInt("brPokusaja");
                String rezultat = rs.getString("rezultatIgre");
                Date datumVremeDate = new Date(datumVreme.getTime());
                RezultatIgre rezultatIgre = new RezultatIgre(id, datumVremeDate, dobitnaKomb, brojPokusaja, rezultat, new ArrayList<Igra>());
                
                lista.add(rezultatIgre);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return lista;
    }

    public ArrayList<Igra> vratiIgre(int id) {
        
        ArrayList<Igra> lista = new ArrayList<Igra>();
        
        String upit = "SELECT * FROM igra WHERE rezultat = " + id;
        System.out.println(upit);
        
        try {
            Statement st = Konekcija.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(upit);
            while(rs.next()){

                String kombinacija = rs.getString("kombinacija");
                int brPogodjenihNaMestu = rs.getInt("naMestu");
                int brPogodjenihNisuNaMestu = rs.getInt("vanMesta");

                Igra igra = new Igra(-1, kombinacija, brPogodjenihNaMestu, brPogodjenihNisuNaMestu);
                lista.add(igra);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return lista;
    }

   
    
}
