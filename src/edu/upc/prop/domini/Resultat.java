package edu.upc.prop.domini;

import java.util.*;

public class Resultat {

    private List<Patro> patterns;
    private List<Sequencia> mostra;
    private int q;
    private int longMin;
    private int longMax;
    private double margeError;

    /**
     * Crea un resultat buit
     */
    public Resultat(int q,int longMin,int longMax,double margeError) {
        patterns = new ArrayList<Patro>();
        mostra = new ArrayList<Sequencia>();
        this.q=q;
        this.longMin = longMin;
        this.longMax = longMax;
        this.margeError = margeError;
    }

    /**
     * Retorna la mostra que s'ha utilitzat per generar aquest resultat
     *
     * @return La mostra utilitzada
     */
    public List<Sequencia> getMostra() {
        return mostra;
    }

    /**
     * Modifica la mostra que s'utilitzara per generar aquest resultat
     *
     * @param mostra La nova mostra utilitzada
     */
    public void setMostra(List<Sequencia> mostra) {
        this.mostra = mostra;
    }

    /**
     * Retorna els patrons trobats de la mostra d'entrada que s'ha utilitzat
     *
     * @return Els patrns trobats
     */
    public List<Patro> getPatterns() {
        return patterns;
    }

    /**
     * Modifica els patrons trobats per la mostra que s'ha entrat
     *
     * @param patterns Els nous patrons trobats
     */
    public void setPatterns(List<Patro> patterns) {
        this.patterns = patterns;
    }

    /**
     * Retorna un patro trobat en concret
     *
     * @param i L'index del patro dins dels resultats
     * @return El patro d'aquest index
     */
    public Patro getPatro(int i) {
        return patterns.get(i);
    }

    /**
     * Afegeix un patro als resultats en la posicio especificada
     *
     * @param i L'index on insertar el patro
     * @param p El patro a insertar dins dels resultats
     */
    public void setPatro(int i, Patro p) {
        patterns.set(i, p);
    }

    /**
     * Afegeix un patro a als resultats
     *
     * @param p El patro a afegir
     */
    public void addPatro(Patro p) {
        patterns.add(p);
    }

    /**
     * Retorna una mostra en concret
     *
     * @param i L'index de la mostra
     * @return La mostra demanada
     */
    public Sequencia getSequencia(int i) {
        return mostra.get(i);
    }

    /**
     * Afegeix una sequencia a la mostra en la posicio especificada
     *
     * @param i
     * @param s
     */
    public void setSequencia(int i, Sequencia s) {
        mostra.set(i, s);
    }

    /**
     * Afegeix una sequencia a la mostra
     *
     * @param s la sequencia a afegir
     */
    public void addSequencia(Sequencia s) {
        if (s != null) {
            mostra.add(s);
        }
    }

    /**
     * Et torna el patrons potencials seguint el criteri. Si el criteri es 0 els
     * patrons potencials que retornara seran els que apareguin en com a minim
     * min sequencies, no obstant si el criteri es 1, els patrons potencials que
     * tornara seran cribats segons els criteri que nomes seran valids aquells
     * els quals apareguin com a minim min vegades en una sequencia
     *
     * @param criteri el criteri que vols seguir
     * @param minim es el minim que seguira el criteri
     * @return la llista de patrons potencials
     */
    public List<String> getPatronsPotencials(int criteri, int min) {
        TreeSet<String> resultat = new TreeSet<String>(new Comparator<String>() {

            @Override
            public int compare(String a, String b) {
                int numeroA = getNum(a);
                int numeroB = getNum(b);
                if (numeroA == numeroB) {
                    return a.compareTo(b);
                } else {
                    return numeroA - numeroB;
                }
            }
        });
        if (criteri == 0) {
            for (Patro patro : patterns) {
                if (patro.esValid(min)) {
                    resultat.add(patro.getSequencia() + ":" + patro.getNumSequencies());
                }
            }
        } else if (criteri == 1) {
            for (int k = 0; k < patterns.size(); k++) {
                Patro patro = patterns.get(k);
                int max = 0;
                String entradaMax = "";
                for (int j = 0; j < patro.getLlistaMatch().size(); j++) {
                    Match match = patro.getMatch(j);
                    for (int i = 0; i < match.getSeq().length; i++) {
                        List<Integer> llista = match.getSeq()[i];
                        if (llista != null && llista.size() >= min) {
                            if (max == 0) {
                                max = llista.size();
                                entradaMax = patro.getSequencia() + ":" + llista.size();
                                resultat.add(entradaMax);
                            } else if (max < llista.size()) {
                                max = llista.size();
                                resultat.remove(entradaMax);
                                entradaMax = patro.getSequencia() + ":" + llista.size();
                                resultat.add(entradaMax);
                            }
                        }
                    }
                }
            }
        }
        else{
            return new ArrayList<String>();
        }
        List<String> llistaResultat = new ArrayList<String>();
        Iterator<String> it = resultat.descendingIterator();
        while (it.hasNext()) {
            llistaResultat.add(it.next());
        }
        return llistaResultat;
    }

