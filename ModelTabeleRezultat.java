/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Igra;
import model.RezultatIgre;

/**
 *
 * @author munja
 */
public class ModelTabeleRezultat extends AbstractTableModel{

    private List<RezultatIgre> lista = new ArrayList<>();
    private String[] kolone = {"id","datumVreme", "kombinacija", "brPokusaja", "rezultatIgre"};

    public ModelTabeleRezultat(List<RezultatIgre> lista) {
        this.lista = lista;
    }
    
    
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RezultatIgre i = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return i.getId();
            case 1: return i.getDatumVreme();
            case 2: return i.getDobitnaKomb();
            case 3: return i.getBrojPokusaja();
            case 4: return i.getRezultat();
            default:
                return "NA";
        }
    }

    public List<RezultatIgre> getLista() {
        return lista;
    }

    int vratiID(int selektovaniRed) {
        return lista.get(selektovaniRed).getId();
    }

    

    
}
