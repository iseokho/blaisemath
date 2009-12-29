/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MultiView.java
 *
 * Created on Oct 23, 2009, 11:00:32 AM
 */

package org.bm.blaise.application;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.bm.blaise.specto.space.SpaceAxes;
import org.bm.blaise.specto.space.SpaceViewObjects;
import org.bm.blaise.specto.space.SpaceVisometry;
import org.bm.blaise.specto.space.basic.SpaceBox;

/**
 *
 * @author ae3263
 */
public class MultiView extends javax.swing.JFrame {

    /** Creates new form MultiView */
    public MultiView() {
        initComponents();

        spacePlotComponent1.setAnaglyph(true);
        spacePlotComponent1.getProjection().setCamSep(0.01);
        spacePlotComponent2.setAnaglyph(true);
        spacePlotComponent2.getProjection().setCamSep(0.01);
        spacePlotComponent3.setAnaglyph(true);
        spacePlotComponent3.getProjection().setCamSep(0.01);

        spacePlotComponent1.getProjection().setDpi(500);
        spacePlotComponent1.getProjection().setScreenDist(2.0);
        spacePlotComponent1.getProjection().setViewDist(10.0);
        spacePlotComponent2.getProjection().setDpi(500);
        spacePlotComponent2.getProjection().setScreenDist(2.0);
        spacePlotComponent2.getProjection().setViewDist(10.0);
        spacePlotComponent3.getProjection().setDpi(500);
        spacePlotComponent3.getProjection().setScreenDist(2.0);
        spacePlotComponent3.getProjection().setViewDist(10.0);

        spacePlotComponent1.addPlottable(new SpaceBox());
        spacePlotComponent1.addPlottable(new SpaceAxes());

        //spacePlotComponent2.addPlottable(new SpaceFunction());
        spacePlotComponent2.addPlottable(new SpaceBox());
        spacePlotComponent2.addPlottable(new SpaceAxes());

        spacePlotComponent3.addPlottable(new SpaceAxes());
        spacePlotComponent3.addPlottable(new SpaceBox());
        spacePlotComponent3.addPlottable(new SpaceViewObjects(((SpaceVisometry) spacePlotComponent1.getVisometry()).getProj()));
        spacePlotComponent3.addPlottable(new SpaceViewObjects(((SpaceVisometry) spacePlotComponent2.getVisometry()).getProj()));

        ChangeListener cl = new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                spacePlotComponent1.repaint();
                spacePlotComponent2.repaint();
                spacePlotComponent3.repaint();
            }
        };

        spacePlotComponent1.getVisometry().addChangeListener(cl);
        spacePlotComponent2.getVisometry().addChangeListener(cl);
        spacePlotComponent3.getVisometry().addChangeListener(cl);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        spacePlotComponent3 = new org.bm.blaise.specto.space.SpacePlotComponent();
        jSplitPane2 = new javax.swing.JSplitPane();
        spacePlotComponent1 = new org.bm.blaise.specto.space.SpacePlotComponent();
        spacePlotComponent2 = new org.bm.blaise.specto.space.SpacePlotComponent();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);

        org.jdesktop.layout.GroupLayout spacePlotComponent3Layout = new org.jdesktop.layout.GroupLayout(spacePlotComponent3);
        spacePlotComponent3.setLayout(spacePlotComponent3Layout);
        spacePlotComponent3Layout.setHorizontalGroup(
            spacePlotComponent3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 527, Short.MAX_VALUE)
        );
        spacePlotComponent3Layout.setVerticalGroup(
            spacePlotComponent3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 278, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(spacePlotComponent3);

        jSplitPane2.setResizeWeight(0.5);

        org.jdesktop.layout.GroupLayout spacePlotComponent1Layout = new org.jdesktop.layout.GroupLayout(spacePlotComponent1);
        spacePlotComponent1.setLayout(spacePlotComponent1Layout);
        spacePlotComponent1Layout.setHorizontalGroup(
            spacePlotComponent1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 260, Short.MAX_VALUE)
        );
        spacePlotComponent1Layout.setVerticalGroup(
            spacePlotComponent1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 279, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(spacePlotComponent1);

        org.jdesktop.layout.GroupLayout spacePlotComponent2Layout = new org.jdesktop.layout.GroupLayout(spacePlotComponent2);
        spacePlotComponent2.setLayout(spacePlotComponent2Layout);
        spacePlotComponent2Layout.setHorizontalGroup(
            spacePlotComponent2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 260, Short.MAX_VALUE)
        );
        spacePlotComponent2Layout.setVerticalGroup(
            spacePlotComponent2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 279, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(spacePlotComponent2);

        jSplitPane1.setBottomComponent(jSplitPane2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MultiView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private org.bm.blaise.specto.space.SpacePlotComponent spacePlotComponent1;
    private org.bm.blaise.specto.space.SpacePlotComponent spacePlotComponent2;
    private org.bm.blaise.specto.space.SpacePlotComponent spacePlotComponent3;
    // End of variables declaration//GEN-END:variables

}