package edu.upc.prop.presentacio;

import edu.upc.prop.Main;
import edu.upc.prop.domini.ControladorMalaltiesSimptomes;
import edu.upc.prop.domini.ControladorPacients;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControladorPresentacioSimptomes {

    private static ControladorPresentacioSimptomes objecteControladorPresentacioSimptomes;
    private static ControladorMalaltiesSimptomes controladorMalaltiesSimptomes;
    private static ControladorPacients controladorPacients;
    private Main main;
    private PanelSimptomes panelSimptomes;

    private ControladorPresentacioSimptomes(Main main) {
        this.main = main;
    }

    public static ControladorPresentacioSimptomes getInstance(Main main) {
        if (objecteControladorPresentacioSimptomes == null) {
            objecteControladorPresentacioSimptomes = new ControladorPresentacioSimptomes(main);

            controladorMalaltiesSimptomes = ControladorMalaltiesSimptomes.getInstance();
            controladorPacients = ControladorPacients.getInstance();
        }

        return objecteControladorPresentacioSimptomes;
    }

    public boolean addSimptoma(String nom, List<String> malalties, List<String> pacients) {
        return controladorMalaltiesSimptomes.addSimptoma(nom, malalties, pacients);
    }

    public void esborrarSimptoma(String simptoma) {
        controladorMalaltiesSimptomes.removeSimptoma(simptoma);
    }

    public boolean modificaSimptoma(String nom, List<String> pacientsAfegir, List<String> pacientsBorrar, List<String> malaltiesAfegir, List<String> malaltiesBorrar) {
        List<String> llistaMalalties = new ArrayList<String>();
        List<String> llistaPacients = new ArrayList<String>();

        for (String esborrar : malaltiesBorrar) {
            llistaMalalties.add("-" + esborrar);
        }

        for (String afegir : malaltiesAfegir) {
            llistaMalalties.add("+" + afegir);
        }

        for (String esborrar : pacientsBorrar) {
            llistaPacients.add("-" + esborrar.substring(0, esborrar.indexOf(":")).trim());
        }

        for (String afegir : pacientsAfegir) {
            llistaPacients.add("+" + afegir);
        }

        return controladorMalaltiesSimptomes.modificaSimptoma(nom, llistaPacients, llistaMalalties);
    }

    public String getDniNomCognomPacient(String dni) {
        String pac = dni + ": " + controladorPacients.getNomCongomsPacient(dni);
        return pac;
    }

    public void setPanelSimptomes(PanelSimptomes panelSimptomes) {
        this.panelSimptomes = panelSimptomes;
    }

    public void enableButtonsMainForm() {
        main.enableButtonsMainForm();
    }

    public void disableButtonsMainForm() {
        main.disableButtonsMainForm();
    }

    public void redibuixarVista(String nom) {
        panelSimptomes.redibuixar(nom);
    }

    public void seleccionaSimptoma(String nom) {
        panelSimptomes.seleccionaSimptoma(nom);
    }

    public void cancelar() {
        panelSimptomes.cancelar();
    }

    public void buidar() {
        controladorMalaltiesSimptomes.buidarSimptomes();
    }

    public void guardar(String path) throws IOException {
        controladorMalaltiesSimptomes.escriureSimptomes(path);
    }

    public void carregar(String path) throws IOException {
        controladorMalaltiesSimptomes.llegirSimptomes(path);
    }

    public void asociar() {
        controladorMalaltiesSimptomes.asociarSimptomes();
    }

    public List<String> getMalalties(String simptoma) {
        return controladorMalaltiesSimptomes.getMalaltiesSimptoma(simptoma);
    }

    public List<String> getPacients(String simptoma) {
        return controladorMalaltiesSimptomes.getPacientsSimptoma(simptoma);
    }

    public void setModificant() {
        panelSimptomes.setModificant();
    }

    public List<String> getNomSimptomes() {
        return controladorMalaltiesSimptomes.getNomSimptomes();
    }
}
