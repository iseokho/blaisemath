/*
 * TestPlaneVisometry.java
 *
 * Created on Jul 30, 2009, 3:15:03 PM
 */
package org.bm.blaise.testing.space;

import data.propertysheet.PropertySheet;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.UnivariateVectorialFunction;
import org.bm.blaise.scio.function.FlexSpaceCurve;
import org.bm.blaise.scio.function.FlexSpaceSurface;
import org.bm.blaise.specto.line.LineAxis;
import org.bm.blaise.specto.plane.PlaneAxes;
import org.bm.blaise.specto.plane.PlaneGrid;
import org.bm.blaise.specto.space.SpaceAxes;
import org.bm.blaise.specto.space.SpacePlotComponent;
import org.bm.blaise.specto.space.function.SpaceParametricCurve;
import org.bm.blaise.specto.space.function.SpaceParametricSurface;
import org.bm.blaise.specto.space.function.SpaceParametricSurfacePatch;
import org.bm.blaise.specto.space.function.SpaceTwoCurveSurface;
import org.bm.blaise.specto.visometry.Plottable;
import scio.function.utils.DemoSurface3D;

/**
 *
 * @author ae3263
 */
public class TestSpaceParametricPlottables extends javax.swing.JFrame {

    /** Creates new form TestPlaneVisometry */
    public TestSpaceParametricPlottables() {
        data.propertysheet.editor.EditorRegistration.registerEditors();
        initComponents();


        // FUNCTIONS

        FlexSpaceCurve curve = new FlexSpaceCurve(new UnivariateVectorialFunction() {
                    public double[] value(double x) throws FunctionEvaluationException {
                        return new double[] { (2 + 1.1*Math.sin(7*x)) * Math.cos(2*x), (2 + 1.1*Math.sin(7*x)) * Math.sin(2*x), 1.1*Math.cos(7*x) };
                    }});
        SpaceParametricCurve spc = new SpaceParametricCurve(curve, 0.0, 2*Math.PI, 200);

        FlexSpaceSurface surface = new FlexSpaceSurface(DemoSurface3D.SPHERE);
        SpaceParametricSurface sps = new SpaceParametricSurface(surface, 0, 2*Math.PI, 0, Math.PI);
        SpaceParametricSurfacePatch spsp = new SpaceParametricSurfacePatch(sps.getFunction(), 0, 0, .1, .1);

        domainPlot1.addPlottable(new LineAxis("t"));
        domainPlot1.addPlottable(spc.getDomainPlottable());
        domainPlot1.setDesiredRange(-1.0, 7.28);

        rangePlot1.addPlottable(new SpaceAxes());
        rangePlot1.addPlottable(spc);

        domainPlot2.addPlottable(new PlaneGrid());
        domainPlot2.addPlottable(new PlaneAxes("u", "v"));
        domainPlot2.addPlottable(sps.getDomainPlottable());
        domainPlot2.addPlottable(spsp.getDomainPlottable());

        rangePlot2.addPlottable(new SpaceAxes());
        rangePlot2.addPlottable(sps);
        rangePlot2.addPlottable(spsp);


        rangePlot3.addPlottable(new SpaceAxes());
        rangePlot3.addPlottable(new SpaceTwoCurveSurface(
                new UnivariateVectorialFunction (){
                    public double[] value(double x) throws FunctionEvaluationException {
                        return new double[]{Math.cos(x), Math.sin(x), -.5};
                    }
                },
                new UnivariateVectorialFunction (){
                    public double[] value(double x) throws FunctionEvaluationException {
                        return new double[]{Math.cos(x+2)+.5, Math.sin(x+2)+.2, 1};
                    }
                }, 0, 2*Math.PI));
        
        // PANELS

        rollupPanel1.add("Visometry", new PropertySheet(rangePlot1.getVisometry()));
        for (Plottable p : rangePlot1.getPlottables()) {
            rollupPanel1.add(p.toString(), new PropertySheet(p));
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        rollupPanel1 = new gui.RollupPanel();
        tabPane = new javax.swing.JTabbedPane();
        rangePlot1 = new org.bm.blaise.specto.space.SpacePlotComponent();
        rangePlot2 = new org.bm.blaise.specto.space.SpacePlotComponent();
        rangePlot3 = new org.bm.blaise.specto.space.SpacePlotComponent();
        jPanel1 = new javax.swing.JPanel();
        domainPlot1 = new org.bm.blaise.specto.line.LinePlotComponent();
        jSeparator1 = new javax.swing.JSeparator();
        domainPlot2 = new org.bm.blaise.specto.plane.PlanePlotComponent();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jToolBar1.setRollover(true);

        jButton1.setText("Toggle Anaglyph");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setViewportView(rollupPanel1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.EAST);

        tabPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabPaneStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout rangePlot1Layout = new org.jdesktop.layout.GroupLayout(rangePlot1);
        rangePlot1.setLayout(rangePlot1Layout);
        rangePlot1Layout.setHorizontalGroup(
            rangePlot1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 988, Short.MAX_VALUE)
        );
        rangePlot1Layout.setVerticalGroup(
            rangePlot1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 305, Short.MAX_VALUE)
        );

