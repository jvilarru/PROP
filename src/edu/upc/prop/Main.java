package edu.upc.prop;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import edu.upc.prop.presentacio.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static final String HOME_DIRECTORY = "Genome/Dades/";
    private MainForm mainForm;
    private ControladorPresentacioPacients controladorPacients;
    private ControladorPresentacioMalalties controladorMalalties;
    private ControladorPresentacioSimptomes controladorSimptomes;
    private ControladorPresentacioSequencies controladorSequencies;
    private ControladorPresentacioEstudis controladorEstudis;

    public static void main(String args[]) {
        try {
            new Main().run();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error intern", JOptionPane.ERROR_MESSAGE);
        } 
    }

    public void run() throws IOException, UnsupportedLookAndFeelException {
        controladorPacients = ControladorPresentacioPacients.getInstance(this);
        controladorMalalties = ControladorPresentacioMalalties.getInstance(this);
        controladorSimptomes = ControladorPresentacioSimptomes.getInstance(this);
        controladorSequencies = ControladorPresentacioSequencies.getInstance(this);
        controladorEstudis = ControladorPresentacioEstudis.getInstance(this);

        // Cambiar el disseny de les vistes
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        mainForm = new MainForm(this, controladorPacients, controladorSimptomes,
                controladorMalalties, controladorSequencies, controladorEstudis);

        mainForm.setVisible(true);
    }

    public void guardar(String absolutePath) throws FileNotFoundException, IOException, StackOverflowError {
        int indexExtension = absolutePath.lastIndexOf(".");
        String path = absolutePath;

        if (indexExtension != -1) {
            path = absolutePath.substring(0, indexExtension);
        }

        controladorSimptomes.guardar(path);
        controladorMalalties.guardar(path);
        controladorPacients.guardar(path);
        controladorSequencies.guardarSequencies(path);
        controladorSequencies.guardarAlfabet(path);
    }

    public void carregar(String absolutePath, boolean isRequiredAll) throws IOException, StackOverflowError {
        int indexExtension = absolutePath.lastIndexOf(".");
        String ext;
        String path;

        if (indexExtension != -1) {
            ext = absolutePath.substring(indexExtension + 1).toLowerCase();
            path = absolutePath.substring(0, indexExtension);

            if (isRequiredAll) {
                controladorSimptomes.carregar(path + ".sim");
                controladorMalalties.carregar(path + ".mal");
                controladorPacients.carregar(path + ".pac");
                controladorSequencies.carregarSequencies(path + ".seq");
                controladorSequencies.carregarAlfabet(path + ".alf");
                
                controladorSimptomes.asociar();
                controladorMalalties.asociar();
                controladorPacients.asociar();
                controladorSequencies.asociar();

                mainForm.refreshAll();
            } else {
                if (ext.equals("sim")) {
                    controladorSimptomes.buidar();
                    controladorSimptomes.carregar(absolutePath);
                    controladorSimptomes.asociar();
                    mainForm.refreshSimptomes();
                } else if (ext.equals("mal")) {
                    controladorMalalties.buidar();
                    controladorMalalties.carregar(absolutePath);
                    controladorMalalties.asociar();
                    mainForm.refreshMalalties();
                } else if (ext.equals("pac")) {
                    controladorPacients.buidar();
                    controladorPacients.carregar(absolutePath);
                    controladorPacients.asociar();
                    mainForm.refreshPacients();
                } else if (ext.equals("seq")) {
                    controladorSequencies.buidarSequencies();
                    controladorSequencies.carregarSequencies(absolutePath);
                    controladorSequencies.asociar();
                    mainForm.refreshSequencies();
                } else if (ext.equals("alf")) {
                    controladorSequencies.buidarAlfabet();
                    controladorSequencies.carregarAlfabet(absolutePath);
                }
            }
        }
    }

    public String getDirectori() {
        return HOME_DIRECTORY;
    }

    public String getCarpetaAvui(boolean isNecessaryCreate) throws IOException {
        return controladorEstudis.crearCarpetaAvui(isNecessaryCreate);
    }

    public void enableButtonsMainForm() {
        mainForm.enableButtons();
    }

    public void disableButtonsMainForm() {
        mainForm.disableButtons();
    }

    public void enableButtonEliminarMainForm() {
        mainForm.enableButtonEliminar();
    }

    public void disableButtonEliminarMainForm() {
        mainForm.disableButtonEliminar();
    }

    public void disableGif() {
        mainForm.desactivarGif();
    }

    public void enableGif() {
        mainForm.activarGif();
    }

    public void insertaResultat(String execucio) {
        mainForm.insertaResultat(execucio);
    }
}
