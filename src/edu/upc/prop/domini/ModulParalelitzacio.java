package edu.upc.prop.domini;

import java.util.ArrayList;
import java.util.List;

public class ModulParalelitzacio implements Runnable {

    private List<String> strings;
    private Algorisme algorisme;
    private boolean nousPatrons;
    private List<Patro> patrons;
    private boolean insertions;

    public ModulParalelitzacio(List<String> strings, Algorisme alg, boolean nousPatrons,boolean insertions) {
        this.strings = strings;
        this.algorisme = alg;
        this.nousPatrons = nousPatrons;
        patrons = null;
        this.insertions = insertions;
    }
    /**
     * Executa l'algoritme segons els paramtres que tingues en la constructora.
     */
    @Override
    public void run() {
        if (!nousPatrons) {
            patrons = algorisme.buscarPatrons(strings,insertions);
        } else {
            patrons = new ArrayList<Patro>();
            for (String s : strings) {
                patrons.addAll(algorisme.expand(s,insertions));
            }
        }
        fin();
    }
    /** Aquesta funció es queda bloquejada fins que l'algoritme acabi de computar, despres 
     * retorna el resultat
     * 
     * @return el resultat de l'eecució
     * @throws InterruptedException per si tenim una interrupcio quan no toca
     */
    public synchronized List<Patro> getPatrons() throws InterruptedException {
        while (patrons == null) {
            wait();
        }
        return patrons;
    }
    /**
     * Notifica a qui s'estigui esperant que ja esta de computar.
     */
    private synchronized void fin() {
        notifyAll();
    }
}