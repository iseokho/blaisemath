/*
 * AVectorField.java
 *
 * Created on October 29, 2008, 6:51 AM
 */

package curro;

import sequor.control.NumberSlider;
import sequor.control.SliderBox;
import specto.euclidean2.PlaneFunction2D;
import specto.euclidean2.VectorField2D;

/**
 *
 * @author  ae3263
 */
public class APlaneFunction extends javax.swing.JApplet {

    /** Initializes the applet AVectorField */
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    functionPanel1.setFunction(0, "f(x,y)=", "cos(x)*sin(y)");
                    PlaneFunction2D pf1 = new PlaneFunction2D(functionPanel1.getFunctionModel(0));                    
                    plot2D1.add(pf1);
                    VectorField2D vf1 = new VectorField2D(pf1.getGradientFunction());
                    plot2D1.add(vf1);
                    
                    NumberSlider ns = vf1.getStyleSlider(0,0);
                    ns.setName("Style");
                    plot2D1.add(ns,5,5);
                    plot2D1.getVisometry().setDesiredBounds(-3,-3,3,3);
                    
                    functionPanel2.setFunction(0, "P(x,y)=", "cos(y)+x");
                    functionPanel2.setFunction(1, "Q(x,y)=", "sin(x)+y");
                    VectorField2D vf2 = new VectorField2D(functionPanel2.getFunctionModel(0), functionPanel2.getFunctionModel(1));                    
                    plot2D2.add(vf2.getDivergence());
                    plot2D2.add(vf2.getScalarCurl());
                    plot2D2.add(vf2);
                    plot2D2.add(vf2.getFlowCurve());
                    
                    SliderBox sb = SliderBox.getStyleAdjusters(0,0,15,plot2D2.getPlottables(),plot2D2);
                    plot2D2.add(sb,5,5);
                    plot2D2.getVisometry().setDesiredBounds(-3,-3,3,3);
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        plot2D1 = new specto.euclidean2.Plot2D();
        functionPanel1 = new sequor.component.FunctionPanel(1);
        jSplitPane2 = new javax.swing.JSplitPane();
        functionPanel2 = new sequor.component.FunctionPanel(2);
        plot2D2 = new specto.euclidean2.Plot2D();

        jSplitPane1.setDividerLocation(40);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setToolTipText("Plot function with density plot, along with the gradient field");

        plot2D1.setAxisStyle(1);

        javax.swing.GroupLayout plot2D1Layout = new javax.swing.GroupLayout(plot2D1);
        plot2D1.setLayout(plot2D1Layout);
        plot2D1Layout.setHorizontalGroup(
            plot2D1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 609, Short.MAX_VALUE)
        );
        plot2D1Layout.setVerticalGroup(
            plot2D1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(plot2D1);
        jSplitPane1.setLeftComponent(functionPanel1);

        jTabbedPane2.addTab("Gradient of Function", jSplitPane1);

        jSplitPane2.setDividerLocation(70);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setToolTipText("Plots divergence and (scalar) curl values of a vector field");
        jSplitPane2.setLeftComponent(functionPanel2);

        plot2D2.setAxisStyle(1);

        javax.swing.GroupLayout plot2D2Layout = new javax.swing.GroupLayout(plot2D2);
        plot2D2.setLayout(plot2D2Layout);
        plot2D2Layout.setHorizontalGroup(
            plot2D2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 609, Short.MAX_VALUE)
        );
        plot2D2Layout.setVerticalGroup(
            plot2D2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(plot2D2);

        jTabbedPane2.addTab("Div and Curl of Vector Field", jSplitPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private sequor.component.FunctionPanel functionPanel1;
    private sequor.component.FunctionPanel functionPanel2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private specto.euclidean2.Plot2D plot2D1;
    private specto.euclidean2.Plot2D plot2D2;
    // End of variables declaration//GEN-END:variables

}