    /**
     * Funcio per consultar les estadistiques de un resultat. format de les
     * estadistiques: PATRO,PATRO on patro es: Patro:num de sequencies on
     * apareix:numero d'aparicions totals:nombre minim d'aparicions en una
     * sequencia:nombre mitj d'aparicions en sequencies: nombre maxim
     * d'aparicions en sequencies
     *
     * @return les estadistiques
     */
    public List<String> getEstadistiques() {
        List<String> resultat = new ArrayList<String>();
        for (Patro patro : patterns) {
            resultat.add(patro.getSequencia() + ":" + patro.getNumSequencies() +
                    ":"+patro.getLlistaMatch().size()+":" + 
                    getEstadistiques(patro.getLlistaMatch()));
        }
        return resultat;
    }

    @Override
    public String toString() {
        String input = "{";
        String output = "{";
        for (Sequencia sequencia : mostra) {
            input += sequencia.getId() + ",";
        }
        if (input.charAt(input.length() - 1) != '{') {
            input = input.substring(0, input.length() - 1);
        }
        input += '}';
        for (Patro patro : patterns) {
            output += patro.toString() + ",";
        }
        if (output.charAt(output.length() - 1) != '{') {
            output = output.substring(0, output.length() - 1);
        }
        output += '}';
        return q+":"+longMin+":"+longMax+":"+margeError+":"+input + ":" + output;
    }

    private int getNum(String string) {
        return Integer.parseInt(string.substring(string.indexOf(":") + 1));
    }
    /**
     * 
     * @return Els parametres de l'execució del algoritme el qual ha generat aquell resultat
     */
    public String getParametres(){
        return q+":"+margeError+":"+longMin+":"+longMax;
    }

    private String getEstadistiques(ArrayList<Match> llistaMatch) {
        int[] aparicions = new int[llistaMatch.get(0).getSeq().length];
        int error = 0;
        for(Match match:llistaMatch){
            error+=match.getError();
            for(int i=0;i<aparicions.length;i++){
                if(match.getSeq()[i] != null && !match.getSeq()[i].isEmpty()){
                    aparicions[i]+=match.getSeq()[i].size();
                }
            }
        }
        double errMitj = (double)error/llistaMatch.size();
        int sum = 0;
        int min =  Integer.MAX_VALUE / 1000;
        int max=0;
        for(int i=0;i<aparicions.length;i++){
            if(aparicions[i] > max){
                max = aparicions[i];
            }
            if(aparicions[i] < min){
                min = aparicions[i];
            } 
            sum += aparicions[i];
        }
        double mitja = (double)sum/(double)aparicions.length;
        return min+":"+mitja+":"+max+":"+errMitj;
    }
    /**
     * 
     * @param indexSequencia l'index de la seuquencia 
     * @return llista de patrons que apareguin en aquella sequencia
     */
    public List<Patro> getPatronsSequencia(int indexSequencia) {
        List<Patro> resultat = new ArrayList<Patro>();
        for(Patro patro:patterns){
            if(patro.getBit(indexSequencia))resultat.add(patro);
        }
        return resultat;
    }
}
