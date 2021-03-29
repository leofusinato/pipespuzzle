package estrutura;

import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;

/**
 * @author Leonardo Alex Fusinato
 */
public class PipePuzzle implements Estado {
    
    private String[] estadoInicial;
    private String[] estadoFinal;
    private JLabel[] icons;

    public PipePuzzle(String[] estadoInicial, String[] estadoFinal) {
        this.estadoInicial = estadoInicial;
        this.estadoFinal = estadoFinal;
    }

    @Override
    public String getDescricao() {
        return "";
    }

    @Override
    public boolean ehMeta() {
        for (int i = 0; i < estadoInicial.length; i++) {
            if(estadoInicial[i].compareTo(estadoFinal[i]) != 0) {
               return false; 
            }
        }
        return true;
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<Estado> sucessores() {
        List<Estado> suc = new LinkedList<Estado>();
        
        for (int i = 0; i < estadoInicial.length; i++) {
            if(estadoInicial[i].compareTo(estadoFinal[i]) != 0) {
                if(Character.getNumericValue(estadoInicial[i].charAt(1)) == 4) {
                   estadoInicial[i] = (estadoInicial[i].charAt(0) + "1").trim();
                } else {
                   estadoInicial[i] = (estadoInicial[i].charAt(0) + "" + (Character.getNumericValue(estadoInicial[i].charAt(1)) + 1) + "").trim();  
                }
            }
        }
        
        suc.add(new PipePuzzle(estadoInicial, estadoFinal));
        return suc;
    }
    
}
