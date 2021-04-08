package puzzle;

import javax.swing.ImageIcon;

/**
 * Peca
 * @author Ruan
 */
public class Peca implements Conectavel {

    static public final int POSICAO_VIRADA_CIMA     = 0;
    static public final int POSICAO_VIRADA_DIREITA  = 1;
    static public final int POSICAO_VIRADA_BAIXO    = 2;
    static public final int POSICAO_VIRADA_ESQUERDA = 3;
    
    static public final char CANO_F = 'F';
    static public final char CANO_R = 'R';
    static public final char CANO_L = 'L';
    static public final char CANO_T = 'T';
    
    private boolean conectaCima;
    private boolean conectaDireita;
    private boolean conectaBaixo;
    private boolean conectaEsquerda;
    
    private final int quantidadeConexoes;
    
    private final boolean f;
    private final boolean l;
    private final boolean r;
    private final boolean t;
    
    private int posicao;
    
    private boolean fixo = false;

    public Peca(boolean conectaCima, boolean conectaDireita, boolean conectaBaixo, boolean conectaEsquerda) {
        this.conectaCima     = conectaCima;
        this.conectaDireita  = conectaDireita;
        this.conectaBaixo    = conectaBaixo;
        this.conectaEsquerda = conectaEsquerda;
        
        this.quantidadeConexoes = this.calculaQuantidadeConexoes();
        
        this.f = this.calculaF();
        this.l = this.calculaL();
        this.r = this.calculaR();
        this.t = this.calculaT();
        
        this.posicao = this.calculaPosicaoInicial();
    }

    @Override
    public boolean isConectaCima() {
        return this.conectaCima;
    }

    @Override
    public boolean isConectaDireita() {
        return this.conectaDireita;
    }

    @Override
    public boolean isConectaBaixo() {
        return this.conectaBaixo;
    }

    @Override
    public boolean isConectaEsquerda() {
        return this.conectaEsquerda;
    }

    public int getQuantidadeConexoes() {
        return this.quantidadeConexoes;
    }

    public boolean isF() {
        return this.f;
    }

    public boolean isL() {
        return this.l;
    }

    public boolean isR() {
        return this.r;
    }

    public boolean isT() {
        return this.t;
    }

    public int getPosicao() {
        return posicao;
    }

    public boolean isPosicaoViradaCima() {
        return this.getPosicao() == POSICAO_VIRADA_CIMA;
    }

    public boolean isPosicaoViradaDireita() {
        return this.getPosicao() == POSICAO_VIRADA_DIREITA;
    }

    public boolean isPosicaoViradaBaixo() {
        return this.getPosicao() == POSICAO_VIRADA_BAIXO;
    }

    public boolean isPosicaoViradaEsquerda() {
        return this.getPosicao() == POSICAO_VIRADA_ESQUERDA;
    }

    public boolean isFixo() {
        return fixo;
    }

    public void setFixo(boolean fixo) {
        this.fixo = fixo;
    }
    
    /**
     * Gira a peça no sentido horário.
     */
    public void gira() {
        this.gira(true);
    }
    
    /**
     * Gira a peça.
     * @param sentidoHorario Sentido que sera rotacionado. <code>true</code> para sentido horário, <code>false</code> para sentido anti-horário.
     */
    public void gira(boolean sentidoHorario) {
        if (sentidoHorario) {
            this.posicao = (this.getPosicao() + 1) % 4;
            
            boolean aux          = this.conectaCima;
            this.conectaCima     = this.conectaEsquerda;
            this.conectaEsquerda = this.conectaBaixo;
            this.conectaBaixo    = this.conectaDireita;
            this.conectaDireita  = aux;
        }
        else {
            this.posicao = (this.getPosicao() + 3) % 4;
            
            boolean aux          = this.conectaCima;
            this.conectaCima     = this.conectaDireita;
            this.conectaDireita  = this.conectaBaixo;
            this.conectaBaixo    = this.conectaEsquerda;
            this.conectaEsquerda = aux;
        }
    }
    
    /**
     * Retorna o tipo da peca
     * Ex: 'T', 'F'
     * 
     * @return char
     */
    public char getNomePeca() {
        char nome;
        
        if (this.isF()) {
            nome = CANO_F;
        }
        else if (this.isR()) {
            nome = CANO_R;
        }
        else if (this.isL()) {
            nome = CANO_L;
        }
        else {
            nome = CANO_T;
        }
        
        return nome;
    }
    
    /**
     * Retorna o nome completo da peca
     * Ex: "T1", "R0"
     * 
     * @return String
     */
    public String getNomeCompletoPeca() {
        return String.valueOf(this.getNomePeca()).concat(String.valueOf(this.getPosicao()));
    }
    
    /**
     * Retorna a instancia do icone da imagem, de acordo com o nome
     * 
     * @return ImageIcon
     */
    public ImageIcon getInstanceImage() {
        return new ImageIcon(getClass().getResource("/images/" + this.getNomeCompletoPeca() + ".png"));
    }

    @Override
    public boolean conectaCima(Conectavel conectavel) {
        return this.isConectaCima() && conectavel.isConectaBaixo();
    }

    @Override
    public boolean conectaDireita(Conectavel conectavel) {
        return this.isConectaDireita() && conectavel.isConectaEsquerda();
    }

    @Override
    public boolean conectaBaixo(Conectavel conectavel) {
        return this.isConectaBaixo() && conectavel.isConectaCima();
    }

    @Override
    public boolean conectaEsquerda(Conectavel conectavel) {
        return this.isConectaEsquerda() && conectavel.isConectaDireita();
    }
    
