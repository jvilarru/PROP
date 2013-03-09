package edu.upc.prop.dades;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorDades {

    public static final String HOME_DIRECTORY = "Genome/";
    private static final String FITXER_PACIENTS = "pacients.txt";
    private static final String FITXER_MALALTIES = "malalties.txt";
    private static final String FITXER_SIMPTOMES = "simptomes.txt";
    private static final String FITXER_ALFABET = "alfabet.txt";
    private static final String FITXER_SEQUENCIES = "sequencies.txt";
    private static final String FITXER_RESULTATS = "resultats.txt";
    public static final int TAMANY_BLOCK = 10;
    public static final int TYPE_PACIENTS = 0;
    public static final int TYPE_MALALTIES = 1;
    public static final int TYPE_SIMPTOMES = 2;
    public static final int TYPE_ALFABET = 3;
    public static final int TYPE_SEQUENCIES = 4;
    public static final int TYPE_RESULTATS = 5;
    private Fitxer fitxer;

    /**
     * Crear el controlador
     */
    public ControladorDades() {
        checkHome();
    }

    /**
     * Obrir el fitxer per llegir
     *
     * @param type El tipus ha de ser un dels que pertanyen al controlador.
     * Indica quin fitxer s'ha de llegir (pacients, sequencines...)
     * @return True si s'ha obert correctament, False si no existeix el fitxer
     * passat al constructor o hi ha hagut algun problem
     * @throws FileNotFoundException Si ocurre un error de I/O
     */
    public boolean obrirLlegir(int type) throws FileNotFoundException {
        String path = getPath(type);

        if (path.equals("")) {
            return false;
        }

        fitxer = new Fitxer(path);

        return fitxer.obrirLlegir();
    }

    /**
     * Obrir el fitxer per llegir
     *
     * @param path La ruta del fitxer a obrir
     * @return True si s'ha obert correctament, False si no existeix el fitxer
     * passat al constructor o hi ha hagut algun problem
     * @throws FileNotFoundException Si ocurre un error de I/O
     */
    public boolean obrirLlegir(String path) throws FileNotFoundException {
        if (path.equals("")) {
            return false;
        }

        fitxer = new Fitxer(path);

        return fitxer.obrirLlegir();
    }

    public boolean obrirLlegirResultat(String nom) throws FileNotFoundException {
        if (nom.equals("")) {
            return false;
        }

        fitxer = new Fitxer(HOME_DIRECTORY + "Resultats/", nom);

        return fitxer.obrirLlegir();
    }

    /**
     * Obrir el fitxer per escriure, si no existeix el fitxer es crea
     *
     * @param type El tipus ha de ser un dels que pertanyen al controlador.
     * Indica quin fitxer s'ha de llegir (pacients, sequencines...)
     * @param append si True indica que s'escriure al final del fitxer, False
     * s'escriue al principi
     * @return True si s'ha obert correctament, False si hi ha hagut algun
     * @throws IOException Si ocurre un error de I/O
     */
    public boolean obrirEscriure(int type, boolean append) throws IOException {
        String path = getPath(type);

        if (path.equals("")) {
            return false;
        }

        fitxer = new Fitxer(path);

        return fitxer.obrirEscriure(append);
    }

    /**
     * Obrir el fitxer per escriure, si no existeix el fitxer es crea
     *
     * @param path
     * @param append si True indica que s'escriure al final del fitxer, False
     * s'escriue al principi
     * @return True si s'ha obert correctament, False si hi ha hagut algun
     * @throws IOException Si ocurre un error de I/O
     */
    public boolean obrirEscriure(String path, boolean append) throws IOException {
        if (path.equals("")) {
            return false;
        }

        fitxer = new Fitxer(path);

        return fitxer.obrirEscriure(append);
    }

    /**
     * Tancar el fitxer que s'ha obert anteriorment, si no s'ha obert no fa res
     *
     * @throws IOException Si ocurre un error de I/O
     */
    public void tancarLlegir() throws IOException {
        if (fitxer != null) {
            fitxer.tancarLlegir();
            fitxer = null;
        }
    }

    /**
     * Tancar el fitxer que s'ha obert anteriorment, si no s'ha creat no fa res
     *
     * @throws IOException Si ocurre un error de I/O
     */
    public void tancarEscriure() throws IOException {
        if (fitxer != null) {
            fitxer.tancarEscriure();
            fitxer = null;
        }
    }

    /**
     * Llegeix un block del fitxer i ho passa com una llista de cadenes, cada
     * posicio de la llista es una linea del fitxer, si no es pot llegir retorna
     * un cadea buida
     *
     * @param len El tamany de block que es vol llegir
     * @return Una llista de cadenes on cada posicio de la llista es una linea
     * del fitxer
     * @throws IOException Si ocurre un error de I/O
     */
    public List<String> llegir(int len) throws IOException {
        if (!potLlegir()) {
            return new ArrayList<String>();
        }

        return fitxer.llegir(len);
    }

    /**
     * Escriur el block que se li passa com a paramatres al fitxer, cada posicio
     * de la llista es una linea al fitxer, si no es pot escriure retorna una
     * cadena buida
     *
     * @param dades El block de dades que es vol esciure al fitxer
     * @return True si pot esciure al fitxer, False altrament
     * @throws IOException Si ocurre algun error de I/O
     */
    public boolean escriure(List<String> dades) throws IOException {
        if (!potEsciure()) {
            return false;
        }

        return fitxer.escriure(dades);
    }

    public List<String> getNomResultats() {
        Fitxer carpeta = new Fitxer(HOME_DIRECTORY, "Resultats/");

        return carpeta.getFills();
    }

    public void creaFitxer(String path, boolean isDirectory) throws IOException {
        creaFitxer("", path, isDirectory);
    }

    public boolean creaFitxer(String parent, String child, boolean isDirectory) throws IOException {
        Fitxer f = new Fitxer(HOME_DIRECTORY + parent, child);

        return f.crear(isDirectory);
    }

    /**
     * Indica si es pot llegir del fitxer, es pot llegir si s'ha obert el fitxer
     * i si no s'ha arribat al final
     *
     * @return True si es pot llegir segons lo descrit abans, False altrament
     * @throws IOException Si ocurre un error de I/O
     */
    public boolean potLlegir() throws IOException {
        if (fitxer == null) {
            return false;
        }

        return fitxer.potLlegir();
    }

    /**
     * Indica si es pot escriure al fitxer, es pot llegir si s'ha obert el
     * fitxer
     *
     * @return True si es pot esccriure segons lo descrit abans, False altrament
     */
    public boolean potEsciure() {
        if (fitxer == null) {
            return false;
        }

        return fitxer.potEsciure();
    }

    public String crearCarpetaAvui(boolean isNecessaryCreate) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ruta = "";

        String child = sdf.format(new Date());

        Fitxer fi = new Fitxer("Genome/Dades/", child);
        if (!fi.existeix() && isNecessaryCreate) {
            fi.crear(true);

            ruta = fi.getRuta();
        } else if (fi.existeix()) {
            ruta = fi.getRuta();
        }

        return ruta;
    }

    private String getPath(int type) {
        String path = "";
        switch (type) {
            case TYPE_PACIENTS:
                path = FITXER_PACIENTS;
                break;
            case TYPE_MALALTIES:
                path = FITXER_MALALTIES;
                break;
            case TYPE_SIMPTOMES:
                path = FITXER_SIMPTOMES;
                break;
            case TYPE_ALFABET:
                path = FITXER_ALFABET;
                break;
            case TYPE_SEQUENCIES:
                path = FITXER_SEQUENCIES;
                break;
            case TYPE_RESULTATS:
                path = FITXER_RESULTATS;
                break;
        }

        return path;
    }

    private void checkHome() {
        try {
            Fitxer fit = new Fitxer("Genome/");
            if (!fit.existeix()) {
                fit.crear(true);
            }

            fit = new Fitxer("Genome/", "Dades/");
            if (!fit.existeix()) {
                fit.crear(true);
            }

            fit = new Fitxer("Genome/", "Resultats/");
            if (!fit.existeix()) {
                fit.crear(true);
            }
        } catch (IOException iox) {
        }
    }
}
