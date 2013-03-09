package edu.upc.prop.domini;

import java.util.ArrayList;
import java.util.List;

public class NodeAvlDni {

    private Pacient pacient;
    private int altura;
    private NodeAvlDni fillDret;
    private NodeAvlDni fillEsquerra;
    private NodeAvlEdat nodeEdat;

    /**
     * Crea un node amb el pacient que s'especifica
     *
     * @param pacient El pacient que es crea al node
     */
    public NodeAvlDni(Pacient pacient) {
        this.pacient = pacient;
        altura = 0;
    }

    /**
     * Retorna el pacient del node
     *
     * @return L'objecte del node
     */
    public Pacient getPacient() {
        return pacient;
    }

    /**
     * Modificar l'objecte pacient del node
     *
     * @param pacient El nou objecte que anira al node
     */
    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    private int getAltura() {
        return altura;
    }

    private int alturaD(NodeAvlDni n) {
        return n == null ? -1 : n.getAltura();
    }

    /**
     * Inserta un node
     *
     * @param n Node que s'inerta
     * @return Node actual modificat
     */
    public NodeAvlDni inserta(NodeAvlDni n) {
        NodeAvlDni r = this;
        if (!isLessThan(r.pacient.getDni(), n.pacient.getDni())) {
            if (r.fillEsquerra == null) {
                r.fillEsquerra = n;
            } else {
                r.fillEsquerra = r.fillEsquerra.inserta(n);
                if (alturaD(r.fillEsquerra) - alturaD(r.fillDret) == 2) {
                    if (!isLessThan(r.fillEsquerra.pacient.getDni(), n.pacient.getDni())) {
                        r = rotacioDreta(r);
                    } else {
                        r = dobleRotacioDreta(r);
                    }
                }
            }
        } else {//p.getDni().compareTo(pacient.getDni()) > 0
            if (r.fillDret == null) {
                r.fillDret = n;
            } else {
                r.fillDret = r.fillDret.inserta(n);
                if (alturaD(r.fillDret) - alturaD(r.fillEsquerra) == 2) {
                    if (isLessThan(r.fillDret.pacient.getDni(), n.pacient.getDni())) {
                        r = rotacioEsquerra(r);
                    } else {
                        r = dobleRotacioEsquerra(r);
                    }
                }
            }

        }
        r.altura = Math.max(alturaD(r.fillDret), alturaD(r.fillEsquerra)) + 1;
        return r;
    }

    /**
     * Esborra un pacient del node
     *
     * @param dni Pacient que es vol esborrar
     * @return El node modificat, sense el pacient
     */
    public NodeAvlDni esborra(String dni) {
        NodeAvlDni r = this;
        if (!isLessThan(r.pacient.getDni(), dni) && !r.pacient.getDni().equals(dni)) {
            if (r.fillEsquerra == null) {
                return r;
            } else {
                r.fillEsquerra = r.fillEsquerra.esborra(dni);
                if (alturaD(r.fillEsquerra) - alturaD(r.fillDret) == 2) {
                    if (!isLessThan(r.fillEsquerra.pacient.getDni(), dni)) {
                        r = rotacioDreta(r);
                    } else {
                        r = dobleRotacioDreta(r);
                    }
                }
            }
        } else if (!r.pacient.getDni().equals(dni)) {//p.getDni().compareTo(pacient.getDni()) > 0
            if (r.fillDret == null) {
                return r;
            } else {
                r.fillDret = r.fillDret.esborra(dni);
                if (alturaD(r.fillDret) - alturaD(r.fillEsquerra) == 2) {
                    if (isLessThan(r.fillDret.pacient.getDni(), dni)) {
                        r = rotacioEsquerra(r);
                    } else {
                        r = dobleRotacioEsquerra(r);
                    }
                }
            }

        } else {
            r.nodeEdat = null;
            if (r.fillDret == null && fillEsquerra == null) {
                return null;
            }
            if (r.fillDret == null && r.fillEsquerra != null) {
                return r.fillEsquerra;
            }
            if (r.fillDret != null && r.fillEsquerra == null) {
                return r.fillDret;
            }
            NodeAvlDni aux = buscarMin(r.fillDret);
            aux.fillDret = r.fillDret.copia();
            aux.fillEsquerra = r.fillEsquerra.copia();
            aux.fillDret = aux.fillDret.esborra(aux.pacient.getDni());
            return aux;
        }
        r.altura = Math.max(alturaD(r.fillDret), alturaD(r.fillEsquerra)) + 1;
        return r;
    }