    /**
     * Calcula quantas conexões a peça faz
     * 
     * @return int
     */
    private int calculaQuantidadeConexoes() {
        int retorno = 0;
        if (this.isConectaCima()) {
            retorno++;
        }
        if (this.isConectaDireita()) {
            retorno++;
        }
        if (this.isConectaBaixo()) {
            retorno++;
        }
        if (this.isConectaEsquerda()) {
            retorno++;
        }
        return retorno;
    }
    
    /**
     * Calcula se a peça é uma peça "final". Peças finais tem somente uma conexão.
     * 
     * @return boolean
     */
    private boolean calculaF() {
        return this.getQuantidadeConexoes() == 1;
    }
    
    /**
     * Calcula se a peça é uma peça em formato de "L". Peças em "L" tem duas conexões e elas são adjacentes:
     * Cima & Direita;
     * Direita & Baixo;
     * Baixo & Esquerda; e
     * Esquerda & Cima.
     * 
     * @return boolean
     */
    private boolean calculaL() {
        return this.getQuantidadeConexoes() == 2
            && (this.isConectaCima()     && this.isConectaDireita()
             || this.isConectaDireita()  && this.isConectaBaixo()
             || this.isConectaBaixo()    && this.isConectaEsquerda()
             || this.isConectaEsquerda() && this.isConectaCima());
    }
    
    /**
     * Calcula se a peça é "reta". Peças "retas" tem duas conexões e elas são opostas:
     * Cima & Baixo; e
     * Esquerda & Direita.
     * 
     * @return boolean
     */
    private boolean calculaR() {
        return this.getQuantidadeConexoes() == 2
            && (this.isConectaCima()     && this.isConectaBaixo()
             || this.isConectaEsquerda() && this.isConectaDireita());
    }
    
    /**
     * Calcula se a peça é uma peça em "T". Peças em "T" tem três conexões.
     * 
     * @return boolean
     */
    private boolean calculaT() {
        return this.getQuantidadeConexoes() == 3;
    }
    
    /**
     * Calcula para qual lado está virada a peça
     * 
     * @return int
     */
    private int calculaPosicaoInicial() {
        int retorno = 0;
        if (this.isF()) {
            retorno = this.calculaPosicaoInicialF();
        }
        if (this.isL()) {
            retorno = this.calculaPosicaoInicialL();
        }
        if (this.isR()) {
            retorno = this.calculaPosicaoInicialR();
        }
        if (this.isT()) {
            retorno = this.calculaPosicaoInicialT();
        }
        return retorno;
    }
    
    /**
     * Calcula para qual lado está virada a peça se a peça for "final"
     * 
     * @return int
     */
    private int calculaPosicaoInicialF() {
        int retorno = 0;
        if (this.isConectaCima()) {
            retorno = POSICAO_VIRADA_CIMA;
        }
        if (this.isConectaDireita()) {
            retorno = POSICAO_VIRADA_DIREITA;
        }
        if (this.isConectaBaixo()) {
            retorno = POSICAO_VIRADA_BAIXO;
        }
        if (this.isConectaEsquerda()) {
            retorno = POSICAO_VIRADA_ESQUERDA;
        }
        return retorno;
    }
    
    /**
     * Calcula para qual lado está virada a peça se a peça for uma peça em "L"
     * 
     * @return int
     */
    private int calculaPosicaoInicialL() {
        int retorno = 0;
        if (this.isConectaCima() && this.isConectaDireita()) {
            retorno = POSICAO_VIRADA_CIMA;
        }
        if (this.isConectaDireita() && this.isConectaBaixo()) {
            retorno = POSICAO_VIRADA_DIREITA;
        }
        if (this.isConectaBaixo() && this.isConectaEsquerda()) {
            retorno = POSICAO_VIRADA_BAIXO;
        }
        if (this.isConectaEsquerda() && this.isConectaCima()) {
            retorno = POSICAO_VIRADA_ESQUERDA;
        }
        return retorno;
    }
    
    /**
     * Calcula para qual lado está virada a peça se for uma peça "reta"
     * 
     * @return int
     */
    private int calculaPosicaoInicialR() {
        int retorno = 0;
        if (this.isConectaCima()) {
            retorno = POSICAO_VIRADA_CIMA;
        }
        if (this.isConectaDireita()) {
            retorno = POSICAO_VIRADA_DIREITA;
        }
        return retorno;
    }
    
    /**
     * Calcula para qual lado está virada a peça se a peça for uma peça em "T"
     * 
     * @return int
     */
    private int calculaPosicaoInicialT() {
        int retorno = 0;
        if (!this.isConectaCima()) {
            retorno = POSICAO_VIRADA_BAIXO;
        }
        if (!this.isConectaDireita()) {
            retorno = POSICAO_VIRADA_ESQUERDA;
        }
        if (!this.isConectaBaixo()) {
            retorno = POSICAO_VIRADA_CIMA;
        }
        if (!this.isConectaEsquerda()) {
            retorno = POSICAO_VIRADA_DIREITA;
        }
        return retorno;
    }

    @Override
    public String toString() {
        return "Peca (" + this.getNomeCompletoPeca() + "){" + "conectaCima=" + conectaCima + ", conectaDireita=" + conectaDireita + ", conectaBaixo=" + conectaBaixo + ", conectaEsquerda=" + conectaEsquerda + ", quantidadeConexoes=" + quantidadeConexoes + ", f=" + f + ", l=" + l + ", r=" + r + ", t=" + t + ", posicao=" + posicao + ", fixo=" + fixo + '}';
    }
    
}