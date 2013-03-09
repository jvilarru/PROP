package edu.upc.prop.domini;

import edu.upc.prop.dades.ControladorDades;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ControladorMalaltiesSimptomes {

    private static ControladorMalaltiesSimptomes objecteMalaltiesSimptomes;
    private TreeSet<Malaltia> malalties;
    private TreeSet<Simptoma> simptomes;
    private TreeMap<String, List<String>> dnisMalalties;
    private TreeMap<String, List<String>> dnisSimptomes;
    private TreeMap<String, List<String>> simptomesMalalties;
    private TreeMap<String, List<String>> malaltiesSimptomes;
    private TreeMap<String, List<Long>> idsMalalties;
    private static ControladorPacients controladorPacients;
    private static ControladorSequencies controladorSequencies;
    private ControladorDades controladorDades;
    private Simptoma limitInferiorS;
    private Simptoma limitSuperiorS;
    private Malaltia limitInferiorM;
    private Malaltia limitSuperiorM;

    private ControladorMalaltiesSimptomes() {
        Comparator<Malaltia> comp = new Comparator<Malaltia>() {

            @Override
            public int compare(Malaltia m1, Malaltia m2) {
                return m1.getNom().compareTo(m2.getNom());
            }
        };
        malalties = new TreeSet<Malaltia>(comp);
        Comparator<Simptoma> comps = new Comparator<Simptoma>() {

            @Override
            public int compare(Simptoma s1, Simptoma s2) {
                return s1.getNom().compareTo(s2.getNom());
            }
        };
        this.simptomes = new TreeSet<Simptoma>(comps);
        controladorDades = new ControladorDades();
    }

    /**
     * Crea una instancia del controlador
     *
     * @return Una instancia del controlador
     */
    public static ControladorMalaltiesSimptomes getInstance() {
        if (objecteMalaltiesSimptomes == null) {
            objecteMalaltiesSimptomes = new ControladorMalaltiesSimptomes();
            controladorPacients = ControladorPacients.getInstance();
            controladorSequencies = ControladorSequencies.getInstance();
        }

        return objecteMalaltiesSimptomes;
    }

    /**
     * Afegeix una malaltia al conjunt de malalties
     *
     * @param nom El nom de la malaltia
     * @param descripcio La descripcio de la malaltia
     * @param dnis Els DNSs dels pacients que tene aquesta malaltia
     * @param simptomes Els simptomes que te la malaltia
     * @param ids Els identificadors de les sequencies associades
     * @return True si s'ha afegit correctament, False altrament
     */
    public boolean addMalaltia(String nom, String descripcio, List<String> dnis, List<String> simptomes, List<Long> ids) {
        if (!consultaMalalties(nom, nom).isEmpty()) {
            return false;
        }

        Malaltia malaltia = new Malaltia(nom);

        if (descripcio != null) {
            malaltia.setDescripcio(descripcio);
        }
        if (dnis != null) {
            for (String s : dnis) {
                malaltia.addPacient(controladorPacients.getPacient(s.trim()));
            }
        }
        if (simptomes != null) {
            for (String s : simptomes) {
                malaltia.afegirSimptoma(consultaSimptomes(s, s).first());
            }
        }
        if (ids != null) {
            for (Long l : ids) {
                malaltia.addSequencia(controladorSequencies.get(l));
            }
        }
        return malalties.add(malaltia);
    }

    /**
     * Afegeix un simptoma al conjunt de simptomes
     *
     * @param nom Nom del simptoma
     * @param malalties Les malalties que te associades aquest simptoma
     * @param dnis Els DNSs dels pacients que tene aquesta malaltia
     * @return True si s'ha afegit correctament, False altrament
     */
    public boolean addSimptoma(String nom, List<String> malalties, List<String> dnis) {
        if (!consultaSimptomes(nom, nom).isEmpty()) {
            return false;
        }

        Simptoma simptoma = new Simptoma(nom);

        if (dnis != null) {
            for (String s : dnis) {
                simptoma.afegirPacient(controladorPacients.getPacient(s.trim()));
            }
        }
        if (malalties != null) {
            for (String s : malalties) {
                simptoma.afegirMalaltia(consultaMalalties(s, s).first());
            }
        }

        return simptomes.add(simptoma);
    }

    /**
     * Modifica una malaltia del conjunt
     *
     * @param nom Nom de la malaltia que es vol modificar
     * @param descripcio La Nova descripcio de la malaltia
     * @param dnis Els nous pacients que te associats la malaltia
     * @param simptomes Els nous simptomes de la malalia
     * @param ids Els nous identificadors de les sequencies associades
     * @return True si s'ha afegit correctament, False altrament
     */
    public boolean modificaMalaltia(String nom, String descripcio, List<String> dnis, List<String> simptomes, List<String> ids) {
        SortedSet<Malaltia> s = consultaMalalties(nom, nom);
        if (s.isEmpty()) {
            return false;
        }
        Malaltia malaltia = s.first();
        if (descripcio != null) {
            malaltia.setDescripcio(descripcio);
        }
        for (String dni : dnis) {
            if (dni.charAt(0) == '+') {
                malaltia.addPacient(controladorPacients.getPacient(dni.substring(1).trim()));
            }
            if (dni.charAt(0) == '-') {
                malaltia.esborrarPacient(controladorPacients.getPacient(dni.substring(1).trim()));
            }
        }
        for (String simptoma : simptomes) {
            if (simptoma.charAt(0) == '+') {
                malaltia.afegirSimptoma(consultaSimptomes(simptoma.substring(1), simptoma.substring(1)).first());
            }
            if (simptoma.charAt(0) == '-') {
                malaltia.esborrarSimptoma(consultaSimptomes(simptoma.substring(1), simptoma.substring(1)).first());
            }
        }
        for (String id : ids) {

            Set<Sequencia> consultaSeq = controladorSequencies.consultaSeq(id.substring(1));
            if (!consultaSeq.isEmpty()) {
                if (id.charAt(0) == '+') {
                    malaltia.addSequencia(consultaSeq.iterator().next());
                }
                if (id.charAt(0) == '-') {
                    malaltia.esborrarSequencia(consultaSeq.iterator().next());
                }
            }

        }
        return true;
    }

    /**
     * Modifica un simptoma del conjunt
     *
     * @param nom Nom del simptoma que es vol modificar
     * @param dnis Els nous pacients que te associats el simptoma
     * @param malalties Les noves malalties del simptoma
     * @return Trus si s'ha afegit correctament, False altrament
     */
    public boolean modificaSimptoma(String nom, List<String> dnis, List<String> malalties) {
        SortedSet<Simptoma> s = consultaSimptomes(nom, nom);
        if (s.isEmpty()) {
            return false;
        }
        Simptoma simptoma = s.first();
        for (String dni : dnis) {
            if (dni.charAt(0) == '+') {
                simptoma.afegirPacient(controladorPacients.getPacient(dni.substring(1).trim()));
            }
            if (dni.charAt(0) == '-') {
                simptoma.esborrarPacient(controladorPacients.getPacient(dni.substring(1).trim()));
            }
        }
        for (String malaltia : malalties) {
            if (malaltia.charAt(0) == '+') {
                simptoma.afegirMalaltia(consultaMalalties(malaltia.substring(1), malaltia.substring(1)).first());
            }

            if (malaltia.charAt(0) == '-') {
                simptoma.esborrarMalaltia(consultaMalalties(malaltia.substring(1), malaltia.substring(1)).first());
            }
        }
        return true;
    }

    /**
     * Comprova si la malaltia especificada esta al conjunt de malalties
     *
     * @param malaltia Malalatia que es vol comprovar
     * @return True si la malaltia esta en el conjunt, False altrament
     */
    public boolean containsMalaltia(String malaltia) {
        return !consultaMalalties(malaltia, malaltia).isEmpty();
    }

    /**
     * Comprova si el simptoma especificada esta al conjunt de simptomes
     *
     * @param simptoma Simptoma que es vol comprovar
     * @return True si el simptoma esta en el conjunt, False altrament
     */
    public boolean containsSimptoma(String simptoma) {
        return !consultaSimptomes(simptoma, simptoma).isEmpty();
    }

    /**
     * Esborra la malaltia especificada
     *
     * @param malaltia La malaltia que es vol esborrar
     * @return True si s'ha esborrat, False altrament
     */
    public boolean removeMalaltia(String malaltia) {
        SortedSet<Malaltia> l = consultaMalalties(malaltia, malaltia);
        if (l.isEmpty()) {
            return false;
        }
        Malaltia m = l.first();
        for (Simptoma simptoma : m.getSimptomesAssociats()) {
            simptoma.esborrarMalaltiaIntern(m);
        }
        for (Pacient pacient : m.getPacientsAssociats()) {
            pacient.esborrarMalaltiaIntern(m);
        }
        for (Sequencia sequencia : m.getSequenciesAssociades()) {
            sequencia.setMalaltia(null);
        }
        return malalties.remove(m);
    }

    /**
     * Esborra el simptoma especificada
     *
     * @param simptoma El simptoma que es vol esborrar
     * @return True si s'ha esborrat, False altrament
     */
    public boolean removeSimptoma(String simptoma) {
        SortedSet<Simptoma> l = consultaSimptomes(simptoma, simptoma);
        if (l.isEmpty()) {
            return false;
        }
        Simptoma s = l.first();
        for (Malaltia malaltia : s.getMalaltiesAssociades()) {
            malaltia.esborrarSimptomaIntern(s);
        }
        for (Pacient pacient : s.getPacientsAssociats()) {
            pacient.esborrarSimptomaIntern(s);
        }
        return simptomes.remove(s);
    }
    /** Borra totes les malalties del sistema.
     * 
     */
    public void buidarMalalties() {
        Iterator<Malaltia> iterator = malalties.iterator();
        List<Malaltia> esborrats = new ArrayList<Malaltia>();
        Malaltia malaltia;

        while (iterator.hasNext()) {
            malaltia = iterator.next();

            for (Simptoma simptoma : malaltia.getSimptomesAssociats()) {
                simptoma.esborrarMalaltiaIntern(malaltia);
            }
            for (Pacient pacient : malaltia.getPacientsAssociats()) {
                pacient.esborrarMalaltiaIntern(malaltia);
            }
            for (Sequencia sequencia : malaltia.getSequenciesAssociades()) {
                sequencia.setMalaltia(null);
            }

            esborrats.add(malaltia);
        }

        malalties.removeAll(esborrats);
    }
    /** Buida tots el simptomes del sistema.
     * 
     */
    public void buidarSimptomes() {
        Iterator<Simptoma> iterator = simptomes.iterator();
        List<Simptoma> esborrats = new ArrayList<Simptoma>();
        Simptoma simptoma;

        while (iterator.hasNext()) {
            simptoma = iterator.next();

            for (Malaltia malaltia : simptoma.getMalaltiesAssociades()) {
                malaltia.esborrarSimptomaIntern(simptoma);
            }
            for (Pacient pacient : simptoma.getPacientsAssociats()) {
                pacient.esborrarSimptomaIntern(simptoma);
            }

            esborrats.add(simptoma);
        }

        simptomes.removeAll(esborrats);
    }

    /**
     * Retorna el tamany de malalties
     *
     * @return Tamany de malalties
     */
    public int sizeMalaltia(String nom) {
        if (nom == null) {
            return malalties.size();
        }
        SortedSet<Malaltia> consulta = consultaMalalties(nom, nom);
        if (consulta.isEmpty()) {
            return 0;
        }
        return consulta.first().tamPacients();
    }

    /**
     * Retorna el tamany de simptomes
     *
     * @return Tamany de simptomes
     */
    public int sizeSimptoma(String nom) {
        if (nom == null) {
            return simptomes.size();
        }
        SortedSet<Simptoma> consulta = consultaSimptomes(nom, nom);
        if (consulta.isEmpty()) {
            return 0;
        }
        return consulta.first().tamPacients();
    }

    /**
     * Donat un rang de malalties(inici, fi) retorna totes les malalties que
     * estan entre aquestes dues
     *
     * @param inici Malaltia inici
     * @param fi Malaltia fi
     * @return Llista amb totes les malalties que estan entre (inici, fi) o una
     * llista buida si no hi ha cap malaltia
     */
    public SortedSet<Malaltia> consultaMalalties(String inici, String fi) {
        Malaltia mInici = new Malaltia(inici);
        boolean a = false;
        Malaltia mFi = new Malaltia(fi);
        boolean b = false;
        if (malalties.contains(mInici)) {
            a = true;
            mInici = (malalties.subSet(mInici, true, mInici, true)).first();
        }
        if (malalties.contains(mFi)) {
            b = true;
            mFi = (malalties.subSet(mFi, true, mFi, true)).first();
        }
        return malalties.subSet(mInici, a, mFi, b);
    }

    /**
     * Donat un rang de simptomes(inici, fi) retorna totes els simptomes que
     * estan entre aquestes dues
     *
     * @param inici Simptoma inici
     * @param fi Simptoma fi
     * @return Llista amb totes els simptomes que estan entre (inici, fi) o una
     * llista buida si no no hi cap simptoma
     */
    public SortedSet<Simptoma> consultaSimptomes(String inici, String fi) {
        Simptoma sInici = new Simptoma(inici);
        boolean a = false;
        Simptoma sFi = new Simptoma(fi);
        boolean b = false;
        if (simptomes.contains(sInici)) {
            a = true;
            sInici = (simptomes.subSet(sInici, true, sInici, true)).first();
        }
        if (simptomes.contains(sFi)) {
            b = true;
            sFi = (simptomes.subSet(sFi, true, sFi, true)).first();
        }
        return simptomes.subSet(sInici, a, sFi, b);
    }

    /**
     * Retorna totes les malalties que te el conjunt
     *
     * @return Totes les malalties del conjunt
     */
    public List<Malaltia> getMalalties() {
        return new ArrayList<Malaltia>(malalties);
    }

    /**
     * Retorna tots els simptomes que te el conjunt
     *
     * @return Tots els simptomes del conjunt
     */
    public List<Simptoma> getSimptomes() {
        return new ArrayList<Simptoma>(simptomes);
    }

    /**
     * Llegeix les malalties del fitxer de disc
     *
     * @return True si s'ha llegit correctament, False altrament
     * @throws FileNotFoundException Si no es troba el fitxer
     * @throws IOException Si ocurre un problema de I/O
     */
    public boolean llegirMalalties(String path) throws FileNotFoundException, IOException {
        dnisMalalties = new TreeMap<String, List<String>>();
        simptomesMalalties = new TreeMap<String, List<String>>();
        idsMalalties = new TreeMap<String, List<Long>>();
        if (!controladorDades.obrirLlegir(path)) {
            return false;
        }
        while (controladorDades.potLlegir()) {
            for (String string : controladorDades.llegir(ControladorDades.TAMANY_BLOCK)) {
                malalties.add(creaMalaltia(string));
            }
        }
        controladorDades.tancarLlegir();
        return true;
    }

    /**
     * Escriu les malalties al fitxer del disc
     *
     * @throws IOException Si ocurre un problema de I/O
     */
    public void escriureMalalties(String path) throws IOException {
        Iterator<Malaltia> iterator = malalties.iterator();
        if (iterator.hasNext()) {
            List<String> llista = new ArrayList<String>();
            Malaltia malaltia;
            controladorDades.obrirEscriure(path + ".mal", false);

            while (iterator.hasNext()) {
                malaltia = iterator.next();

                llista.add(malaltia.toString());
                if (llista.size() == ControladorDades.TAMANY_BLOCK) {
                    controladorDades.escriure(llista);
                    llista.clear();
                }
            }
            if (llista.size() > 0) {
                controladorDades.escriure(llista);
            }
            controladorDades.tancarEscriure();
        }
    }

    /**
     * Llegeix els simptomes del fitxer de disc
     *
     * @return True si s'ha llegit correctament, False altrament
     * @throws FileNotFoundException Si no es troba el fitxer
     * @throws IOException Si ocurre un problema de I/O
     */
    public boolean llegirSimptomes(String path) throws FileNotFoundException, IOException {
        dnisSimptomes = new TreeMap<String, List<String>>();
        malaltiesSimptomes = new TreeMap<String, List<String>>();

        if (!controladorDades.obrirLlegir(path)) {
            return false;
        }
        while (controladorDades.potLlegir()) {
            for (String string : controladorDades.llegir(ControladorDades.TAMANY_BLOCK)) {
                simptomes.add(creaSimptoma(string));
            }
        }
        controladorDades.tancarLlegir();
        return true;
    }

    /**
     * Escriu els simptomes al fitxer del disc
     *
     * @throws IOException Si ocurre un problema de I/O
     */
    public void escriureSimptomes(String path) throws IOException {
        Iterator<Simptoma> iterator = simptomes.iterator();
        if (iterator.hasNext()) {
            List<String> llista = new ArrayList<String>();
            Simptoma simptoma;
            controladorDades.obrirEscriure(path + ".sim", false);

            while (iterator.hasNext()) {
                simptoma = iterator.next();

                llista.add(simptoma.toString());
                if (llista.size() == ControladorDades.TAMANY_BLOCK) {
                    controladorDades.escriure(llista);
                    llista.clear();
                }
            }
            if (llista.size() > 0) {
                controladorDades.escriure(llista);
            }
            controladorDades.tancarEscriure();
        }
    }

    private Malaltia creaMalaltia(String string) {

        int pivote = string.indexOf(":");
        String nom = string.substring(0, pivote);
        string = string.substring(pivote + 1);

        pivote = string.indexOf(":");
        String descripcio = string.substring(0, pivote);
        string = string.substring(pivote + 2);

        pivote = string.indexOf("}");
        dnisMalalties.put(nom, llista(string.substring(0, pivote)));
        string = string.substring(pivote + 3);

        pivote = string.indexOf("}");
        idsMalalties.put(nom, llistaid(string.substring(0, pivote)));
        string = string.substring(pivote + 3);

        pivote = string.indexOf("}");
        simptomesMalalties.put(nom, llista(string.substring(0, pivote)));

        Malaltia malaltia = new Malaltia(nom);
        malaltia.setDescripcio(descripcio);
        return malaltia;
    }

    private List<String> llista(String substring) {
        List<String> resultat = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(substring, ",");
        while (st.hasMoreTokens()) {
            resultat.add(st.nextToken());
        }
        return resultat;
    }

    private List<Long> llistaid(String substring) {
        List<Long> resultat = new ArrayList<Long>();
        StringTokenizer st = new StringTokenizer(substring, ",");
        while (st.hasMoreTokens()) {
            resultat.add(Long.parseLong(st.nextToken()));
        }
        return resultat;
    }

    private Simptoma creaSimptoma(String string) {

        int pivote = string.indexOf(":");
        String nom = string.substring(0, pivote);
        string = string.substring(pivote + 2);

        pivote = string.indexOf("}");
        malaltiesSimptomes.put(nom, llista(string.substring(0, pivote)));
        string = string.substring(pivote + 3);

        pivote = string.indexOf("}");
        dnisSimptomes.put(nom, llista(string.substring(0, pivote)));

        Simptoma simptoma = new Simptoma(nom);
        return simptoma;
    }
    /** Associa les malalties amb els seus simptomes, sequencies i pacients.
     * 
     */
    public void asociarMalalties() {
        Iterator<Malaltia> itm = malalties.iterator();
        while (itm.hasNext()) {
            Malaltia malaltia = itm.next();
            Iterator<String> itdni = dnisMalalties.get(malaltia.getNom()).iterator();
            while (itdni.hasNext()) {
                malaltia.addPacient(controladorPacients.getPacient(itdni.next()));
            }
            Iterator<Long> itid = idsMalalties.get(malaltia.getNom()).iterator();
            while (itid.hasNext()) {
                malaltia.addSequencia(controladorSequencies.get(itid.next()));
            }
            Iterator<String> itsimp = simptomesMalalties.get(malaltia.getNom()).iterator();
            while (itsimp.hasNext()) {
                String simptoma = itsimp.next();
                SortedSet<Simptoma> simp = consultaSimptomes(simptoma, simptoma);
                if (!simp.isEmpty()) {
                    malaltia.afegirSimptoma(simp.first());
                }
            }

        }

        idsMalalties = null;
        dnisMalalties = null;
        simptomesMalalties = null;
    }
    /**
     * Asocia els simptomes amb els seus corresponents pacients i malalties.
     */
    public void asociarSimptomes() {
        Iterator<Simptoma> its = simptomes.iterator();
        while (its.hasNext()) {
            Simptoma simptoma = its.next();
            Iterator<String> itdni = dnisSimptomes.get(simptoma.getNom()).iterator();
            while (itdni.hasNext()) {
                simptoma.afegirPacient(controladorPacients.getPacient(itdni.next()));
            }
            Iterator<String> itmal = malaltiesSimptomes.get(simptoma.getNom()).iterator();
            while (itmal.hasNext()) {
                String malaltia = itmal.next();
                SortedSet<Malaltia> malal = consultaMalalties(malaltia, malaltia);
                if (!malal.isEmpty()) {
                    simptoma.afegirMalaltia(malal.first());
                }
            }

        }

        dnisSimptomes = null;
        malaltiesSimptomes = null;
    }
    /**
     * 
     * @return el nom de totes les malalties del sistema.
     */
    public List<String> getNomMalalties() {
        List<String> llista = new ArrayList<String>();
        Malaltia malaltia;
        Iterator<Malaltia> iter = malalties.iterator();

        while (iter.hasNext()) {
            malaltia = iter.next();
            llista.add(malaltia.getNom());
        }

        return llista;
    }
    /**
     * 
     * @return el nom de tots els simptomes del sistema.
     */
    public List<String> getNomSimptomes() {
        List<String> llista = new ArrayList<String>();
        Simptoma simptoma;
        Iterator<Simptoma> iter = simptomes.iterator();

        while (iter.hasNext()) {
            simptoma = iter.next();
            llista.add(simptoma.getNom());
        }

        return llista;
    }
    /**
     * 
     * @param malaltia el nom de la malaltia de la qual volem saber la descripcio
     * @return la descripcio de aquesta malaltia
     */
    public String getDescripcioMalaltia(String malaltia) {
        SortedSet<Malaltia> m = consultaMalalties(malaltia, malaltia);
        if (m.isEmpty()) {
            return "";
        }
        return m.first().getDescripcio();
    }
    /**
     * 
     * @param malaltia el nom de la malaltia
     * @return el nom de tots els simptomes associats amb la malaltia malaltia
     */
    public List<String> getSimptomesMalaltia(String malaltia) {
        List<String> simps = new ArrayList<String>();
        SortedSet<Malaltia> m = consultaMalalties(malaltia, malaltia);
        if (!m.isEmpty()) {
            Malaltia mal = m.first();
            for (Simptoma s : mal.getSimptomesAssociats()) {
                if (s != null) {
                    simps.add(s.getNom());
                }
            }
        }
        return simps;
    }
    /**
     * 
     * @param malaltia el nom de la malaltia
     * @return una llista amb la representacio de les sequencies associades amb la malaltia
     */
    public List<String> getSequenciesMalaltia(String malaltia) {
        List<String> seqs = new ArrayList<String>();
        SortedSet<Malaltia> m = consultaMalalties(malaltia, malaltia);
        if (!m.isEmpty()) {
            Malaltia mal = m.first();
            for (Sequencia s : mal.getSequenciesAssociades()) {
                if (s != null) {
                    seqs.add(s.getId() + ":" + s.getSequencia());
                }
            }
        }
        return seqs;
    }
    /**
     * 
     * @param malaltia el nom de la malaltia
     * @return llista dels dnis de tots els pacients associats amb la malaltia
     */
    public List<String> getPacientsMalaltia(String malaltia) {
        List<String> dnis = new ArrayList<String>();
        SortedSet<Malaltia> mals = consultaMalalties(malaltia, malaltia);
        if (!mals.isEmpty()) {
            Malaltia m = mals.first();
            for (Pacient p : m.getPacientsAssociats()) {
                if (p != null) {
                    String s = p.getDni();
                    s += " : ";
                    s += p.getCognom();
                    s += ", ";
                    s += p.getNom();
                    dnis.add(s);
                }
            }
        }
        return dnis;
    }
    /**
     * 
     * @param simptoma el nom del simptoma
     * @return llista de tots els noms de les malalties associades amb el simptoma
     */
    public List<String> getMalaltiesSimptoma(String simptoma) {
        List<String> mals = new ArrayList<String>();
        SortedSet<Simptoma> simps = consultaSimptomes(simptoma, simptoma);
        if (!simps.isEmpty()) {
            Simptoma s = simps.first();
            for (Malaltia m : s.getMalaltiesAssociades()) {
                if (m != null) {
                    mals.add(m.getNom());
                }
            }
        }
        return mals;
    }
    /**
     * 
     * @param simptoma el nom del simptoma
     * @return llista de tots els pacients associats amb aquell simptoma
     */
    public List<String> getPacientsSimptoma(String simptoma) {
        List<String> dnis = new ArrayList<String>();
        SortedSet<Simptoma> simps = consultaSimptomes(simptoma, simptoma);
        if (!simps.isEmpty()) {
            Simptoma simp = simps.first();
            for (Pacient p : simp.getPacientsAssociats()) {
                if (p != null) {
                    String s = p.getDni();
                    s += " : ";
                    s += p.getCognom();
                    s += ", ";
                    s += p.getNom();
                    dnis.add(s);
                }
            }
        }
        return dnis;
    }
}
