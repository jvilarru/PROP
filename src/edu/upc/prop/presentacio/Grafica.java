package edu.upc.prop.presentacio;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Grafica extends javax.swing.JPanel {

    private final String nomResultat;
    private ArrayList<Long> idsSequencia;
    private ArrayList<String> stringsSequencies;
    private ControladorPresentacioEstudis controlEstudis;

    public Grafica(String nomResultat, ControladorPresentacioEstudis controlEstudis) throws IOException {
        initComponents();
        
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.nomResultat = nomResultat;
        this.controlEstudis = controlEstudis;
        idsSequencia = new ArrayList<Long>();
        stringsSequencies = new ArrayList<String>();
        obteIdsStrings();        
        ((JPanelLiniesSequencies) jPanelLiniesSequencies).setIds(idsSequencia);
        ((JPanelLiniesSequencies) jPanelLiniesSequencies).setContoladorEstudis(controlEstudis);
        ((JPanelLiniesSequencies) jPanelLiniesSequencies).setNomResultat(nomResultat);
    }

    private void obteIdsStrings() throws IOException {
        ArrayList[] llistaSeq = new ArrayList[2];
        llistaSeq[0] = new ArrayList<Long>();
        llistaSeq[1] = new ArrayList<String>();
        llistaSeq = controlEstudis.getLlistaSequencies(nomResultat);
        idsSequencia = llistaSeq[0];
        stringsSequencies = llistaSeq[1];
        ((JPanelLiniesSequencies) jPanelLiniesSequencies).setStringsSequencies(stringsSequencies);
    }

    private void pintaStringSequencia(String sequencia) {
        labelSequencia.setText(sequencia);
    }

    private String obteSubString(int x, String sequencia) {

        int llargada = sequencia.length();
        int tamanyPantallaPixels = ((JPanelLiniesSequencies) jPanelLiniesSequencies).getWidth();
        int caracterCentral = calcularPercentatge(x, llargada, tamanyPantallaPixels);
        int tamanyLabelSequencia = labelSequencia.getWidth();
        int tamanyLabelChars = (int) (tamanyLabelSequencia / 8.5);

        String partSequencia;
        int offset = tamanyLabelChars / 2;
        if (tamanyLabelChars < llargada) {
            if (caracterCentral - offset < 0) {
                partSequencia = sequencia.substring(0, tamanyLabelChars - 1) + " --";
            } else if (caracterCentral + offset > llargada) {
                partSequencia = "-- " + sequencia.substring(llargada - tamanyLabelChars, llargada - 1);
            } else {
                partSequencia = "-- " + sequencia.substring(caracterCentral - offset - 3, caracterCentral + offset) + " --";
            }
        } else {
            partSequencia = sequencia;
        }
        return partSequencia;
    }

    private int calcularPercentatge(int x, int llargada, int tamany) {
        if (tamany != 0) {
            return (x * llargada) / tamany;
        } else {
            return 1;
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelLiniesSequencies = new JPanelLiniesSequencies();
        labelSequencia = new javax.swing.JTextPane();

        jPanelLiniesSequencies.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelLiniesSequencies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelLiniesSequenciesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelLiniesSequenciesLayout = new javax.swing.GroupLayout(jPanelLiniesSequencies);
        jPanelLiniesSequencies.setLayout(jPanelLiniesSequenciesLayout);
        jPanelLiniesSequenciesLayout.setHorizontalGroup(
            jPanelLiniesSequenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 771, Short.MAX_VALUE)
        );
        jPanelLiniesSequenciesLayout.setVerticalGroup(
            jPanelLiniesSequenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanelLiniesSequencies);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                    .addComponent(labelSequencia))
                .addGap(62, 62, 62))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(32, 32, 32)
                .addComponent(labelSequencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPanelLiniesSequenciesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelLiniesSequenciesMouseClicked
        String sequencia = ((JPanelLiniesSequencies) jPanelLiniesSequencies).mouseEvent(evt.getX(), evt.getY());
        String zoomString = obteSubString(evt.getX(), sequencia);
        pintaStringSequencia(zoomString);

    }//GEN-LAST:event_jPanelLiniesSequenciesMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelLiniesSequencies;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane labelSequencia;
    // End of variables declaration//GEN-END:variables

    private class JPanelLiniesSequencies extends javax.swing.JPanel {

        private ArrayList<Long> ids;
        private ArrayList<RepresentacioSequencia> conjuntSequencies;
        private ControladorPresentacioEstudis controlEstudis;
        private ArrayList<String> conjuntStringSequencies;
        private String nomResultat;

        public JPanelLiniesSequencies() {
            this.ids = new ArrayList<Long>();
            this.conjuntSequencies = new ArrayList<RepresentacioSequencia>();
        }

        public void setNomResultat(String nom) {
            this.nomResultat = nom;
        }

        public void setContoladorEstudis(ControladorPresentacioEstudis control) {
            this.controlEstudis = control;
        }

        public void setIds(ArrayList<Long> identificadors) {
            ids.addAll(identificadors);
        }

        public void setStringsSequencies(ArrayList<String> sequencies) {
            this.conjuntStringSequencies = sequencies;
        }

        private int numeroLinies() {
            return this.getHeight() / 40;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);            
            Graphics2D g2 = (Graphics2D) g;            
            g2.setColor(Color.BLACK);
            float gruix = 1.0f;
            Stroke stroke = new BasicStroke(gruix, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0);
            g2.setStroke(stroke);
            g2.setColor(Color.blue);
            int yLinia = 20;
            for (int i = 0; i < ids.size(); i++) {
                ArrayList[] posicionsPatrons = new ArrayList[3];
                posicionsPatrons[0] = new ArrayList();
                posicionsPatrons[1] = new ArrayList();
                posicionsPatrons[2] = new ArrayList();

                Boolean tenimPosicionsPatrons = Boolean.FALSE;
                try {
                    posicionsPatrons = controlEstudis.getPosicionsPatrons(nomResultat, i);
                    tenimPosicionsPatrons = Boolean.TRUE;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en la carrega de IO", JOptionPane.ERROR_MESSAGE);
                }

                RepresentacioSequencia relacioDibuixSequencia = new RepresentacioSequencia(yLinia - 15, yLinia + 15, ids.get(i), conjuntStringSequencies.get(i));
                relacioDibuixSequencia.setPosicionsPatrons(posicionsPatrons);
                conjuntSequencies.add(relacioDibuixSequencia);
                pintaADN(g2, yLinia);
                if (tenimPosicionsPatrons) {
                    pintaMarcaMachos(g2, relacioDibuixSequencia);
                }
                yLinia += 50;
            }
        }

        private void pintaMarcaMachos(Graphics2D g2, Grafica.JPanelLiniesSequencies.RepresentacioSequencia relacioDibuixSequencia) {
            int yInferior = relacioDibuixSequencia.getLiniaInferior();
            int ySuperior = relacioDibuixSequencia.getLiniaSuperior();
            int tamanyStringSequencia = relacioDibuixSequencia.getTamanyStringSequencia();
            ArrayList<Color> colors = inicialitzaColors();

            ArrayList<Integer> distancies = relacioDibuixSequencia.getDistancies();
            ArrayList<Integer> tamanys = relacioDibuixSequencia.getTamanys();
            ArrayList<Integer> indexos = relacioDibuixSequencia.getIndexos();
            for (int i = 0; i < distancies.size(); i++) {
                Integer distanciaAlFinal = distancies.get(i);
                Integer tamanyPatro = tamanys.get(i);
                Integer indexVectorPatrons = indexos.get(i);
                int posicioAbsoluta = tamanyStringSequencia - distanciaAlFinal;
                double percentatgeInicial = calcularPercentatge(tamanyStringSequencia, posicioAbsoluta);
                double percentatgePatro = calcularPercentatge(tamanyStringSequencia, tamanyPatro);

                double xInicial = (percentatgeInicial / 100) * this.getWidth();
                double xFi = xInicial + ((percentatgePatro / 100) * this.getWidth());
                Color color = colors.get((indexVectorPatrons % 6));
                pintaMarca(g2, xInicial, xFi, yInferior, ySuperior, color);
            }
        }

        private void pintaMarca(Graphics2D g2, double x1, double x2, int yInferior, int ySuperior, Color color) {
            Stroke oldStroke = g2.getStroke();
            float gruix = 0.8f;
            Stroke stroke = new BasicStroke(gruix, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0);
            g2.setStroke(stroke);
            g2.setColor(color);

            int llargadaRectangle = (int) (x2 - x1);
            int alturaRectangle = ySuperior - yInferior;
            if (llargadaRectangle < 1) {
                g2.drawLine((int) x1, yInferior, (int) x1, ySuperior);
            } else {
                g2.drawRect((int) x1, yInferior, llargadaRectangle - 1, alturaRectangle);
            }
            // Restaurem l'stroke anterior
            g2.setStroke(oldStroke);
        }

        private void pintaSin(Graphics2D pintador, int yInicial, int xInicial, int heightMax, int ampladaMax, double amplitudInversa, double fase) {
            int xAnt = xInicial;
            double yAnt = yInicial + Math.sin(fase) * heightMax;
            int xAct = xAnt;
            double yAct;
            double i = amplitudInversa;
            while (xAct <= ampladaMax) {
                xAct++;
                yAct = yInicial + Math.sin((i + fase)) * heightMax;
                pintador.drawLine(xAnt, (int) yAnt, xAct, (int) yAct);
                xAnt = xAct;
                yAnt = yAct;
                i += amplitudInversa;
            }
        }

        private void pintaADN(Graphics2D g2, int yLinia) {
            int yInicial = yLinia;
            int xInicial = 0;
            int heightMax = 10;
            int ampladaMax = this.getWidth() - 1;
            double amplitudInversa = 0.1;
            double fase = -0.7;

            g2.setColor(Color.blue);
            pintaSin(g2, yInicial, xInicial, heightMax, ampladaMax, amplitudInversa, fase);
            pintaSin(g2, yInicial, xInicial, heightMax, ampladaMax, amplitudInversa, fase + 100);

            g2.setColor(Color.gray);
            pintaSin(g2, yInicial, xInicial, heightMax, ampladaMax, amplitudInversa, fase + 110);
            pintaSin(g2, yInicial, xInicial, heightMax, ampladaMax, amplitudInversa, fase + 210);
        }

        private double calcularPercentatge(int tamany, int posicio) {
            if (tamany != 0) {
                return (posicio * 100) / tamany;
            } else {
                return 1;
            }
        }

        private ArrayList<Color> inicialitzaColors() {
            ArrayList<Color> colors = new ArrayList<Color>();
            Color color = new Color(238,154,73);
            colors.add(color);
            color = new Color(238,154,73);
            colors.add(color);
            color = new Color(188,238,104);
            colors.add(color);
            color = new Color(238,154,0);
            colors.add(color);
            color = new Color(250,250,210);
            colors.add(color);
            color = new Color(153,204,50);            
            colors.add(color);
            
            return colors;
        }

        public String mouseEvent(int x, int y) {
            for (Grafica.JPanelLiniesSequencies.RepresentacioSequencia relacioSequenciaDibuix : conjuntSequencies) {
                int yInferior = relacioSequenciaDibuix.getLiniaInferior();
                int ySuperior = relacioSequenciaDibuix.getLiniaSuperior();
                if (yInferior <= y && y <= ySuperior) {
                    return relacioSequenciaDibuix.getStringSequencia();
                }
            }


            return "";
        }
        
        private class RepresentacioSequencia {

            private int yLiniaInferior;
            private int yLiniaSuperior;
            private long idSequencia;
            private String stringSequencia;
            private ArrayList[] posicionsPatrons;

            public RepresentacioSequencia(int yLiniaInferior, int yLiniaSuperior, long idSequencia, String stringSequencia) {
                this.yLiniaInferior = yLiniaInferior;
                this.yLiniaSuperior = yLiniaSuperior;
                this.idSequencia = idSequencia;
                this.stringSequencia = stringSequencia;
                this.posicionsPatrons = new ArrayList[3];
                this.posicionsPatrons[0] = new ArrayList<Integer>();
                this.posicionsPatrons[1] = new ArrayList<Integer>();
                this.posicionsPatrons[2] = new ArrayList<Integer>();
            }

            public void setPosicionsPatrons(ArrayList[] posicions) {
                this.posicionsPatrons = posicions;
            }

            public ArrayList[] getPosicionsPatrons() {
                return posicionsPatrons;
            }

            public ArrayList<Integer> getDistancies() {
                return posicionsPatrons[0];
            }

            public ArrayList<Integer> getTamanys() {
                return posicionsPatrons[1];
            }

            public ArrayList<Integer> getIndexos() {
                return posicionsPatrons[2];
            }

            public String getStringSequencia() {
                return stringSequencia;
            }

            public int getLiniaInferior() {
                return yLiniaInferior;
            }

            public int getLiniaSuperior() {
                return yLiniaSuperior;
            }

            public int getTamanyStringSequencia() {
                return stringSequencia.length();
            }
        }
    }
}
