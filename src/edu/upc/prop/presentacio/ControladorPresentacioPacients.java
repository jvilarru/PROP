package edu.upc.prop.presentacio;

import edu.upc.prop.Main;
import edu.upc.prop.domini.ControladorPacients;
import edu.upc.prop.domini.ControladorSequencies;
import edu.upc.prop.domini.Sequencia;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ControladorPresentacioPacients {

    private static ControladorPresentacioPacients objecteControladorPresentacioMalalties;
    private static ControladorSequencies controladorSequencies;
    private static ControladorPacients controladorPacients;
    private Main main;
    private PanelPacients panelPacients;

    private ControladorPresentacioPacients(Main main) {
        this.main = main;
    }

    public static ControladorPresentacioPacients getInstance(Main main) {
        if (objecteControladorPresentacioMalalties == null) {
            objecteControladorPresentacioMalalties = new ControladorPresentacioPacients(main);

            controladorSequencies = ControladorSequencies.getInstance();
            controladorPacients = ControladorPacients.getInstance();
        }

        return objecteControladorPresentacioMalalties;
    }

    public boolean addPacient(String dni, String nom, String cognom, String dataNaixament, char sexe, String adn, List<String> malalties, List<String> simptomes) {
        return controladorPacients.afegirPacient(dni, nom, cognom, dataNaixament, sexe, adn, malalties, simptomes);
    }

    public void esborrarPacient(String dni) {
        controladorPacients.esborra(dni);
    }

    public boolean modificaPacient(String dni, String nom, String cognom, String adn, List<String> simptomesAfegir, List<String> simptomesBorrar, List<String> malaltiesAfegir, List<String> malaltiesBorrar) {
        List<String> llistaMalalties = new ArrayList<String>();
        List<String> llistaSimptomes = new ArrayList<String>();

        for (String esborrar : malaltiesBorrar) {
            llistaMalalties.add("-" + esborrar);
        }
        for (String afegir : malaltiesAfegir) {
            llistaMalalties.add("+" + afegir);
        }

        for (String esborrar : simptomesBorrar) {
            llistaSimptomes.add("-" + esborrar);
        }
        for (String afegir : simptomesAfegir) {
            llistaSimptomes.add("+" + afegir);
        }

        return controladorPacients.modifica(dni, nom, cognom, adn, llistaMalalties, llistaSimptomes);
    }

    public String getSequenciaString(long id) {
        return controladorSequencies.getStringSeq(id);
    }

    public String getDniNomCognomPacient(String dni) {
        String pac = dni + ": " + controladorPacients.getNomCongomsPacient(dni);

        return pac;
    }

    public List<String> consultaSequencies(String regExp) throws StackOverflowError {
        return controladorSequencies.consulta(regExp);
    }

    public String formatData(String string) {
        StringTokenizer st = new StringTokenizer(string, "/");

        int numeros[] = new int[6];
        int i = 0;
        while (st.hasMoreTokens()) {
            numeros[i] = Integer.parseInt(st.nextToken());
            i++;
        }
        return numeros[2] + "/" + (numeros[1] - 1) + "/" + numeros[0] + " " + numeros[3] + ":" + numeros[4] + ":" + numeros[5];
    }

    public void setPanelPacients(PanelPacients panelPacients) {
        this.panelPacients = panelPacients;
    }

    public void enableButtonsMainForm() {
        main.enableButtonsMainForm();
    }

    public void disableButtonsMainForm() {
        main.disableButtonsMainForm();
    }

    public void redibuixarVista(String dni, boolean forcePaint) {
        panelPacients.redibuixar(dni, forcePaint);
    }

    public void seleccionaPacients(String dni) {
        panelPacients.seleccionaPacient(dni);
    }

    public void cancelar() {
        panelPacients.cancelar();
    }

    public List<Sequencia> getMostra(int edatInici, int edatFi, String dniInici, String dniFinal, char sexe, List<String> dnis, List<String> malalties, List<String> simptomes) {
        return controladorPacients.getMostra(edatInici, edatFi, dniInici, dniFinal, sexe, dnis, simptomes, malalties);
    }

    public void buidar() {
        controladorPacients.buidar();
    }

    public void guardar(String path) throws IOException {
        controladorPacients.escriurePacients(path);
    }

    public void carregar(String path) throws IOException {
        controladorPacients.llegirPacients(path);
    }

    public void asociar() {
        controladorPacients.asociar();
    }

    public String getNom(String dni) {
        return controladorPacients.getNom(dni);
    }

    public String getCognom(String dni) {
        return controladorPacients.getCognom(dni);
    }

    public char getSexe(String dni) {
        return controladorPacients.getSexe(dni);
    }

    public String getDataNaixement(String dni) {
        return controladorPacients.getDataNaixement(dni);
    }

    public String getAdn(String dni) {
        return controladorPacients.getAdn(dni);
    }

    public List<String> getMalalties(String dni) {
        return controladorPacients.getMalalties(dni);
    }

    public List<String> getSimptomes(String dni) {
        return controladorPacients.getSimptomes(dni);
    }

    public List<String> getHistorial(String dni) {
        return controladorPacients.getHistorial(dni);
    }

    public void setModificant() {
        panelPacients.setModificant();
    }

    public List<String> getDniNomCognomPacients() {
        return controladorPacients.getDniNomCognomPacients();
    }
}
