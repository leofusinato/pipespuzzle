package estrutura;

import java.util.LinkedList;
import java.util.List;
import puzzle.Peca;

/**
 * @author Leonardo Alex Fusinato
 */
public class PipePuzzle implements Estado {
    
    private Peca pecas[][];

    public PipePuzzle(Peca[][] pecas) {
        this.pecas = pecas;
        
       this.verificaPecasFixas();
    }

    private void verificaPecasFixas() {
        for (int i = 0; i < pecas.length; i++) {
            for (int j = 0; j < pecas[i].length; j++) {
                Peca peca = pecas[i][j];
                if (this.isCanto(i, j)) {
                    peca.setFixo(peca.isL());
                    this.fixaPecaCanto(peca, i, j);
                }
                if (this.isLateral(i, j)) {
                    peca.setFixo(peca.isR() || peca.isT());
                    this.fixaPecaLateral(peca, i, j);
                }
            }
        }
    }

    public Peca[][] getPecas() {
        return pecas;
    }
    
    @Override
    public String getDescricao() {
        return "PipePuzzle";
    }

    @Override
    public boolean ehMeta() {
        return false;
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<Estado> sucessores() {
        List<Estado> retorno = new LinkedList<Estado>();
        
        
        
        return retorno;
    }

    private boolean isCanto(int i, int j) {
        return this.isCantoSuperiorEsquerdo(i, j) || this.isCantoSuperiorDireito(i, j) || this.isCantoInferiorEsquerdo(i, j) || this.isCantoInferiorDireito(i, j);
    }

    private boolean isCantoSuperiorEsquerdo(int i, int j) {
        return i == 0 && j == 0;
    }

    private boolean isCantoSuperiorDireito(int i, int j) {
        return i == 0 && j == pecas[i].length;
    }

    private boolean isCantoInferiorEsquerdo(int i, int j) {
        return i == pecas.length && j == 0;
    }

    private boolean isCantoInferiorDireito(int i, int j) {
        return i == pecas.length && j == pecas[i].length;
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
        return i == 1 && j == pecas[i].length;
    }

    private boolean isLateralInferior(int i, int j) {
        return i == pecas.length && j == 1;
    }
    
    private void fixaPecaCanto(Peca peca, int i, int j) {
        if (this.isCantoSuperiorEsquerdo(i, j)) {
            while (!peca.isPosicaoViradaDireita()) {
                peca.gira();
            }
        }
        else if (this.isCantoSuperiorDireito(i, j)) {
            while (!peca.isPosicaoViradaBaixo()) {
                peca.gira();
            }
        }
        else if (this.isCantoSuperiorDireito(i, j)) {
            while (!peca.isPosicaoViradaEsquerda()) {
                peca.gira();
            }
        }
        else if (this.isCantoSuperiorEsquerdo(i, j)) {
            while (!peca.isPosicaoViradaCima()) {
                peca.gira();
            }
        }
    }
    
    private void fixaPecaLateral(Peca peca, int i, int j) {
        if (peca.isR()) {
            if (this.isLateralSuperior(i, j) || this.isLateralInferior(i, j)) {
                while (!(peca.isPosicaoViradaDireita() || peca.isPosicaoViradaEsquerda())) {
                    peca.gira();
                }
            }
            if (this.isLateralDireita(i, j) || this.isLateralEsquerda(i, j)) {
                while (!(peca.isPosicaoViradaCima()|| peca.isPosicaoViradaBaixo())) {
                    peca.gira();
                }
            }
        }
        else if (peca.isT()) {
            if (this.isLateralSuperior(i, j)) {
                while (!peca.isPosicaoViradaBaixo()) {
                    peca.gira();
                }
            }
            else if (this.isLateralDireita(i, j)) {
                while (!peca.isPosicaoViradaEsquerda()) {
                    peca.gira();
                }
            }
            else if (this.isLateralInferior(i, j)) {
                while (!peca.isPosicaoViradaCima()) {
                    peca.gira();
                }
            }
            else if (this.isLateralEsquerda(i, j)) {
                while (!peca.isPosicaoViradaCima()) {
                    peca.gira();
                }
            }
        }
    }
        
}
