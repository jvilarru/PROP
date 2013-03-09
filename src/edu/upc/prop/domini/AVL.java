package edu.upc.prop.domini;

import java.util.ArrayList;
import java.util.List;

public class AVL {

    private NodeAvlDni arreld;
    private NodeAvlEdat arrele;
    private int size;
    private String dniInici;
    private String dniFi;

    /**
     * Crea el AVL buit
     */
    public AVL() {
        size = 0;
    }

    /**
     * Inserta el pacient que se li passa per parametre a l'arbre
     *
     * @param pacient El pacient que es vol insertar
     * @return True si s'ha insertat bé, False si ja existeix
     */
    public boolean inserta(Pacient pacient) {
        if (arreld == null || arrele == null) {
            arreld = new NodeAvlDni(pacient);
            arrele = new NodeAvlEdat(arreld);
            arreld.setNodeEdat(arrele);
            size++;
            return true;
        } else {
            List<Pacient> l = consulta(pacient.getDni(), pacient.getDni());
            if (l == null || l.isEmpty()) {
                NodeAvlDni d = new NodeAvlDni(pacient);
                NodeAvlEdat e = new NodeAvlEdat(d);
                d.setNodeEdat(e);
                arreld = arreld.inserta(d);
                arrele = arrele.inserta(e);
                size++;
                return true;
            }
            return false;
        }
    }

    /**
     * Esborra el pacient de l'arbre que se li passa com parametre
     *
     * @param pacient El pacient que es vol esborrar
     * @return True si s'ha esborrat bé, False si no existeix
     */
    public boolean esborra(String pacient) {
        List<Pacient> l = consulta(pacient, pacient);
        if (l == null || l.isEmpty()) {
            return false;
        }
        arreld = arreld.esborra(pacient);
        arrele = arrele.esborra(pacient);
        size--;
        return true;
    }

    /**
     * Consulta els pacients de l'arbre segons un rang d'edat (inici, fi)
     * inclusius
     *
     * @param inici Edat inici desde on es vol consultar
     * @param fi Edat fi fins a on es vol consultar
     * @return Una llita de pacients si compleixen la condicio o una llista
     * buida si no hi ha cap pacient que la compleixi
     */
    public List<Pacient> consulta(int inici, int fi) {
        if (arrele != null) {
            return arrele.consulta(inici, fi);
        } else {
            return new ArrayList<Pacient>();
        }
    }

    /**
     * Consulta els pacients de l'arbre segons un rang de DNIs (inici, fi)
     * inclusius
     *
     * @param inici DNI inici desde on es vol consultar
     * @param fi DNi fi fins a on es vol consultar
     * @return Una llita de pacients si compleixen la condicio o una llista
     * buida si no hi ha cap pacient que la compleixi
     */
    public List<Pacient> consulta(String inici, String fi) {
        if (arreld != null) {
            return arreld.consulta(inici, fi);
        } else {
            return new ArrayList<Pacient>();
        }
    }

    /**
     * Consulta els pacients de l'arbre segons un rang de DNIs (dniInici, dniFi)
     * i un rang d'edats (edatInici, edatFi) inclusius, fa una interseccio entre
     * els dos rangs
     *
     * @param edatInici Edat inici desde on es vol consultar
     * @param edatFi Edat fi fins a on es vol consultar
     * @param dniInici DNI inici desde on es vol consultar
     * @param dniFi DNI inici desde on es vol consultar
     * @return Una llita de pacients si compleixen la condicio o una llista
     * buida si no hi ha cap pacient que la compleixi
     */
    public List<Pacient> consulta(int edatInici, int edatFi, String dniInici, String dniFi) {
        List<Pacient> l = new ArrayList<Pacient>();
        if (arreld != null && arrele != null) {
            List<Pacient> edat = arrele.consulta(edatInici, edatFi);
            List<Pacient> dni = arreld.consulta(dniInici, dniFi);
            if (edat.size() >= dni.size()) {
                for (Pacient p : dni) {
                    if (edat.contains(p)) {
                        l.add(p);
                    }
                }
            } else {
                for (Pacient p : edat) {
                    if (dni.contains(p)) {
                        l.add(p);
                    }
                }
            }
        }
        return l;
    }

    /**
     * Retorna el tamany de l'arbre
     *
     * @return El tamany de l'arbre
     */
    public int size() {
        return size;
    }

}
