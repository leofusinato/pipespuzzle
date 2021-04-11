package puzzle;

/**
 *
 * @author Ruan
 */
public interface Conectavel {
    
    /**
     * Define se e possivel se conectar diretamente com a peca por cima
     * 
     * @param conectavel a peca que sera a outra ponta da conexao
     * @return boolean
     */
    public boolean conectaCima(Conectavel conectavel);
    
    /**
     * Define se e possivel se conectar diretamente com a peca pela direita
     * 
     * @param conectavel a peca que sera a outra ponta da conexao
     * @return boolean
     */
    public boolean conectaDireita(Conectavel conectavel);
     
    /**
     * Define se e possivel se conectar diretamente com a peca por baixo
     * 
     * @param conectavel a peca que sera a outra ponta da conexao
     * @return boolean
     */
    public boolean conectaBaixo(Conectavel conectavel);
    
    /**
     * Define se e possivel se conectar diretamente com a peca pela esquerda
     * 
     * @param conectavel a peca que sera a outra ponta da conexao
     * @return boolean
     */
    public boolean conectaEsquerda(Conectavel conectavel);
    
    /**
     * Define se a peca pode se conectar por cima
     * 
     * @return boolean
     */
    public boolean isConectaCima();
    
    /**
     * Define se a peca pode se conectar pela direita
     * 
     * @return boolean
     */
    public boolean isConectaDireita();
    
    /**
     * Define se a peca pode se conectar por baixo
     * 
     * @return boolean
     */
    public boolean isConectaBaixo();

    /**
     * Define se a peca pode se conectar pela esquerda
     * 
     * @return boolean
     */    
    public boolean isConectaEsquerda();
    
}