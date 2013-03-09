package edu.upc.prop.domini;

import edu.upc.prop.dades.ControladorDades;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ControladorPacients {

    private static ControladorPacients objecteControladorPacients;
    private AVL pacients;
    private ControladorDades controladorDades;
    private static ControladorSequencies controladorSequencies;
    private static ControladorMalaltiesSimptomes controladorMalaltiesSimptomes;
    private Map<String, Long> adns;
    private Map<String, List<String>> malalties;
    private Map<String, List<String>> simptomes;
    private Map<String, Map<Date, Long>> historials;

    private ControladorPacients() {
        pacients = new AVL();

        controladorDades = new ControladorDades();
    }

    /**
     * Crea una instancia del controlador
     *
     * @return Una instancia del controlador
     */
    public static ControladorPacients getInstance() {
        if (objecteControladorPacients == null) {
            objecteControladorPacients = new ControladorPacients();
            controladorSequencies = ControladorSequencies.getInstance();
            controladorMalaltiesSimptomes = ControladorMalaltiesSimptomes.getInstance();
        }
        return objecteControladorPacients;
    }

    /**
     * Afegeix un pacient al conjunt de pacients
     *
     * @param dni DNI del pacient
     * @param nom Nom del pacient
     * @param cognom Cognom del pacient
     * @param dataNaixament Data de naixement del pacient
     * @param sexe Sexe del pacient
     * @param adn ADN del pacient
     * @param malalties Les malalties que te el pacient
     * @param simptomes Els simptomes que te el pacient
     * @return
     */
    public boolean afegirPacient(String dni, String nom, String cognom, String dataNaixament,
            char sexe, String adn, List<String> malalties, List<String> simptomes) throws StackOverflowError {
        if (conte(dni)) {
            return false;
        }
        Sequencia seq;
        Set<Sequencia> seqs = controladorSequencies.consultaSeq(adn);
        if (seqs.isEmpty()) {
            boolean inserta = controladorSequencies.inserta(adn, null, null);
            if (!inserta) {
                return false;
            }
            seqs = controladorSequencies.consultaSeq(adn);
            if(seqs.isEmpty())return false;
            seq = seqs.iterator().next();
        } else {
            seq = seqs.iterator().next();
        }

        Pacient p = new Pacient(dni, dataNaixament, sexe, seq, false);
        p.setNom(nom);
        p.setCognom(cognom);
        for (String s : malalties) {
            SortedSet<Malaltia> setm = controladorMalaltiesSimptomes.consultaMalalties(s, s);
            if (!setm.isEmpty()) {
                p.afegirMalaltia(setm.first());
            }
        }
        for (String s : simptomes) {
            SortedSet<Simptoma> sets = controladorMalaltiesSimptomes.consultaSimptomes(s, s);
            if (!sets.isEmpty()) {
                p.afegirSimptoma(sets.first());
            }
        }
        return pacients.inserta(p);
    }

    /**
     * Passat un DNI comprova si aquest existeix al conjunt
     *
     * @param dni DNI del pacient a comprovar
     * @return True si el DNI existeix en el conjunt, False altrament
     */
    public boolean conte(String dni) {
        return !pacients.consulta(dni, dni).isEmpty();
    }

    /**
     * Donat un DNI retorna l'objecte pacient
     *
     * @param dni DNI del pacient a retornar
     * @return L'objecte pacient o null si el DNI passat no existeix
     */
    public Pacient getPacient(String dni) {
        List<Pacient> l = pacients.consulta(dni, dni);
        if (!l.isEmpty()) {
            return l.get(0);
        }
        return null;
    }

    /**
     * Retorna tots els pacients
     *
     * @return Una llista de tots els pacients o una llista buida si no hi
     * pacients
     */
    public List<Pacient> getPacients() {
        return pacients.consulta("00000000", "99999999");
    }

    /**
     * Donat un rang de dnis (dniInici, dniFi) retorna tots els pacients entre
     * aquest rang
     *
     * @param dniInici DNI inici del pacient
     * @param dniFi DNI fi del pacient
     * @return Una llista amb tots els pacients que estan dins del rang
     */
    public List<Pacient> consulta(String dniInici, String dniFi) {
        return pacients.consulta(dniInici, dniFi);
    }

    /**
     * Modifica un pacient del conjunt
     *
     * @param dni DNI del pacient que es vol modificar
     * @param nom El nou nom del pacient
     * @param cognom El nou cognom del pacient
     * @param adn EL nou adn del pacient
     * @param malalties Les noves malalties del pacient
     * @param simptomes Els nous simptomes del pacient
     */
    public boolean modifica(String dni, String nom, String cognom, String adn, List<String> malalties, List<String> simptomes) throws StackOverflowError {
        boolean ok = true;
        Pacient p = pacients.consulta(dni, dni).get(0);
        if (p == null) return false;
        if (nom != null && !nom.equals("")) {
            p.setNom(nom);
        }
        if (cognom != null && !cognom.equals("")) {
            p.setCognom(cognom);
        }
        if (adn != null) {
            if (!adn.equals(p.getAdn().getSequencia())) {
                Sequencia seq;
                Set<Sequencia> seqs = controladorSequencies.consultaSeq(adn);
                if (seqs.isEmpty()) {
                    ok = controladorSequencies.inserta(adn, null, null);
                    seq = controladorSequencies.consultaSeq(adn).iterator().next();
                } else {
                    seq = seqs.iterator().next();
                }

                p.setAdn(seq, false);
            }
        }
        for (String s : malalties) {
            String malal = s.substring(1);
            Malaltia malaltia = controladorMalaltiesSimptomes.consultaMalalties(malal, malal).first();
            if (s.charAt(0) == '+') {
                p.afegirMalaltia(malaltia);
            } else if (s.charAt(0) == '-') {
                p.esborrarMalaltia(malaltia);
            }
        }

        for (String s : simptomes) {
            String simp = s.substring(1);
            Simptoma simptoma = controladorMalaltiesSimptomes.consultaSimptomes(simp, simp).first();
            if (s.charAt(0) == '+') {
                p.afegirSimptoma(simptoma);
            } else if (s.charAt(0) == '-') {
                p.esborrarSimptoma(simptoma);
            }
        }
        return ok;
    }

    /**
     * Esborrar un pacient del conjunt
     *
     * @param dni El DNI del pacient que es vol esborrar
     * @return True si s'ha esborrat correctament, False altrament
     */
    public boolean esborra(String dni) {
        List<Pacient> pac = pacients.consulta(dni, dni);
        if (pac == null || pac.isEmpty()) {
            return false;
        }

        Pacient pacient = pac.get(0);
        for (Simptoma sim : pacient.getSimptomes()) {
            sim.esborrarPacientIntern(pacient);
        }
        for (Malaltia mal : pacient.getMalalties()) {
            mal.esborrarPacientIntern(pacient);
        }
        Sequencia adn = pacient.getAdn();
        if (adn != null) {
            adn.setPacient(null);
        }
        for (Sequencia seq : pacient.getHistorial().getSequencies()) {
            seq.setPacient(null);
        }
        return pacients.esborra(dni);
    }
    /**
     * Buida tots els pacients del sistema.
     */
    public void buidar() {
        List<Pacient> pac = pacients.consulta("00000000", "99999999");

        for (Pacient p : pac) {
            esborra(p.getDni());
        }
    }

    /**
     * Retorna el tamany del conjunt
     *
     * @return El tamany del conjunt
     */
    public int size() {
        return pacients.size();
    }

    /**
     * Selecciona una mostra del conjunt de pacients segon els parametres que se
     * li passen
     *
     * @param edatInici Edat inici
     * @param edatFinal Edat fi
     * @param dniInici DNI inici
     * @param dniFinal DNI fi
     * @param sexe Sexe del pacient
     * @param dni DNI del pacient que ha de estar en la mostra
     * @param simptomes Llistat de malalties que han de tindre els pacients
     * seleccionats
     * @param malalties Llistat de simptomes que han de tindre els pacients
     * seleccionats
     * @return Una llista amb totes les sequencies de la mostra seleccionada o
     * una llista buida si no hi ha una mostra disponible
     */
    public List<Sequencia> getMostra(int edatInici, int edatFinal, String dniInici, String dniFinal, char sexe, List<String> dni, List<String> simptomes, List<String> malalties) {
        List<Pacient> l;
        List<Pacient> l2 = new ArrayList<Pacient>();
        List<Sequencia> l3 = new ArrayList<Sequencia>();
        if (edatInici == -1) {
            edatInici = 0;
        }
        if (edatFinal == -1) {
            edatFinal =  Integer.MAX_VALUE / 1000;
        }
        if (dniInici == null) {
            dniInici = "00000000";
        }
        if (dniFinal == null) {
            dniFinal = "99999999";
        }
        l = pacients.consulta(edatInici, edatFinal, dniInici, dniFinal);
        if (sexe == Pacient.SEXE_MASCULI || sexe == Pacient.SEXE_FEMENI) {
            for (Pacient p : l) {
                if (p.getSexe() == sexe || conteAlgunSimptoma(p.getSimptomes(), simptomes) && conteAlgunaMalaltia(p.getMalalties(), malalties)) {
                    l2.add(p);
                }
            }
        } else {
            for (Pacient p : l) {
                if (conteAlgunSimptoma(p.getSimptomes(), simptomes) && conteAlgunaMalaltia(p.getMalalties(), malalties)) {
                    l2.add(p);
                }
            }
        }

        List<Pacient> aux;
        Pacient p;
        for (String s : dni) {
            aux = pacients.consulta(s, s);
            if (!aux.isEmpty()) {
                p = aux.get(0);
                if (!l2.contains(p)) {
                    l2.add(p);
                }
            }
        }
        for (Pacient pac : l2) {
            l3.add(pac.getAdn());
        }
        return l3;
    }

    /**
     * Llegeix els pacients del fitxer de disc
     *
     * @return True si s'ha llegit correctament, False altrament
     * @throws FileNotFoundException Si no es troba el fitxer
     * @throws IOException Si ocurre un problema de I/O
     */
    public boolean llegirPacients(String path) throws FileNotFoundException, IOException {
        adns = new HashMap<String, Long>();
        malalties = new HashMap<String, List<String>>();
        simptomes = new HashMap<String, List<String>>();
        historials = new HashMap<String, Map<Date, Long>>();
        if (!controladorDades.obrirLlegir(path)) {
            return false;
        }
        while (controladorDades.potLlegir()) {
            for (String string : controladorDades.llegir(ControladorDades.TAMANY_BLOCK)) {
                pacients.inserta(creaPacient(string));
            }
        }
        controladorDades.tancarLlegir();
        return true;
    }

    /**
     * Escriu els pacients al fitxer del disc
     *
     * @throws IOException Si ocurre un problema de I/O
     */
    public void escriurePacients(String path) throws IOException {
        List<Pacient> pac = pacients.consulta(0,  Integer.MAX_VALUE / 1000);

        if (!pac.isEmpty()) {
            List<String> llista = new ArrayList<String>();

            controladorDades.obrirEscriure(path + ".pac", false);
            for (Pacient pacient : pac) {
                llista.add(pacient.toString());
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
     * Associa tots els pacients amb els seus corresponents simtpomes, sequencies a l'historial,malalties
     * i ADN actual.
     */
    public void asociar() {
        for (String dni : adns.keySet()) {
            Pacient pacient = pacients.consulta(dni, dni).get(0);
            pacient.setAdn(controladorSequencies.get(adns.get(dni)), true);
            Map<Date, Sequencia> historial = new HashMap<Date, Sequencia>();
            for (Date data : historials.get(dni).keySet()) {
                Sequencia seq = controladorSequencies.get(historials.get(dni).get(data));
                if (seq != null) {
                    historial.put(data, seq);
                }
            }
            pacient.insertaHistorial(historial);
            for (String str : simptomes.get(dni)) {
                SortedSet<Simptoma> simp = controladorMalaltiesSimptomes.consultaSimptomes(str, str);
                if (!simp.isEmpty()) {
                    pacient.afegirSimptoma(simp.first());
                }
            }
            for (String str : malalties.get(dni)) {
                SortedSet<Malaltia> mal = controladorMalaltiesSimptomes.consultaMalalties(str, str);
                if (!mal.isEmpty()) {
                    pacient.afegirMalaltia(mal.first());
                }
            }
        }

        adns = null;
        historials = null;
        simptomes = null;
        malalties = null;
    }

    private Pacient creaPacient(String string) {
        int pivote = string.indexOf(":");
        String dni = string.substring(0, pivote);
        string = string.substring(pivote + 1);

        pivote = string.indexOf(":");
        String nom = string.substring(0, pivote);
        string = string.substring(pivote + 1);

        pivote = string.indexOf(":");
        String cognom = string.substring(0, pivote);
        string = string.substring(pivote + 1);

        pivote = string.indexOf(":");
        String dataNaixament = string.substring(0, pivote);
        string = string.substring(pivote + 1);

        char sexe = string.charAt(0);
        string = string.substring(2);

        pivote = string.indexOf(":");
        long adn = Long.parseLong(string.substring(0, pivote));
        adns.put(dni, adn);
        string = string.substring(pivote + 2);

        pivote = string.indexOf("}");
        simptomes.put(dni, llista(string.substring(0, pivote)));
        string = string.substring(pivote + 3);

        pivote = string.indexOf("}");
        malalties.put(dni, llista(string.substring(0, pivote)));
        string = string.substring(pivote + 3);

        pivote = string.indexOf("}");
        historials.put(dni, map(string.substring(0, pivote)));

        Pacient pacient = new Pacient(dni, dataNaixament, sexe, null, true);
        pacient.setCognom(cognom);
        pacient.setNom(nom);
        return pacient;

    }

    private boolean conteAlgunSimptoma(List<Simptoma> simptomes, List<String> simptomes2) {
        if (simptomes2.isEmpty()) {
            return true;
        }
        for (Simptoma s : simptomes) {
            if (simptomes2.contains(s.getNom())) {
                return true;
            }
        }
        return false;
    }

    private boolean conteAlgunaMalaltia(List<Malaltia> malalties, List<String> malalties2) {
        if (malalties2.isEmpty()) {
            return true;
        }
        for (Malaltia m : malalties) {
            if (malalties2.contains(m.getNom())) {
                return true;
            }
        }
        return false;
    }

    private List<String> llista(String substring) {
        List<String> resultat = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(substring, ",");
        while (st.hasMoreTokens()) {
            resultat.add(st.nextToken());
        }
        return resultat;
    }

    private Map<Date, Long> map(String string) {
        Map<Date, Long> resultat = new HashMap<Date, Long>();
        StringTokenizer st = new StringTokenizer(string, ",");
        int pivote;
        while (st.hasMoreTokens()) {
            String substring = st.nextToken();
            pivote = substring.indexOf(":");
            Date d = getdata(substring.substring(0, pivote));
            substring = substring.substring(pivote + 1, substring.length());
            Long l = Long.valueOf(substring);
            resultat.put(d, l);
        }
        return resultat;
    }

    private Date getdata(String substring) {
        Calendar c = Calendar.getInstance();
        StringTokenizer st = new StringTokenizer(substring, "/");
        int numeros[] = new int[6];
        int i = 0;
        while (st.hasMoreTokens()) {
            numeros[i] = Integer.parseInt(st.nextToken());
            i++;
        }
        c.set(numeros[2], numeros[1] - 1, numeros[0], numeros[3], numeros[4], numeros[5]);
        return c.getTime();
    }

    public String getNomCongomsPacient(String dni) {
        Pacient pac = getPacient(dni);

        return pac.getCognom() + ", " + pac.getNom();
    }

    public String getNom(String dni) {
        Pacient p = getPacient(dni);
        if (p != null) {
            return p.getNom();
        }
        return "";
    }

    public String getCognom(String dni) {
        Pacient p = getPacient(dni);
        if (p != null) {
            return p.getCognom();
        }
        return "";
    }

    public char getSexe(String dni) {
        Pacient p = getPacient(dni);
        if (p != null) {
            return p.getSexe();
        }
        return 'M';
    }

    public String getDataNaixement(String dni) {
        Pacient p = getPacient(dni);
        if (p != null) {
            return p.getDataNaixament();
        }
        return "";
    }

    public String getAdn(String dni) {
        Pacient p = getPacient(dni);
        if (p != null) {
            Sequencia s = p.getAdn();
            if (s != null) {
                return s.getSequencia();
            }
        }
        return "";
    }

    public List<String> getMalalties(String dni) {
        Pacient p = getPacient(dni);
        List<String> llistaMalalties = new ArrayList<String>();
        if (p != null) {
            List<Malaltia> mals = p.getMalalties();
            for (Malaltia m : mals) {
                llistaMalalties.add(m.getNom());
            }
        }
        return llistaMalalties;
    }

    public List<String> getSimptomes(String dni) {
        Pacient p = getPacient(dni);
        List<String> llistaSimptomes = new ArrayList<String>();
        if (p != null) {
            List<Simptoma> simps = p.getSimptomes();
            for (Simptoma s : simps) {
                llistaSimptomes.add(s.getNom());
            }
        }
        return llistaSimptomes;
    }

    public List<String> getHistorial(String dni) {
        Pacient p = getPacient(dni);
        List<String> llistaAdns = new ArrayList<String>();
        if (p != null) {
            Historial hist = p.getHistorial();
            Set<Date> dates = hist.claus();
            for (Date d : dates) {
                Sequencia seq = hist.consulta(d);
                String s = "";
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                s += c.get(Calendar.DAY_OF_MONTH);
                s += "/";
                s += (c.get(Calendar.MONTH) + 1);
                s += "/";
                s += c.get(Calendar.YEAR);
                s += " ";
                s += c.get(Calendar.HOUR_OF_DAY);
                s += ":";
                s += c.get(Calendar.MINUTE);
                s += " -> ";
                if (seq != null) {
                    s += seq.getSequencia();
                }
                llistaAdns.add(s);
            }
        }
        return llistaAdns;
    }
    /** Associa una malaltiaa un pacient.
     * 
     * @param dni el dni del pacient que es vol modificar
     * @param malaltia la malaltia que se li vol afegir
     * @return si s'ha pogut fer la associacio
     */
    public boolean afegirMalaltia(String dni, String malaltia) {
        Pacient p = getPacient(dni);
        SortedSet<Malaltia> ms = controladorMalaltiesSimptomes.consultaMalalties(malaltia, malaltia);
        if (!ms.isEmpty()) {
            return p.afegirMalaltia(ms.first());
        }
        return false;
    }
    /**
     * 
     * @return la llista de tots els pacients amb el format DNI: COGNOM, NOM
     */
    public List<String> getDniNomCognomPacients() {
        List<String> llista = new ArrayList<String>();
        String str;

        for (Pacient pac : getPacients()) {
            str = pac.getDni() + ": " + pac.getCognom() + ", " + pac.getNom();

            llista.add(str);
        }

        return llista;
    }
}
