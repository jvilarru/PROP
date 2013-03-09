package edu.upc.prop.domini;

import java.util.*;

public class Simptoma {

    private String nom;
    private TreeSet<Malaltia> malaltiesAssociades;
    private AVL pacientsAssociats;

    /**
     * Crea un simptoma amb el nom especificat
     *
     * @param nom El nom del simptoma
     */
    public Simptoma(String nom) {
        Comparator<Malaltia> comp = new Comparator<Malaltia>() {

            @Override
            public int compare(Malaltia m1, Malaltia m2) {
                return m1.getNom().compareTo(m2.getNom());
            }
        };

        this.nom = nom;
        pacientsAssociats = new AVL();
        malaltiesAssociades = new TreeSet<Malaltia>(comp);
    }

    /**
     * Retorna el nom del simptoma
     *
     * @return El nom del simptoma
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifica el nom al siptoma
     *
     * @param nom El nou nom que tindra el simptoma
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retorna totes les malalties associades al simptoma
     *
     * @return Una llista amb totes les malalties associades al simptoma o una
     * llista buida si no te cap
     */
    public List<Malaltia> getMalaltiesAssociades() {
        List<Malaltia> llista = new ArrayList<Malaltia>();
        Iterator<Malaltia> iter = malaltiesAssociades.iterator();
        while (iter.hasNext()) {
            llista.add(iter.next());
        }
        return llista;
    }

    /**
     * Afegeix una malaltia al simptoma
     *
     * @param malaltia La malaltia que es vol associar al simptoma
     * @return True si la malaltia s'ha afegit correctament, False altrament
     */
    public boolean afegirMalaltia(Malaltia malaltia) {
        if (!malaltiesAssociades.contains(malaltia)) {
            boolean b = malaltiesAssociades.add(malaltia);
            malaltia.afegirSimptoma(this);

            return b;
        }

        return false;
    }

    /**
     * Esborra una malaltia del simptoma
     *
     * @param malaltia La malaltia que es vol esborrar del simptoma
     * @return True si la malaltia s'ha esborrat correctament, False altrament
     */
    public boolean esborrarMalaltia(Malaltia malaltia) {
        if (malaltiesAssociades.contains(malaltia)) {
            boolean b = malaltiesAssociades.remove(malaltia);
            malaltia.esborrarSimptoma(this);

            return b;
        }

        return false;
    }

    /**
     * Consulta si el simptoma te la malaltia especificada
     *
     * @param malaltia La malaltia que es vol consultar
     * @return True si el simptoma te associat la malaltia en concret, False en
     * cas contrari
     */
    public boolean teMalaltia(Malaltia malaltia) {
        return malaltiesAssociades.contains(malaltia);
    }

    /**
     * Retorna tots els pacients associats al simptoma
     *
     * @return Una llista de tots els pacients associats al simptoma o una
     * llista buida si no te cap
     */
    public List<Pacient> getPacientsAssociats() {
        return pacientsAssociats.consulta("00000000", "99999999");
    }

    /**
     * Afegeix un pacient al simptoma
     *
     * @param pacient El pacient que es vol asociar
     * @return True si el pacient s'ha afeit correctament, False altrament
     */
    public boolean afegirPacient(Pacient pacient) {
        if (pacient != null) {
            if (pacientsAssociats.consulta(pacient.getDni(), pacient.getDni()).isEmpty()) {
                boolean b = pacientsAssociats.inserta(pacient);
                pacient.afegirSimptoma(this);

                return b;
            }
        }
        return false;
    }

    /**
     * Esborra un pacient del simptoma
     *
     * @param pacient El Pacient que es vol esborrar del simptoma
     * @return True si el pacient s'ha esborrat correctament, False altrament
     */
    public boolean esborrarPacient(Pacient pacient) {
        if (!pacientsAssociats.consulta(pacient.getDni(), pacient.getDni()).isEmpty()) {
            boolean b = pacientsAssociats.esborra(pacient.getDni());
            pacient.esborrarSimptoma(this);

            return b;
        }

        return false;
    }

    /**
     * Consulta si el simptoma te associat el pacient especificat
     *
     * @param paceint El pacient que es vol consultar
     * @return True si el simptoma te associat el pacient en concret, False en
     * cas contrari
     */
    public boolean tePacient(Pacient pacient) {
        return !pacientsAssociats.consulta(pacient.getDni(), pacient.getDni()).isEmpty();
    }

    @Override
    public String toString() {
        String dnis = "{";
        for (Pacient pacient : pacientsAssociats.consulta("00000000", "99999999")) {
            dnis += pacient.getDni() + ',';
        }
        if (dnis.charAt(dnis.length() - 1) != '{') {
            dnis = dnis.substring(0, dnis.length() - 1);
        }
        dnis += "}";
        String malalties = "{";
        Iterator<Malaltia> it = malaltiesAssociades.iterator();
        while (it.hasNext()) {
            malalties += it.next().getNom() + ',';
        }
        if (malalties.charAt(malalties.length() - 1) != '{') {
            malalties = malalties.substring(0, malalties.length() - 1);
        }
        malalties += "}";
        return nom + ":" + malalties + ":" + dnis;
    }

    @Override
    public boolean equals(Object o) {
        return nom.equals(((Simptoma) o).nom);
    }

    public void esborrarMalaltiaIntern(Malaltia m) {
        malaltiesAssociades.remove(m);
    }

    public void esborrarPacientIntern(Pacient pacient) {
        pacientsAssociats.esborra(pacient.getDni());
    }
    /** Funció que et retorna la quantitat de pacients del simptoma.
     * 
     * @return el nombre de pacients del simptoma
     */
    public int tamPacients() {
        return pacientsAssociats.size();
    }
}
