package estrutura;

import java.util.LinkedList;
import java.util.List;
import puzzle.Peca;

/**
 * @author Leonardo Alex Fusinato
 */
public class PipePuzzle implements Estado {
    
    private Peca pecas[][];
    private boolean allPecasFixasFixadas;
    private boolean allPecasFixasFixadasConsequentes;
    
    public PipePuzzle(Peca[][] pecas) {
        this.pecas = pecas;
        this.setAllPecasFixasFixadas(false);
        this.setAllPecasFixasFixadasConsequentes(false);
    }

    public Peca[][] getPecas() {
        return pecas;
    }

    private boolean isAllPecasFixasFixadas() {
        return allPecasFixasFixadas;
    }

    private void setAllPecasFixasFixadas(boolean allPecasFixasFixadas) {
        this.allPecasFixasFixadas = allPecasFixasFixadas;
    }

    private boolean isAllPecasFixasFixadasConsequentes() {
        return allPecasFixasFixadasConsequentes;
    }

    private void setAllPecasFixasFixadasConsequentes(boolean allPecasFixasFixadasConsequentes) {
        this.allPecasFixasFixadasConsequentes = allPecasFixasFixadasConsequentes;
    }
    
    @Override
    public String getDescricao() {
        return "PipePuzzle";
    }

