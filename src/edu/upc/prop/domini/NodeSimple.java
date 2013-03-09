package edu.upc.prop.domini;

import java.util.HashSet;
import java.util.Set;

public class NodeSimple extends Node {

    /**
     * Crea un node amb el valor especificat
     *
     * @param value Valor del node
     */
    public NodeSimple(char value) throws StackOverflowError {
        super(value);
    }
    /**
     * 
     * @param sequencia la sequencia a insertar
     * @param index a quin nivell de recursivitat estem
     * @return si s'ha insertar be o no
     * @throws StackOverflowError si la sequencia es massa llarga
     */
    @Override
    public boolean inserta(Sequencia sequencia, int index) throws StackOverflowError {
        char c;
        if (index < sequencia.getSequencia().length()) {
            c = sequencia.getSequencia().charAt(index);
        } else {
            c = Alfabet.getInstance().getFiSequencia();
        }
        if (this.value == c) {
            if (this.value != Alfabet.getInstance().getFiSequencia()) {
                return this.getFillCentral().inserta(sequencia, index + 1);
            } else {
                return false;
            }
        } else if (c < this.value) {
            if (!this.men()) {
                this.setGermaMenor(creaFills(sequencia, index));
                return true;
            } else {
                return this.getGermaMenor().inserta(sequencia, index);
            }
        } else {
            if (!this.maj()) {
                this.setGermaMajor(creaFills(sequencia, index));
                return true;
            } else {
                return this.getGermaMajor().inserta(sequencia, index);
            }
        }
    }

