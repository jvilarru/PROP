package edu.upc.prop.domini;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NodeBits extends Node {

    private ArrayList<Integer>[] bits;

    /**
     * Crea un nodeBits amb el valor especificat i el numero de sequencies total
     *
     * @param value El valor del node
     * @param numSequencies El numero total de sequencies que hi ha
     */
    public NodeBits(char value, int numSequencies) {
        super(value);
        this.bits = new ArrayList[numSequencies];
    }

    /**
     * Retorna la matriu de distancies
     *
     * @return La matriu de distancies
     */
    public ArrayList<Integer>[] getBits() {
        return bits;
    }

    /**
     * Modifica la matriu de distancies
     *
     * @param bits La nova matriu de distancies
     */
    public void setBits(ArrayList<Integer>[] bits) {
        this.bits = bits;
    }
    /**
     * 
     * @param sufix l'string a insertar
     * @param indexSequencia la sequencia a la qual pertany
     * @return si s'ha insertat correctament o no
     * @throws StackOverflowError si intentem insertar una sequencia massa llarga
     */
    @Override
    public boolean inserta(String sufix, int indexSequencia) throws StackOverflowError {
        if (value == sufix.charAt(0)) {
            if (bits[indexSequencia] == null) {
                bits[indexSequencia] = new ArrayList<Integer>();
            }
            bits[indexSequencia].add(sufix.length() - 1);
            Alfabet alfabet = Alfabet.getInstance();
            if (value != alfabet.getFiSequencia() && !mig()) {
                fillCentral = creaFills(sufix.substring(1), indexSequencia);
                return true;
            } else if (value != alfabet.getFiSequencia() && mig()) {
                return fillCentral.inserta(sufix.substring(1), indexSequencia);
            }
        } else if (sufix.charAt(0) < value) {
            if (germaMenor == null) { //crea germa Menor con valor sufix e hijos
                germaMenor = creaFills(sufix, indexSequencia);
                return true;
            } else {
                return germaMenor.inserta(sufix, indexSequencia);
            }
        } else {
            if (germaMajor == null) { // crea germa Major con valor sufix e hijos
                germaMajor = creaFills(sufix, indexSequencia);
                return true;
            } else {
                return germaMajor.inserta(sufix, indexSequencia);
            }
        }
        return false;
    }

    private NodeBits creaFills(String sufix, int indexSequencia) throws StackOverflowError {
        NodeBits node = new NodeBits(sufix.charAt(0), bits.length);
        node.bits[indexSequencia] = new ArrayList<Integer>();
        node.bits[indexSequencia].add(sufix.length() - 1);

        if (sufix.length() > 1) {
            node.fillCentral = creaFills(sufix.substring(1), indexSequencia);
        }

        return node;
    }

    /**
     * Retorna els germans del node
     *
     * @param nivells Quants Nivells de recursivitat
     * @return Una llista de gemans del node
     */
    protected List<NodeBits> getBros(int nivells) throws StackOverflowError {
        List<NodeBits> lista = new ArrayList<NodeBits>();
        if (nivells == 0) {
            return lista;
        }
        if (men()) {
            lista.add((NodeBits) germaMenor);
            lista.addAll(((NodeBits) germaMenor).getBros(nivells));
        }
        if (maj()) {
            lista.add((NodeBits) germaMajor);
            lista.addAll(((NodeBits) germaMajor).getBros(nivells));
        }
        if (mig() && --nivells > 0) {
            lista.add((NodeBits) fillCentral);
            lista.addAll(((NodeBits) fillCentral).getBros(nivells));
        }
        return lista;
    }
    /**
     * 
     * @param pattern el patro amb expressió regular
     * @return un set amb les sequencies que coincideixen
     * @throws StackOverflowError 
     */
    @Override
    public Set<String> consulta(String pattern) throws StackOverflowError {
        Set<String> llista = new HashSet<String>();
        llista.addAll(consultafs(pattern + Alfabet.getInstance().getFiSequencia(), ""));
        return llista;
    }

    private HashSet<String> consultafs(String pattern, String way) throws StackOverflowError {
        HashSet<String> llista = new HashSet<String>();
        Alfabet alfabet = Alfabet.getInstance();
        if (!pattern.isEmpty() || pattern.equals(""+alfabet.getFiSequencia())) {
            char c = pattern.charAt(0);
            String patronou, waynou;
            if (c == '*') {
                char d = pattern.charAt(1);
                if (d == value || (d == '.' && value != alfabet.getFiSequencia())) {
                    if (mig()) {
                        llista.addAll(((NodeBits) fillCentral).consultafs(pattern.substring(2), way + value));
                    } else if (!way.equals("")) {
                        llista.add(way);
                    }
                }
                if (mig()) {
                    llista.addAll(((NodeBits) fillCentral).consultafs(pattern, way + value));
                }
                if (men()) {
                    llista.addAll(((NodeBits) germaMenor).consultafs(pattern, way));
                }
                if (maj()) {
                    llista.addAll(((NodeBits) germaMajor).consultafs(pattern, way));
                }
            } else {
                if (value == c || c == '.') {
                    patronou = pattern.substring(1);
                    waynou = way + value;
                    if (mig()) {
                        llista.addAll(((NodeBits) fillCentral).consultafs(patronou, waynou));
                    } else {
                        if (patronou.equals("")) {
                            llista.add(way);
                        }
                    }
                }
                if (c < value || c == '.') {
                    if (men()) {
                        llista.addAll(((NodeBits) germaMenor).consultafs(pattern, way));
                    }
                }
                if (c > value || c == '.') {
                    if (maj()) {
                        llista.addAll(((NodeBits) germaMajor).consultafs(pattern, way));
                    }
                }
            }
        }
        return llista;
    }
}