    @Override
    public boolean ehMeta() {
        boolean retorno = this.isAllPecasFixasFixadas() && this.isAllPecasFixasFixadasConsequentes();
        
        return retorno;
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<Estado> sucessores() {
        List<Estado> retorno = new LinkedList<Estado>();
        
        if (this.isAllPecasFixasFixadas()) {
            if (this.isAllPecasFixasFixadasConsequentes()) {
                
            }
            else {
                this.setAllPecasFixasFixadasConsequentes(this.verificaPecasFixasConsequentes());
            }
        }
        else {
            this.setAllPecasFixasFixadas(this.verificaPecasFixas());
        }
        
        PipePuzzle novoEstado = new PipePuzzle(this.getPecas());
        novoEstado.setAllPecasFixasFixadas(this.isAllPecasFixasFixadas());
        novoEstado.setAllPecasFixasFixadasConsequentes(this.isAllPecasFixasFixadasConsequentes());
        retorno.add(novoEstado);
        
        return retorno;
    }

    private boolean verificaPecasFixas() {
        for (int i = 0; i < pecas.length; i++) {
            for (int j = 0; j < pecas[i].length; j++) {
                Peca peca = pecas[i][j];
                if (!peca.isFixo()) {
                    if (this.isCanto(i, j) && peca.isL()) {
                        peca.setFixo(this.fixaPecaCanto(peca, i, j));
                        return false;
                    }
                    else if (this.isLateral(i, j) && (peca.isR() || peca.isT())) {
                        peca.setFixo(this.fixaPecaLateral(peca, i, j));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean verificaPecasFixasConsequentes() {
        for (int i = 0; i < pecas.length; i++) {
            for (int j = 0; j < pecas[i].length; j++) {
                Peca peca = pecas[i][j];
                if (!peca.isFixo()) {
                    if (this.isCanto(i, j) && peca.isF()) {
                        peca.setFixo(this.verificaPecaFixaConsequenteCanto(peca, i, j));
                        return false;
                    }
                    if (this.isLateral(i, j) && (peca.isF() || peca.isL())) {
                        peca.setFixo(this.verificaPecaFixaConsequenteLateral(peca, i, j));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean verificaPecaFixaConsequenteCanto(Peca peca, int i, int j) {
        if (this.isCantoSuperiorEsquerdo(i, j)) {
            return this.verificaPecaFixaConsequenteCantoSuperiorEsquerdo(peca, i, j);
        }
        else if (this.isCantoSuperiorDireito(i, j)) {
            return this.verificaPecaFixaConsequenteCantoSuperiorDireito(peca, i, j);
        }
        else if (this.isCantoInferiorEsquerdo(i, j)) {
            return this.verificaPecaFixaConsequenteCantoInferiorEsquerdo(peca, i, j);
        }
        else if (this.isCantoInferiorDireito(i, j)) {
            return this.verificaPecaFixaConsequenteCantoInferiorDireito(peca, i, j);
        }
        return true;
    }

    private boolean verificaPecaFixaConsequenteCantoSuperiorEsquerdo(Peca peca, int i, int j) {
        if (this.pecaDireita(i, j).isFixo() && !peca.isPosicaoViradaDireita()) {
            peca.gira();
            if (!peca.isPosicaoViradaDireita()) {
                return false;
            }
        }
        else if (this.pecaInferior(i, j).isFixo() && !peca.isPosicaoViradaBaixo()) {
            peca.gira();
            if (!peca.isPosicaoViradaBaixo()) {
                return false;
            }
        }
        return true;
    }

    private boolean verificaPecaFixaConsequenteCantoSuperiorDireito(Peca peca, int i, int j) {
        if (this.pecaInferior(i, j).isFixo() && !peca.isPosicaoViradaBaixo()) {
            peca.gira();
            if (!peca.isPosicaoViradaBaixo()) {
                return false;
            }
        }
        else if (this.pecaEsquerda(i, j).isFixo() && !peca.isPosicaoViradaEsquerda()) {
            peca.gira();
            if (!peca.isPosicaoViradaEsquerda()) {
                return false;
            }
        }
        return true;
    }

    private boolean verificaPecaFixaConsequenteCantoInferiorEsquerdo(Peca peca, int i, int j) {
        if (this.pecaDireita(i, j).isFixo() && !peca.isPosicaoViradaDireita()) {
            peca.gira();
            if (!peca.isPosicaoViradaDireita()) {
                return false;
            }
        }
        else if (this.pecaSuperior(i, j).isFixo() && !peca.isPosicaoViradaCima()) {
            peca.gira();
            if (!peca.isPosicaoViradaCima()) {
                return false;
            }
        }
        return true;
    }

    private boolean verificaPecaFixaConsequenteCantoInferiorDireito(Peca peca, int i, int j) {
        if (this.pecaSuperior(i, j).isFixo() && !peca.isPosicaoViradaCima()) {
            peca.gira();
            if (!peca.isPosicaoViradaCima()) {
                return false;
            }
        }
        else if (this.pecaEsquerda(i, j).isFixo() && !peca.isPosicaoViradaEsquerda()) {
            peca.gira();
            if (!peca.isPosicaoViradaEsquerda()) {
                return false;
            }
        }
        return true;
    }
    
    private boolean verificaPecaFixaConsequenteLateral(Peca peca, int i, int j) {
        if (peca.isF()) {
            return this.verificaPecaFixaConsequenteLateralF(peca, i, j);
        }
        else if (peca.isL()) {
            return this.verificaPecaFixaConsequenteLateralL(peca, i, j);
        }
        return true;
    }
    
    private boolean verificaPecaFixaConsequenteLateralF(Peca peca, int i, int j) {
        if (this.isLateralSuperior(i, j) || this.isLateralInferior(i, j)) {
            return this.verificaPecaFixaConsequenteLateralFSuperiorInferior(peca, i, j);
        }
        else if (this.isLateralDireita(i, j) || this.isLateralEsquerda(i, j)) {
            return this.verificaPecaFixaConsequenteLateralFDireitaEsquerda(peca, i, j);
        }
        return true;
    }
    
    private boolean verificaPecaFixaConsequenteLateralFSuperiorInferior(Peca peca, int i, int j) {
        Peca pecaEsquerda = this.pecaEsquerda(i, j);
        if (pecaEsquerda.isFixo() && pecaEsquerda.isConectaDireita() && !peca.isPosicaoViradaEsquerda()) {
            peca.gira();
            if (!peca.isPosicaoViradaEsquerda()) {
                return false;
            }
        }
        else {
            Peca pecaDireita = this.pecaDireita(i, j);
            if (pecaDireita.isFixo() && pecaDireita.isConectaEsquerda() && !peca.isPosicaoViradaEsquerda()) {
                peca.gira();
                if (!peca.isPosicaoViradaEsquerda()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean verificaPecaFixaConsequenteLateralFDireitaEsquerda(Peca peca, int i, int j) {
        Peca pecaBaixo = this.pecaInferior(i, j);
        if (pecaBaixo.isFixo() && pecaBaixo.isConectaCima() && !peca.isPosicaoViradaBaixo()) {
            peca.gira();
            if (!peca.isPosicaoViradaBaixo()) {
                return false;
            }
        }
        else {
            Peca pecaCima = this.pecaSuperior(i, j);
            if (pecaCima.isFixo() && pecaCima.isConectaBaixo() && !peca.isPosicaoViradaCima()) {
                peca.gira();
                if (!peca.isPosicaoViradaCima()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean verificaPecaFixaConsequenteLateralL(Peca peca, int i, int j) {
        if (this.isLateralSuperior(i, j)) {
            return this.verificaPecaFixaConsequenteLateralLSuperior(peca, i, j);
        }
        else if (this.isLateralDireita(i, j)) {
            return this.verificaPecaFixaConsequenteLateralLDireita(peca, i, j);
        }
        else if (this.isLateralInferior(i, j)) {
            return this.verificaPecaFixaConsequenteLateralLInferior(peca, i, j);
        }
        else if (this.isLateralEsquerda(i, j)) {
            return this.verificaPecaFixaConsequenteLateralLEsquerda(peca, i, j);
        }
        return true;
    }
    
    private boolean verificaPecaFixaConsequenteLateralLSuperior(Peca peca, int i, int j) {
        Peca pecaEsquerda = this.pecaEsquerda(i, j);
        if (pecaEsquerda.isFixo() && pecaEsquerda.isConectaDireita() && !peca.isPosicaoViradaBaixo()) {
            peca.gira();
            if (!peca.isPosicaoViradaBaixo()) {
                return false;
            }
        }
        else {
            Peca pecaDireita = this.pecaDireita(i, j);
            if (pecaDireita.isFixo() && pecaDireita.isConectaEsquerda() && !peca.isPosicaoViradaDireita()) {
                peca.gira();
                if (!peca.isPosicaoViradaDireita()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean verificaPecaFixaConsequenteLateralLDireita(Peca peca, int i, int j) {
        Peca pecaBaixo = this.pecaInferior(i, j);
        if (pecaBaixo.isFixo() && pecaBaixo.isConectaCima() && !peca.isPosicaoViradaBaixo()) {
            peca.gira();
            if (!peca.isPosicaoViradaBaixo()) {
                return false;
            }
        }
        else {
            Peca pecaCima = this.pecaSuperior(i, j);
            if (pecaCima.isFixo() && pecaCima.isConectaBaixo() && !peca.isPosicaoViradaEsquerda()) {
                peca.gira();
                if (!peca.isPosicaoViradaEsquerda()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean verificaPecaFixaConsequenteLateralLInferior(Peca peca, int i, int j) {
        Peca pecaDireita = this.pecaDireita(i, j);
        Peca pecaEsquerda = this.pecaEsquerda(i, j);
        if ((pecaDireita.isFixo() && pecaDireita.isConectaEsquerda()) && !peca.isPosicaoViradaCima()) {
            peca.gira();
            if (!peca.isPosicaoViradaCima()) {
                return false;
            }
        }
        else if (pecaEsquerda.isFixo() && pecaEsquerda.isConectaDireita() && !peca.isPosicaoViradaEsquerda()) {
            peca.gira();
            if (!peca.isPosicaoViradaEsquerda()) {
                return false;
            }
        }
        return true;
    }
    
    private boolean verificaPecaFixaConsequenteLateralLEsquerda(Peca peca, int i, int j) {
        Peca pecaCima = this.pecaSuperior(i, j);
        if (pecaCima.isFixo() && pecaCima.isConectaBaixo() && !peca.isPosicaoViradaCima()) {
            peca.gira();
            if (!peca.isPosicaoViradaCima()) {
                return false;
            }
        }
        else {
            Peca pecaBaixo = this.pecaInferior(i, j);
            if (pecaBaixo.isFixo() && pecaBaixo.isConectaCima()&& !peca.isPosicaoViradaDireita()) {
                peca.gira();
                if (!peca.isPosicaoViradaDireita()) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isCanto(int i, int j) {
        return this.isCantoSuperiorEsquerdo(i, j) || this.isCantoSuperiorDireito(i, j) || this.isCantoInferiorEsquerdo(i, j) || this.isCantoInferiorDireito(i, j);
    }

    private boolean isCantoSuperiorEsquerdo(int i, int j) {
        return i == 0 && j == 0;
    }

    private boolean isCantoSuperiorDireito(int i, int j) {
        return i == 0 && j == pecas[i].length- 1;
    }

    private boolean isCantoInferiorEsquerdo(int i, int j) {
        return i == pecas.length - 1 && j == 0;
    }

    private boolean isCantoInferiorDireito(int i, int j) {
        return i == pecas.length -1 && j == pecas[i].length -1;
    }

    private boolean isLateral(int i, int j) {
        return this.isLateralSuperior(i, j) || this.isLateralEsquerda(i, j) || this.isLateralDireita(i, j) || this.isLateralInferior(i, j);
    }

    private boolean isLateralSuperior(int i, int j) {
        return i == 0 && j == 1;
    }

    private boolean isLateralEsquerda(int i, int j) {
        return i == 1 && j == 0;
    }

    private boolean isLateralDireita(int i, int j) {
        return i == 1 && j == pecas[i].length -1;
    }

    private boolean isLateralInferior(int i, int j) {
        return i == pecas.length -1 && j == 1;
    }
    
    private boolean fixaPecaCanto(Peca peca, int i, int j) {
        if (this.isCantoSuperiorEsquerdo(i, j)) {
            if (!peca.isPosicaoViradaDireita()) {
                peca.gira();
                if (!peca.isPosicaoViradaDireita()) {
                    return false;
                }
            }
        }
        else if (this.isCantoSuperiorDireito(i, j)) {
            if (!peca.isPosicaoViradaBaixo()) {
                peca.gira();
                if (!peca.isPosicaoViradaBaixo()) {
                    return false;
                }
            }
        }
        else if (this.isCantoInferiorDireito(i, j)) {
            if (!peca.isPosicaoViradaEsquerda()) {
                peca.gira();
                if (!peca.isPosicaoViradaEsquerda()) {
                    return false;
                }
            }
        }
        else if (this.isCantoInferiorEsquerdo(i, j)) {
            if (!peca.isPosicaoViradaCima()) {
                peca.gira();
                if (!peca.isPosicaoViradaCima()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean fixaPecaLateral(Peca peca, int i, int j) {
        if (peca.isR()) {
            if (this.isLateralSuperior(i, j) || this.isLateralInferior(i, j)) {
                if (!(peca.isPosicaoViradaDireita() || peca.isPosicaoViradaEsquerda())) {
                    peca.gira();
                    if (!(peca.isPosicaoViradaDireita() || peca.isPosicaoViradaEsquerda())) {
                        return false;
                    }
                }
            }
            if (this.isLateralDireita(i, j) || this.isLateralEsquerda(i, j)) {
                if (!(peca.isPosicaoViradaCima()|| peca.isPosicaoViradaBaixo())) {
                    peca.gira();
                    if (!(peca.isPosicaoViradaCima()|| peca.isPosicaoViradaBaixo())) {
                        return false;
                    }
                }
            }
        }
        else if (peca.isT()) {
            if (this.isLateralSuperior(i, j)) {
                if (!peca.isPosicaoViradaBaixo()) {
                    peca.gira();
                    if (!peca.isPosicaoViradaBaixo()) {
                        return false;
                    }
                }
            }
            else if (this.isLateralDireita(i, j)) {
                if (!peca.isPosicaoViradaEsquerda()) {
                    peca.gira();
                    if (!peca.isPosicaoViradaEsquerda()) {
                        return false;
                    }
                }
            }
            else if (this.isLateralInferior(i, j)) {
                if (!peca.isPosicaoViradaCima()) {
                    peca.gira();
                    if (!peca.isPosicaoViradaCima()) {
                        return false;
                    }
                }
            }
            else if (this.isLateralEsquerda(i, j)) {
                if (!peca.isPosicaoViradaCima()) {
                    peca.gira();
                    if (!peca.isPosicaoViradaCima()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Peca pecaSuperior(int i, int j) {
        return pecas[i - 1][j];
    }

    private Peca pecaDireita(int i, int j) {
        return pecas[i][j + 1];
    }

    private Peca pecaInferior(int i, int j) {
        return pecas[i + 1][j];
    }

    private Peca pecaEsquerda(int i, int j) {
        return pecas[i][j - 1];
    }
    
    @Override
    public String toString() {
        String[][] retorno = new String[3][3];
        
        for (int i = 0; i < pecas.length; i++) {
            for (int j = 0; j < pecas[i].length; j++) {
                retorno[i][j] = pecas[i][j].toString();
            }
        }
        
        return "PipePuzzle {\n"
             + "    " + retorno[0][0] + ", " + retorno[0][1] + ", " + retorno[0][2] + "\n"
             + "    " + retorno[1][0] + ", " + retorno[1][1] + ", " + retorno[1][2] + "\n"
             + "    " + retorno[2][0] + ", " + retorno[2][1] + ", " + retorno[2][2] + "\n"
             + "}";
    }
    
}