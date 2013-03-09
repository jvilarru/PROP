package edu.upc.prop;

import edu.upc.prop.domini.Algorisme;
import edu.upc.prop.domini.Match;
import edu.upc.prop.domini.Patro;
import edu.upc.prop.domini.Sequencia;
import java.util.ArrayList;
import java.util.List;

public class MainAntic {

    public static void main(String args[]) throws InterruptedException {
        int ids = 0;
        boolean esMostra = true;//el primer que entra son mostres
        List<String> mostra = new ArrayList<String>();
        List<String> patrons = new ArrayList<String>();
        boolean insertions = args[0].contains("INSERTIONS");
        boolean paralelo = args[1].contains("PARALELO");
        boolean imprimir = args[2].contains("IMPRIME");
        boolean todo = args[2].contains("TODO");
        double margeError = Double.parseDouble(args[3]);
        int q = Integer.parseInt(args[4]);
        int lMin = Integer.parseInt(args[5]);
        int lMax = Integer.parseInt(args[6]);
        for (int i = 7; i < args.length; i++) {
            if (esMostra) {
                if (args[i].equals("PATRONS")) {
                    esMostra = false;
                } else {
                    mostra.add(new Sequencia(args[i], ids++).getSequencia());
                }
            } else {
                patrons.add(args[i]);
            }

        }
        Algorisme alg = new Algorisme(margeError, q);
        alg.setLongMin(lMin);
        alg.setLongMax(lMax);
        List<Patro> resultat;
        long inici = System.currentTimeMillis();
        if (esMostra) {
            resultat = alg.executarAlgorisme(mostra,paralelo,insertions);
            System.out.println("L'algoritme de buscar nous patrons  paralel s'ha executat en " + (System.currentTimeMillis() - inici) + " milisegons");
        } else {
            resultat = alg.executarAlgorisme(mostra, patrons,paralelo,insertions);
            System.out.println("L'algoritme de buscar coneguts paralel s'ha executat en " + (System.currentTimeMillis() - inici) + " milisegons");
        }
        if (imprimir) {
            escriu(resultat, todo);
        }
    }

    private static void escriu(List<Patro> resultats, boolean todo) {
        int abc = 0;
        for (Patro result : resultats) {
            if (todo) {
                System.out.print(result.getSequencia() + " ");
                System.out.print("occBits[");

                for (int i = 0; i < result.getOcurrencies().length; i++) {
                    if (result.getBit(i) == true) {
                        System.out.print(1 + "");
                    } else {
                        System.out.print(0 + "");
                    }
                }
                System.out.print("]\n");
                System.out.println("\tMachos");
            }
            for (Match m : result.getLlistaMatch()) {
                abc++;
                if (todo) {
                    System.out.println("\t" + m.getSequencia() + " error " + m.getError());
                    int i = 0;
                    System.out.println("\t\tDistancies:");
                    for (ArrayList<Integer> b : m.getSeq()) {
                        if (b != null && !b.isEmpty()) {
                            System.out.println("\t\t\tSequencia " + i);
                            System.out.print("\t\t\t");
                            for (Integer a : b) {
                                System.out.print(a + " ");
                            }
                            System.out.print("\n");
                        }
                        i++;
                    }
                }
            }
        }
        System.out.println("En total hi han " + abc + " matchos");
        System.out.println("Repartits entre " + resultats.size() + " patrons");
    }
}