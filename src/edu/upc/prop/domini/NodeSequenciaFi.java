package edu.upc.prop.domini;

public class NodeSequenciaFi extends NodeSimple {

    private Sequencia sequencia;

    /**
     * Crea un node amb la sequencia que s'especifica
     *
     * @param sequencia la sequencia que s'inserta en el node
     */
    public NodeSequenciaFi(Sequencia sequencia) {
        super('+');
        this.sequencia = sequencia;
    }
    /**
     * Elimina la sequencia del sistema.
     */
    @Override
    public void elimina() {
        this.sequencia = null;
    }

    /**
     * Retorna la sequencia que hi ha al node
     *
     * @return La sequencia del node
     */
    public Sequencia getObjecteSequencia() {
        return this.sequencia;
    }
}