    private Node creaFills(Sequencia sequencia, int index) throws StackOverflowError {
        char c;
        if (index < sequencia.getSequencia().length()) {
            c = sequencia.getSequencia().charAt(index);
        } else {
            c = Alfabet.getInstance().getFiSequencia();
        }
        Node node = new NodeSimple(c);

        if (sequencia.getSequencia().length() - index > 1) {
            node.setFillCentral(creaFills(sequencia, index + 1));
        } else {
            Node nodeFi;
            if (c != Alfabet.getInstance().getFiSequencia()) {
                nodeFi = new NodeSimple(Alfabet.getInstance().getFiSequencia());
                Node nodeFiEspecial = new NodeSequenciaFi(sequencia);
                nodeFi.setFillCentral(nodeFiEspecial);
            } else {
                nodeFi = new NodeSequenciaFi(sequencia);
            }
            node.setFillCentral(nodeFi);
        }
        return node;
    }
    /**
     * 
     * @param node el node d'entrada
     * @param sequencia la sequencia a eliminar del sistema
     * @return el node sense la sequencia
     * @throws StackOverflowError 
     */
    @Override
    public NodeSimple elimina(NodeSimple node, String sequencia) throws StackOverflowError {
        Alfabet a = Alfabet.getInstance();
        if (node.getValue() == a.getFiSequencia() && node.mig() && sequencia.isEmpty()) {
            if (node.mig()) {
                node.fillCentral.elimina();
            }
            node.fillCentral = null;
            if (node.maj()) {
                return (NodeSimple) node.germaMajor;
            }
            return null;
        }
        if (sequencia.isEmpty()) {
            if (node.men()) {
                node.setGermaMenor(elimina((NodeSimple) node.germaMenor, sequencia));
                if (node.getFillCentral().mig()) {
                    return node;
                }
                if (node.getFillCentral().men() && node.getFillCentral().maj()) {
                    node.setFillCentral(recoloca(node.getFillCentral().getGermaMenor(), node.getFillCentral().getGermaMajor()));
                } else if (node.getFillCentral().men()) {
                    node.setFillCentral(node.getFillCentral().getGermaMenor());
                } else if (node.getFillCentral().maj()) {
                    node.setFillCentral(node.getFillCentral().getGermaMajor());
                } else {
                    node.setFillCentral(null);
                }
            }
            return node;
        }
        if (node.getValue() == sequencia.charAt(0)) {
            node.setFillCentral(elimina((NodeSimple) node.getFillCentral(), sequencia.substring(1)));
            if (!node.mig()) {
                if (node.men() && node.maj()) {
                    return recoloca(node.getGermaMenor(), node.getGermaMajor());
                } else if (node.men()) {
                    return (NodeSimple) node.getGermaMenor();
                } else if (node.maj()) {
                    return (NodeSimple) node.germaMajor;
                } else {
                    return null;
                }
            }
            if (node.getFillCentral().mig()) {
                return node;
            }
            if (node.getFillCentral().men() && node.getFillCentral().maj()) {
                node.setFillCentral(recoloca(node.getFillCentral().getGermaMenor(), node.getFillCentral().getGermaMajor()));
            } else if (node.getFillCentral().men()) {
                node.setFillCentral(node.getFillCentral().getGermaMenor());
            } else if (node.getFillCentral().maj()) {
                node.setFillCentral(node.getFillCentral().getGermaMajor());
            } else {
                node.setFillCentral(null);
            }
            return node;
        } else if (node.getValue() < sequencia.charAt(0)) {
            if (node.maj()) {
                node.setGermaMajor(elimina((NodeSimple) node.germaMajor, sequencia));
            }
            return node;
        } else {
            if (node.men()) {
                node.setGermaMenor(elimina((NodeSimple) node.germaMenor, sequencia));
            }
            return node;
        }
    }
    /** Fa una consulta amb expressions regulars
     * 
     * @param pattern el patro amb expressions regulars
     * @return el conjunt de sequencies que cumpleixen amb el pattern
     * @throws StackOverflowError 
     */
    @Override
    public Set<String> consulta(String pattern) throws StackOverflowError {
        return consultasfs(pattern, "");
    }
    /** Fa una consulta amb expressions regulars
     * 
     * @param pattern el patro amb expressions regulars
     * @return el conjunt de objecte sequencia que cumpleixen amb el pattern
     * @throws StackOverflowError 
     */
    @Override
    public Set<Sequencia> consultaseq(String pattern) throws StackOverflowError {
        return consultaseq(pattern, "");
    }
    /**
     * 
     * @param sequencia l'string de la sequencia
     * @return l'objecte sequencia
     */
    @Override
    public Sequencia get(String sequencia) {

        Alfabet a = Alfabet.getInstance();
        if (sequencia.equals("") && value == a.getFiSequencia()) {
            return ((NodeSequenciaFi) fillCentral).getObjecteSequencia();
        } else if (sequencia.equals("")) {
            if (men()) {
                return germaMenor.get(sequencia);
            }
        } else if (value == sequencia.charAt(0)) {
            if (mig()) {
                return fillCentral.get(sequencia.substring(1));
            }
        } else if (value < sequencia.charAt(0)) {
            if (maj()) {
                return germaMajor.get(sequencia);
            }
        } else {
            if (men()) {
                return germaMenor.get(sequencia);
            }
        }
        return null;
    }

