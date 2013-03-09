package edu.upc.prop.presentacio;

import edu.upc.prop.Main;
import edu.upc.prop.domini.ControladorMalaltiesSimptomes;
import edu.upc.prop.domini.ControladorPacients;
import edu.upc.prop.domini.ControladorSequencies;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControladorPresentacioMalalties {

    private static ControladorPresentacioMalalties objecteControladorPresentacioMalalties;
    private static ControladorMalaltiesSimptomes controladorMalaltiesSimptomes;
    private static ControladorSequencies controladorSequencies;
    private static ControladorPacients controladorPacients;
    private Main main;
    private PanelMalalties panelMalalties;

    private ControladorPresentacioMalalties(Main main) {
        this.main = main;
    }

    public static ControladorPresentacioMalalties getInstance(Main main) {
        if (objecteControladorPresentacioMalalties == null) {
            objecteControladorPresentacioMalalties = new ControladorPresentacioMalalties(main);

            controladorMalaltiesSimptomes = ControladorMalaltiesSimptomes.getInstance();
            controladorSequencies = ControladorSequencies.getInstance();
            controladorPacients = ControladorPacients.getInstance();
        }

        return objecteControladorPresentacioMalalties;
    }

    public boolean addMalaltia(String nom, String descripcio, List<String> pacients, List<String> simptomes, List<String> sequencies) {
        List<Long> ids = new ArrayList<Long>();

        for (String sequencia : sequencies) {
            Long id = controladorSequencies.get(sequencia);
            if (id != null) {
                ids.add(id);
            }
        }

        return controladorMalaltiesSimptomes.addMalaltia(nom, descripcio, pacients, simptomes, ids);
    }

    public void esborrarMalaltia(String malaltia) {
        controladorMalaltiesSimptomes.removeMalaltia(malaltia);
    }

    public boolean modificaMalaltia(String nom, String descripcio, List<String> pacientsBorrar, List<String> pacientsAfegir, List<String> simptomesAfegir, List<String> simptomesBorrar, List<String> sequenciesAfegir, List<String> sequenciesBorrar) {
        List<String> llistaPacients = new ArrayList<String>();
        List<String> llistaSimptomes = new ArrayList<String>();
        List<String> llistaSequencies = new ArrayList<String>();

        for (String esborrar : pacientsBorrar) {
            llistaPacients.add("-" + esborrar.substring(0, esborrar.indexOf(":")).trim());
        }
        for (String afegir : pacientsAfegir) {
            llistaPacients.add("+" + afegir.substring(0, afegir.indexOf(":")).trim());
        }

        for (String esborrar : simptomesBorrar) {
            llistaSimptomes.add("-" + esborrar);
        }
        for (String afegir : simptomesAfegir) {
            llistaSimptomes.add("+" + afegir);
        }

        for (String esborrar : sequenciesBorrar) {
            llistaSequencies.add("-" + esborrar.substring(esborrar.indexOf(":") + 1).trim());
        }
        for (String afegir : sequenciesAfegir) {
            llistaSequencies.add("+" + afegir.substring(afegir.indexOf(":") + 1).trim());
        }

        return controladorMalaltiesSimptomes.modificaMalaltia(nom, descripcio, llistaPacients, llistaSimptomes, llistaSequencies);
    }

    public String getSequenciaString(long id) {
        return controladorSequencies.getStringSeq(id);
    }

    public String getDniNomCognomPacient(String dni) {
        String pac = dni + ": " + controladorPacients.getNomCongomsPacient(dni);
        return pac;
    }

    public void setPanelMalalties(PanelMalalties panelMalalties) {
        this.panelMalalties = panelMalalties;
    }

    public void enableButtonsMainForm() {
        main.enableButtonsMainForm();
    }

    public void disableButtonsMainForm() {
        main.disableButtonsMainForm();
    }

    public void redibuixarVista(String nom) {
        panelMalalties.redibuixar(nom);
    }

    public void seleccionaMalaltia(String nom) {
        panelMalalties.seleccionaMalaltia(nom);
    }

    public void cancelar() {
        panelMalalties.cancelar();
    }

    public void buidar() {
        controladorMalaltiesSimptomes.buidarMalalties();
    }

    public void guardar(String path) throws IOException {
        controladorMalaltiesSimptomes.escriureMalalties(path);
    }

    public void carregar(String path) throws IOException {
        controladorMalaltiesSimptomes.llegirMalalties(path);
    }

    public void asociar() {
        controladorMalaltiesSimptomes.asociarMalalties();
    }

    public String getDescripcio(String malaltia) {
        return controladorMalaltiesSimptomes.getDescripcioMalaltia(malaltia);
    }

    public List<String> getSimptomes(String malaltia) {
        return controladorMalaltiesSimptomes.getSimptomesMalaltia(malaltia);
    }

    public List<String> getSequencies(String malaltia) {
        return controladorMalaltiesSimptomes.getSequenciesMalaltia(malaltia);
    }

    public List<String> getPacients(String malaltia) {
        return controladorMalaltiesSimptomes.getPacientsMalaltia(malaltia);
    }

    public void setModificant() {
        panelMalalties.setModificant();
    }

    public List<String> getNomMalalties() {
        return controladorMalaltiesSimptomes.getNomMalalties();
    }
}
