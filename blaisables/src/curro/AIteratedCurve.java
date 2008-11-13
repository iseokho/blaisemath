/*
 * ASpaceFilling.java
 *
 * Created on April 2, 2008, 9:03 AM
 */

package curro;

import scio.coordinate.R2;
import sequor.control.NumberSlider;
import sequor.control.SliderBox;
import specto.euclidean2.FractalShape2D;

/**
 *
 * @author  ae3263
 */
public class AIteratedCurve extends javax.swing.JApplet {
    
    /** Initializes the applet ASpaceFilling */
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    FractalShape2D.Edges se1 = new FractalShape2D.Edges(new R2(0,0), new R2(1/3.,0));
                    se1.add(1/3.,1/3.);
                    se1.add(2/3.,1/3.);
                    se1.add(2/3.,0);
                    se1.add(1,0);
                    plot2D1.add(se1);
                    NumberSlider ns = new NumberSlider(210,10,se1.getIterModel());
                    ns.setName("Iterations");
                    plot2D1.add(ns,3,5);
                    plot2D1.getVisometry().setDesiredBounds(-.4,-.4,1.4,.7);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plot2D1 = new specto.euclidean2.Plot2D();

        plot2D1.setAnimatorVisible(false);
        plot2D1.setAxisVisible(false);
        plot2D1.setGridVisible(false);
        plot2D1.setMarkerBoxVisible(false);

        javax.swing.GroupLayout plot2D1Layout = new javax.swing.GroupLayout(plot2D1);
        plot2D1.setLayout(plot2D1Layout);
        plot2D1Layout.setHorizontalGroup(
            plot2D1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        plot2D1Layout.setVerticalGroup(
            plot2D1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plot2D1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plot2D1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private specto.euclidean2.Plot2D plot2D1;
    // End of variables declaration//GEN-END:variables
    
}