package edu.upc.prop.domini;

import java.util.Set;

public class Node {

    protected char value;
    protected Node germaMenor;
    protected Node fillCentral;
    protected Node germaMajor;

    /**
     * Crea un nou node amb el valor especificat
     *
     * @param value El valor del node
     */
    public Node(char value) {
        this.value = value;
    }

    /**
     * Retorna el valor del node
     *
     * @return El valor del node
     */
    public char getValue() {
        return value;
    }

    /**
     * Modificar el valor del node
     *
     * @param value El nou valor del node
     */
    public void setValue(char value) {
        this.value = value;
    }

    /**
     * Inserta una sequencia
     *
     * @param sequencia La cadena de la sequencia
     * @param numSeq Numero de sequencies que hi ha en total
     * @return True si s'ha insertat, False en ca contrari
     */
    protected boolean inserta(String sequencia, int numSeq) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Inserta una sequencia
     *
     * @param sequencia La secuendia que es vol insertar
     * @param index Index de la sequencia en el conjunt
     * @return True si s'ha insertat, False en cas contrari
     */
    protected boolean inserta(Sequencia sequencia, int index) {
        throw new UnsupportedOperationException("Not supported.");
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
    protected Set<String> consulta(String pattern) {
        throw new UnsupportedOperationException("Not supported.");
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
    protected Set<Sequencia> consultaseq(String pattern) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Elimina una sequencia del node
     */
    protected void elimina() {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Comprova si el node te fill central
     *
     * @return Trus si el node te fill central, False en cas contrari
     */
    public boolean mig() {
        return fillCentral != null;
    }

    /**
     * Comprova si el node te germa menor
     *
     * @return Trus si el node te germa menor, False en cas contrari
     */
    public boolean men() {
        return germaMenor != null;
    }

    /**
     * Comprova si el node te germa major
     *
     * @return Trus si el node te germa major, False en cas contrari
     */
    public boolean maj() {
        return germaMajor != null;
    }

    /**
     * Retorna el fill central del node
     *
     * @return Fill central del node
     */
    public Node getFillCentral() {
        return fillCentral;
    }

    /**
     * Modifica el fill central del node
     *
     * @param fillCentral El nou fill central del node
     */
    public void setFillCentral(Node fillCentral) {
        this.fillCentral = fillCentral;
    }

    /**
     * Retorna el germa menor del node
     *
     * @return Germa menor del node
     */
    public Node getGermaMajor() {
        return germaMajor;
    }

    /**
     * Modifica el germa major del node
     *
     * @param fillCentral El nou germa major del node
     */
    public void setGermaMajor(Node germaMajor) {
        this.germaMajor = germaMajor;
    }

    /**
     * Retorna el germa menor del node
     *
     * @return Germa menor del node
     */
    public Node getGermaMenor() {
        return germaMenor;
    }

    /**
     * Modifica el germa menor del node
     *
     * @param fillCentral El nou germa menor del node
     */
    public void setGermaMenor(Node germaMenor) {
        this.germaMenor = germaMenor;
    }

    /**
     * Donada una cadena retorna l'objecte sequencia
     *
     * @param sequencia La cadena a consultar
     * @return L'objecte sequencia representat per la cadena especificada
     */
    protected Sequencia get(String sequencia) {
        throw new UnsupportedOperationException("Not supported");
    }

    /**
     * Elimina la sequencia especificada en el node especificat
     *
     * @param node El node on s'ha de eliminar la sequencia
     * @param sequencia La sequencia a eliminar
     * @return El node modificat, sense la sequencia    
     */
    protected NodeSimple elimina(NodeSimple node, String sequencia) {
        throw new UnsupportedOperationException("Not supported");
    }
}
