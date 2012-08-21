package specto.euclidean3;

/*
 * BlaisePlotter.java
 *
 * Created on November 9, 2007, 1:36 PM
 */


import specto.euclidean3.VectorField3D;
import specto.euclidean3.Function3D;
import specto.euclidean3.DESolution3D;
import sequor.control.SliderBox;
import specto.euclidean2.Point2D;
import specto.euclidean2.Rectangle2D;

/**
 *
 * @author  ae3263
 */
public class TestPlottables3 extends javax.swing.JFrame {
    
    /** Creates new form BlaisePlotter */
    public TestPlottables3() {
        initComponents();
        
        VectorField3D vf3 = new VectorField3D();
        
        defaultPlot.add(new Function3D());
        ParametricCurve3D pc1 = new ParametricCurve3D();
        defaultPlot.add(pc1);
        
        ParametricSurface3D ps1 = new ParametricSurface3D();
        surfacePlot.add(ps1);
        surfacePlot.add(new FluxIntegral3D(vf3));
        ParametricSurface3D.SurfacePoint sfcPt = ps1.getSurfacePoint();
        surfacePlot.add(sfcPt);
        surfacePlot.add(SliderBox.getStyleAdjusters(100,10,15,surfacePlot.getPlottables(),surfacePlot));
        domainPlot.add(new Rectangle2D(sfcPt.getConstraintModel()));
        domainPlot.add(sfcPt.getConstrainedPoint());
        surfacePlot.add(ps1.getNormalVectors());
        surfacePlot.add(ps1.getSurfaceField(vf3));
        surfacePlot.add(vf3);
        
        
        vectorFieldPlot.add(vf3);
        vectorFieldPlot.add(new DESolution3D(vf3));
        vectorFieldPlot.add(SliderBox.getStyleAdjusters(100,10,15,vectorFieldPlot.getPlottables(),vectorFieldPlot));
        
        stokesPlot.add(vf3);
        stokesPlot.add(pc1.getTangentVectors());
        stokesPlot.add(SliderBox.getStyleAdjusters(100,10,15,defaultPlot.getPlottables(),defaultPlot));
        stokesPlot.add(new LineIntegral3D(vf3));
        stokesPlot.add(vf3);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        defaultPlot = new specto.euclidean3.Plot3D();
        surfacePlot = new specto.euclidean3.Plot3D();
        domainPlot = new specto.euclidean2.Plot2D();
        vectorFieldPlot = new specto.euclidean3.Plot3D();
        stokesPlot = new specto.euclidean3.Plot3D();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        menuBar1 = new javax.swing.JMenuBar();
        fileMenu1 = new javax.swing.JMenu();
        openMenuItem1 = new javax.swing.JMenuItem();
        saveMenuItem1 = new javax.swing.JMenuItem();
        saveAsMenuItem1 = new javax.swing.JMenuItem();
        exitMenuItem1 = new javax.swing.JMenuItem();
        editMenu1 = new javax.swing.JMenu();
        cutMenuItem1 = new javax.swing.JMenuItem();
        copyMenuItem1 = new javax.swing.JMenuItem();
        pasteMenuItem1 = new javax.swing.JMenuItem();
        deleteMenuItem1 = new javax.swing.JMenuItem();
        helpMenu1 = new javax.swing.JMenu();
        contentsMenuItem1 = new javax.swing.JMenuItem();
        aboutMenuItem1 = new javax.swing.JMenuItem();
        menuBar2 = new javax.swing.JMenuBar();
        fileMenu2 = new javax.swing.JMenu();
        openMenuItem2 = new javax.swing.JMenuItem();
        saveMenuItem2 = new javax.swing.JMenuItem();
        saveAsMenuItem2 = new javax.swing.JMenuItem();
        exitMenuItem2 = new javax.swing.JMenuItem();
        editMenu2 = new javax.swing.JMenu();
        cutMenuItem2 = new javax.swing.JMenuItem();
        copyMenuItem2 = new javax.swing.JMenuItem();
        pasteMenuItem2 = new javax.swing.JMenuItem();
        deleteMenuItem2 = new javax.swing.JMenuItem();
        helpMenu2 = new javax.swing.JMenu();
        contentsMenuItem2 = new javax.swing.JMenuItem();
        aboutMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Demo of 3D Plottables");

        javax.swing.GroupLayout defaultPlotLayout = new javax.swing.GroupLayout(defaultPlot);
        defaultPlot.setLayout(defaultPlotLayout);
        defaultPlotLayout.setHorizontalGroup(
            defaultPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 669, Short.MAX_VALUE)
        );
        defaultPlotLayout.setVerticalGroup(
            defaultPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
        );

