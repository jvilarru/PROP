package edu.upc.prop.domini;

import edu.upc.prop.dades.ControladorDades;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ControladorAlgorisme {

    private static ControladorAlgorisme objecteControladorAlgorisme;
    private ControladorDades controladorDades;
    private static ControladorSequencies controladorSequencies;
    private static ControladorMalaltiesSimptomes controladorMalaltiesSimptomes;
    private TreeMap<String, Resultat> resultats;
    private List<String> nomsResultats;
    private Algorisme algorisme;

    private ControladorAlgorisme() {
        controladorDades = new ControladorDades();
        resultats = new TreeMap<String, Resultat>();
        algorisme = new Algorisme(0.01, -1);
        nomsResultats = new ArrayList<String>();
        getNomResultatsDisc();
    }

    /**
     * Crea una instancia del controlador
     *
     * @return L'objecte controlador
     */
    public static ControladorAlgorisme getInstance() {
        if (objecteControladorAlgorisme == null) {
            objecteControladorAlgorisme = new ControladorAlgorisme();
            controladorSequencies = ControladorSequencies.getInstance();
            controladorMalaltiesSimptomes = ControladorMalaltiesSimptomes.getInstance();
        }
        return objecteControladorAlgorisme;
    }

    /**
     * Retorna el numero minim de sequencies en que un patro trobat ha
     * d'aparèixer perque es consideri vàlid
     *
     * @return El valor minim
     */
    public int getQ() {
        return algorisme.getQ();
    }

    /**
     * Modifica el numero minim de sequencies en que un patro trobat ha
     * d'aparèixer perque es consideri vàlid
     *
     * @param q El nou minim
     */
    public void setQ(int q) {
        algorisme.setQ(q);
    }

    /**
     * Retorna la longitud minima perque un patro trobat es consideri vàlid
     *
     * @return La longitud minima
     */
    public int getLongMin() {
        return algorisme.getLongMin();
    }

    /**
     * Modifica la longitud minima perque un patro trobat es consideri vàlid
     *
     * @param longMin El nou valor
     */
    public void setLongMin(int longMin) {
        algorisme.setLongMin(longMin);
    }

    /**
     * Retorna la longitud maxima perque un patro trobat es consideri vàlid
     *
     * @return La longitud maxima
     */
    public int getLongMax() {
        return algorisme.getLongMax();
    }

    /**
     * Modifica la longitud maxima perque un patro trobat es consideri vàlid
     *
     * @param longMax El nou valor
     */
    public void setLongMax(int longMax) {
        algorisme.setLongMax(longMax);
    }

    /**
     * Modifica el marge d'error, El marge d'error ha ser un numero vàlid entre
     * 0 i 1
     *
     * @param margeError EL nou marge d'error
     */
    public void setMargeError(double margeError) {
        if (margeError < 1.0 && margeError >= 0.0) {
            algorisme.setMargeError(margeError);
        }
    }

    /**
     * Retorna el marge d'error que s'esta utilitzant
     *
     * @return E marge d'error
     */
    public double getMargeError() {
        return algorisme.getMargeError();
    }

    /**
     * Executa l'algorisme que a partir d'una mostra i uns patrons donats,
     * comprova si aquests patrons estan a la mostra
     *
     * @param mostra La mostra on busca
     * @param patrons Els patrons a buscar
     * @return True si s'ha executat bé, False altrament
     */
    public String executa(List<Sequencia> mostra, List<String> patrons, boolean paralel, boolean insertions) throws InterruptedException, StackOverflowError {
        if (algorisme.getQ() == -1) {
            algorisme.setQ(Math.min(mostra.size() / 2, 1));
        }
        List<String> stringsMostra = new ArrayList<String>();
        for (Sequencia sequencia : mostra) {
            stringsMostra.add(sequencia.getSequencia());
        }
        Resultat resultat = new Resultat(algorisme.getQ(), algorisme.getLongMin(), algorisme.getLongMax(), algorisme.getMargeError());
        resultat.setPatterns(algorisme.executarAlgorisme(stringsMostra, patrons, paralel, insertions));
        resultat.setMostra(mostra);
        String nom = add(resultat);
        return nom;
    }

    /**
     * Executa l'algorisme que a partir d'una mostra donada troba nous patrons
     *
     * @param mostra La mostra on busca per trobar nous patrons
     * @return True si s'ha executat bé, False altrament
     */
    public String executa(List<Sequencia> mostra, boolean paralel, boolean insertions) throws InterruptedException {
        if (algorisme.getQ() == -1) {
            algorisme.setQ(Math.min(mostra.size() / 2, 1));
        }
        List<String> stringsMostra = new ArrayList<String>();
        for (Sequencia sequencia : mostra) {
            stringsMostra.add(sequencia.getSequencia());
        }
        Resultat resultat = new Resultat(algorisme.getQ(), algorisme.getLongMin(), algorisme.getLongMax(), algorisme.getMargeError());
        resultat.setPatterns(algorisme.executarAlgorisme(stringsMostra, paralel, insertions));
        resultat.setMostra(mostra);
        String nom = add(resultat);
        return nom;
    }

    private String add(Resultat resultat) {
        String data = imprime(new Date());
        resultats.put(data, resultat);
        nomsResultats.add(0, data);
        return data;
    }

    /**
     * Llegeix el resultat identificat per data que hi ha guardat al fitxer del
     * disc i fa les associacions necessaries.
     *
     * @param data identificador del resultat que volem llegir de disc
     * @return True si s'ha llegit bé, False altrament
     * @throws FileNotFoundException Si no troba els fitxers
     * @throws IOException Si ocurre un problema de I/O
     */
    public boolean llegirResultat(String nom) throws FileNotFoundException, IOException {
        List<Long> ids = new ArrayList<Long>();
        if (!controladorDades.obrirLlegirResultat(nom)) {
            return false;
        }
        Resultat resultat = null;
        while (controladorDades.potLlegir()) {
            for (String string : controladorDades.llegir(ControladorDades.TAMANY_BLOCK)) {
                if (string.charAt(0) == '*') {
                    int q = Integer.parseInt(string.substring(1, string.indexOf(":")));
                    string = string.substring(string.indexOf(":") + 1);
                    double margeError = Double.parseDouble(string.substring(0, string.indexOf(":")));
                    string = string.substring(string.indexOf(":") + 1);
                    int longMin = Integer.parseInt(string.substring(0, string.indexOf(":")));
                    string = string.substring(string.indexOf(":") + 1);
                    int longMax = Integer.parseInt(string.substring(0, string.indexOf(",")));
                    string = string.substring(1);
                    resultat = new Resultat(q, longMin, longMax, margeError);
                    ids = getInput(string.substring(string.indexOf(":") + 1));
                } else {
                    resultat.addPatro(getPatro(string));
                }
            }
            for (Long l : ids) {
                resultat.addSequencia(controladorSequencies.get(l));
            }
            resultats.put(nom, resultat);
        }
        controladorDades.tancarLlegir();

        return true;
    }

    /**
     * Escriu els resultats a fitxer en el disc
     *
     * @param data la data del resultat que vols
     * @throws IOException Si ocurre un problema de I/O
     */
    public void escriureResultat(String nom, String path) throws IOException {
        Resultat resultatEscriure = resultats.get(nom);
        if (resultatEscriure != null) {
            List<String> llista = new ArrayList<String>();
            controladorDades.obrirEscriure(path + ".res", false);
            String nouNom = path.substring(path.lastIndexOf('/') + 1);
            nomsResultats.remove(nom);
            nomsResultats.add(nouNom);
            String resultat = "*" + resultatEscriure.getParametres();
            for (Sequencia seq : resultatEscriure.getMostra()) {
                resultat += seq.getId() + ",";
            }
            llista.add(resultat);
            for (Patro p : resultatEscriure.getPatterns()) {
                llista.add(p.toString());
                if (llista.size() >= ControladorDades.TAMANY_BLOCK) {
                    controladorDades.escriure(llista);
                    llista.clear();
                }
            }
            if (llista.size() > 0) {
                controladorDades.escriure(llista);
            }
            controladorDades.tancarEscriure();
            resultats.remove(nom);
            resultats.put(nouNom, resultatEscriure);
        }
    }

    /**
     * Funcio que retorna tots els noms dels fitxers que siguin resultats.
     *
     * @return llista de les dates de tots els fiters dins de resultats
     */
    private void getNomResultatsDisc() {
        nomsResultats = controladorDades.getNomResultats();
    }

    /**
     * Et torna el patrons potencials del resultat identificat per data seguint
     * el criteri. Si el criteri es 0 els patrons potencials que retornara seran
     * els que apareguin en com a minim min sequencies, no obstant si el criteri
     * es 1, els patrons potencials que tornara seran cribats segons els criteri
     * que nomes seran valids aquells els quals apareguin com a minim min
     * vegades en una sequencia.
     *
     * @param criteri el criteri que vols seguir.
     * @param minim es el minim que seguira el criteri.
     * @param nom la data que identifica el resultat del qual volem obtenir els
     * patrons potencials.
     * @return la llista de patrons potencials,en cas de que els resultat no
     * existeixi torna null.
     */
    public List<String> getPatronsPotencials(int criteri, int min, String nom) throws IOException {
        Resultat resultat = resultats.get(nom);
        if (resultat != null || nomsResultats.contains(nom)) {
            if (resultat == null) {
                llegirResultat(nom);
                resultat = resultats.get(nom);
            }
            return resultat.getPatronsPotencials(criteri, min);
        }
        return new ArrayList<String>();
    }

    /**
     * Analitza a partir de les dades del resultat "data" les malalties
     * potencials que poden tenir els pacients continguts dins de la mostra del
     * resultat. En cas de que el resultat identificat per data noes trobi en el
     * sistema es tornara un valor null
     *
     * @param nom la data del resultat a analitzar
     * @param paralel es un bolea que indica si el algoritme s'executa en
     * paralel
     * @return la matriu que t'indica si una malaltia potencialment pot sortir
     * en un pacient
     * @throws InterruptedException si hi ha hagut problemes en el paralelisme
     */
    public String[][] getMalaltiesPotencials(String nom, boolean paralel) throws InterruptedException, FileNotFoundException, IOException, StackOverflowError {
        Resultat resultat = resultats.get(nom);
        if (resultat != null || nomsResultats.contains(nom)) {
            if (resultat == null) {
                llegirResultat(nom);
                resultat = resultats.get(nom);
            }
            List<Patro> patrones = resultat.getPatterns();
            List<Malaltia> malalties = controladorMalaltiesSimptomes.getMalalties();
            String[][] sortida = new String[resultat.getMostra().size() + 1][malalties.size() + 1];
            List<String> sequenciesMalalties = new ArrayList<String>();
            if (malalties.isEmpty()) return sortida;
            int[] numeroSequencies = new int[malalties.size()];
            for (int i = 0; i < malalties.size(); i++) {
                Malaltia malaltia = malalties.get(i);
                for (Sequencia sequencia : malaltia.getSequenciesAssociades()) {
                    sequenciesMalalties.add(sequencia.getSequencia());
                    numeroSequencies[i]++;
                }
            }
            List<String> patrons = new ArrayList<String>();
            for (Patro patro : resultat.getPatterns()) {
                patrons.add(patro.getSequencia());
            }
            sortida[0][0] = "X";
            for (int i = 0; i < resultat.getMostra().size(); i++) {
                Sequencia seq = resultat.getMostra().get(i);
                Pacient pacient = seq.getPacient();
                if (pacient != null) {
                    sortida[i + 1][0] = pacient.getDni();
                } else {
                    sortida[i + 1][0] = "Sequencia sense pacient associat";
                }
            }
            for (int i = 0; i < malalties.size(); i++) {
                sortida[0][i + 1] = malalties.get(i).getNom();
            }
            List<Patro> algoritme = executaConclusions(patrons, sequenciesMalalties, paralel);
            int pos = 0;
            for (int i = 0; i < malalties.size(); i++) {
                for (int j = 0; j < numeroSequencies[i]; j++) {
                    Patro patro = algoritme.get(pos++);
                    for (int k = 0; k < patro.getOcurrencies().length; k++) {
                        boolean b = patro.getOcurrencies()[k];
                        if (b) {
                            for (int l = 0; l < patrones.get(k).getOcurrencies().length; l++) {
                                if (patrones.get(k).getOcurrencies()[l]) {
                                    sortida[l][i] = "true";
                                }
                            }
                        }
                    }
                }
            }
            return sortida;
        } else {
            return null;
        }
    }

    /**
     * Torna les estadistiques del resultat identificat per data. format de les
     * estadistiques: PATRO,PATRO on patro es: Patro: num de sequencies on
     * apareix:numero d'aparicions totals: nombre minim d'aparicions en una
     * sequencia: nombre mitj d'aparicions en sequencies: nombre maxim
     * d'aparicions en sequencies
     *
     * @param nom la data del resultat que volem saber les estadistiques
     * @return les estadistiques del resultat
     */
    public List<String> getEstadistiques(String nom) throws IOException {
        Resultat resultat = resultats.get(nom);
        if (resultat != null || nomsResultats.contains(nom)) {
            if (resultat == null) {
                llegirResultat(nom);
                resultat = resultats.get(nom);
            }
            return resultat.getEstadistiques();
        } else {
            return new ArrayList<String>();
        }
    }

    private String imprime(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
    }

    private Patro getPatro(String string) {
        int token = string.indexOf(":");
        String seq = string.substring(0, token);
        List<Boolean> l = new ArrayList<Boolean>();
        string = string.substring(token + 2);
        token = string.indexOf("}");
        StringTokenizer st = new StringTokenizer(string.substring(0, token), ",");
        while (st.hasMoreTokens()) {
            l.add(st.nextElement().equals("1"));
        }
        boolean[] array = new boolean[l.size()];
        for (int i = 0; i < l.size(); i++) {
            array[i] = l.get(i).booleanValue();
        }
        Patro patro = new Patro(l.size());
        patro.setSequencia(seq);
        patro.setOcurrencies(array);
        string = string.substring(token + 3, string.length() - 1);
        st = new StringTokenizer(string, ",");
        while (st.hasMoreTokens()) {
            patro.addMatch(getMatch(st.nextToken()));
        }
        return patro;
    }

    private Match getMatch(String string) {
        String sequencia = string.substring(0, string.indexOf(":"));
        string = string.substring(string.indexOf(":") + 1);
        int error = Integer.parseInt(string.substring(0, string.indexOf(":")));
        string = string.substring(string.indexOf("[") + 1, string.length() - 1);
        ArrayList<ArrayList<Integer>> matriu = new ArrayList<ArrayList<Integer>>();
        StringTokenizer st = new StringTokenizer(string, "{");
        int i = 0;
        while (st.hasMoreTokens()) {
            ArrayList<Integer> columna = new ArrayList<Integer>();
            String l = st.nextToken();
            StringTokenizer st2 = new StringTokenizer(l.substring(0, l.length() - 1), ";");
            while (st2.hasMoreTokens()) {
                columna.add(Integer.valueOf(st2.nextToken()));
            }
            matriu.add(columna);
        }
        ArrayList<Integer> l[] = new ArrayList[matriu.size()];
        for (ArrayList<Integer> lis : matriu) {
            l[i] = lis;
        }
        Match m = new Match(error, l.length, sequencia);
        m.setSeq(l);
        return m;
    }

    private List<Long> getInput(String substring) {
        List<Long> llista = new ArrayList<Long>();
        StringTokenizer st = new StringTokenizer(substring, ",");
        while (st.hasMoreTokens()) {
            llista.add(Long.valueOf(st.nextToken()));
        }
        return llista;
    }

    private List<Patro> executaConclusions(List<String> mostra, List<String> patrons, boolean paralel) throws InterruptedException, StackOverflowError {
        double margeError = algorisme.getMargeError();
        algorisme.setMargeError(0.0);
        List<Patro> resultat = algorisme.executarAlgorisme(mostra, patrons, paralel, false);
        algorisme.setMargeError(margeError);
        return resultat;
    }
    /** torna la llista de sequencies i mes coses de un resultat
     * 
     * @param nom nom del resultat del qual volem obtenir les coses
     * @return la llista de sequencies(id,String)
     * @throws FileNotFoundException si no troba el fitxer en questio
     * @throws IOException si reventa per culpa de HDD
     */
    public ArrayList<String>[] getLlistaSequencies(String nom) throws FileNotFoundException, IOException {
        ArrayList[] matResultat = new ArrayList[2];
        matResultat[0] = new ArrayList<String>();
        matResultat[1] = new ArrayList<String>();
        Resultat resultat = resultats.get(nom);
        if (resultat == null) {
            llegirResultat(nom);
            resultat = resultats.get(nom);
        }
        for (Sequencia sequencia : resultat.getMostra()) {
            matResultat[0].add(sequencia.getId());
            matResultat[1].add(sequencia.getSequencia());
        }
        return matResultat;
    }
    /** Retorna la matriu de distancies de una sequencia.
     * 
     * @param nom el nom del resultat a buscar les dades
     * @param index el index de la sequencia a analitzar dins del resultat
     * @return les posicions dels patrons a pintar que es una matriu de 3 llistes
     * la primera es la posicio on comença, la segona es la llargada dels patrons
     * i la tercera es el index del patro al qual pertany
     * @throws IOException 
     */
    public ArrayList<Integer>[] getPosicionsPatrons(String nom, int index) throws IOException {
        ArrayList[] matResultat = new ArrayList[3];
        matResultat[0] = new ArrayList<Integer>();
        matResultat[1] = new ArrayList<Integer>();
        matResultat[2] = new ArrayList<Integer>();
        Resultat resultat = resultats.get(nom);
        if (resultat == null) {
            llegirResultat(nom);
            resultat = resultats.get(nom);
        }
        int i = 0;
        for (Patro patro : resultat.getPatterns()) {
            if (patro.getBit(index)) {
                for (Match match : patro.getLlistaMatch()) {
                    for (Integer distancia : match.getSeq()[index]) {
                        matResultat[0].add(distancia);
                        matResultat[1].add(patro.getSequencia().length());
                        matResultat[2].add(i);
                    }
                }
            }
            i++;
        }
        return matResultat;
    }
    /** Funció 
     * 
     * @param nom identificador del resultat a buscar
     * @return torna els parametres de l'execució del resultat
     * @throws IOException si el fitxer dona problemes
     */
    public String getParametres(String nom) throws IOException {
        Resultat resultat = resultats.get(nom);
        if (resultat == null) {
            llegirResultat(nom);
            resultat = resultats.get(nom);
        }
        return resultat.getParametres();
    }
    /** Crea la carpeta amb el dia d'avui.
     * 
     * @param isNecessaryCreate si es necessari crearla
     * @return la ruta de la carpeta de avui
     * @throws IOException si passa algo en la escriptura de disc
     */
    public String crearCarpetaAvui(boolean isNecessaryCreate) throws IOException {
        return controladorDades.crearCarpetaAvui(isNecessaryCreate);
    }
    /** Funcio que retorna els noms dels resultats guardats a disc.
     * 
     * @return los nombres 
     */
    public List<String> getNoms() {
        return nomsResultats;
    }
    /** Funció de retornar el tamany de la llista de resultats.
     * 
     * @return la quantitat de resultats
     */
    public int getQuantitatResultats() {
        return nomsResultats.size();
    }
}
