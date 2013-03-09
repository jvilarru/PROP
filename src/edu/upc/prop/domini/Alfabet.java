package edu.upc.prop.domini;

import java.util.ArrayList;
import java.util.List;

public class Alfabet {

    private List<Character> llistaCaracters;
    private char fiSequencia;
    private static Alfabet objecteAlfabet;

    private Alfabet() {
        this.llistaCaracters = new ArrayList<Character>();
        setDefault();
    }

    /**
     * Crear si no esta creada una instancia i la etorna
     *
     * @return La instancia de l'alfabet
     */
    public static Alfabet getInstance() {
        if (objecteAlfabet == null) {
            objecteAlfabet = new Alfabet();
        }
        return objecteAlfabet;
    }

    /**
     * Retorna la llista de caracter que formen l'alfabet
     *
     * @return Llista de caracter de l'alfabet
     */
    public List<Character> getLlistaCaracters() {
        return llistaCaracters;
    }

    /**
     * Modificar els caracter que té l'alfabet;
     *
     * @param llistaCaracters La nova llista de caracter que es vol afegir
     */
    public void setLlistaCaracters(List<Character> llistaCaracters) {
        this.llistaCaracters = llistaCaracters;
    }

    /**
     * Afegir nomes un caracter a l'alfabet
     *
     * @param caracter El nou caracter que es vol afegir
     * @return True si s'ha insertat bé el caracrer, False si ja el conte
     */
    public boolean addCaracter(Character caracter) {
        if (!conte(caracter)) {
            return llistaCaracters.add(caracter);
        }

        return false;
    }

    /**
     * Esborrar un caracter de l'alfabet
     *
     * @param caracter El caracter que es vol esborrar
     * @return True si s'ha eliminat correctament, False altrament
     */
    public boolean removeCaracter(Character caracter) {
        return llistaCaracters.remove(caracter);
    }

    /**
     * Retorna True si el caracter passat com reference existeix a l'alfabte,
     * False altrament
     *
     * @param caracter El caracter que es vol comprovar
     * @return True si el caracter existeix a l'alfabet, False altrament
     */
    public boolean conte(Character caracter) {
        return llistaCaracters.contains(caracter);
    }

    /**
     * Retorna el caracter fi de sequencia
     *
     * @return El caracter fi de sequenci
     */
    public char getFiSequencia() {
        return fiSequencia;
    }

    /**
     * Fica a l'alfabet els caracters predefinits (Per defecte)
     */
    public void setDefault() {
        llistaCaracters.clear();

        llistaCaracters.add('A');
        llistaCaracters.add('C');
        llistaCaracters.add('G');
        llistaCaracters.add('T');

        fiSequencia = '#';
    }

    /**
     * Retorna el tamany de l'alfabet
     *
     * @return El tamany de l'alfabet
     */
    public int size() {
        return llistaCaracters.size();
    }
    /** Buida el alfabet.
     * 
     */
    public void buidar() {
        llistaCaracters.clear();
    }
}
