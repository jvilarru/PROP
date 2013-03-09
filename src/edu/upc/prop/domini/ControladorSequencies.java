package edu.upc.prop.domini;

import edu.upc.prop.dades.ControladorDades;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ControladorSequencies {

    private static ControladorSequencies objecteControladorSequencies;
    private TST conjuntSequencia;
    private TreeMap<Long, String> dnis;
    private TreeMap<Long, String> malalties;
    private Alfabet alfabet;
    private long idMax;
    private static ControladorMalaltiesSimptomes controladorMalaltiesSimptomes;
    private static ControladorPacients controladorPacients;
    private ControladorDades controladorDades;

    private ControladorSequencies() {
        this.conjuntSequencia = new TST();
        controladorDades = new ControladorDades();
        alfabet = Alfabet.getInstance();
        idMax = 0;
    }

    /**
     * Crea una instancia del controlador
     *
     * @return La instancia del controlador
     */
    public static ControladorSequencies getInstance() {
        if (objecteControladorSequencies == null) {
            objecteControladorSequencies = new ControladorSequencies();
            controladorMalaltiesSimptomes = ControladorMalaltiesSimptomes.getInstance();
            controladorPacients = ControladorPacients.getInstance();
        }
        return objecteControladorSequencies;
    }

    /**
     * Retorna la llista de caracter que formen l'alfabet
     *
     * @return Llista de caracter de l'alfabet
     */
    public List<Character> getLlistaCaracters() {
        return alfabet.getLlistaCaracters();
    }

    /**
     * Modificar els caracter que té l'alfabet;
     *
     * @param llistaCaracters La nova llista de caracter que es vol afegir
     */
    public boolean setLlistaCaracters(List<Character> llistaCaracters) {
        for (Character caracter : llistaCaracters) {
            if (caracter.charValue() > 'Z' || caracter.charValue() < 'A') {
                return false;
            }
        }
        alfabet.setLlistaCaracters(llistaCaracters);
        return true;
    }

    /**
     * Afegir nomes un caracter a l'alfabet
     *
     * @param caracter El nou caracter que es vol afegir
     * @return True si s'ha insertat bé el caracrer, False si ja el conte
     */
    public boolean addCaracter(Character caracter) {
        if (caracter.charValue() <= 'Z' && caracter.charValue() >= 'A') {
            return alfabet.addCaracter(caracter);
        } else {
            return false;
        }
    }

    /**
     * Esborrar un caracter de l'alfabet
     *
     * @param caracter El caracter que es vol esborrar
     * @return True si s'ha eliminat correctament, False altrament
     */
    public boolean removeCaracter(Character caracter) {
        return alfabet.removeCaracter(caracter);
    }

    /**
     * Retorna True si el caracter passat com reference existeix a l'alfabte,
     * False altrament
     *
     * @param caracter El caracter que es vol comprovar
     * @return True si el caracter existeix a l'alfabet, False altrament
     */
    public boolean conte(Character caracter) {
        return alfabet.conte(caracter);
    }

    /**
     * Retorna el caracter fi de sequencia
     *
     * @return El caracter fi de sequenci
     */
    public char getFiSequencia() {
        return alfabet.getFiSequencia();
    }

    /**
     * Fica a l'alfabet els caracters predefinits (Per defecte)
     */
    public void setDefault() {
        alfabet.setDefault();
    }

    /**
     * Retorna el tamany de l'alfabet
     *
     * @return El tamany de l'alfabet
     */
    public int getTamanyAlfabet() {
        return alfabet.size();
    }

    /**
     * Inserta ina sequencia al conjunt
     *
     * @param sequencia la cadena que ha de tindre la sequencia, ha de se vàlida
     * segons l'alfabet que es tingui en el moment de insertar
     * @param malaltia La malaltia que te associada aquesta sequencia
     * @param dni El pacient que te associat aquesta sequecia
     * @return Trus si s'ha insertat, False altrament
     */
    public boolean inserta(String sequencia, String malaltia, String dni) throws StackOverflowError {
        Sequencia seq = new Sequencia(sequencia, idMax);
        if (dni != null) {
            seq.setPacient(controladorPacients.getPacient(dni));
        }
        if (malaltia != null) {
            seq.setMalaltia(controladorMalaltiesSimptomes.consultaMalalties(malaltia, malaltia).first());
        }
        if (comprovarAlfabet(seq)) {
            if (conjuntSequencia.inserta(seq)) {
                idMax++;
                return true;
            }
        }
        return false;
    }

    /**
     * Donat un identificador retorna el dni associat a aquell identificador
     *
     * @param id Identificador que es vol consultar
     * @return El DNI associat amb aquell identificador o null si no existeix
     */
    public String getDni(Long id) {
        if (dnis == null) {
            return null;
        }
        return dnis.get(id);
    }

    /**
     * Donat un identificador retorna la malaltia associada
     *
     * @param id Identificador que es vol consultar
     * @return La malaltia associat amb aquell identificador o null si no
     * existeix
     */
    public String getMalaltia(Long id) {
        if (malalties == null) {
            return null;
        }
        return malalties.get(id);
    }

    /**
     * Donada una sequencia comprova si existex en el conjunt
     *
     * @param sequencia Sequencia a comprovar
     * @return True si la sequencia existeix, False en cas contrari
     */
    public boolean existeixSequencia(String sequencia) throws StackOverflowError {
        return !conjuntSequencia.consulta(sequencia).isEmpty();
    }

    /**
     * Donat un identificador comprova si en el conjunt existeix alguna
     * sequencia associada a ell
     *
     * @param id Identificador a comprovar
     * @return True si el identificador te una sequencia associada, False en cas
     * contrari
     */
    public boolean existeixSequencia(long id) {
        return !(conjuntSequencia.get(id) == null);
    }

    /**
     * Consulta a l'abre el patro introduit: el patro pot ser: {*, ., paraula}
     * Asterisc (*) retorna tots els elements de l'abre;Punt (.) retorna
     * qualssevol caracter pero nomes un;Paraula retorna els elements que son
     * iguals que la paraula. ex: A*AB*T.G*, AAAA, .., *
     *
     * @param regexp la expressio regular a buscar
     * @return Llista amb el nom de la sequencia que compleix la condicio o una
     * llista buida si no hi ha cap sequencia que la compleixi
     */
    public List<String> consulta(String regexp) throws StackOverflowError {
        return new ArrayList(conjuntSequencia.consulta(regexp));
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
    public Set<Sequencia> consultaSeq(String regexp) throws StackOverflowError {
        return conjuntSequencia.consultaseq(regexp);
    }

    /**
     * Elimina la sequencia que te com a cadena seq
     *
     * @param seq La cadena a eliminar
     * @return True si s'ha eliminar, False en cas contrari
     */
    public boolean elimina(String seq) throws StackOverflowError {
        Set<Sequencia> s = consultaSeq(seq);
        if (s.isEmpty()) {
            return false;
        }
        Sequencia sequencia = s.iterator().next();
        Pacient p = sequencia.getPacient();
        if (p != null) {
            if (p.esborrarAdn(sequencia)) {
                Malaltia m = sequencia.getMalaltia();
                if (m != null) {
                    m.esborrarSequencia(sequencia);
                }
                return conjuntSequencia.elimina(sequencia);
            }
            return false;
        }
        Malaltia m = sequencia.getMalaltia();
        if (m != null) {
            m.esborrarSequencia(sequencia);
        }
        return conjuntSequencia.elimina(sequencia);
    }

    /**
     * ELimina una sequencia que te com a indentificador el id
     *
     * @param id Indetificador de la sequencia a eliminar
     * @return True si s'ha eliminar correctament, False en cas contrari
     */
    public boolean elimina(long id) throws StackOverflowError {
        Sequencia sequencia = get(id);
        if (sequencia == null) {
            return false;
        }
        Pacient p = sequencia.getPacient();
        if (p != null) {
            if (p.esborrarAdn(sequencia)) {
                Malaltia m = sequencia.getMalaltia();
                if (m != null) {
                    m.esborrarSequencia(sequencia);
                }
                return conjuntSequencia.elimina(sequencia);
            }
            return false;
        }
        Malaltia m = sequencia.getMalaltia();
        if (m != null) {
            m.esborrarSequencia(sequencia);
        }
        return conjuntSequencia.elimina(sequencia);
    }
    /**
     * Buida totes les sequencies del sistema.
     * @throws StackOverflowError si hi ha hagut algun problema de stack
     */
    public void buidarSequencies() throws StackOverflowError {
        Set<Sequencia> seq = conjuntSequencia.consultaseq("*");

        for (Sequencia s : seq) {
            conjuntSequencia.elimina(s);
        }
    }
    /**
     * Buida l'alfabet.
     */
    public void buidarAlfabet() {
        alfabet.buidar();
    }

    /**
     * Modifica una sequencia
     *
     * @param id Identificador de la sequencia a modificar
     * @param malaltia La nova malaltia que te associada aquesta sequencia
     * @param dni El nou DNI que te associat aquesta sequencia
     */
    public void modificaSequencia(long id, String malaltia, String dni) {
        Sequencia sequencia = get(id);
        if (malaltia != null) {
            sequencia.setMalaltia(controladorMalaltiesSimptomes.consultaMalalties(malaltia, malaltia).first());
        }
        if (dni != null) {
            sequencia.setPacient(controladorPacients.getPacient(dni));
        }
    }

    /**
     * Modifica una sequencia
     *
     * @param seq La cadena de la sequencia a modificar
     * @param malaltia La nova malaltia que te associada aquesta sequencia
     * @param dni El nou DNI que te associat aquesta sequencia
     */
    public void modificaSequencia(String seq, String malaltia, String dni) {
        Set<Sequencia> s = consultaSeq(seq);
        Sequencia sequencia = s.iterator().next();
        if (malaltia != null) {
            sequencia.setMalaltia(controladorMalaltiesSimptomes.consultaMalalties(malaltia, malaltia).first());
        }
        if (dni != null) {
            sequencia.setPacient(controladorPacients.getPacient(dni));
        }
    }

    /**
     * Retorna el tamany del conjunt
     *
     * @return Tamany del conjunt
     */
    public int size() {
        return conjuntSequencia.size();
    }

    /**
     * Donat un identificador retorna la sequencia associada a ell
     *
     * @param id Identificador de la sequencia que es vol retornar
     * @return Sequencia que te aquest identificador
     */
    public Sequencia get(Long id) {
        return conjuntSequencia.get(id);
    }
    /**
     * 
     * @param sequencia la sequencia en questio
     * @return l'identificador de la sequencia
     * @throws StackOverflowError 
     */
    public Long get(String sequencia) throws StackOverflowError {
        Set<Sequencia> consultaSeq = consultaSeq(sequencia);
        if (consultaSeq.isEmpty()) {
            return null;
        }
        return consultaSeq.iterator().next().getId();
    }
    /**
     * 
     * @param id l'identificador de la sequencia que volem saber
     * @return l'string de la sequencia
     */
    public String getStringSeq(long id) {
        return get(id).getSequencia();
    }

    /**
     * Llegeix les sequencies del fitxer de disc
     *
     * @return True si s'ha llegit correctament, False altrament
     * @throws FileNotFoundException Si no es troba el fitxer
     * @throws IOException Si ocurre un problema de I/O
     */
    public boolean llegirSequencies(String path) throws FileNotFoundException, IOException, StackOverflowError {
        dnis = new TreeMap<Long, String>();
        malalties = new TreeMap<Long, String>();
        if (!controladorDades.obrirLlegir(path)) {
            return false;
        }
        long idGran = 0;
        Sequencia se;
        while (controladorDades.potLlegir()) {
            for (String string : controladorDades.llegir(ControladorDades.TAMANY_BLOCK)) {
                se = creaSequencia(string);
                conjuntSequencia.inserta(se);

                if (se.getId() > idGran) {
                    idGran = se.getId();
                }
            }
        }
        idMax = idGran + 1;
        controladorDades.tancarLlegir();
        return true;
    }

    /**
     * Escriu les sequencies al fitxer del disc
     *
     * @throws IOException Si ocurre un problema de I/O
     */
    public void escriureSequencies(String path) throws IOException, StackOverflowError {
        Set<Sequencia> seq = conjuntSequencia.consultaseq("*");

        if (!seq.isEmpty()) {
            List<String> llista = new ArrayList<String>();
            controladorDades.obrirEscriure(path + ".seq", false);

            for (Sequencia sequencia : seq) {
                llista.add(sequencia.toString());
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
     * Llegeix l'alfabet del fitxer de disc
     *
     * @return True si s'ha llegit correctament, False altrament
     * @throws FileNotFoundException Si no es troba el fitxer
     * @throws IOException Si ocurre un problema de I/O
     */
    public void llegirAlfabet(String path) throws FileNotFoundException, IOException {
        controladorDades.obrirLlegir(path);
        if (controladorDades.potLlegir()) {
            String string = controladorDades.llegir(ControladorDades.TAMANY_BLOCK).get(0);
            List<Character> l = new ArrayList<Character>();
            for (int i = 1; i < string.length(); i++) {
                l.add(string.charAt(i));
            }
            alfabet.setLlistaCaracters(l);
        }
        controladorDades.tancarLlegir();
    }

    /**
     * Escriu l'alfabet al fitxer del disc
     *
     * @throws IOException Si ocurre un problema de I/O
     */
    public void escriureAlfabet(String path) throws IOException {
        controladorDades.obrirEscriure(path + ".alf", false);
        String s = "" + alfabet.getFiSequencia();
        for (Character c : alfabet.getLlistaCaracters()) {
            s += c;
        }
        List<String> l = new ArrayList<String>();
        l.add(s);
        controladorDades.escriure(l);
        controladorDades.tancarEscriure();
    }

    private Sequencia creaSequencia(String string) {
        int pivote = string.indexOf(":");
        long id = Long.parseLong(string.substring(0, pivote));
        string = string.substring(pivote + 1);

        pivote = string.indexOf(":");
        String seq = string.substring(0, pivote);
        string = string.substring(pivote + 1);

        pivote = string.indexOf(":");
        String dni = string.substring(0, pivote);
        string = string.substring(pivote + 1);
        String malaltia = string.substring(0, string.length());

        Sequencia sequencia = new Sequencia(seq, id);
        if (dni != null) {
            dnis.put(id, dni);
        }
        if (malaltia != null) {
            malalties.put(id, malaltia);
        }
        return sequencia;

    }
    /**
     * Associa la sequencia amb el seu pacient i malaltia.
     */
    public void asociar() {
        for (Sequencia sequencia : conjuntSequencia.consultaseq("*")) {
            Long id = sequencia.getId();
            Pacient pacient = controladorPacients.getPacient(dnis.get(id));
            if (pacient != null) {
                sequencia.setPacient(pacient);
            }
            String malaltia = malalties.get(id);
            SortedSet<Malaltia> mal = controladorMalaltiesSimptomes.consultaMalalties(malaltia, malaltia);
            if (!mal.isEmpty()) {
                sequencia.setMalaltia(mal.first());
            }
        }
    }

    /**
     * Funció que retorna les sequencies que no tenen pacient associat.
     *
     * @return totes les sequencies queno tenen pacient associat
     */
    public List<String> getSequenciesSensePacient() throws StackOverflowError {
        List<String> resultat = new ArrayList<String>();
        for (Sequencia sequencia : consultaSeq("*")) {
            if (sequencia.getPacient() == null) {
                resultat.add(sequencia.getSequencia());
            }
        }
        return resultat;
    }

    private boolean comprovarAlfabet(Sequencia seq) {
        for (int i = 0; i < seq.getSequencia().length(); i++) {
            if (!alfabet.conte(seq.getSequencia().charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retorna totes les sequencies que hi ha al sistema
     *
     * @return Un llistat de totes les sequencies que hi ha
     */
    public List<String> getSequencies() throws StackOverflowError {
        List<String> llista = new ArrayList<String>();
        String str;

        for (Sequencia seq : consultaSeq("*")) {
            str = seq.getId() + ": " + seq.getSequencia();

            llista.add(str);
        }
        
        return llista;
    }
}
