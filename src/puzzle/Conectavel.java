package puzzle;

/**
 *
 * @author Ruan
 */
public interface Conectavel {
    
    /**
     * Define se é possível se conectar diretamente com a peça por cima
     * 
     * @param conectavel a peça que será a outra ponta da conexão
     * @return boolean
     */
    public boolean conectaCima(Conectavel conectavel);
    
    /**
     * Define se é possível se conectar diretamente com a peça pela direita
     * 
     * @param conectavel a peça que será a outra ponta da conexão
     * @return boolean
     */
    public boolean conectaDireita(Conectavel conectavel);
     
    /**
     * Define se é possível se conectar diretamente com a peça por baixo
     * 
     * @param conectavel a peça que será a outra ponta da conexão
     * @return boolean
     */
    public boolean conectaBaixo(Conectavel conectavel);
    
    /**
     * Define se é possível se conectar diretamente com a peça pela esquerda
     * 
     * @param conectavel a peça que será a outra ponta da conexão
     * @return boolean
     */
    public boolean conectaEsquerda(Conectavel conectavel);
    
    /**
     * Define se a peça pode se conectar por cima
     * 
     * @return boolean
     */
    public boolean isConectaCima();
    
    /**
     * Define se a peça pode se conectar pela direita
     * 
     * @return boolean
     */
    public boolean isConectaDireita();
    
    /**
     * Define se a peça pode se conectar por baixo
     * 
     * @return boolean
     */
    public boolean isConectaBaixo();

    /**
     * Define se a peça pode se conectar pela esquerda
     * 
     * @return boolean
     */    
    public boolean isConectaEsquerda();
    
}