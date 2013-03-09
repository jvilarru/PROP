package edu.upc.prop.domini;

import java.util.*;

public class Pacient {

    public static final char SEXE_MASCULI = 'M';
    public static final char SEXE_FEMENI = 'F';
    private String nom;
    private String cognom;
    private String dataNaixament;
    private char sexe;
    private String dni;
    private int edat;
    private Historial historial;
    private Sequencia adn;
    private List<Simptoma> simptomes;
    private List<Malaltia> malalties;

    /**
     * Crea un nou pacient amb els parametres que se li passa
     *
     * @param dni El dni del pacient
     * @param dataNaixament La data de naixement. La forma de la data ha de ser:
     * dd/mm/yyyy
     */
    public Pacient(String dni, String dataNaixament, char sexe, Sequencia adn, boolean disc) {
        this.dni = dni;
        this.sexe = sexe;
        this.dataNaixament = dataNaixament;
        this.nom = "";
        this.cognom = "";
        historial = new Historial();
        simptomes = new ArrayList<Simptoma>();
        malalties = new ArrayList<Malaltia>();

        setAdn(adn, disc);
        calculaEdat(dataNaixament);
    }

    /**
     * Retorna el dni del pacient
     *
     * @return El dni del pacient
     */
    public String getDni() {
        return dni;
    }

    /**
     * Retorna la edat del pacient
     *
     * @return La edat del pacient
     */
    public int getEdat() {
        return edat;
    }

    /**
     * Retorna El sexe del pacient
     *
     * @return El sexe del pacient
     */
    public char getSexe() {
        return sexe;
    }

    /**
     * Retorna els cognoms del pacient
     *
     * @return Els cognoms del pacient
     */
    public String getCognom() {
        return cognom;
    }

    /**
     * Modifica els cognoms del pacient
     *
     * @param cognom Els nous cognoms del pacient
     */
    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    /**
     * Retorna la data de naixement de pacient
     *
     * @return La data de naixement
     */
    public String getDataNaixament() {
        return dataNaixament;
    }

    /**
     * Retorna el nom del pacient
     *
     * @return El nom del pacient
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifica el nom del pacient
     *
     * @param nom El nou nom del pacient
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retorna l'historial del pacient
     *
     * @return L'historial del pacient
     */
    public Historial getHistorial() {
        return historial;
    }

    /**
     * Buida l'historial del pacient
     */
    public void buidarHistorial() {
        historial.buidar();
    }

    /**
     * Retorna l'adn del pacient
     *
     * @return L'adn del pacient
     */
    public Sequencia getAdn() {
        return adn;
    }

    /**
     * Modifica l'adn del pacient
     *
     * @param adn El nou and del pacient
     */
    public void setAdn(Sequencia adn, boolean disc) {
        if (adn != null) {
            if (!disc) {
                historial.inserta(adn);
            }

            this.adn = adn;
            adn.setPacient(this);
        }
    }

    /**
     * Retorna tots els simptomes del pacient
     *
     * @return Una llista amb tots els simptomes del pacient o una llista buida
     * si el no te cap
     */
    public List<Simptoma> getSimptomes() {
        return simptomes;
    }

    /**
     * Afegeix el simptoma especificat al pacient
     *
     * @param simptoma El simptoma que s'afegira al pacient
     * @return True si s'ha afegit correctament, False altrament
     */
    public boolean afegirSimptoma(Simptoma simptoma) {
        if (simptoma != null) {
            if (!simptomes.contains(simptoma)) {
                boolean b = simptomes.add(simptoma);
                simptoma.afegirPacient(this);

                return b;
            }
        }
        return false;
    }

    /**
     * Esborra el simptoma especificat del pacient
     *
     * @param simptoma El simptoma que es vol esborrar
     * @return True si s'ha esborrat correctament, False altrament
     */
    public boolean esborrarSimptoma(Simptoma simptoma) {
        if (simptomes.contains(simptoma)) {
            boolean b = simptomes.remove(simptoma);
            simptoma.esborrarPacient(this);

            return b;
        }

        return false;
    }

    /**
     * Retorna totes les malalties que te el pacient
     *
     * @return Una llista amb totes les malalties del pacient o una llsiat buida
     * si no te cap
     */
    public List<Malaltia> getMalalties() {
        return malalties;
    }

    /**
     * Afegeix la malaltia especificada al pacient
     *
     * @param malaltia La malaltia que s'afegira al pacient
     * @return True si s'ha afegit correctament, False altrament
     */
    public boolean afegirMalaltia(Malaltia malaltia) {
        if (malaltia != null) {
            if (!malalties.contains(malaltia)) {
                boolean b = malalties.add(malaltia);
                malaltia.addPacient(this);

                return b;
            }
        }
        return false;
    }

    /**
     * Esborra la malaltia especificada del pacient
     *
     * @param malaltia La malaltia que es vol esborrar
     * @return True si s'ha esborrat correctament, False altrament
     */
    public boolean esborrarMalaltia(Malaltia malaltia) {
        if (malalties.contains(malaltia)) {
            boolean b = malalties.remove(malaltia);
            malaltia.esborrarPacient(this);

            return b;
        }

        return false;
    }

    /**
     * Esborra un adn del historial del pacient
     *
     * @param adn L'adn que es vol esborrar
     * @return True si s'ha esborrat, False si es el seu adn actual que no es
     * pot esborrar
     */
    public boolean esborrarAdn(Sequencia adn) {
        if (this.adn.equals(adn)) {
            return false;
        }

        return historial.esborra(adn);
    }

    /**
     * Inserta un conjunt de sequencies amb unes dates a l'historial del pacient
     *
     * @param mapa El conjunt que es vol insertar a l'historial
     */
    public void insertaHistorial(Map<Date, Sequencia> mapa) {
        historial.inserta(mapa);
    }

    private void calculaEdat(String dataNaixement) {
        int dia = Integer.parseInt(dataNaixement.substring(0, 2));
        int mes = Integer.parseInt(dataNaixement.substring(3, 5));
        int any = Integer.parseInt(dataNaixement.substring(6, 10));

        int anyActual = Calendar.getInstance().get(Calendar.YEAR);
        int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int diaActual = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        edat = anyActual - any;

        if (mesActual < mes) {
            edat--;
        } else if (mesActual == mes) {
            if (diaActual < dia) {
                edat--;
            }
        }
    }

    @Override
    public String toString() {
        String resultat = dni + ":" + nom + ":" + cognom + ":" + dataNaixament + ":" + sexe + ":" + adn.getId() + ":";
        resultat += '{';
        for (int i = 0; i < simptomes.size(); i++) {
            resultat += simptomes.get(i).getNom();
            if (i < simptomes.size() - 1) {
                resultat += ',';
            }
        }
        resultat += "}:{";
        for (int i = 0; i < malalties.size(); i++) {
            resultat += malalties.get(i).getNom();
            if (i < malalties.size() - 1) {
                resultat += ',';
            }
        }
        resultat += "}:";
        resultat += historial.toString();
        return resultat;
    }

    public void esborrarMalaltiaIntern(Malaltia m) {
        malalties.remove(m);
    }

    public void esborrarSimptomaIntern(Simptoma s) {
        simptomes.remove(s);
    }
}
