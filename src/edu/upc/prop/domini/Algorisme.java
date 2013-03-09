package edu.upc.prop.domini;

import java.util.ArrayList;
import java.util.List;

public class Algorisme {

    private double margeError;
    private Alfabet alfabet;
    private TST arbre;
    private int q;
    private List<String> mostra;
    private int longMin;
    private int longMax;

    /**
     * Crea un algorisme
     *
     * @param margeError El marge d'error per executar l'algorisme
     * @param q El numero minim de sequencies en que un patro trobat ha
     * d'aparèixer perque es consideri vàlid
     */
    public Algorisme(double margeError, int q) {
        this.alfabet = Alfabet.getInstance();
        this.margeError = margeError;
        this.q = q;
        longMin = 0;
        longMax =  Integer.MAX_VALUE / 1000;
    }

    /**
     * Executa l'algorisme que a partir d'una mostra i uns patrons donats,
     * comprova si aquests patrons estan a la mostra
     *
     * @param mostra La mostra on busca
     * @param patrons Els patrons a buscar
     * @return Un llistat de tots els resultats trobats, o una llista buida si
     * no ha trobat cap
     */
    public List<Patro> executarAlgorisme(List<String> mostra, List<String> patrons, boolean paralel,boolean insertions) throws InterruptedException, StackOverflowError {
        List<Patro> r = new ArrayList<Patro>();
        this.mostra = mostra;
        if(q <=0)q=Math.max(mostra.size()/2,1);
        if (!mostra.isEmpty()) {
            arbre = crearSuffixTree();
            if (paralel) {
                r = buscarPatronsConegutsParalel(patrons,insertions);
            } else {
                r = buscarPatrons(patrons,insertions);
            }
        }
        return r;
    }

    /**
     * Executa l'algorisme que a partir d'una mostra donada troba nous patrons
     *
     * @param mostra La mostra on busca per trobar nous patrons
     * @return Una llista amb els nous patrons trobats a partir de la mostra o
     * un llista buida si no hi ha cap
     */
    public List<Patro> executarAlgorisme(List<String> mostra, boolean paralel,boolean insertions) throws InterruptedException, StackOverflowError {
        List<Patro> r = new ArrayList<Patro>();
        if (!mostra.isEmpty()) {
            if(q <=0)q=Math.max(mostra.size()/2,1);
            this.mostra = mostra;
            arbre = crearSuffixTree();
            if (paralel) {
                r = buscarPatronsNousParalel(insertions);
            } else {
                for (char c : alfabet.getLlistaCaracters()) {
                    r.addAll(expand("" + c,insertions));
                }
            }
        }
        return r;
    }

    /**
     * Executa l'algorisme que a partir d'una mostra donada troba nous patrons
     *
     * @param patro El patro a buscar dins de la mostra
     * @return Un Patro omplert amb el matchos corresponents
     */
    public List<Patro> buscarPatrons(List<String> patrons,boolean insertions) {
        List<Patro> l = new ArrayList<Patro>();
        for (String s : patrons) {
            Patro p = new Patro(arbre.getNumSequencies());
            p.setSequencia(s);
            Match m = new Match(0, arbre.getNumSequencies());
            omplirMatch(p, ((NodeBits) arbre.getArrel()).getBros(1), 0, m,insertions);
            l.add(p);
        }
        return l;
    }

    /**
     * Retorna la longitud minima perque un patro trobat es consideri vàlid
     *
     * @return La longitud minima
     */
    public int getLongMin() {
        return longMin;
    }

    /**
     * Modifica la longitud minima perque un patro trobat es consideri vàlid
     *
     * @param longMin El nou valor
     */
    public void setLongMin(int longMin) {
        this.longMin = longMin;
    }

    /**
     * Retorna la longitud maxima perque un patro trobat es consideri vàlid
     *
     * @return La longitud maxima
     */
    public int getLongMax() {
        return longMax;
    }

    /**
     * Modifica la longitud maxima perque un patro trobat es consideri vàlid
     *
     * @param lonMax El nou valor
     */
    public void setLongMax(int longMax) {
        this.longMax = longMax;
    }

    /**
     * Retorna el marge d'error que s'esta utilitzant
     *
     * @return E marge d'error
     */
    public double getMargeError() {
        return margeError;
    }

    /**
     * Modifica el marge d'error
     *
     * @param margeError EL nou marge d'error
     */
    public void setMargeError(double margeError) {
        this.margeError = margeError;
    }

    /**
     * Retorna el numero minim de sequencies en que un patro trobat ha
     * d'aparèixer perque es consideri vàlid
     *
     * @return El valor minim
     */
    public int getQ() {
        return q;
    }

    /**
     * Modifica el numero minim de sequencies en que un patro trobat ha
     * d'aparèixer perque es consideri vàlid
     *
     * @param q El nou minim
     */
    public void setQ(int q) {
        this.q = q;
    }

