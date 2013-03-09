package edu.upc.prop.domini;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oriol
 */
public class NodeAvlEdat {

    private int altura;
    private NodeAvlEdat fillDret;
    private NodeAvlEdat fillEsquerra;
    private NodeAvlDni nodeDni;

    /**
     * Crea un node i fica com reference el seu node germa node de DNIs
     *
     * @param nodeDni
     */
    public NodeAvlEdat(NodeAvlDni nodeDni) {
        this.nodeDni = nodeDni;
        altura = 0;
    }

    private int getAltura() {
        return altura;
    }

    private int alturaE(NodeAvlEdat n) {
        return n == null ? -1 : n.getAltura();
    }

    /**
     * Inserta un node
     *
     * @param n Node que s'inerta
     * @return Node actual modificat
     */
    public NodeAvlEdat inserta(NodeAvlEdat n) {
        NodeAvlEdat r = this;//posar-hi copia()?
        if (r.nodeDni.getPacient().getEdat() >= n.nodeDni.getPacient().getEdat()) {
            if (r.fillEsquerra == null) {
                r.fillEsquerra = n;
            } else {
                r.fillEsquerra = r.fillEsquerra.inserta(n);
                if (alturaE(r.fillEsquerra) - alturaE(r.fillDret) == 2) {
                    if (r.fillEsquerra.nodeDni.getPacient().getEdat() >= n.nodeDni.getPacient().getEdat()) {
                        r = rotacioDreta(r);
                    } else {
                        r = dobleRotacioDreta(r);
                    }
                }
            }
        } else {
            if (r.fillDret == null) {
                r.fillDret = n;
            } else {
                r.fillDret = r.fillDret.inserta(n);
                if (alturaE(r.fillDret) - alturaE(r.fillEsquerra) == 2) {
                    if (r.nodeDni.getPacient().getEdat() < n.nodeDni.getPacient().getEdat()) {
                        r = rotacioEsquerra(r);
                    } else {
                        r = dobleRotacioEsquerra(r);
                    }
                }
            }

        }
        r.altura = Math.max(alturaE(r.fillDret), alturaE(r.fillEsquerra)) + 1;
        return r;
    }

    private NodeAvlEdat rotacioDreta(NodeAvlEdat n) {
        NodeAvlEdat a = n.copia();
        n = n.fillEsquerra;
        a.fillEsquerra = n.fillDret;
        n.fillDret = a;
        n.altura = Math.max(alturaE(n.fillEsquerra), a.altura) + 1;
        a.altura = Math.max(alturaE(a.fillEsquerra), alturaE(a.fillDret)) + 1;
        return n;
    }

    private NodeAvlEdat rotacioEsquerra(NodeAvlEdat n) {
        NodeAvlEdat a = n.copia();
        n = n.fillDret;
        a.fillDret = n.fillEsquerra;
        n.fillEsquerra = a;
        n.altura = Math.max(alturaE(n.fillEsquerra), a.altura) + 1;
        a.altura = Math.max(alturaE(a.fillEsquerra), alturaE(a.fillDret)) + 1;
        return n;
    }

    private NodeAvlEdat dobleRotacioEsquerra(NodeAvlEdat n) {
        n.fillDret = rotacioDreta(n.fillDret);
        return rotacioEsquerra(n);
    }

    private NodeAvlEdat dobleRotacioDreta(NodeAvlEdat n) {
        n.fillEsquerra = rotacioEsquerra(n.fillEsquerra);
        return rotacioDreta(n);
    }

    /**
     * Consulta l'arbre sobre els pacients que estan dins de rang (inici, fi)
     *
     * @param inici Inici d'edats
     * @param fi Fi d'edats
     * @return Una llista de pacients que estan dins aquest rang o una llista
     * buida si no hi cap pacient dins el rang
     */
    public List<Pacient> consulta(int inici, int fi) {
        List<Pacient> l = new ArrayList<Pacient>();
        if (nodeDni.getPacient().getEdat() < inici) {
            if (fillDret != null) {
                l.addAll(fillDret.consulta(inici, fi));
            }
        } else if (nodeDni.getPacient().getEdat() > fi) {
            if (fillEsquerra != null) {
                l.addAll(fillEsquerra.consulta(inici, fi));
            }
        } else if (nodeDni.getPacient().getEdat() >= inici && nodeDni.getPacient().getEdat() <= fi) {
            l.add(nodeDni.getPacient());
            if (fillEsquerra != null) {
                l.addAll(fillEsquerra.consulta(inici, fi));
            }
            if (fillDret != null) {
                l.addAll(fillDret.consulta(inici, fi));
            }
        }
        return l;
    }

    /**
     * Esborra un pacient del node
     *
     * @param dni Pacient que es vol esborrar
     * @return El node modificat, sense el pacient
     */
    public NodeAvlEdat esborra(String dni) {
        NodeAvlEdat r = this;//posar-hi copia()?
        if (!isLessThan(r.nodeDni.getPacient().getDni(), dni) && !r.nodeDni.getPacient().getDni().equals(dni)) {
            if (r.fillEsquerra == null) {
                return r;
            } else {
                r.fillEsquerra = r.fillEsquerra.esborra(dni);
                if (alturaE(r.fillEsquerra) - alturaE(r.fillDret) == 2) {
                    if (!isLessThan(r.fillEsquerra.nodeDni.getPacient().getDni(), dni)) {
                        r = rotacioDreta(r);
                    } else {
                        r = dobleRotacioDreta(r);
                    }
                }
            }
        } else if (!r.nodeDni.getPacient().getDni().equals(dni)) {
            if (r.fillDret == null) {
                return r;
            } else {
                r.fillDret = r.fillDret.esborra(dni);
                if (alturaE(r.fillDret) - alturaE(r.fillEsquerra) == 2) {
                    if (isLessThan(r.fillDret.nodeDni.getPacient().getDni(), dni)) {
                        r = rotacioEsquerra(r);
                    } else {
                        r = dobleRotacioEsquerra(r);
                    }
                }
            }

        } else {
            r.nodeDni = null;
            if (r.fillDret == null && r.fillEsquerra == null) {
                return null;
            }
            if (r.fillDret == null && r.fillEsquerra != null) {
                return r.fillEsquerra;
            }
            if (r.fillDret != null && r.fillEsquerra == null) {
                return r.fillDret;
            }
            NodeAvlEdat aux = buscarMin(r.fillDret);
            aux.fillDret = r.fillDret.copia();
            aux.fillEsquerra = r.fillEsquerra.copia();
            aux.fillDret = aux.fillDret.esborra(aux.nodeDni.getPacient().getDni());
            return aux;
        }
        r.altura = Math.max(alturaE(r.fillDret), alturaE(r.fillEsquerra)) + 1;
        return r;
    }

    private NodeAvlEdat copia() {
        NodeAvlEdat r = new NodeAvlEdat(nodeDni);
        r.altura = altura;
        r.fillDret = fillDret;
        r.fillEsquerra = fillEsquerra;
        return r;
    }

    private NodeAvlEdat buscarMin(NodeAvlEdat node) {
        if (node.fillEsquerra == null) {
            return node.copia();
        }
        NodeAvlEdat min = buscarMin(node.fillEsquerra);
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
