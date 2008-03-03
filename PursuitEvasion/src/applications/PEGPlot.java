/*
 * PEGPlot.java
 * Created on September 8, 2007, 8:59 AM
 */

// TODO synchronize timers in the two different plot windows.

package applications;

import scio.coordinate.R2;
import sequor.model.ComboBoxEditor;
import simulation.Statistics;
import specto.gridplottable.Grid2D;
import utility.DataLog;

/**
 *
 * @author  ae3263
 */
public class PEGPlot extends javax.swing.JFrame {
    
    /** Creates new form PEGPlot */
    @SuppressWarnings("unchecked")
    public PEGPlot() {
        initComponents();
        plot2D1.getVisometry().setBounds(new R2(-70,-70),new R2(70,70));
        plot2D2.getVisometry().setBounds(new R2(-10,-100),new R2(200,100));
        plot2D2.getVisometry().setAspectRatio(2);
        simulation1.setAnimationCycle(plot2D1);
        simulation1.setAnimationCycle(plot2D2);
        dataLog1.initialize(simulation1, plot2D1, plot2D2);
        simulation1.run();
        settingsMenu.add(simulation1.getMenu("Simulation"));
        jComboBox1.setModel(new ComboBoxEditor(simulation1.getGameTypeModel()));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        simulation1 = new simulation.Simulation();
        menuSimModeGroup = new javax.swing.ButtonGroup();
        dataLog1 = new utility.DataLog();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        plot2D2 = new specto.plotpanel.Plot2D();
        jScrollPane5 = new javax.swing.JScrollPane();
        notificationWindow = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        simulationSettingsPanel1 = new applications.SimulationSettingsPanel(simulation1.ss);
        plot2D1 = new specto.plotpanel.Plot2D();
        statusBar = new javax.swing.JPanel();
        statusText = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        resetButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        simulationMenu = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        ModeMenu = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        appearanceMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();

        simulation1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulation1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pursuit-Evasion Games");

        jSplitPane2.setDividerLocation(450);

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(450, 600));
        jTabbedPane1.addTab("Goal Functions", plot2D2);

        notificationWindow.setColumns(20);
        notificationWindow.setEditable(false);
        notificationWindow.setRows(5);
        jScrollPane5.setViewportView(notificationWindow);

        jTabbedPane1.addTab("Log", jScrollPane5);
        jTabbedPane1.addTab("Network View", jScrollPane3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
            .addComponent(simulationSettingsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(simulationSettingsPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane2.setLeftComponent(jPanel1);
        jSplitPane2.setRightComponent(plot2D1);

        getContentPane().add(jSplitPane2, java.awt.BorderLayout.CENTER);

        statusBar.setBackground(new java.awt.Color(255, 255, 255));
        statusBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        statusText.setText("Status: nonfunctional status bar.");

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusText, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusText)
        );

        getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);

        jToolBar1.setRollover(true);

        jLabel1.setText("Game Preset:  ");
        jToolBar1.add(jLabel1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setMaximumSize(new java.awt.Dimension(150, 20));
        jToolBar1.add(jComboBox1);
        jToolBar1.add(jSeparator1);

        resetButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        resetButton.setText("RANDOMIZE");
        resetButton.setToolTipText("Click here to reset the simulation and re-randomize starting positions.");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(resetButton);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jButton1.setText("BATCH");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator3);