    /**
     * Modifica la referencia al node germa d'edat
     *
     * @param nodeEdat Node germa d'edat a referenciar
     */
    public void setNodeEdat(NodeAvlEdat nodeEdat) {
        this.nodeEdat = nodeEdat;
    }

    private NodeAvlDni rotacioDreta(NodeAvlDni n) {
        NodeAvlDni a = n.copia();
        n = n.fillEsquerra;
        a.fillEsquerra = n.fillDret;
        n.fillDret = a;
        n.altura = Math.max(alturaD(n.fillEsquerra), a.altura) + 1;
        a.altura = Math.max(alturaD(a.fillEsquerra), alturaD(a.fillDret)) + 1;
        return n;
    }

    private NodeAvlDni rotacioEsquerra(NodeAvlDni n) {
        NodeAvlDni a = n.copia();
        n = n.fillDret;
        a.fillDret = n.fillEsquerra;
        n.fillEsquerra = a;
        n.altura = Math.max(alturaD(n.fillEsquerra), a.altura) + 1;
        a.altura = Math.max(alturaD(a.fillEsquerra), alturaD(a.fillDret)) + 1;
        return n;
    }

    private NodeAvlDni dobleRotacioEsquerra(NodeAvlDni n) {
        n.fillDret = rotacioDreta(n.fillDret);
        return rotacioEsquerra(n);
    }

    private NodeAvlDni dobleRotacioDreta(NodeAvlDni n) {
        n.fillEsquerra = rotacioEsquerra(n.fillEsquerra);
        return rotacioDreta(n);
    }

    /**
     * Consulta l'arbre sobre els pacients que estan dins de rang (inici, fi)
     *
     * @param inici Inici de DNIs
     * @param fi Fi de DNIs
     * @return Una llista de pacients que estan dins aquest rang o una llista
     * buida si no hi cap pacient dins el rang
     */
    public List<Pacient> consulta(String inici, String fi) {
        List<Pacient> l = new ArrayList<Pacient>();
        if (pacient.getDni().compareTo(inici) < 0) {
            if (fillDret != null) {
                l.addAll(fillDret.consulta(inici, fi));
            }
        } else if (pacient.getDni().compareTo(fi) > 0) {
            if (fillEsquerra != null) {
                l.addAll(fillEsquerra.consulta(inici, fi));
            }
        } else if (pacient.getDni().compareTo(inici) >= 0 && pacient.getDni().compareTo(fi) <= 0) {
            l.add(pacient);
            if (fillEsquerra != null) {
                l.addAll(fillEsquerra.consulta(inici, fi));
            }
            if (fillDret != null) {
                l.addAll(fillDret.consulta(inici, fi));
            }
        }
        return l;
    }

    private NodeAvlDni copia() {
        NodeAvlDni r = new NodeAvlDni(pacient);
        r.altura = altura;
        r.fillDret = fillDret;
        r.fillEsquerra = fillEsquerra;
        r.nodeEdat = nodeEdat;
        return r;
    }

    private NodeAvlDni buscarMin(NodeAvlDni node) {
        if (node.fillEsquerra == null) {
            return node.copia();
        }
        NodeAvlDni min = buscarMin(node.fillEsquerra);
        if (node.fillEsquerra == min) {
            node.fillEsquerra = min.fillDret;
        }
        return min;
    }

    private boolean isLessThan(String dniPropi, String dni) {

        for (int i = 0; i < dniPropi.length(); i++) {
            if (dniPropi.charAt(i) < dni.charAt(i)) {
                return true;
            } else if (dniPropi.charAt(i) > dni.charAt(i)) {
                return false;
            }
        }

        return false;
    }
}