        tabs.addTab("General", defaultPlot);

        domainPlot.setAnimatorVisible(false);
        domainPlot.setAxisStyle(1);
        domainPlot.setMarkerBoxVisible(false);

        javax.swing.GroupLayout domainPlotLayout = new javax.swing.GroupLayout(domainPlot);
        domainPlot.setLayout(domainPlotLayout);
        domainPlotLayout.setHorizontalGroup(
            domainPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        domainPlotLayout.setVerticalGroup(
            domainPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 101, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout surfacePlotLayout = new javax.swing.GroupLayout(surfacePlot);
        surfacePlot.setLayout(surfacePlotLayout);
        surfacePlotLayout.setHorizontalGroup(
            surfacePlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, surfacePlotLayout.createSequentialGroup()
                .addContainerGap(487, Short.MAX_VALUE)
                .addComponent(domainPlot, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        surfacePlotLayout.setVerticalGroup(
            surfacePlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, surfacePlotLayout.createSequentialGroup()
                .addContainerGap(314, Short.MAX_VALUE)
                .addComponent(domainPlot, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("Surface", surfacePlot);

        javax.swing.GroupLayout vectorFieldPlotLayout = new javax.swing.GroupLayout(vectorFieldPlot);
        vectorFieldPlot.setLayout(vectorFieldPlotLayout);
        vectorFieldPlotLayout.setHorizontalGroup(
            vectorFieldPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 669, Short.MAX_VALUE)
        );
        vectorFieldPlotLayout.setVerticalGroup(
            vectorFieldPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
        );

        tabs.addTab("Vector Field", vectorFieldPlot);

        javax.swing.GroupLayout stokesPlotLayout = new javax.swing.GroupLayout(stokesPlot);
        stokesPlot.setLayout(stokesPlotLayout);
        stokesPlotLayout.setHorizontalGroup(
            stokesPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 669, Short.MAX_VALUE)
        );
        stokesPlotLayout.setVerticalGroup(
            stokesPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
        );

        tabs.addTab("Stoke's", stokesPlot);

        fileMenu.setText("File"); // NOI18N

        openMenuItem.setText("Open"); // NOI18N
        fileMenu.add(openMenuItem);

        saveMenuItem.setText("Save"); // NOI18N
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText("Save As ..."); // NOI18N
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setText("Exit"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit"); // NOI18N

        cutMenuItem.setText("Cut"); // NOI18N
        editMenu.add(cutMenuItem);

        copyMenuItem.setText("Copy"); // NOI18N
        editMenu.add(copyMenuItem);

        pasteMenuItem.setText("Paste"); // NOI18N
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setText("Delete"); // NOI18N
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setText("Help"); // NOI18N

        contentsMenuItem.setText("Contents"); // NOI18N
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setText("About"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        fileMenu1.setText("File"); // NOI18N

        openMenuItem1.setText("Open"); // NOI18N
        fileMenu1.add(openMenuItem1);

        saveMenuItem1.setText("Save"); // NOI18N
        fileMenu1.add(saveMenuItem1);

        saveAsMenuItem1.setText("Save As ..."); // NOI18N
        fileMenu1.add(saveAsMenuItem1);

        exitMenuItem1.setText("Exit"); // NOI18N
        exitMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu1.add(exitMenuItem1);

        menuBar1.add(fileMenu1);

        editMenu1.setText("Edit"); // NOI18N

        cutMenuItem1.setText("Cut"); // NOI18N
        editMenu1.add(cutMenuItem1);

        copyMenuItem1.setText("Copy"); // NOI18N
        editMenu1.add(copyMenuItem1);

        pasteMenuItem1.setText("Paste"); // NOI18N
        editMenu1.add(pasteMenuItem1);

        deleteMenuItem1.setText("Delete"); // NOI18N
        editMenu1.add(deleteMenuItem1);

        menuBar1.add(editMenu1);

        helpMenu1.setText("Help"); // NOI18N

        contentsMenuItem1.setText("Contents"); // NOI18N
        helpMenu1.add(contentsMenuItem1);

        aboutMenuItem1.setText("About"); // NOI18N
        helpMenu1.add(aboutMenuItem1);

        menuBar1.add(helpMenu1);

        fileMenu2.setText("File"); // NOI18N

        openMenuItem2.setText("Open"); // NOI18N
        fileMenu2.add(openMenuItem2);

        saveMenuItem2.setText("Save"); // NOI18N
        fileMenu2.add(saveMenuItem2);

        saveAsMenuItem2.setText("Save As ..."); // NOI18N
        fileMenu2.add(saveAsMenuItem2);

        exitMenuItem2.setText("Exit"); // NOI18N
        exitMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu2.add(exitMenuItem2);

        menuBar2.add(fileMenu2);

        editMenu2.setText("Edit"); // NOI18N

        cutMenuItem2.setText("Cut"); // NOI18N
        editMenu2.add(cutMenuItem2);

        copyMenuItem2.setText("Copy"); // NOI18N
        editMenu2.add(copyMenuItem2);

        pasteMenuItem2.setText("Paste"); // NOI18N
        editMenu2.add(pasteMenuItem2);

        deleteMenuItem2.setText("Delete"); // NOI18N
        editMenu2.add(deleteMenuItem2);

        menuBar2.add(editMenu2);

        helpMenu2.setText("Help"); // NOI18N

        contentsMenuItem2.setText("Contents"); // NOI18N
        helpMenu2.add(contentsMenuItem2);

        aboutMenuItem2.setText("About"); // NOI18N
        helpMenu2.add(aboutMenuItem2);

        menuBar2.add(helpMenu2);

        setJMenuBar(menuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestPlottables3().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem aboutMenuItem1;
    private javax.swing.JMenuItem aboutMenuItem2;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem contentsMenuItem1;
    private javax.swing.JMenuItem contentsMenuItem2;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem copyMenuItem1;
    private javax.swing.JMenuItem copyMenuItem2;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem cutMenuItem1;
    private javax.swing.JMenuItem cutMenuItem2;
    private specto.euclidean3.Plot3D defaultPlot;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenuItem deleteMenuItem1;
    private javax.swing.JMenuItem deleteMenuItem2;
    private specto.euclidean2.Plot2D domainPlot;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu editMenu1;
    private javax.swing.JMenu editMenu2;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem exitMenuItem1;
    private javax.swing.JMenuItem exitMenuItem2;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu fileMenu1;
    private javax.swing.JMenu fileMenu2;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenu helpMenu1;
    private javax.swing.JMenu helpMenu2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuBar menuBar1;
    private javax.swing.JMenuBar menuBar2;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem openMenuItem1;
    private javax.swing.JMenuItem openMenuItem2;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem pasteMenuItem1;
    private javax.swing.JMenuItem pasteMenuItem2;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem1;
    private javax.swing.JMenuItem saveAsMenuItem2;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem saveMenuItem1;
    private javax.swing.JMenuItem saveMenuItem2;
    private specto.euclidean3.Plot3D stokesPlot;
    private specto.euclidean3.Plot3D surfacePlot;
    private javax.swing.JTabbedPane tabs;
    private specto.euclidean3.Plot3D vectorFieldPlot;
    // End of variables declaration//GEN-END:variables
    
}
