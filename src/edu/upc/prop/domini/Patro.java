package edu.upc.prop.domini;

import java.util.ArrayList;

public class Patro {

    private String sequencia;
    private ArrayList<Match> llistaMatch;
    private boolean[] ocurrencies;

    /**
     * Crea un patro buit
     *
     * @param numSequencies El numero de sequencies on pot apareixer aquest
     * patro
     */
    public Patro(int numSequencies) {
        sequencia = "";
        this.llistaMatch = new ArrayList<Match>();
        this.ocurrencies = new boolean[numSequencies];
    }

    /**
     * Retorna la sequencia (cadena) d'aquets patro
     *
     * @return La sequencia del patro
     */
    public String getSequencia() {
        return sequencia;
    }

    /**
     * Modificar la sequencia del patro
     *
     * @param sequencia La nova sequencia
     */
    public void setSequencia(String sequencia) {
        this.sequencia = sequencia;
    }

    /**
     * Retorna si un patro es vàlid. Un patro es vàlid si apareix com a minim en
     * q sequencies diferents
     */
    /**
     * Retorna si un patro es vàlid, un patro es vàlid si apareix com a minim en
     * q sequencies diferents
     *
     * @param q El numero minim de sequencies on ha d'apareixer el patro
     * @return True si es vàlid, False altrament
     */
    public boolean esValid(int q) {
        int cont = 0;
        for (int i = 0; i < ocurrencies.length; i++) {
            if (ocurrencies[i]) {
                cont++;
            }
        }
        return cont >= q;
    }

    /**
     * Afegir un match al patro
     *
     * @param nouMatch El nou match que s'afegira al patro
     */
    public void addMatch(Match nouMatch) {
        for (Match matchPatro : llistaMatch) {
            if (matchPatro.getSequencia().equals(nouMatch.getSequencia()) && matchPatro.getError()==nouMatch.getError()) {
                for (int i = 0; i < matchPatro.getSeq().length; i++) {
                    ArrayList<Integer> l = nouMatch.getSeq()[i];
                    if (l != null) {
                        for (Integer a : matchPatro.getSeq()[i]) {
                            if (!l.contains(a)) {
                                l.add(a);
                            }
                        }
                    }
                }
                return;
            }
        }
        llistaMatch.add(nouMatch);
        ArrayList<Integer>[] b = nouMatch.getSeq();
        for (int i = 0; i < ocurrencies.length; i++) {
            if (b[i] != null && b[i].size() > 0) {
                setBit(i, true);
            }
        }
    }

    /**
     * Retorna un llistat de matchs que te el patro
     *
     * @return Una llista amb tots els matchs que te el patro o una llist buida
     * si no te cap
     */
    public ArrayList<Match> getLlistaMatch() {
        return llistaMatch;
    }

    /**
     * Modifica la llista de matchs del patro
     *
     * @param llistaMatch La nova llista que tindra el patro
     */
    public void setLlistaMatch(ArrayList<Match> llistaMatch) {
        this.llistaMatch = llistaMatch;
    }

    /**
     * Retorna el match que hi ha a la posicio especificada
     *
     * @param pos L'index del match a retornar
     * @return EL match de la posicio especificada
     */
    public Match getMatch(int pos) {
        return llistaMatch.get(pos);
    }

    /**
     * Afegeix un match a la posicio indicada
     *
     * @param pos L'index on s'afegira
     * @param m El match que s'afegeix
     */
    public void setMatch(int pos, Match m) {
        this.llistaMatch.set(pos, m);
    }

    /**
     * Retorna un vector que indica en quines sequencies que hi ha apareix
     * aquest patro
     *
     * @return Un vector de ocurrències
     */
    public boolean[] getOcurrencies() {
        return ocurrencies;
    }

    /**
     * Modifica el vector de ocurrències per un de nou
     *
     * @param ocurrencies El nou vector de ocurrències
     */
    public void setOcurrencies(boolean[] ocurrencies) {
        if (ocurrencies.length == this.ocurrencies.length) {
            this.ocurrencies = ocurrencies;
        }
    }

    /**
     * Retorna una posicio en concret del vector de ocurrències
     *
     * @param pos L'index que es vol consultar
     * @return Diu si en la sequencia indicada per pos apareix aquest patro
     */
    public boolean getBit(int pos) {
        return ocurrencies[pos];
    }

    /**
     * Modifica una posicio del vector de ocurrències
     *
     * @param pos La posicio a modificar
     * @param b El nou valor que tindra
     */
    public void setBit(int pos, boolean b) {
        if (pos >= 0 && pos < ocurrencies.length) {
            this.ocurrencies[pos] = b;
        }
    }

    /**
     * Retorna el tamany del patro
     *
     * @return El tamany del patro
     */
    public int length() {
        return sequencia.length();
    }

    @Override
    public String toString() {
        String occ = "{";
        for (boolean b : ocurrencies) {
            if (b) {
                occ += "1";
            } else {
                occ += "0";
            }
            occ += ",";
        }
        if (occ.charAt(occ.length() - 1) != '{') {
            occ = occ.substring(0, occ.length() - 1);
        }
        occ += "}";
        String matchos = "{";
        for (Match m : llistaMatch) {
            matchos += m.toString();
            matchos += ",";
        }
        if (matchos.charAt(matchos.length() - 1) != '{') {
            matchos = matchos.substring(0, matchos.length() - 1);
        }
        matchos += "}";
        return sequencia + ":" + occ + ":" + matchos;
    }
    /** Funcio per saber el nombre de sequencies en les que surt un patro determinat.
     * 
     * 
     * @return el nombre de sequencies en les que surt el patro
     */
    public int getNumSequencies() {
        int cont = 0;
        for (int i = 0; i < ocurrencies.length; i++) {
            if (ocurrencies[i]) {
                cont++;
            }
        }
        return cont;
    }
}
