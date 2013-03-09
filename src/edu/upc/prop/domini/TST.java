package edu.upc.prop.domini;

import java.util.Set;
import java.util.TreeMap;

public class TST {

    private int numSequencies;
    private TreeMap<Long, Sequencia> ids;
    private Alfabet alfabet = Alfabet.getInstance();
    private Node arrel;
    private int size;
    /**
     * Crea el TST buit
     */
    public TST() {
        arrel = new NodeSimple(alfabet.getFiSequencia());
        ids = new TreeMap<Long, Sequencia>();
        size = 0;
    }

    /**
     * Crea el TST buit indicant-l'hi quantes sequencies tindré
     *
     * @param numSequencies El numero de sequencies que tindra el TSTS
     */
    public TST(int numSequencies) {
        arrel = new NodeBits(alfabet.getFiSequencia(), numSequencies);
        this.numSequencies = numSequencies;
        size = 0;
    }

    /**
     * Retorna el numero de sequencies que hi ha l'abre
     *
     * @return El numero de sequencies que hi ha a l'abre
     */
    public int getNumSequencies() {
        return numSequencies;
    }

    /**
     * Retorna l'arrel de l'arbre
     *
     * @return L'arrel de l'arbre
     */
    public Node getArrel() {
        return arrel;
    }

    /**
     * Inserta un sufix a l'abre de la sequencia indicada pel parametre
     *
     * @param suffix EL sufix que s'insertará a l'arbre
     * @param indexSequencia EL numero de sequencia que pertany aquest suffix
     * @return True si s'ha insertat bé, False altrament
     */
    public boolean inserta(String suffix, int indexSequencia) throws StackOverflowError {
        if (arrel.inserta(suffix, indexSequencia)) {
            size++;
            return true;
        }
        return false;
    }

    /**
     * Inserta una sequencia a l'arbre
     *
     * @param sequencia La sequencia que es vol insertar a l'abre
     * @return True si s'ha insertat bé, False altrament
     */
    public boolean inserta(Sequencia sequencia) throws StackOverflowError {
        if (arrel.inserta(sequencia, 0)) {
            size++;
            ids.put(sequencia.getId(), sequencia);
            return true;
        }
        return false;
    }

    /**
     * Consulta a l'abre el patro introduit: el patro pot ser: {*, ., paraula}
     * Asterisk (*) retorna tots els elements de l'abre;Punt (.) retorna
     * qualssevol caracter pero nomes un;Paraula retorna els elements que son
     * iguals que la paraula. ex: A*AB*T.G*, AAAA, .., *
     *
     * @param patro la expressio regular a buscar
     * @return Llista amb el nom de la sequencia que compleix la condicio o una
     * llista buida si no hi ha cap sequencia que la compleixi
     */
    public Set<String> consulta(String patro) throws StackOverflowError {
        return arrel.consulta(patro.toUpperCase());
    }

    /**
     * Consulta a l'abre el patro introduit: el patro pot ser: {*, ., paraula}
     * Asterisk (*) retorna tots els elements de l'abre;Punt (.) retorna
     * qualssevol caracter pero nomes un;Paraula retorna els elements que son
     * iguals que la paraula. ex: A*AB*T.G*, AAAA, ..,
     *
     * @param patro la expressio regular a buscar
     * @return Llista amb la sequencia que compleix la condicio o una llista
     * buida si no hi ha cap sequencia que la compleixi
     */
    public Set<Sequencia> consultaseq(String patro) throws StackOverflowError {
        return arrel.consultaseq(patro.toUpperCase());
    }

    /**
     * Eliminar de l'abre la sequencia que es passa per parametre
     *
     * @param sequencia la sequencia que es vol eliminar de l'abre
     * @return True si s'ha pogut esborrar bé, False si la sequencia no existeix
     */
    public boolean elimina(Sequencia sequencia) throws StackOverflowError {
            if (consulta(sequencia.getSequencia()).isEmpty()) {
            return false;
        }
        arrel = arrel.elimina((NodeSimple) arrel, sequencia.getSequencia());
        ids.remove(sequencia.getId());
        size--;
        return true;
    }

    /**
     * Retorna una sequencia passat el seu nom per parametre
     *
     * @param sequencia El nom de la sequencia que es vol
     * @return La sequencia si el nom es troba dins l'abre, False altrament
     */
    public Sequencia get(String sequencia) {
        return arrel.get(sequencia);
    }

    /**
     * retorna una sequencia donat el seu identificador
     *
     * @param id L'identificador de la sequencia
     * @return La sequencia si existeix a l'abre, sino retorna buit
     */
    public Sequencia get(Long id) {
        return ids.get(id);
    }

    /**
     * Retorna el tamany de l'arbre
     *
     * @return El tamany de l'arbre
     */
    public int size() {
        return size;
    }
}