        tabPane.addTab("Parametric Curve", rangePlot1);

        rangePlot2.setBackground(new java.awt.Color(0, 0, 0));
        rangePlot2.setDefaultFillOpacity(0.9F);

        org.jdesktop.layout.GroupLayout rangePlot2Layout = new org.jdesktop.layout.GroupLayout(rangePlot2);
        rangePlot2.setLayout(rangePlot2Layout);
        rangePlot2Layout.setHorizontalGroup(
            rangePlot2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 988, Short.MAX_VALUE)
        );
        rangePlot2Layout.setVerticalGroup(
            rangePlot2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 305, Short.MAX_VALUE)
        );

        tabPane.addTab("Parametric Surface", rangePlot2);

        rangePlot3.setBackground(new java.awt.Color(0, 0, 0));

        org.jdesktop.layout.GroupLayout rangePlot3Layout = new org.jdesktop.layout.GroupLayout(rangePlot3);
        rangePlot3.setLayout(rangePlot3Layout);
        rangePlot3Layout.setHorizontalGroup(
            rangePlot3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 988, Short.MAX_VALUE)
        );
        rangePlot3Layout.setVerticalGroup(
            rangePlot3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 305, Short.MAX_VALUE)
        );

        tabPane.addTab("2-Curve Surface", rangePlot3);

        getContentPane().add(tabPane, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        org.jdesktop.layout.GroupLayout domainPlot1Layout = new org.jdesktop.layout.GroupLayout(domainPlot1);
        domainPlot1.setLayout(domainPlot1Layout);
        domainPlot1Layout.setHorizontalGroup(
            domainPlot1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 500, Short.MAX_VALUE)
        );
        domainPlot1Layout.setVerticalGroup(
            domainPlot1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 200, Short.MAX_VALUE)
        );

        jPanel1.add(domainPlot1);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setMaximumSize(new java.awt.Dimension(2, 32767));
        jSeparator1.setPreferredSize(new java.awt.Dimension(2, 10));
        jPanel1.add(jSeparator1);

        org.jdesktop.layout.GroupLayout domainPlot2Layout = new org.jdesktop.layout.GroupLayout(domainPlot2);
        domainPlot2.setLayout(domainPlot2Layout);
        domainPlot2Layout.setHorizontalGroup(
            domainPlot2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 500, Short.MAX_VALUE)
        );
        domainPlot2Layout.setVerticalGroup(
            domainPlot2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 200, Short.MAX_VALUE)
        );

        jPanel1.add(domainPlot2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SpacePlotComponent spc = (SpacePlotComponent) tabPane.getSelectedComponent();
        spc.setAnaglyph(!spc.isAnaglyph());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabPaneStateChanged
        rollupPanel1.removeAll();
        SpacePlotComponent ppc = (SpacePlotComponent) tabPane.getSelectedComponent();
        rollupPanel1.add("Visometry", new PropertySheet(ppc.getVisometry()));
        for (Plottable p : ppc.getPlottables()) {
            rollupPanel1.add(p.toString(), new PropertySheet(p));
        }
}//GEN-LAST:event_tabPaneStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TestSpaceParametricPlottables().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.bm.blaise.specto.line.LinePlotComponent domainPlot1;
    private org.bm.blaise.specto.plane.PlanePlotComponent domainPlot2;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private org.bm.blaise.specto.space.SpacePlotComponent rangePlot1;
    private org.bm.blaise.specto.space.SpacePlotComponent rangePlot2;
    private org.bm.blaise.specto.space.SpacePlotComponent rangePlot3;
    private gui.RollupPanel rollupPanel1;
    private javax.swing.JTabbedPane tabPane;
    // End of variables declaration//GEN-END:variables
}
