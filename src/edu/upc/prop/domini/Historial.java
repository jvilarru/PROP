package edu.upc.prop.domini;

import java.util.*;

public class Historial {

    private TreeMap<Date, Sequencia> historial;

    /**
     * Crea un historial buit
     */
    public Historial() {
        this.historial = new TreeMap<Date, Sequencia>();
    }

    /**
     * Inserta en l'historial un adn d'un pacient
     *
     * @param sequencia L'adn del pacient a insertar
     * @return True si s'ha insertat correctament, false altrament
     */
    public boolean inserta(Sequencia sequencia) {
        Date date = new Date();
        if (sequencia == null) {
            return false;
        }
        this.historial.put(date, sequencia);
        return true;
    }

    /**
     * Retorna toes les dates on s'han insertat adns d'un pacient
     *
     * @return Un llista de dates o buit si el pacient no te historial
     */
    public Set<Date> claus() {
        return historial.keySet();
    }

    /**
     * Donat un rang de dates (inici, fi) retorna tots els ADNs del pacient que
     * s'han afegit entre aquestes dues dates
     *
     * @param inici Data inici
     * @param fi Data fi
     * @return Tots ADNs que estan entre el rang especificat
     */
    public Map<Date, Sequencia> consulta(Date inici, Date fi) {
        if (historial.isEmpty() || inici == null || fi == null) {
            return null;
        }
        if (inici.after(fi)) {
            return new HashMap<Date, Sequencia>();
        }
        boolean a = false;
        boolean b = false;
        if (historial.containsKey(inici)) {
            a = true;
        }
        if (historial.containsKey(fi)) {
            b = true;
        }
        if (historial.firstKey().after(inici)) {
            inici = historial.firstKey();
        }
        if (!historial.containsKey(inici)) {
            return null;
        }
        return historial.subMap(inici, a, fi, b);
    }

    /**
     * Retorna el tamany de l'historial
     *
     * @return EL tamany de l'historial
     */
    public int size() {
        return historial.size();
    }

    /**
     * Donada una data (d) retorna la sequencia (ADN) que s'ha fet en aquella
     * data
     *
     * @param d Data que es vol consultar
     * @return L'ADN del pacient que s'ha agafat en la data especificada
     */
    public Sequencia consulta(Date d) {
        if (d == null || !historial.containsKey(d)) {
            return null;
        }
        return historial.get(d);
    }

    /**
     * Buida tot l'historial
     */
    public void buidar() {
        for (Date d : historial.keySet()) {
            historial.get(d).setPacient(null);
        }

        historial.clear();
    }

    /**
     * Esborra un ADN de l'historial del pacient
     *
     * @param sequencia ADN que es vol esborrar
     * @return True si s'ha pogut esborrar correctament, False altrament
     */
    public boolean esborra(Sequencia sequencia) {
        for (Date d : historial.keySet()) {
            if (historial.get(d).equals(sequencia)) {
                historial.remove(d);
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna tots els adns de pacient
     *
     * @return Una llista amb tots els adns del pacient o buit si l'historial
     * esta buit
     */
    public List<Sequencia> getSequencies() {
        List<Sequencia> l = new ArrayList<Sequencia>();
        for (Date d : historial.keySet()) {
            l.add(historial.get(d));
        }
        return l;
    }

    @Override
    public String toString() {
        Calendar calendar = Calendar.getInstance();
        String resultat = "{";
        List<Date> l = new ArrayList<Date>();
        l.addAll(claus());
        for (int i = 0; i < l.size(); i++) {
            calendar.setTime(l.get(i));
            resultat += calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.HOUR_OF_DAY) + "/" + calendar.get(Calendar.MINUTE) + "/" + calendar.get(Calendar.SECOND) + ":" + historial.get(l.get(i)).getId();
            if (i < l.size() - 1) {
                resultat += ',';
            }
        }
        resultat += "}";
        return resultat;
    }

    /**
     * Inserta un conjunt de sequencies amb unes dates a l'historial del pacient
     *
     * @param mapa El conjunt que es vol insertar a l'historial
     */
    public boolean inserta(Map<Date, Sequencia> mapa) {
        if (mapa == null || mapa.isEmpty()) {
            return false;
        }
        historial.putAll(mapa);
        return true;
    }
}
