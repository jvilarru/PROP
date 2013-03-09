package edu.upc.prop.dades;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Fitxer {

    private File file;
    private BufferedReader reader;
    private BufferedWriter writer;

    /**
     * Crea una instancia amb la ruta especificada
     *
     * @param path La ruta del fitxer que es vol tratar
     */
    public Fitxer(String path) {
        file = new File(path);
    }

    public Fitxer(String parent, String child) {
        file = new File(parent, child);
    }

    /**
     * Crea un fitxer vuit amb el path que se li ha passat al constructor
     *
     * @return True si s'ha creat correctament i False si ja existeix un altra
     * fitxer amb el mateix nom
     * @throws IOException Si ocurra un error de I/O
     */
    public boolean crear(boolean isDirectory) throws IOException {
        if (isDirectory) {
            return file.mkdir();
        }

        return file.createNewFile();
    }

    /**
     * Esborra el fitxer denotat amb el path que se li pasa al constructor, si
     * es un directori i esta viut s'esborra, si esta ple s'ha d'utilitzar el
     * parametre force
     *
     * @param recursive Si es true i es un directori s'esborren tots els arxius
     * dins el directori, si false no s'seborren.
     * @return True si s'ha efectuat correctament l'esborrat, False altrament
     */
    public boolean esborrarFitxer(boolean recursive) {
        if (file == null) {
            return false;
        }

        if (recursive && file.isDirectory()) {
            boolean ok = false;
            for (File fil : file.listFiles()) {
                ok = fil.delete();
            }
            if (ok) {
                file.delete();
            }

            return ok;
        } else {
            return file.delete();
        }
    }

    /**
     * Comprova si existeix un fitxer amb el path que se li ha passat al
     * constructor
     *
     * @return True si existeix, False altrament
     */
    public boolean existeix() {
        return file.exists();
    }

    /**
     * Obrir el fitxer per llegir
     *
     * @return True si s'ha obert correctament, False si no existeix el fitxer
     * passat al constructor o hi ha hagut algun problem
     * @throws FileNotFoundException si ocurre un error de I/O
     */
    public boolean obrirLlegir() throws FileNotFoundException {
        if (file == null || !existeix()) {
            return false;
        }

        reader = new BufferedReader(new FileReader(file));

        return true;
    }

    /**
     * Tancar el fitxer que s'ha obert anteriorment, si no s'ha obert no fa res
     *
     * @throws IOException si ocurre un error de I/O
     */
    public void tancarLlegir() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }

    /**
     * Obrir el fitxer per escriure, si no existeix el fitxer es crea
     *
     * @param append si True indica que s'escriure al final del fitxer, False
     * s'escriue al principi
     * @return True si s'ha obert correctament, False si hi ha hagut algun
     * problem
     * @throws IOException
     */
    public boolean obrirEscriure(boolean append) throws IOException {
        if (file == null) {
            return false;
        }

        if (!existeix()) {
            crear(false);
        }

        writer = new BufferedWriter(new FileWriter(file, append));

        return true;
    }

    /**
     * Tancar el fitxer que s'ha obert anteriorment, si no s'ha creat no fa res
     *
     * @throws IOException si ocurre un error de I/O
     */
    public void tancarEscriure() throws IOException {
        if (writer != null) {
            writer.close();

        }
    }

    /**
     * Llegeix un block del fitxer i ho passa com una llista de cadenes, cada
     * posicio de la llista es una linea del fitxer
     *
     * @param len El tamany de block que es vol llegir
     * @return Una llista de cadenes on cada posicio de la llista es una linea
     * del fitxer, si no pot llegir retorna una llista buida
     * @throws IOException si ocurre un error de I/O
     */
    public List<String> llegir(int len) throws IOException {
        List<String> dades = new ArrayList<String>();

        int i = 0;
        while (i < len && potLlegir()) {
            dades.add(reader.readLine());
            i++;
        }

        return dades;
    }

    /**
     * Escriur el block que se li passa com a paramatres al fitxer, cada posicio
     * de la llista es una linea al fitxer
     *
     * @param dades El block de dades que es vol esciure al fitxer
     * @return True si pot esciure al fitxer, False altrament
     * @throws IOException si ocurre algun error de I/O
     */
    public boolean escriure(List<String> dades) throws IOException {
        if (!potEsciure()) {
            return false;
        }

        for (String str : dades) {
            writer.write(str);
            writer.newLine();
        }

        return true;
    }

    /**
     * Indica si es pot llegir del fitxer, es pot llegir si s'ha obert el fitxer
     * i si no s'ha arribat al final
     *
     * @return True si es pot llegir segons lo descrit abans, False altrament
     * @throws IOException si ocurre un error de I/O
     */
    public boolean potLlegir() throws IOException {
        if (reader == null) {
            return false;
        }

        return reader.ready();
    }

    /**
     * Indica si es pot escriure al fitxer, es pot llegir si s'ha obert el
     * fitxer
     *
     * @return True si es pot esccriure segons lo descrit abans, False altrament
     */
    public boolean potEsciure() {
        return writer != null;
    }

    public List<String> getFills() {
        List<String> fills = new ArrayList<String>();
        Fitxer tmp;

        if (file.exists()) {
            if (file.isDirectory()) {
                for (File fil : file.listFiles()) {
                    tmp = new Fitxer(fil.getAbsolutePath());

                    fills.addAll((tmp.getFills()));
                }
            } else {
                fills.add(file.getName());
            }
        }

        return fills;
    }

    public String getRuta() {
        return file.getAbsolutePath();
    }
}