    private HashSet<String> consultasfs(String pattern, String way) throws StackOverflowError {
        HashSet<String> llista = new HashSet<String>();
        Alfabet alfabet = Alfabet.getInstance();
        if (value == alfabet.getFiSequencia() && mig()) {
            if (pattern.isEmpty()) {
                llista.add(way);
            } else if (pattern.charAt(0) == '*') {
                if (pattern.equals("*")) {
                    llista.add(way);
                }
            }
            if (maj()) {
                llista.addAll(((NodeSimple) germaMajor).consultasfs(pattern, way));
            }
        } else {
            if (!pattern.isEmpty()) {
                char c = pattern.charAt(0);
                String patronou;
                if (c == '*') {
                    if (pattern.length() > 1) {
                        char d = pattern.charAt(1);
                        if (d == value || d == '.') {
                            if (mig()) {
                                llista.addAll(((NodeSimple) fillCentral).consultasfs(pattern.substring(2), way + value));
                            }
                        }
                    }
                    if (mig()) {
                        llista.addAll(((NodeSimple) fillCentral).consultasfs(pattern, way + value));
                    }
                    if (men()) {
                        llista.addAll(((NodeSimple) germaMenor).consultasfs(pattern, way));
                    }
                    if (maj()) {
                        llista.addAll(((NodeSimple) germaMajor).consultasfs(pattern, way));
                    }
                } else {
                    if (value == c || c == '.') {
                        patronou = pattern.substring(1);
                        if (mig()) {
                            llista.addAll(((NodeSimple) fillCentral).consultasfs(patronou, way + value));
                        }
                    }
                    if (c < value || c == '.') {
                        if (men()) {
                            llista.addAll(((NodeSimple) germaMenor).consultasfs(pattern, way));
                        }
                    }
                    if (c > value || c == '.') {
                        if (maj()) {
                            llista.addAll(((NodeSimple) germaMajor).consultasfs(pattern, way));
                        }
                    }
                }
            } else {
                if (men()) {
                    llista.addAll(((NodeSimple) germaMenor).consultasfs(pattern, way));
                }
            }
        }
        return llista;
    }

    private HashSet<Sequencia> consultaseq(String pattern, String way) throws StackOverflowError {
        HashSet<Sequencia> llista = new HashSet<Sequencia>();
        Alfabet alfabet = Alfabet.getInstance();
        if (value == alfabet.getFiSequencia() && mig()) {
            if (pattern.isEmpty()) {
                llista.add(((NodeSequenciaFi) fillCentral).getObjecteSequencia());
            } else if (pattern.charAt(0) == '*') {
                if (pattern.equals("*")) {
                    llista.add(((NodeSequenciaFi) fillCentral).getObjecteSequencia());
                }
            }
            if (maj()) {
                llista.addAll(((NodeSimple) germaMajor).consultaseq(pattern, way));
            }

        } else {
            if (!pattern.isEmpty()) {
                char c = pattern.charAt(0);
                if (c == '*') {
                    if (pattern.length() > 1) {
                        char d = pattern.charAt(1);
                        if (d == value || d == '.') {
                            if (mig()) {
                                llista.addAll(((NodeSimple) fillCentral).consultaseq(pattern.substring(2), way + value));
                            }
                        }
                    }
                    if (mig()) {
                        llista.addAll(((NodeSimple) fillCentral).consultaseq(pattern, way + value));
                    }
                    if (men()) {
                        llista.addAll(((NodeSimple) germaMenor).consultaseq(pattern, way));
                    }
                    if (maj()) {
                        llista.addAll(((NodeSimple) germaMajor).consultaseq(pattern, way));
                    }
                } else {
                    if (value == c || c == '.') {
                        if (mig()) {
                            llista.addAll(((NodeSimple) fillCentral).consultaseq(pattern.substring(1), way + value));
                        }
                    }
                    if (c < value || c == '.') {
                        if (men()) {
                            llista.addAll(((NodeSimple) germaMenor).consultaseq(pattern, way));
                        }
                    }
                    if (c > value || c == '.') {
                        if (maj()) {
                            llista.addAll(((NodeSimple) germaMajor).consultaseq(pattern, way));
                        }
                    }
                }
            } else {
                if (men()) {
                    llista.addAll(((NodeSimple) germaMenor).consultaseq(pattern, way));
                }
            }
        }
        return llista;
    }

    private NodeSimple recoloca(Node germaMenor, Node germaMajor) {
        if (germaMenor.maj()) {
            germaMenor.setGermaMajor(recoloca(germaMenor.germaMajor, germaMajor));
            return (NodeSimple) germaMenor;
        } else {
            germaMenor.germaMajor = germaMajor;
            return (NodeSimple) germaMenor;
        }
    }
}
