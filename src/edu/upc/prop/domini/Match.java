package edu.upc.prop.domini;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private String sequencia;
    private int error;
    private ArrayList<Integer>[] seq;

    /**
     * Crea un match amb un marge d'error, el numero de sequencies i una
     * sequencia
     *
     * @param error Marge d'error
     * @param numseq Numero de sequencies
     * @param string La sequencia on apareix
     */
    public Match(int error, int numseq, String string) {
        sequencia = string;
        this.error = error;
        this.seq = new ArrayList[numseq];
        for (int i = 0; i < numseq; i++) {
            seq[i] = new ArrayList<Integer>();
        }
    }

    /**
     * Crea un match amb un marge d'error, el numero de sequencies
     *
     * @param error Marge d'error
     * @param numseq Numero de sequencies
     */
    public Match(int error, int numseq) {
        sequencia = "";
        this.error = error;
        this.seq = new ArrayList[numseq];
        for (int i = 0; i < numseq; i++) {
            seq[i] = new ArrayList<Integer>();
        }
    }

    /**
     * Retorna la sequencia on apareix aquest match
     *
     * @return La sequencia on apareix
     */
    public String getSequencia() {
        return sequencia;
    }

    /**
     * Modificar la sequencia on apareix aquest match
     *
     * @param sequencia La nova sequencia on apareix
     */
    public void setSequencia(String sequencia) {
        this.sequencia = sequencia;
    }

    /**
     * Retorna una matriu de sequencies
     *
     * @return Matriu de sequencies
     */
    public ArrayList<Integer>[] getSeq() {
        return seq;
    }

    /**
     * Modifica la matriu
     *
     * @param Seq La nova matriu
     */
    public void setSeq(ArrayList<Integer>[] Seq) {
        this.seq = Seq;
    }

    /**
     *
     * Retorna el marge d'error d'aquest match
     *
     * @return El marge d'error
     */
    public int getError() {
        return error;
    }

    /**
     * Modifica el marge d'error per aquest match
     *
     * @param error El nou marge d'error
     */
    public void setError(int error) {
        this.error = error;
    }

    @Override
    public String toString() {
        String matriu = "[";
        for (List<Integer> l : seq) {
            matriu += "{";
            if (l != null) {
                for (Integer i : l) {
                    matriu += i + ";";
                }
            }
            if (matriu.charAt(matriu.length() - 1) == ';') {
                matriu = matriu.substring(0, matriu.length() - 1);
            }
            matriu += "}";
        }
        matriu += "]";
        return sequencia + ":" + error + ":" + matriu;
    }
}
