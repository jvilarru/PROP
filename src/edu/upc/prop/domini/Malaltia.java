package edu.upc.prop.domini;

import java.util.ArrayList;
import java.util.List;

public class Malaltia {

    private String nom;
    private String descripcio;
    private AVL pacientsAssociats;
    private List<Sequencia> sequenciesAssociades;
    private List<Simptoma> simptomesAssociats;

    /**
     * Crea una malaltia amb el nom espcificat
     *
     * @param nom El nom de la malaltia
     */
    public Malaltia(String nom) {
        this.nom = nom;
        descripcio = "";
        pacientsAssociats = new AVL();
        sequenciesAssociades = new ArrayList<Sequencia>();
        simptomesAssociats = new ArrayList<Simptoma>();
    }

    /**
     * Retorna el nom de la malaltia
     *
     * @return El nom de la malaltia
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retorna la descripcio de la malaltia
     *
     * @return La descripcio de la malaltia
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Modifica la descripcio de la malaltia
     *
     * @param descripcio La nova descripcio de la malaltia
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Retorna tots els simptomes associats a la malaltia
     *
     * @return Una llista de tots el simptomes associats a la malaltia o una
     * llista buida si no te caps
     */
    public List<Simptoma> getSimptomesAssociats() {
        return simptomesAssociats;
    }

    /**
     * Retorna totes les sequencies associades a la malaltia
     *
     * @return Una llista de totes les sequencies associades a la malaltia o una
     * llista buida si no te cap
     */
    public List<Sequencia> getSequenciesAssociades() {
        return sequenciesAssociades;
    }

    /**
     * Retorna tots els pacients associats a la malaltia
     *
     * @return Una llista de tots els apcients associats a la malaltia o una
     * llista buida si no te cap
     */
    public List<Pacient> getPacientsAssociats() {
        return pacientsAssociats.consulta("00000000", "99999999");
    }

    /**
     * Afegeix un simptoma a la malaltia
     *
     * @param simptoma El simptoma que es vol afegir
     * @return True si s'ha afegit correctament, False altrament
     */
    public boolean afegirSimptoma(Simptoma simptoma) {
        if (simptoma != null) {
            if (!simptomesAssociats.contains(simptoma)) {
                boolean b = simptomesAssociats.add(simptoma);
                simptoma.afegirMalaltia(this);

                return b;
            }
        }

        return false;
    }

    /**
     * Esborra un simptoma de la malaltia
     *
     * @param simptoma El simptoma que es vol esborrar
     * @return Tru si s'ha esborrat correctament, False altrament
     */
    public boolean esborrarSimptoma(Simptoma simptoma) {
        if (simptomesAssociats.contains(simptoma)) {
            boolean b = simptomesAssociats.remove(simptoma);
            simptoma.esborrarMalaltia(this);

            return b;
        }

        return false;
    }

    /**
     * Consulta si la malaltia te el simptoma especificat
     *
     * @param simptoma Simtoma que es vol consultar
     * @return True si la malaltia te associada aquest simptoma, False en cas
     * contrari
     */
    public boolean teSimptoma(Simptoma simptoma) {
        return simptomesAssociats.contains(simptoma);
    }

    /**
     * Afegeix la sequencia passada a les sequencies associades
     *
     * @param sequencia Sequencia que es vol insertar
     * @return nothing
     */
    public void addSequencia(Sequencia sequencia) {
        if (sequencia != null) {
            if (!sequenciesAssociades.contains(sequencia)) {
                sequenciesAssociades.add(sequencia);
                sequencia.setMalaltia(this);
            }
        }
    }

    /**
     * Consulta si la malaltia te el simptoma passat
     *
     * @param sequencia Sequencia que es vol consultar
     * @return True si la malaltia te associada aquesta sequencia, False en cas
     * contrari
     */
    public boolean teSequencia(Sequencia sequencia) {
        return sequenciesAssociades.contains(sequencia);
    }

    /**
     * Esborra un simptoma de la malaltia
     *
     * @param sequencia La sequencia que es vol esborrar
     * @return True si s'ha esborrat correctament, False altrament
     */
    public boolean esborrarSequencia(Sequencia sequencia) {
        sequencia.setMalaltia(null);
        return sequenciesAssociades.remove(sequencia);
    }

    /**
     * Afegeix el pacient especificat als pacients associats
     *
     * @param p El pacient que es vol afegir
     * @return Trus si s'ha afegit correctament, False altrament
     */
    public boolean addPacient(Pacient p) {
        if (p != null) {
            if (pacientsAssociats.consulta(p.getDni(), p.getDni()).isEmpty()) {
                boolean b = pacientsAssociats.inserta(p);
                p.afegirMalaltia(this);

                return b;
            }
        }

        return false;
    }

    /**
     * Esborra un pacient de la malaltia
     *
     * @param pacient El pacient que es vol esborrar
     * @return True si s'ha esborrat correctament, False altrament
     */
    public boolean esborrarPacient(Pacient pacient) {
        if (!pacientsAssociats.consulta(pacient.getDni(), pacient.getDni()).isEmpty()) {
            boolean b = pacientsAssociats.esborra(pacient.getDni());
            pacient.esborrarMalaltia(this);

            return b;
        }

        return false;
    }

    /**
     * Consulta si la malaltia te associat el pacient especificat
     *
     * @param pacient El pacient a consultar
     * @return True si el pacient esta associat amb la malaltia, False altrament
     */
    public boolean tePacient(Pacient pacient) {
        if (pacientsAssociats.consulta(pacient.getDni(), pacient.getDni()).isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String descripcioMalaltia = "";
        if (descripcio != null) {
            descripcioMalaltia = descripcio;
        }
        String dnis = "{";
        for (Pacient pacient : pacientsAssociats.consulta("00000000", "99999999")) {
            dnis += pacient.getDni() + ",";
        }
        if (dnis.charAt(dnis.length() - 1) != '{') {
            dnis = dnis.substring(0, dnis.length() - 1);
        }
        dnis += "}";
        String ids = "{";
        for (Sequencia sequencia : sequenciesAssociades) {
            ids += sequencia.getId() + ",";
        }
        if (ids.charAt(ids.length() - 1) != '{') {
            ids = ids.substring(0, ids.length() - 1);
        }
        ids += "}";
        String simptomes = "{";
        for (Simptoma simptoma : simptomesAssociats) {
            simptomes += simptoma.getNom() + ",";
        }
        if (simptomes.charAt(simptomes.length() - 1) != '{') {
            simptomes = simptomes.substring(0, simptomes.length() - 1);
        }
        simptomes += "}";
        return (nom + ":" + descripcioMalaltia + ":" + dnis + ":" + ids + ":" + simptomes);
    }

    @Override
    public boolean equals(Object o) {
        return nom.equals(((Malaltia) o).nom);
    }
    
    /** Funció interna d'esborrar simptoma.
     * 
     * @param s el simtoma a eesborrar
     */
    public void esborrarSimptomaIntern(Simptoma s) {
        simptomesAssociats.remove(s);
    }
    
    /** Funció interna d'esborrar simptoma.
     * 
     * @param p el pacient a eesborrar
     */
    public void esborrarPacientIntern(Pacient p) {
        pacientsAssociats.esborra(p.getDni());
    }
    /** Funció que retorna el nombre de pacients de la malaltia
     * 
     * @return el nombre de pacients associats
     */
    public int tamPacients() {
        return  pacientsAssociats.size();
    }
}
