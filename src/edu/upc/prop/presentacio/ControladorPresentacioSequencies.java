package edu.upc.prop.presentacio;

import edu.upc.prop.Main;
import edu.upc.prop.domini.ControladorSequencies;
import java.io.IOException;
import java.util.List;

public class ControladorPresentacioSequencies {

    private static ControladorPresentacioSequencies objecteControladorPresentacioSequencies;
    private static ControladorSequencies controladorSequencies;
    private Main main;
    private PanelSequencies panelSequencies;

    private ControladorPresentacioSequencies(Main main) {
        this.main = main;
    }

    public static ControladorPresentacioSequencies getInstance(Main main) {
        if (objecteControladorPresentacioSequencies == null) {
            objecteControladorPresentacioSequencies = new ControladorPresentacioSequencies(main);

            controladorSequencies = ControladorSequencies.getInstance();
        }

        return objecteControladorPresentacioSequencies;
    }

    public boolean addSequencia(String sequencia) throws StackOverflowError {
        return controladorSequencies.inserta(sequencia, null, null);
    }

    public boolean esborrarSequencia(long id) throws StackOverflowError {
        return controladorSequencies.elimina(id);
    }

    public long getId(String sequencia) throws StackOverflowError {
        Long l = controladorSequencies.get(sequencia);

        if (l == null) {
            return -1;
        }

        return l;
    }

    public String getSequencia(long id) {
        return controladorSequencies.getStringSeq(id);
    }

    public void setPanelSequencies(PanelSequencies panelSequencies) {
        this.panelSequencies = panelSequencies;
    }

    public void enableEliminarButtonMainForm() {
        main.enableButtonEliminarMainForm();
    }

    public void disableEliminarButtonMainForm() {
        main.disableButtonEliminarMainForm();
    }

    public void redibuixarVista(long id) {
        panelSequencies.redibuixar(id);
    }

    public void seleccionaSequencia(long id) {
        panelSequencies.seleccionaSequencia(id);
    }

    public void cancelar() {
        panelSequencies.cancelar();
    }

    public void buidarSequencies() throws StackOverflowError {
        controladorSequencies.buidarSequencies();
    }

    public void buidarAlfabet() {
        controladorSequencies.buidarAlfabet();
    }

    public void guardarSequencies(String path) throws IOException, StackOverflowError {
        controladorSequencies.escriureSequencies(path);
    }

    public void guardarAlfabet(String path) throws IOException {
        controladorSequencies.escriureAlfabet(path);
    }

    public void carregarSequencies(String path) throws IOException {
        controladorSequencies.llegirSequencies(path);
    }

    public void asociar() {
        controladorSequencies.asociar();
    }

    public void carregarAlfabet(String path) throws IOException {
        controladorSequencies.llegirAlfabet(path);
    }

    public List<Character> getLlistaCaractersAlfabet() {
        return controladorSequencies.getLlistaCaracters();
    }

    public boolean addCaracterAlfabet(char caracter) {
        return controladorSequencies.addCaracter(caracter);
    }

    public boolean removeCaracterAlfabet(char caracter) {
        return controladorSequencies.removeCaracter(caracter);
    }

    public void setDefaultAlfabet() {
        controladorSequencies.setDefault();
    }

    public boolean conteCaracterAlfabet(char caracter) {
        return controladorSequencies.conte(caracter);
    }

    /**
     * Retorna el tamany de l'alfabet
     *
     * @return El tamany de l'alfabet
     */
    public int getTamanyAlfabet() {
        return controladorSequencies.getTamanyAlfabet();
    }

    public void setModificant() {
        panelSequencies.setModificant();
    }

    /**
     * Retorna totes les sequencies que hi ha al sistema
     *
     * @return Un llistat de totes les sequencies que hi ha
     */
    public List<String> getSequencies() throws StackOverflowError {
        return controladorSequencies.getSequencies();
    }

    public String getIDSequencia(long id) { 
        return id + ": " + controladorSequencies.getStringSeq(id);
    }
}