    /**
     * Donat un String p i un caracter c concatena el caracter a l'String i el
     * busca dins de la mostra, si es dona el cas, segeix ampliant el patro
     * recursivament
     *
     * @param p L'string a verificar
     * @param c caracter a afegir al patro
     *
     * @return La llista de patrons que ha trobat
     */
    public List<Patro> expand(String p,boolean insertions) {
        List<Patro> resultats = new ArrayList<Patro>();
        Patro patro = new Patro(arbre.getNumSequencies());
        patro.setSequencia(p);
        Match m = new Match(0, arbre.getNumSequencies());
        omplirMatch(patro, ((NodeBits) arbre.getArrel()).getBros(1), 0, m,insertions);
        if (patro.esValid(q) && patro.length() <= longMax) {
            if (patro.length() >= longMin) {
                resultats.add(patro);
            }
            if (patro.length() < longMax) {
                for (char nouc : alfabet.getLlistaCaracters()) {
                    resultats.addAll(expand(p + nouc,insertions));
                }
            }

        }

        return resultats;
    }

    private TST crearSuffixTree() throws StackOverflowError {
        int numSequencies = mostra.size();
        TST suffixTree = new TST(numSequencies);
        int indexSequencia = 0;
        for (String sequencia : mostra) {
            for (int i = 0; i < sequencia.length(); i++) {
                suffixTree.inserta(sequencia.substring(i) + alfabet.getFiSequencia(), indexSequencia);
            }
            indexSequencia++;
        }
        return suffixTree;
    }

    private void omplirMatch(Patro p, List<NodeBits> cn, int pos, Match semi, boolean insertions) {
        boolean b;
        int eMax = (int) Math.ceil(margeError * (p.length()));
        int error = semi.getError();
        if (pos == p.length()) {
            if (!semi.getSequencia().equals("")) {
                p.addMatch(semi);
            }
            if (error < eMax && insertions) {
                for (NodeBits np : cn) {
                    ArrayList<NodeBits> ns = new ArrayList<NodeBits>();
                    if (np.mig()) {
                        Match m = new Match(error + 1, arbre.getNumSequencies(), semi.getSequencia() + np.getValue());
                        m.setSeq(abab(semi.getSeq(), np.getBits(), semi.getSequencia().length()));
                        ns.add((NodeBits) np.getFillCentral());
                        ns.addAll(((NodeBits) np.getFillCentral()).getBros(1));
                        omplirMatch(p, ns, pos, m, insertions);
                    }
                }
            }
            return;
        }
        for (NodeBits np : cn) {
            ArrayList<NodeBits> ns = new ArrayList<NodeBits>();
            Match m = new Match(error, arbre.getNumSequencies(), semi.getSequencia() + np.getValue());
            m.setSeq(abab(semi.getSeq(), np.getBits(), semi.getSequencia().length()));
            b = true;
            if (p.getSequencia().charAt(pos) == np.getValue()) {
            } else if (error + 1 <= eMax && (np.getValue() != alfabet.getFiSequencia())) {
                m.setError(error + 1);
            } else {
                b = false;
            }
            if (b) {
                if (np.mig()) {
                    ns.add((NodeBits) np.getFillCentral());
                    ns.addAll(((NodeBits) np.getFillCentral()).getBros(1));
                }
                omplirMatch(p, ns, pos + 1, m, insertions);
                if (insertions) {
                    Match ins = new Match(error + 1, arbre.getNumSequencies(), m.getSequencia());
                    ins.setSeq(m.getSeq());
                    if (error + 1 <= eMax) {
                        omplirMatch(p, ns, pos, ins, insertions);
                    }
                }
            }
        }
        if (insertions) {
            Match del = new Match(error + 1, arbre.getNumSequencies(), semi.getSequencia());
            del.setSeq(semi.getSeq());
            if (error + 1 <= eMax) {
                omplirMatch(p, cn, pos + 1, del, insertions);
            }
        }
    }

    private ArrayList<Integer>[] abab(ArrayList<Integer>[] occDC, ArrayList<Integer>[] occDN, int dist) {
        ArrayList<Integer>[] resultado = new ArrayList[occDC.length];
        for (int i = 0; i < resultado.length; i++) {
            if (occDC[i] == null || occDN[i] == null) {
                resultado[i] = null;
            } else {
                if (occDC[i].isEmpty()) {
                    resultado[i] = occDN[i];
                } else {
                    resultado[i] = new ArrayList<Integer>();
                    for (Integer d : occDC[i]) {
                        if (occDN[i].contains(d - dist)) {
                            resultado[i].add(d);
                        }
                    }
                }
            }
        }
        return resultado;
    }

