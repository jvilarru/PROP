package edu.upc.prop.presentacio;

import edu.upc.prop.Main;
import edu.upc.prop.domini.ControladorAlgorisme;
import edu.upc.prop.domini.ControladorMalaltiesSimptomes;
import edu.upc.prop.domini.ControladorPacients;
import edu.upc.prop.domini.ControladorSequencies;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ControladorPresentacioEstudis {

    private static ControladorAlgorisme controladorAlgorisme;
    private static ControladorPresentacioEstudis objecteControladorPresentacioEstudis;
    private static ControladorPresentacioPacients controladorPresentacioPacients;
    private static ControladorPacients controladorPacients;
    private static ControladorSequencies controladorSequencies;
    private static ControladorMalaltiesSimptomes controladorMalalties;
    private int edatInici;
    private int edatFi;
    private char sexe;
    private String dniInici;
    private String dniFinal;
    private double margeError;
    private int q;
    private int longMin;
    private int longMax;
    private List<String> dnis;
    private List<String> malalties;
    private List<String> simptomes;
    private List<String> llistaPatronsConeguts;
    private Main main;
    private PanelNousPatrons nousPatrons;
    private PanelPatronsConeguts patronsConeguts;

    private ControladorPresentacioEstudis(Main main) {
        this.main = main;
        edatInici = 0;
        edatFi =  Integer.MAX_VALUE / 1000;
        sexe = 'X';
        dniInici = "00000000";
        dniFinal = "99999999";
        q = 0;
        margeError = 0.01;
        longMin = 0;
        longMax =  Integer.MAX_VALUE / 1000;
        dnis = new ArrayList<String>();
        malalties = new ArrayList<String>();
        simptomes = new ArrayList<String>();
        llistaPatronsConeguts = new ArrayList<String>();
    }

    public static ControladorPresentacioEstudis getInstance(Main main) {
        if (objecteControladorPresentacioEstudis == null) {
            objecteControladorPresentacioEstudis = new ControladorPresentacioEstudis(main);
            controladorAlgorisme = ControladorAlgorisme.getInstance();
            controladorPacients = ControladorPacients.getInstance();
            controladorSequencies = ControladorSequencies.getInstance();
            controladorMalalties = ControladorMalaltiesSimptomes.getInstance();
            controladorPresentacioPacients = ControladorPresentacioPacients.getInstance(main);
        }
        return objecteControladorPresentacioEstudis;
    }

    public void setDniFinal(String dniFinal) {
        this.dniFinal = dniFinal;
    }

    public void setDniInici(String dniInici) {
        this.dniInici = dniInici;
    }

    public void setDnis(List<String> dnis) {
        this.dnis.addAll(dnis);
    }

    public void setEdatFi(int edatFi) {
        this.edatFi = edatFi;
    }

    public void setEdatInici(int edatInici) {
        this.edatInici = edatInici;
    }

    public void setLongMax(int longMax) {
        this.longMax = longMax;
    }

    public void setLongMin(int longMin) {
        this.longMin = longMin;
    }

    public void setMalalties(List<String> malalties) {
        this.malalties.addAll(malalties);
    }

    public void setMargeError(double margeError) {
        this.margeError = margeError;
    }

    public void setPatronsConeguts(List<String> patronsConeguts) {
        this.llistaPatronsConeguts = patronsConeguts;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    public void setSimptomes(List<String> simptomes) {
        this.simptomes.addAll(simptomes);
    }

    public void buscarNousPatrons(final boolean paralel, final boolean insertions) {
        controladorAlgorisme.setQ(q);
        controladorAlgorisme.setLongMax(longMax);
        controladorAlgorisme.setLongMin(longMin);
        controladorAlgorisme.setMargeError(margeError);
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String execucio = controladorAlgorisme.executa(controladorPresentacioPacients.getMostra(edatInici, edatFi, dniInici, dniFinal, sexe, dnis, malalties, simptomes), paralel, insertions);
                    main.insertaResultat(execucio);
                    main.disableGif();
                    nousPatrons.enableTot();
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "Error intern " + ex.getMessage());
                }
            }
        });
        t.start();
    }

    public void buscarPatronsExistents(final boolean paralel, final boolean insertions) {
        controladorAlgorisme.setQ(q);
        controladorAlgorisme.setLongMax(longMax);
        controladorAlgorisme.setLongMin(longMin);
        controladorAlgorisme.setMargeError(margeError);
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String execucio = controladorAlgorisme.executa(controladorPresentacioPacients.getMostra(edatInici, edatFi, dniInici, dniFinal, sexe, dnis, malalties, simptomes), llistaPatronsConeguts, paralel, insertions);
                    main.disableGif();
                    patronsConeguts.enableTot();
                    main.insertaResultat(execucio);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "Error intern " + ex.getMessage());
                }
            }
        });
        t.start();
    }

    public void setNousPatrons(PanelNousPatrons nousPatrons) {
        this.nousPatrons = nousPatrons;
    }

    public void setPatronsConeguts(PanelPatronsConeguts patronsConeguts) {
        this.patronsConeguts = patronsConeguts;
    }

    public List<String> getEstadistiques(String element) throws IOException {
        return controladorAlgorisme.getEstadistiques(element);
    }

    public List<String> getConclusionsResultat(int criteri, String nom) throws IOException {
        return controladorAlgorisme.getPatronsPotencials(criteri, 0, nom);
    }

    public String[][] getMatriu(String nom) throws IOException, InterruptedException {
        return controladorAlgorisme.getMalaltiesPotencials(nom, false);
    }

    public void posarGif() {
        main.enableGif();
    }

    public ArrayList<String>[] getLlistaSequencies(String nom) throws IOException {
        return controladorAlgorisme.getLlistaSequencies(nom);
    }

    public ArrayList<Integer>[] getPosicionsPatrons(String nom, int index) throws IOException {
        return controladorAlgorisme.getPosicionsPatrons(nom, index);
    }

    public String getParametres(String nom) throws IOException {
        return controladorAlgorisme.getParametres(nom);
    }

    public void guardaDisc(String nom, String ruta) throws IOException {
        controladorAlgorisme.escriureResultat(nom, ruta);
    }

    public String crearCarpetaAvui(boolean necessaryCreate) throws IOException {
        return controladorAlgorisme.crearCarpetaAvui(necessaryCreate);
    }

    public boolean afegirMalalties(String dni, List<String> malalties) {
        Boolean ok = true;
        for (String m : malalties) {
            ok = ok && controladorPacients.afegirMalaltia(dni, m);
        }
        return ok;
    }

    public void novaSequencia(String patro) {
        controladorSequencies.inserta(patro, null, null);
    }

    public void novaMalaltiaAmbSequencia(String malaltia, String patro) {
        Long id = controladorSequencies.get(patro);
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        controladorMalalties.addMalaltia(malaltia, null, new ArrayList<String>(), new ArrayList<String>(), ids);
    }

    public List<String> getNomResultats() {
        return controladorAlgorisme.getNoms();
    }
}
