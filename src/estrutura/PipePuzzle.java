package estrutura;

import java.util.LinkedList;
import java.util.List;
import puzzle.Peca;

/**
 * @author Leonardo Alex Fusinato
 */
public class PipePuzzle implements Estado {
    
    private Peca    pecas[][];
    private boolean allPecasFixasFixadas;
    private boolean allPecasFixasFixadasConsequentes;
    private boolean pecaMeioFixada;
    
    public PipePuzzle(Peca[][] pecas) {
        this.pecas = new Peca[][]{
            {pecas[0][0].clona(), pecas[0][1].clona(), pecas[0][2].clona()},
            {pecas[1][0].clona(), pecas[1][1].clona(), pecas[1][2].clona()},
            {pecas[2][0].clona(), pecas[2][1].clona(), pecas[2][2].clona()}
        };
        
        this.allPecasFixasFixadas             = false;
        this.allPecasFixasFixadasConsequentes = false;
        this.pecaMeioFixada                   = false;
    }
    
    public PipePuzzle(Peca[][] pecas, boolean allPecasFixasFixadas, boolean allPecasFixasFixadasConsequentes, boolean pecaMeioFixada) {
        this.pecas = new Peca[][]{
            {pecas[0][0].clona(), pecas[0][1].clona(), pecas[0][2].clona()},
            {pecas[1][0].clona(), pecas[1][1].clona(), pecas[1][2].clona()},
            {pecas[2][0].clona(), pecas[2][1].clona(), pecas[2][2].clona()}
        };
        
        this.allPecasFixasFixadas             = allPecasFixasFixadas;
        this.allPecasFixasFixadasConsequentes = allPecasFixasFixadasConsequentes;
        this.pecaMeioFixada                   = pecaMeioFixada;
    }

    public Peca[][] getPecas() {
        return pecas;
    }

    public boolean isAllPecasFixasFixadas() {
        return allPecasFixasFixadas;
    }

    public void setAllPecasFixasFixadas(boolean allPecasFixasFixadas) {
        this.allPecasFixasFixadas = allPecasFixasFixadas;
    }

    public boolean isAllPecasFixasFixadasConsequentes() {
        return allPecasFixasFixadasConsequentes;
    }

    public void setAllPecasFixasFixadasConsequentes(boolean allPecasFixasFixadasConsequentes) {
        this.allPecasFixasFixadasConsequentes = allPecasFixasFixadasConsequentes;
    }

    public boolean isPecaMeioFixada() {
        return pecaMeioFixada;
    }

    public void setPecaMeioFixada(boolean pecaMeioFixada) {
        this.pecaMeioFixada = pecaMeioFixada;
    }
    
    @Override
    public String getDescricao() {
        return "PipePuzzle";
    }