    private synchronized List<Patro> buscarPatronsNousParalel(boolean insertions) throws InterruptedException {
        List<Patro> resultat = new ArrayList<Patro>();
        List<String> llistaCaracters = new ArrayList<String>();
        int nProcs = Runtime.getRuntime().availableProcessors();
        for (char caracter : alfabet.getLlistaCaracters()) {
            if (nProcs > 1) {
                llistaCaracters.add("" + caracter);
            } else {
                resultat.addAll(expand("" + caracter,insertions));
            }
        }
        if (nProcs <= 1) {
            return resultat;
        }
        int tamBloc = llistaCaracters.size() / nProcs;
        if (nProcs <= llistaCaracters.size()) {
            ModulParalelitzacio moduls[] = new ModulParalelitzacio[nProcs - 1];
            for (int i = 1; i < nProcs; i++) {
                if (i == nProcs - 1) {
                    moduls[i - 1] = new ModulParalelitzacio(llistaCaracters.subList(i * tamBloc, llistaCaracters.size()), this, true,insertions);
                } else {
                    moduls[i - 1] = new ModulParalelitzacio(llistaCaracters.subList(i * tamBloc, (i + 1) * tamBloc), this, true,insertions);
                }
                new Thread(moduls[i - 1]).start();
            }
            for (String s : llistaCaracters.subList(0, tamBloc)) {
                resultat.addAll(expand(s,insertions));
            }
            for (int i = 0; i < moduls.length; i++) {
                resultat.addAll(moduls[i].getPatrons());
            }
        } else {
            List<String> poolCrides = new ArrayList<String>(llistaCaracters);
            while (poolCrides.size() < nProcs) {
                List<Patro> llistaPatronsPool = buscarPatronsConegutsParalel(poolCrides,insertions);
                poolCrides.clear();
                for (Patro elemPool : llistaPatronsPool) {
                    if (elemPool.esValid(q) && elemPool.length() <= longMax) {
                        if (elemPool.length() >= longMin) {
                            resultat.add(elemPool);
                        }
                        if (elemPool.length() < longMax) {
                            for (String caracter : llistaCaracters) {
                                poolCrides.add(elemPool.getSequencia() + caracter);
                            }
                        }

                    }

                }
            }
            tamBloc = poolCrides.size() / nProcs;
            ModulParalelitzacio moduls[] = new ModulParalelitzacio[nProcs - 1];
            for (int i = 1; i < nProcs; i++) {
                if (i == nProcs - 1) {
                    moduls[i - 1] = new ModulParalelitzacio(poolCrides.subList(i * tamBloc, llistaCaracters.size()), this, true,insertions);
                } else {
                    moduls[i - 1] = new ModulParalelitzacio(poolCrides.subList(i * tamBloc, (i + 1) * tamBloc), this, true,insertions);
                }
                new Thread(moduls[i - 1]).start();
            }
            for (String s : poolCrides.subList(0, tamBloc)) {
                resultat.addAll(expand(s,insertions));
            }
            for (int i = 0; i < moduls.length; i++) {
                resultat.addAll(moduls[i].getPatrons());
            }
        }
        return resultat;
    }

    private synchronized List<Patro> buscarPatronsConegutsParalel(List<String> patrons,boolean insertions) throws InterruptedException {
        List<Patro> resultat = new ArrayList<Patro>();
        int nprocs = Runtime.getRuntime().availableProcessors();
        if (nprocs <= 1) {
            return buscarPatrons(patrons,insertions);
        }
        int tamBloc = patrons.size() / nprocs;
        if (tamBloc == 0) {
            if (patrons.size() > 1) {
                ModulParalelitzacio moduls[] = new ModulParalelitzacio[patrons.size() - 1];
                for (int i = 1; i < patrons.size(); i++) {
                    moduls[i - 1] = new ModulParalelitzacio(patrons.subList(i, i + 1), this, false,insertions);
                    new Thread(moduls[i - 1]).start();
                }
                resultat.addAll(buscarPatrons(patrons.subList(0, 1),insertions));
                for (int i = 0; i < moduls.length; i++) {
                    resultat.addAll(moduls[i].getPatrons());
                }
            } else {
                return buscarPatrons(patrons,insertions);
            }
        } else {
            ModulParalelitzacio moduls[] = new ModulParalelitzacio[nprocs - 1];
            for (int i = 1; i < nprocs; i++) {
                if (i < nprocs - 1) {
                    moduls[i - 1] = new ModulParalelitzacio(patrons.subList(i * tamBloc, (i + 1) * tamBloc), this, false,insertions);
                } else {
                    moduls[i - 1] = new ModulParalelitzacio(patrons.subList(i * tamBloc, patrons.size()), this, false,insertions);
                }
                new Thread(moduls[i - 1]).start();
            }
            resultat.addAll(buscarPatrons(patrons.subList(0, tamBloc),insertions));
            for (int i = 0; i < moduls.length; i++) {
                resultat.addAll(moduls[i].getPatrons());
            }
        }
        return resultat;
    }
}
