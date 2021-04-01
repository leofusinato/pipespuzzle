package puzzle;

/**
 * @author Ruan
 */
abstract public class FactoryPeca {

    static public Peca instanciaPeca(String nome) {
        Peca instancia = null;
        
        char tipo = nome.substring(0, 1).charAt(0);
        int  lado = Integer.valueOf(nome.substring(1, 2)) - 1;
        
        switch (tipo) {
            case Peca.CANO_F : {
                switch (lado) {
                    case Peca.POSICAO_VIRADA_CIMA: {
                        instancia = new Peca(true, false, false, false);
                    } break;
                    case Peca.POSICAO_VIRADA_DIREITA: {
                        instancia = new Peca(false, true, false, false);
                    } break;
                    case Peca.POSICAO_VIRADA_BAIXO: {
                        instancia = new Peca(false, false, true, false);
                    } break;
                    case Peca.POSICAO_VIRADA_ESQUERDA: {
                        instancia = new Peca(false, false, false, true);
                    } break;
                }
            } break;
            case Peca.CANO_R : {
                switch (lado) {
                    case Peca.POSICAO_VIRADA_CIMA: {
                        instancia = new Peca(true, false, true, false);
                    } break;
                    case Peca.POSICAO_VIRADA_DIREITA: {
                        instancia = new Peca(false, true, false, true);
                    } break;
                    case Peca.POSICAO_VIRADA_BAIXO: {
                        instancia = new Peca(true, false, true, false);
                    } break;
                    case Peca.POSICAO_VIRADA_ESQUERDA: {
                        instancia = new Peca(false, true, false, true);
                    } break;
                }
            } break;
            case Peca.CANO_L : {
                switch (lado) {
                    case Peca.POSICAO_VIRADA_CIMA: {
                        instancia = new Peca(true, true, false, false);
                    } break;
                    case Peca.POSICAO_VIRADA_DIREITA: {
                        instancia = new Peca(false, true, true, false);
                    } break;
                    case Peca.POSICAO_VIRADA_BAIXO: {
                        instancia = new Peca(false, false, true, true);
                    } break;
                    case Peca.POSICAO_VIRADA_ESQUERDA: {
                        instancia = new Peca(true, false, false, true);
                    } break;
                }
            } break;
            case Peca.CANO_T : {
                switch (lado) {
                    case Peca.POSICAO_VIRADA_CIMA: {
                        instancia = new Peca(true, true, false, true);
                    } break;
                    case Peca.POSICAO_VIRADA_DIREITA: {
                        instancia = new Peca(true, true, true, false);
                    } break;
                    case Peca.POSICAO_VIRADA_BAIXO: {
                        instancia = new Peca(false, true, true, true);
                    } break;
                    case Peca.POSICAO_VIRADA_ESQUERDA: {
                        instancia = new Peca(true, false, true, true);
                    } break;
                }
            } break;
        }
        
        return instancia;
    }
    
}