        jButton2.setText("Add Dots");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        fileMenu.setText("File");

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("New");
        fileMenu.add(jMenuItem9);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem11.setText("Open");
        fileMenu.add(jMenuItem11);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Save");
        fileMenu.add(jMenuItem10);

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem12.setText("Quit");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem12);

        jMenuBar1.add(fileMenu);

        simulationMenu.setText("Simulation");

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem13.setText("Randomize Starting Locations");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        simulationMenu.add(jMenuItem13);

        jMenuBar1.add(simulationMenu);

        settingsMenu.setText("Settings");

        ModeMenu.setText("Simulation Mode");

        jRadioButtonMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSimModeGroup.add(jRadioButtonMenuItem1);
        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("One Simulation (Dynamic)");
        ModeMenu.add(jRadioButtonMenuItem1);

        jRadioButtonMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSimModeGroup.add(jRadioButtonMenuItem2);
        jRadioButtonMenuItem2.setText("Two Simulations (Comparison)");
        ModeMenu.add(jRadioButtonMenuItem2);

        jRadioButtonMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSimModeGroup.add(jRadioButtonMenuItem3);
        jRadioButtonMenuItem3.setText("Multiple Simulations (Statistical)");
        ModeMenu.add(jRadioButtonMenuItem3);

        settingsMenu.add(ModeMenu);

        jMenuBar1.add(settingsMenu);

        appearanceMenu.setText("Appearance");

        jMenuItem1.setText("Plot Window");
        appearanceMenu.add(jMenuItem1);

        jMenuItem2.setText("Points");
        appearanceMenu.add(jMenuItem2);

        jMenuItem7.setText("Paths");
        appearanceMenu.add(jMenuItem7);

        jMenuBar1.add(appearanceMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    simulation1.runSeveral(100);
}//GEN-LAST:event_jButton1ActionPerformed

private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
    System.exit(0);
}//GEN-LAST:event_jMenuItem12ActionPerformed

private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    simulation1.initStartingLocations();
    simulation1.run();
}//GEN-LAST:event_resetButtonActionPerformed

    @SuppressWarnings("unchecked")
private void simulation1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulation1ActionPerformed
    //System.out.println("pegplot action performed: "+evt.getActionCommand());
    if(evt.getActionCommand().equals("redraw")){
        plot2D1.repaint();
        plot2D2.repaint();
    }
    else if(evt.getActionCommand().equals("recolor")){
        dataLog1.recolor();
        plot2D1.repaint();
        plot2D2.repaint();
        simulationSettingsPanel1.repaint();
    }
    // simulation has changed in some fundamental way
    else if(evt.getActionCommand().equals("reset")){
        plot2D1.removeAll();
        plot2D2.removeAll();
        simulation1.setAnimationCycle(plot2D1);
        simulation1.setAnimationCycle(plot2D2);
        plot2D1.rebuildOptionsMenu();
        plot2D2.rebuildOptionsMenu();
        dataLog1.initialize(simulation1, plot2D1, plot2D2);
        simulationSettingsPanel1.setTree(simulation1.ss);
    }
    else if(evt.getActionCommand().equals("animation")){
        simulation1.setAnimationCycle(plot2D1);  
        simulation1.setAnimationCycle(plot2D2);
    }
    else if(evt.getActionCommand().equals("log")){
        if(evt.getSource() instanceof DataLog){
            ((DataLog)evt.getSource()).output(notificationWindow);
        }else if(evt.getSource() instanceof Statistics){
            ((Statistics)evt.getSource()).output(notificationWindow);
        }
    }
    else{
        notificationWindow.append(evt.getActionCommand()+"\n");
        statusText.setText(evt.getActionCommand());
    }
}//GEN-LAST:event_simulation1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        plot2D1.add(new Statistics().getInitialPositionTestPlot(plot2D1.getVisometry(), simulation1));
    }//GEN-LAST:event_jButton2ActionPerformed
    
    /**
     * @param args the command lineSegment arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PEGPlot().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ModeMenu;
    private javax.swing.JMenu appearanceMenu;
    private utility.DataLog dataLog1;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.ButtonGroup menuSimModeGroup;
    private javax.swing.JTextArea notificationWindow;
    private specto.plotpanel.Plot2D plot2D1;
    private specto.plotpanel.Plot2D plot2D2;
    private javax.swing.JButton resetButton;
    private javax.swing.JMenu settingsMenu;
    private simulation.Simulation simulation1;
    private javax.swing.JMenu simulationMenu;
    private applications.SimulationSettingsPanel simulationSettingsPanel1;
    private javax.swing.JPanel statusBar;
    private javax.swing.JLabel statusText;
    // End of variables declaration//GEN-END:variables
}
