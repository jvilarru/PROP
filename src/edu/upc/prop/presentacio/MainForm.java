package edu.upc.prop.presentacio;

import edu.upc.prop.Main;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MainForm extends javax.swing.JFrame {

    private static final int VISTA_PACIENTS = 0;
    private static final int VISTA_MALALTIES = 1;
    private static final int VISTA_SIMPTOMES = 2;
    private static final int VISTA_SEQUENCIES = 3;
    private static final int VISTA_NP = 4;
    private static final int VISTA_PC = 5;
    private static final int VISTA_VRP = 6;
    private Main main;
    private int vistaActual;
    private PanelPacients panelPacients;
    private PanelMalalties panelMalalties;
    private PanelSimptomes panelSimptomes;
    private PanelSequencies panelSequencies;
    private PanelNousPatrons panelNousPatrons;
    private PanelPatronsConeguts panelPatronsConeguts;
    private PanelResultats panelResultats;
    private DialogAlfabet dialogAlfabet;
    private Ajuda ajuda;
    private boolean modificat;

    /**
     * Creates new form MainForm
     */
    public MainForm(Main main, ControladorPresentacioPacients controladorPacients,
            ControladorPresentacioSimptomes controladorSimptomes,
            ControladorPresentacioMalalties controladorMalalties,
            ControladorPresentacioSequencies controladorSequencies, ControladorPresentacioEstudis controladorEstudis) {
        this.main = main;

        initComponents();
        panelPacients = new PanelPacients(controladorPacients, controladorSimptomes, controladorMalalties);
        panelSimptomes = new PanelSimptomes(controladorSimptomes, controladorMalalties, controladorPacients);
        panelMalalties = new PanelMalalties(controladorMalalties, controladorSequencies, controladorSimptomes, controladorPacients);
        panelSequencies = new PanelSequencies(controladorSequencies);
        panelNousPatrons = new PanelNousPatrons(controladorEstudis, controladorPacients, controladorMalalties, controladorSimptomes);
        panelPatronsConeguts = new PanelPatronsConeguts(controladorEstudis, controladorPacients, controladorMalalties, controladorSimptomes, controladorSequencies);
        panelResultats = new PanelResultats(controladorEstudis);
        dialogAlfabet = new DialogAlfabet(controladorSequencies);
        ajuda = new Ajuda();
        modificat = false;

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

        Rectangle rectangle = graphicsEnvironment.getMaximumWindowBounds();

        int width = rectangle.width;
        int height = rectangle.height;

        setSize(width - 50, height - 50);


//        String nomResultat = "resultat";        
//        jPanelContingut.add(new MainFerran(nomResultat,controladorEstudis));

        cambiarVistaDades(VISTA_PACIENTS); // Fiquem la vista que toqui
        URL locas[] = new URL[8];
        ImageIcon icono;
        locas[0] = ClassLoader.getSystemResource("edu/upc/prop/icons/pacients.png");
        locas[1] = ClassLoader.getSystemResource("edu/upc/prop/icons/malalties.png");
        locas[2] = ClassLoader.getSystemResource("edu/upc/prop/icons/simptomes.png");
        locas[3] = ClassLoader.getSystemResource("edu/upc/prop/icons/sequencia.png");
        locas[4] = ClassLoader.getSystemResource("edu/upc/prop/icons/add.png");
        locas[5] = ClassLoader.getSystemResource("edu/upc/prop/icons/edit.png");
        locas[6] = ClassLoader.getSystemResource("edu/upc/prop/icons/remove.png");
        locas[7] = ClassLoader.getSystemResource("edu/upc/prop/icons/genome.png");
        if (locas[0] != null) {
            icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(locas[0]));
            jButtonPacients.setIcon(icono);
            jButtonPacients.setText("Pacients");
        }
        if (locas[1] != null) {
            icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(locas[1]));
            jButtonMalalties.setIcon(icono);
            jButtonMalalties.setText("Malalties");
        }
        if (locas[2] != null) {
            icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(locas[2]));
            jButtonSimptomes.setIcon(icono);
            jButtonSimptomes.setText("Símptomes");
        }
        if (locas[3] != null) {
            icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(locas[3]));
            jButtonSequencies.setIcon(icono);
            jButtonSequencies.setText("Seqüències");
        }
        if (locas[4] != null) {
            icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(locas[4]));
            jButtonAfegir.setIcon(icono);
            jButtonAfegir.setText("");
        }

        if (locas[5] != null) {
            icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(locas[5]));
            jButtonModificar.setIcon(icono);
            jButtonModificar.setText("");
        }
        if (locas[6] != null) {
            icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(locas[6]));
            jButtonEliminar.setIcon(icono);
            jButtonEliminar.setText("");
        }
        if (locas[7] != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(locas[7]));
        }

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                sortir();
            }
        });

        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Es centra al centre de la pantalla

        jButtonPacients.setSelected(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupDades = new javax.swing.ButtonGroup();
        buttonGroupEstudis = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanelButtonsDades = new javax.swing.JPanel();
        jButtonAfegir = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jButtonPacients = new javax.swing.JToggleButton();
        jButtonMalalties = new javax.swing.JToggleButton();
        jButtonSimptomes = new javax.swing.JToggleButton();
        jButtonSequencies = new javax.swing.JToggleButton();
        jPanelContingut = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanelButtonsEstudis = new javax.swing.JPanel();
        jButtonNP = new javax.swing.JToggleButton();
        jButtonPC = new javax.swing.JToggleButton();
        jButtonVRP = new javax.swing.JToggleButton();
        jPanelEstudis = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuArxiu = new javax.swing.JMenu();
        jMenuItemObrir = new javax.swing.JMenuItem();
        jMenuItemGuardar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSortir = new javax.swing.JMenuItem();
        jMenuEines = new javax.swing.JMenu();
        jMenuItemAlfabet = new javax.swing.JMenuItem();
        jMenuAjuda = new javax.swing.JMenu();
        jMenuItemManual = new javax.swing.JMenuItem();
        jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Genome");

        jPanelButtonsDades.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButtonAfegir.setText("+");
        jButtonAfegir.setToolTipText("Afegir");
        jButtonAfegir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAfegirActionPerformed(evt);
            }
        });

        jButtonModificar.setText("*");
        jButtonModificar.setToolTipText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jButtonEliminar.setText("-");
        jButtonEliminar.setToolTipText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        buttonGroupDades.add(jButtonPacients);
        jButtonPacients.setText("Pacients");
        jButtonPacients.setFocusPainted(false);
        jButtonPacients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPacientsActionPerformed(evt);
            }
        });

        buttonGroupDades.add(jButtonMalalties);
        jButtonMalalties.setText("Malalties");
        jButtonMalalties.setFocusPainted(false);
        jButtonMalalties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMalaltiesActionPerformed(evt);
            }
        });

        buttonGroupDades.add(jButtonSimptomes);
        jButtonSimptomes.setText("Símptomes");
        jButtonSimptomes.setFocusPainted(false);
        jButtonSimptomes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSimptomesActionPerformed(evt);
            }
        });

        buttonGroupDades.add(jButtonSequencies);
        jButtonSequencies.setText("Seqüències");
        jButtonSequencies.setFocusPainted(false);
        jButtonSequencies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSequenciesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelButtonsDadesLayout = new javax.swing.GroupLayout(jPanelButtonsDades);
        jPanelButtonsDades.setLayout(jPanelButtonsDadesLayout);
        jPanelButtonsDadesLayout.setHorizontalGroup(
            jPanelButtonsDadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsDadesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonPacients)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonMalalties)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSimptomes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSequencies)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAfegir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEliminar)
                .addContainerGap(572, Short.MAX_VALUE))
        );
        jPanelButtonsDadesLayout.setVerticalGroup(
            jPanelButtonsDadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanelButtonsDadesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelButtonsDadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButtonPacients)
                    .addComponent(jButtonMalalties)
                    .addComponent(jButtonSimptomes)
                    .addComponent(jButtonSequencies)
                    .addComponent(jButtonAfegir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );

        jPanelContingut.setLayout(new javax.swing.BoxLayout(jPanelContingut, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelButtonsDades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelContingut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelButtonsDades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelContingut, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dades", jPanel1);

        jPanelButtonsEstudis.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroupEstudis.add(jButtonNP);
        jButtonNP.setText("Nous Patrons");
        jButtonNP.setFocusPainted(false);
        jButtonNP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNPActionPerformed(evt);
            }
        });

        buttonGroupEstudis.add(jButtonPC);
        jButtonPC.setText("Patrons Coneguts");
        jButtonPC.setFocusPainted(false);
        jButtonPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPCActionPerformed(evt);
            }
        });

        buttonGroupEstudis.add(jButtonVRP);
        jButtonVRP.setText("Resultats");
        jButtonVRP.setFocusPainted(false);
        jButtonVRP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVRPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelButtonsEstudisLayout = new javax.swing.GroupLayout(jPanelButtonsEstudis);
        jPanelButtonsEstudis.setLayout(jPanelButtonsEstudisLayout);
        jPanelButtonsEstudisLayout.setHorizontalGroup(
            jPanelButtonsEstudisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsEstudisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonNP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonVRP)
                .addContainerGap(750, Short.MAX_VALUE))
        );
        jPanelButtonsEstudisLayout.setVerticalGroup(
            jPanelButtonsEstudisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsEstudisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelButtonsEstudisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNP)
                    .addComponent(jButtonPC)
                    .addComponent(jButtonVRP))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelEstudis.setLayout(new javax.swing.BoxLayout(jPanelEstudis, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelButtonsEstudis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelEstudis, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanelButtonsEstudis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelEstudis, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Estudis", jPanel2);

        jMenuArxiu.setText("Arxiu");

        jMenuItemObrir.setText("Obrir");
        jMenuItemObrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemObrirActionPerformed(evt);
            }
        });
        jMenuArxiu.add(jMenuItemObrir);

        jMenuItemGuardar.setText("Guardar");
        jMenuItemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGuardarActionPerformed(evt);
            }
        });
        jMenuArxiu.add(jMenuItemGuardar);
        jMenuArxiu.add(jSeparator1);

        jMenuItemSortir.setText("Sortir");
        jMenuItemSortir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSortirActionPerformed(evt);
            }
        });
        jMenuArxiu.add(jMenuItemSortir);

        jMenuBar1.add(jMenuArxiu);

        jMenuEines.setText("Eines");

        jMenuItemAlfabet.setText("Alfabet");
        jMenuItemAlfabet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAlfabetActionPerformed(evt);
            }
        });
        jMenuEines.add(jMenuItemAlfabet);

        jMenuBar1.add(jMenuEines);

        jMenuAjuda.setText("Ajuda");

        jMenuItemManual.setText("Manual");
        jMenuItemManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemManualActionPerformed(evt);
            }
        });
        jMenuAjuda.add(jMenuItemManual);

        jMenuItemAbout.setText("Acerca de...");
        jMenuAjuda.add(jMenuItemAbout);

        jMenuBar1.add(jMenuAjuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        boolean mod = false;
        if (vistaActual == VISTA_PACIENTS) {
            mod = panelPacients.esborraPacient();
        } else if (vistaActual == VISTA_MALALTIES) {
            mod = panelMalalties.esborrarMalaltia();
        } else if (vistaActual == VISTA_SIMPTOMES) {
            mod = panelSimptomes.esborrarSimptoma();
        } else if (vistaActual == VISTA_SEQUENCIES) {
            mod = panelSequencies.esborrarSequencia();
        }
        modificat = mod;
        disableButtons();
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        boolean mod = false;
        if (vistaActual == VISTA_PACIENTS) {
            mod = panelPacients.modificarPacient();
        } else if (vistaActual == VISTA_MALALTIES) {
            mod = panelMalalties.modificarMalaltia();
        } else if (vistaActual == VISTA_SIMPTOMES) {
            mod = panelSimptomes.modificarSimptoma();
        }
        modificat = mod;
        disableButtons();
        enableButtonEliminar();
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonAfegirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAfegirActionPerformed
        boolean mod = false;
        if (vistaActual == VISTA_PACIENTS) {
            mod = panelPacients.nouPacient();
        } else if (vistaActual == VISTA_MALALTIES) {
            mod = panelMalalties.novaMalaltia();
        } else if (vistaActual == VISTA_SIMPTOMES) {
            mod = panelSimptomes.nouSimptoma();
        } else if (vistaActual == VISTA_SEQUENCIES) {
            mod = panelSequencies.novaSequencia();
        }
        modificat = mod;
        disableButtons();
    }//GEN-LAST:event_jButtonAfegirActionPerformed

    private void jButtonPacientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPacientsActionPerformed
        cambiarVistaDades(VISTA_PACIENTS);
    }//GEN-LAST:event_jButtonPacientsActionPerformed

    private void jButtonMalaltiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMalaltiesActionPerformed
        cambiarVistaDades(VISTA_MALALTIES);
    }//GEN-LAST:event_jButtonMalaltiesActionPerformed

    private void jButtonSimptomesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimptomesActionPerformed
        cambiarVistaDades(VISTA_SIMPTOMES);
    }//GEN-LAST:event_jButtonSimptomesActionPerformed

    private void jButtonSequenciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSequenciesActionPerformed
        cambiarVistaDades(VISTA_SEQUENCIES);
    }//GEN-LAST:event_jButtonSequenciesActionPerformed

    private void jButtonPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPCActionPerformed
        cambiarVistaEstudis(VISTA_PC);
    }//GEN-LAST:event_jButtonPCActionPerformed

    private void jButtonNPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNPActionPerformed
        cambiarVistaEstudis(VISTA_NP);
    }//GEN-LAST:event_jButtonNPActionPerformed

    private void jButtonVRPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVRPActionPerformed
        cambiarVistaEstudis(VISTA_VRP);
    }//GEN-LAST:event_jButtonVRPActionPerformed

    private void jMenuItemObrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemObrirActionPerformed
        if (ofrecerGuardar()) {
            obrir();
        }
    }//GEN-LAST:event_jMenuItemObrirActionPerformed

    private void jMenuItemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_jMenuItemGuardarActionPerformed

    private void jMenuItemSortirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSortirActionPerformed
        sortir();
    }//GEN-LAST:event_jMenuItemSortirActionPerformed

    private void jMenuItemAlfabetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAlfabetActionPerformed
        dialogAlfabet.setVisible(true);
    }//GEN-LAST:event_jMenuItemAlfabetActionPerformed

    private void jMenuItemManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemManualActionPerformed
        ajuda.setVisible(true);
    }//GEN-LAST:event_jMenuItemManualActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupDades;
    private javax.swing.ButtonGroup buttonGroupEstudis;
    private javax.swing.JButton jButtonAfegir;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JToggleButton jButtonMalalties;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JToggleButton jButtonNP;
    private javax.swing.JToggleButton jButtonPC;
    private javax.swing.JToggleButton jButtonPacients;
    private javax.swing.JToggleButton jButtonSequencies;
    private javax.swing.JToggleButton jButtonSimptomes;
    private javax.swing.JToggleButton jButtonVRP;
    private javax.swing.JMenu jMenuAjuda;
    private javax.swing.JMenu jMenuArxiu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEines;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemAlfabet;
    private javax.swing.JMenuItem jMenuItemGuardar;
    private javax.swing.JMenuItem jMenuItemManual;
    private javax.swing.JMenuItem jMenuItemObrir;
    private javax.swing.JMenuItem jMenuItemSortir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelButtonsDades;
    private javax.swing.JPanel jPanelButtonsEstudis;
    private javax.swing.JPanel jPanelContingut;
    private javax.swing.JPanel jPanelEstudis;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    public void enableButtons() {
        if (vistaActual != VISTA_SEQUENCIES) {
            jButtonModificar.setVisible(true);
        }
        jButtonEliminar.setVisible(true);
    }

    public void disableButtons() {
        jButtonModificar.setVisible(false);
        jButtonEliminar.setVisible(false);
    }

    public void enableButtonEliminar() {
        jButtonEliminar.setVisible(true);
    }

    public void disableButtonEliminar() {
        jButtonEliminar.setVisible(false);
    }

    private void cambiarVistaDades(int tipus) {
        boolean disable = false;

        vistaActual = tipus;
        jPanelContingut.removeAll();

        if (tipus == VISTA_PACIENTS) {
//            panelPacients.refresh();
            jPanelContingut.add(panelPacients);
            disable = panelPacients.seleccioBuida();
        } else if (tipus == VISTA_MALALTIES) {
            panelMalalties.refresh();
            jPanelContingut.add(panelMalalties);
            disable = panelMalalties.seleccioBuida();
        } else if (tipus == VISTA_SIMPTOMES) {
//            panelSimptomes.refresh();
            jPanelContingut.add(panelSimptomes);
            disable = panelSimptomes.seleccioBuida();
        } else if (tipus == VISTA_SEQUENCIES) {
            panelSequencies.refresh();
            jPanelContingut.add(panelSequencies);
            disable = panelSequencies.seleccioBuida();
        }

        if (disable) {
            disableButtons();
        } else {
            enableButtons();
        }

        // Forcem que es pinti la nova vista
        jPanelContingut.revalidate();
        jPanelContingut.repaint();
    }

    private void cambiarVistaEstudis(int tipus) {
        jPanelEstudis.removeAll();

        if (tipus == VISTA_NP) {
            jPanelEstudis.add(panelNousPatrons);
        } else if (tipus == VISTA_PC) {
            jPanelEstudis.add(panelPatronsConeguts);
        } else if (tipus == VISTA_VRP) {
            jPanelEstudis.add(panelResultats);
        }

        // Forcem que es pinti la nova vista
        jPanelEstudis.revalidate();
        jPanelEstudis.repaint();
    }

    public void activarGif() {
        URL loca = ClassLoader.getSystemResource("edu/upc/prop/icons/loader.gif");
        if (loca != null) {
            ImageIcon icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(loca));
            jTabbedPane1.setIconAt(1, icono);
        }
    }

    public void desactivarGif() {
        jTabbedPane1.setIconAt(1, null);
    }

    public void insertaResultat(String execucio) {
        panelResultats.insertaResultat(execucio);
    }

    private void obrir() {
        String directori = main.getDirectori();

        try {
            directori = main.getCarpetaAvui(false);

            if (directori.equals("")) {
                directori = main.getDirectori();
            }
        } catch (IOException ex) {
        }

        JFileChooser chooser = new JFileChooser(directori);

        OpenRequired req = new OpenRequired();
        chooser.setAccessory(req);

        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileFilter());
        chooser.setMultiSelectionEnabled(false);

        int resultado = chooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                waitCursor();
                boolean required = req.isRequiredSelected();

                main.carregar(chooser.getSelectedFile().getAbsolutePath(), required);
            } catch (Exception ex) {
                String msg = "Error carregant els fitxers";
                String title = "Carregant";
                JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
            } catch (StackOverflowError sof) {
                JOptionPane.showMessageDialog(this, "Hi ha hagut un problema", "", JOptionPane.ERROR_MESSAGE);
            } finally {
                defaultCursor();
            }
        }
    }

    private void guardar() {
        String directori = main.getDirectori();

        try {
            directori = main.getCarpetaAvui(true);

            if (directori.equals("")) {
                directori = main.getDirectori();
            }
        } catch (IOException ex) {
        }

        JFileChooser chooser = new JFileChooser(directori);

        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileFilter());
        chooser.setMultiSelectionEnabled(false);

        int resultado = chooser.showSaveDialog(this);
        String msg;
        String title;
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                if (chooser.getSelectedFile().exists()) {
                    msg = "El fitxer ja existeix. El vol sobreescriure?";
                    title = "Sobreescriure";
                    resultado = JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION);
                }

                if (resultado == JOptionPane.OK_OPTION) {
                    waitCursor();

                    main.guardar(chooser.getSelectedFile().getAbsolutePath());
                    modificat = false;
                }
            } catch (Exception ex) {
                msg = "Error guardant els fitxers";
                title = "Guardant";
                JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
            } catch (StackOverflowError sof) {
                JOptionPane.showMessageDialog(this, "Hi ha hagut un problema", "", JOptionPane.ERROR_MESSAGE);
            } finally {
                defaultCursor();
            }
        }
    }

    public void refreshAll() {
        refreshMalalties();
        refreshSimptomes();
        refreshSequencies();
        refreshPacients();
    }

    public void refreshSequencies() {
        panelSequencies.refresh();
    }

    public void refreshPacients() {
        panelPacients.refresh();
    }

    public void refreshSimptomes() {
        panelSimptomes.refresh();
    }

    public void refreshMalalties() {
        panelMalalties.refresh();
    }

    public boolean ofrecerGuardar() {
        if(!modificat) return true;
        String msg = "Vols guardar els fitxers actuals?";
        String title = "Desar fitxers actuals";
        int option = JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_CANCEL_OPTION);
        switch (option) {
            case JOptionPane.YES_OPTION:
                guardar();
                return true;
            case JOptionPane.NO_OPTION:
                return true;
            case JOptionPane.CANCEL_OPTION:
                return false;
            default:
                return false;
        }
    }

    private void sortir() {
        if (ofrecerGuardar()) {
            dispose();
            System.exit(0);
        }
    }

    public void waitCursor() {
        getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        getGlassPane().setVisible(true);
    }

    public void defaultCursor() {
        getGlassPane().setVisible(false);
    }

    private class FileFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }

            int index = file.getName().lastIndexOf(".");//NOI18N
            if (index != -1) {
                String extension = file.getName().substring(index + 1).toLowerCase();
                return extension.equals("pac")
                        || extension.equals("mal")
                        || extension.equals("sim")
                        || extension.equals("alf")
                        || extension.equals("seq");
            }

            return false;
        }

        @Override
        public String getDescription() {
            return "Fitxers Genome";
        }
    }

    private class OpenRequired extends javax.swing.JPanel {

        private javax.swing.JCheckBox jCheckBox1;

        public OpenRequired() {
            initComponents();

            setPreferredSize(new Dimension(320, 200));
        }

        public boolean isRequiredSelected() {
            return jCheckBox1.isSelected();
        }

        private void initComponents() {
            jCheckBox1 = new javax.swing.JCheckBox();

            jCheckBox1.setSelected(true);
            jCheckBox1.setText("Obrir dependències");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jCheckBox1).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jCheckBox1).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        }
    }
}