    @Override
    public boolean ehMeta() {
        return this.isAllPecasFixasFixadas() && this.isAllPecasFixasFixadasConsequentes() && this.isPecaMeioFixada();
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<Estado> sucessores() {
        List<Estado> retorno = new LinkedList<Estado>();
        
        if (!this.isAllPecasFixasFixadas()) {
            this.setAllPecasFixasFixadas(this.verificaPecasFixas());
        }
        if (this.isAllPecasFixasFixadas() && !this.isAllPecasFixasFixadasConsequentes()) {
            this.setAllPecasFixasFixadasConsequentes(this.verificaPecasFixasConsequentes());
        }
        if (this.isAllPecasFixasFixadas() && this.isAllPecasFixasFixadasConsequentes()) {
            this.setPecaMeioFixada(this.fixaPecaMeio());
        }
        
        PipePuzzle novoEstado = new PipePuzzle(this.getPecas(), this.isAllPecasFixasFixadas(), this.isAllPecasFixasFixadasConsequentes(), this.isPecaMeioFixada());

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
                    if (this.isCanto(i, j) && peca.isF() && this.verificaPecaFixavelConsequenteCanto(peca, i, j)) {
                        peca.setFixo(this.fixaPecaConsequenteCanto(peca, i, j));
                        return false;
                    }
                    if (this.isLateral(i, j) && (peca.isF() || peca.isL()) && this.verificaPecaFixavelConsequenteLateral(peca, i, j)) {
                        peca.setFixo(this.fixaPecaConsequenteLateral(peca, i, j));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean fixaPecaMeio() {
        Peca pecaMeio     = pecas[1][1];
        Peca pecaSuperior = this.pecaSuperior(1, 1);
        Peca pecaInferior = this.pecaInferior(1, 1);
        Peca pecaEsquerda = this.pecaEsquerda(1, 1);
        Peca pecaDireita  = this.pecaDireita(1, 1);
        
        while (!(pecaMeio.isConectaCima()     == pecaSuperior.isConectaBaixo()
              && pecaMeio.isConectaBaixo()    == pecaInferior.isConectaCima()
              && pecaMeio.isConectaEsquerda() == pecaEsquerda.isConectaDireita()
              && pecaMeio.isConectaDireita()  == pecaDireita.isConectaEsquerda())) {
            pecaMeio.gira();
        }
        
        return true;
    }
    
    private boolean verificaPecaFixavelConsequenteCanto(Peca peca, int i, int j) {
        return (this.isCantoSuperiorEsquerdo(i, j) && (this.pecaDireita(i, j).isFixo()  || this.pecaInferior(i, j).isFixo()))
            || (this.isCantoSuperiorDireito(i, j)  && (this.pecaEsquerda(i, j).isFixo() || this.pecaInferior(i, j).isFixo()))
            || (this.isCantoInferiorEsquerdo(i, j) && (this.pecaDireita(i, j).isFixo()  || this.pecaSuperior(i, j).isFixo()))
            || (this.isCantoInferiorDireito(i, j)  && (this.pecaEsquerda(i, j).isFixo() || this.pecaSuperior(i, j).isFixo()));
    }
    
    private boolean verificaPecaFixavelConsequenteLateral(Peca peca, int i, int j) {
        if (peca.isF()) {
            return ((this.isLateralSuperior(i, j) || this.isLateralInferior(i, j)) && this.pecaDireita(i, j).isFixo()  && this.pecaEsquerda(i, j).isFixo())
                || ((this.isLateralDireita(i, j)  || this.isLateralEsquerda(i, j)) && this.pecaSuperior(i, j).isFixo() && this.pecaInferior(i, j).isFixo());
        }
        else if (peca.isL()) {
            return ((this.isLateralSuperior(i, j) || this.isLateralInferior(i, j)) && (this.pecaDireita(i, j).isFixo()  || this.pecaEsquerda(i, j).isFixo()))
                || ((this.isLateralDireita(i, j)  || this.isLateralEsquerda(i, j)) && (this.pecaSuperior(i, j).isFixo() || this.pecaInferior(i, j).isFixo()));
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteCanto(Peca peca, int i, int j) {
        if (this.isCantoSuperiorEsquerdo(i, j)) {
            return this.fixaPecaConsequenteCantoSuperiorEsquerdo(peca, i, j);
        }
        else if (this.isCantoSuperiorDireito(i, j)) {
            return this.fixaPecaConsequenteCantoSuperiorDireito(peca, i, j);
        }
        else if (this.isCantoInferiorEsquerdo(i, j)) {
            return this.fixaPecaConsequenteCantoInferiorEsquerdo(peca, i, j);
        }
        else if (this.isCantoInferiorDireito(i, j)) {
            return this.fixaPecaConsequenteCantoInferiorDireito(peca, i, j);
        }
        return true;
    }

    private boolean fixaPecaConsequenteCantoSuperiorEsquerdo(Peca peca, int i, int j) {
        Peca pecaInferior = this.pecaInferior(i, j);
        Peca pecaDireita  = this.pecaDireita(i, j);
        if ((pecaDireita.isFixo() && pecaDireita.isConectaEsquerda() || pecaInferior.isFixo() && !pecaInferior.isConectaCima()) && !peca.isPosicaoViradaDireita()) {
            while (!peca.isPosicaoViradaDireita()) {
                peca.gira();
            }
            return true;
        }
        else if ((pecaInferior.isFixo() && pecaInferior.isConectaCima() || pecaDireita.isFixo() && !pecaDireita.isConectaEsquerda()) && !peca.isPosicaoViradaBaixo()) {
            while (!peca.isPosicaoViradaBaixo()) {
                peca.gira();
            }
            return true;
        }
        return true;
    }

    private boolean fixaPecaConsequenteCantoSuperiorDireito(Peca peca, int i, int j) {
        Peca pecaEsquerda = this.pecaEsquerda(i, j);
        Peca pecaInferior = this.pecaInferior(i, j);
        if ((pecaInferior.isFixo() && pecaInferior.isConectaCima() || pecaEsquerda.isFixo() && !pecaEsquerda.isConectaDireita()) && !peca.isPosicaoViradaBaixo()) {
            while (!peca.isPosicaoViradaBaixo()) {
                peca.gira();
            }
            return true;
        }
        else if ((pecaEsquerda.isFixo() && pecaEsquerda.isConectaDireita() || pecaInferior.isFixo() && !pecaInferior.isConectaCima()) && !peca.isPosicaoViradaEsquerda()) {
            while (!peca.isPosicaoViradaEsquerda()) {
                peca.gira();
            }
            return true;
        }
        return true;
    }

    private boolean fixaPecaConsequenteCantoInferiorEsquerdo(Peca peca, int i, int j) {
        Peca pecaSuperior = this.pecaSuperior(i, j);
        Peca pecaDireita  = this.pecaDireita(i, j);
        if ((pecaDireita.isFixo() && pecaDireita.isConectaEsquerda() || pecaSuperior.isFixo() && !pecaSuperior.isConectaBaixo()) && !peca.isPosicaoViradaDireita()) {
            while (!peca.isPosicaoViradaDireita()) {
                peca.gira();
            }
            return true;
        }
        else if ((pecaSuperior.isFixo() && pecaSuperior.isConectaBaixo() || pecaDireita.isFixo() && !pecaDireita.isConectaEsquerda()) && !peca.isPosicaoViradaCima()) {
            while (!peca.isPosicaoViradaCima()) {
                peca.gira();
            }
            return true;
        }
        return true;
    }

    private boolean fixaPecaConsequenteCantoInferiorDireito(Peca peca, int i, int j) {
        Peca pecaEsquerda = this.pecaEsquerda(i, j);
        Peca pecaSuperior = this.pecaSuperior(i, j);
        if ((pecaSuperior.isFixo() && pecaSuperior.isConectaBaixo() || pecaEsquerda.isFixo() && !pecaEsquerda.isConectaDireita()) && !peca.isPosicaoViradaCima()) {
            while (!peca.isPosicaoViradaCima()) {
                peca.gira();
            }
            return true;
        }
        else if ((pecaEsquerda.isFixo() && pecaEsquerda.isConectaDireita() || pecaSuperior.isFixo() && !pecaSuperior.isConectaBaixo()) && !peca.isPosicaoViradaEsquerda()) {
            while (!peca.isPosicaoViradaEsquerda()) {
                peca.gira();
            }
            return true;
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateral(Peca peca, int i, int j) {
        if (peca.isF()) {
            return this.fixaPecaConsequenteLateralF(peca, i, j);
        }
        else if (peca.isL()) {
            return this.fixaPecaConsequenteLateralL(peca, i, j);
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralF(Peca peca, int i, int j) {
        if (this.isLateralSuperior(i, j)) {
            return this.fixaPecaConsequenteLateralFSuperior(peca, i, j);
        }
        else if (this.isLateralInferior(i, j)) {
            return this.fixaPecaConsequenteLateralFInferior(peca, i, j);
        }
        else if (this.isLateralDireita(i, j)) {
            return this.fixaPecaConsequenteLateralFDireita(peca, i, j);
        }
        else if (this.isLateralEsquerda(i, j)) {
            return this.fixaPecaConsequenteLateralFEsquerda(peca, i, j);            
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralFSuperior(Peca peca, int i, int j) {
        Peca pecaEsquerda = this.pecaEsquerda(i, j);
        Peca pecaDireita  = this.pecaDireita(i, j);
        if (pecaEsquerda.isFixo() && pecaDireita.isFixo()) {
            if (pecaEsquerda.isConectaDireita() && !peca.isPosicaoViradaEsquerda()) {
                while (!peca.isPosicaoViradaEsquerda()) {
                    peca.gira();
                }
                return true;
            }
            else if (pecaDireita.isConectaEsquerda() && !peca.isPosicaoViradaEsquerda()) {
                while (!peca.isPosicaoViradaEsquerda()) {
                    peca.gira();
                }
                return true;
            }
            else if (!peca.isPosicaoViradaBaixo()) {
                while (!peca.isPosicaoViradaBaixo()) {
                    peca.gira();
                }
                return true;
            }
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralFDireita(Peca peca, int i, int j) {
        Peca pecaBaixo = this.pecaInferior(i, j);
        Peca pecaCima  = this.pecaSuperior(i, j);
        if (pecaBaixo.isFixo() && pecaCima.isFixo()) {
            if (pecaBaixo.isConectaCima() && !peca.isPosicaoViradaBaixo()) {
                while (!peca.isPosicaoViradaBaixo()) {
                    peca.gira();
                }
                return true;
            }
            else if (pecaCima.isConectaBaixo() && !peca.isPosicaoViradaCima()) {
                while (!peca.isPosicaoViradaCima()) {
                    peca.gira();
                }
                return true;
            }
            else if (!peca.isPosicaoViradaEsquerda()) {
                while (!peca.isPosicaoViradaEsquerda()) {
                    peca.gira();
                }
                return true;
            }
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralFInferior(Peca peca, int i, int j) {
        Peca pecaEsquerda = this.pecaEsquerda(i, j);
        Peca pecaDireita  = this.pecaDireita(i, j);
        if (pecaEsquerda.isFixo() && pecaDireita.isFixo()) {
            if (pecaEsquerda.isConectaDireita() && !peca.isPosicaoViradaEsquerda()) {
                while (!peca.isPosicaoViradaEsquerda()) {
                    peca.gira();
                }
                return true;
            }
            else if (pecaDireita.isConectaEsquerda() && !peca.isPosicaoViradaEsquerda()) {
                while (!peca.isPosicaoViradaEsquerda()) {
                    peca.gira();
                }
                return true;
            }
            else if (!peca.isPosicaoViradaCima()) {
                while (!peca.isPosicaoViradaCima()) {
                    peca.gira();
                }
                return true;
            }
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralFEsquerda(Peca peca, int i, int j) {
        Peca pecaBaixo = this.pecaInferior(i, j);
        Peca pecaCima  = this.pecaSuperior(i, j);
        if (pecaBaixo.isFixo() && pecaCima.isFixo()) {
            if (pecaBaixo.isConectaCima() && !peca.isPosicaoViradaBaixo()) {
                while (!peca.isPosicaoViradaBaixo()) {
                    peca.gira();
                }
                return true;
            }
            else if (pecaCima.isConectaBaixo() && !peca.isPosicaoViradaCima()) {
                while (!peca.isPosicaoViradaCima()) {
                    peca.gira();
                }
                return true;
            }
            else if (!peca.isPosicaoViradaDireita()) {
                while (!peca.isPosicaoViradaDireita()) {
                    peca.gira();
                }
                return true;
            }
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralL(Peca peca, int i, int j) {
        if (this.isLateralSuperior(i, j)) {
            return this.fixaPecaConsequenteLateralLSuperior(peca, i, j);
        }
        else if (this.isLateralDireita(i, j)) {
            return this.fixaPecaConsequenteLateralLDireita(peca, i, j);
        }
        else if (this.isLateralInferior(i, j)) {
            return this.fixaPecaConsequenteLateralLInferior(peca, i, j);
        }
        else if (this.isLateralEsquerda(i, j)) {
            return this.fixaPecaConsequenteLateralLEsquerda(peca, i, j);
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralLSuperior(Peca peca, int i, int j) {
        Peca pecaEsquerda = this.pecaEsquerda(i, j);
        if (pecaEsquerda.isFixo()) {
            if (pecaEsquerda.isConectaDireita()) {
                if (!peca.isPosicaoViradaBaixo()) {
                    while (!peca.isPosicaoViradaBaixo()) {
                        peca.gira();
                    }
                    return true;
                }
            }
            else {
                if (!peca.isPosicaoViradaDireita()) {
                    while (!peca.isPosicaoViradaDireita()) {
                        peca.gira();
                    }
                    return true;
                }
            }
        }
        else {
            Peca pecaDireita = this.pecaDireita(i, j);
            if (pecaDireita.isFixo()) {
                if (pecaDireita.isConectaEsquerda()) {
                    if (!peca.isPosicaoViradaDireita()) {
                        while (!peca.isPosicaoViradaDireita()) {
                            peca.gira();
                        }
                        return true;
                    }
                }
                else if (!peca.isPosicaoViradaBaixo()) {
                    while (!peca.isPosicaoViradaBaixo()) {
                        peca.gira();
                    }
                    return true;
                }
            }
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralLDireita(Peca peca, int i, int j) {
        Peca pecaBaixo = this.pecaInferior(i, j);
        if (pecaBaixo.isFixo()) {
            if (pecaBaixo.isConectaCima()) {
                if (!peca.isPosicaoViradaBaixo()) {
                    while (!peca.isPosicaoViradaBaixo()) {
                        peca.gira();
                    }
                    return true;
                }
            }
            else {
                if (!peca.isPosicaoViradaEsquerda()) {
                    while (!peca.isPosicaoViradaEsquerda()) {
                        peca.gira();
                    }
                    return true;
                }
            }
        }
        else {
            Peca pecaCima = this.pecaSuperior(i, j);
            if (pecaCima.isFixo()) {
                if (pecaCima.isConectaBaixo()) {
                    if (!peca.isPosicaoViradaEsquerda()) {
                        while (!peca.isPosicaoViradaEsquerda()) {
                            peca.gira();
                        }
                        return true;
                    }
                }
                else if (!peca.isPosicaoViradaBaixo()) {
                    while (!peca.isPosicaoViradaBaixo()) {
                        peca.gira();
                    }
                    return true;
                }
            }
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralLInferior(Peca peca, int i, int j) {
        Peca pecaDireita = this.pecaDireita(i, j);
        if (pecaDireita.isFixo()) {
            if (pecaDireita.isConectaEsquerda()) {
                if (!peca.isPosicaoViradaCima()) {
                    while (!peca.isPosicaoViradaCima()) {
                        peca.gira();
                    }
                    return true;
                }
            }
            else {
                if (!peca.isPosicaoViradaEsquerda()) {
                    while (!peca.isPosicaoViradaEsquerda()) {
                        peca.gira();
                    }
                    return true;
                }
            }
        }
        else {
            Peca pecaEsquerda = this.pecaEsquerda(i, j);
            if (pecaEsquerda.isFixo()) {
                if (pecaEsquerda.isConectaDireita()) {
                    if (!peca.isPosicaoViradaEsquerda()) {
                        while (!peca.isPosicaoViradaEsquerda()) {
                            peca.gira();
                        }
                        return true;
                    }
                }
                else if (!peca.isPosicaoViradaCima()) {
                    while (!peca.isPosicaoViradaCima()) {
                        peca.gira();
                    }
                    return true;
                }
            }
        }
        return true;
    }
    
    private boolean fixaPecaConsequenteLateralLEsquerda(Peca peca, int i, int j) {
        Peca pecaCima = this.pecaSuperior(i, j);
        if (pecaCima.isFixo()) {
            if (pecaCima.isConectaBaixo()) {
                if (!peca.isPosicaoViradaCima()) {
                    while (!peca.isPosicaoViradaCima()) {
                        peca.gira();
                    }
                    return true;
                }
            }
            else {
                if (!peca.isPosicaoViradaDireita()) {
                    while (!peca.isPosicaoViradaDireita()) {
                        peca.gira();
                    }
                    return true;
                }
            }
        }
        else {
            Peca pecaBaixo = this.pecaInferior(i, j);
            if (pecaBaixo.isFixo()) {
                if (pecaBaixo.isConectaCima()) {
                    if (!peca.isPosicaoViradaDireita()) {
                        while (!peca.isPosicaoViradaDireita()) {
                            peca.gira();
                        }
                        return true;
                    }
                }
                else if (!peca.isPosicaoViradaCima()) {
                    while (!peca.isPosicaoViradaCima()) {
                        peca.gira();
                    }
                    return true;
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
                while (!peca.isPosicaoViradaDireita()) {
                    peca.gira();
                }
                return true;
            }
        }
        else if (this.isCantoSuperiorDireito(i, j)) {
            if (!peca.isPosicaoViradaBaixo()) {
                while (!peca.isPosicaoViradaBaixo()) {
                    peca.gira();
                }
                return true;
            }
        }
        else if (this.isCantoInferiorDireito(i, j)) {
            if (!peca.isPosicaoViradaEsquerda()) {
                while (!peca.isPosicaoViradaEsquerda()) {
                    peca.gira();
                }
                return true;
            }
        }
        else if (this.isCantoInferiorEsquerdo(i, j)) {
            if (!peca.isPosicaoViradaCima()) {
                while (!peca.isPosicaoViradaCima()) {
                    peca.gira();
                }
                return true;
            }
        }
        return true;
    }
    
    private boolean fixaPecaLateral(Peca peca, int i, int j) {
        if (peca.isR()) {
            if (this.isLateralSuperior(i, j) || this.isLateralInferior(i, j)) {
                if (!(peca.isPosicaoViradaDireita() || peca.isPosicaoViradaEsquerda())) {
                    while (!(peca.isPosicaoViradaDireita() || peca.isPosicaoViradaEsquerda())) {
                        peca.gira();
                    }
                    return true;
                }
            }
            if (this.isLateralDireita(i, j) || this.isLateralEsquerda(i, j)) {
                if (!(peca.isPosicaoViradaCima()|| peca.isPosicaoViradaBaixo())) {
                    while (!(peca.isPosicaoViradaCima()|| peca.isPosicaoViradaBaixo())) {
                        peca.gira();
                    }
                    return true;
                }
            }
        }
        else if (peca.isT()) {
            if (this.isLateralSuperior(i, j)) {
                if (!peca.isPosicaoViradaBaixo()) {
                    while (!peca.isPosicaoViradaBaixo()) {
                        peca.gira();
                    }
                    return true;
                }
            }
            else if (this.isLateralDireita(i, j)) {
                if (!peca.isPosicaoViradaEsquerda()) {
                    while (!peca.isPosicaoViradaEsquerda()) {
                        peca.gira();
                    }
                    return true;
                }
            }
            else if (this.isLateralInferior(i, j)) {
                if (!peca.isPosicaoViradaCima()) {
                    while (!peca.isPosicaoViradaCima()) {
                        peca.gira();
                    }
                    return true;
                }
            }
            else if (this.isLateralEsquerda(i, j)) {
                if (!peca.isPosicaoViradaDireita()) {
                    while (!peca.isPosicaoViradaDireita()) {
                        peca.gira();
                    }
                    return true;
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
        
        return "PipePuzzle {\n"
             + "    " + pecas[0][0].getNomeCompletoPeca() + ", " + pecas[0][1].getNomeCompletoPeca() + ", " + pecas[0][2].getNomeCompletoPeca() + "\n"
             + "    " + pecas[1][0].getNomeCompletoPeca() + ", " + pecas[1][1].getNomeCompletoPeca() + ", " + pecas[1][2].getNomeCompletoPeca() + "\n"
             + "    " + pecas[2][0].getNomeCompletoPeca() + ", " + pecas[2][1].getNomeCompletoPeca() + ", " + pecas[2][2].getNomeCompletoPeca() + "\n"
             + "}";
    }
    
}