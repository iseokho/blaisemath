/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TestPlane.java
 *
 * Created on Jan 29, 2011, 11:27:39 AM
 */

package test;

import org.bm.blaise.graphics.renderer.BasicPointRenderer;
import org.bm.blaise.graphics.renderer.BasicStrokeRenderer;
import org.bm.blaise.graphics.renderer.PointRenderer;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import org.bm.blaise.specto.plane.PlanePlotComponent;
import org.bm.blaise.specto.plottable.VPoint;
import org.bm.blaise.specto.plottable.VPointGraph;
import org.bm.blaise.specto.plottable.VPointSet;
import org.bm.blaise.specto.plottable.VPolygonalPath;
import org.bm.blaise.specto.plottable.VSegment;
import utils.IndexedGetter;
import utils.MapGetter;

/**
 *
 * @author Elisha
 */
public class TestPlane extends javax.swing.JFrame {

    /** Creates new form TestPlane */
    public TestPlane() {
        initComponents();
        PlanePlotComponent ppc = new PlanePlotComponent();

        for (int i = 0; i < 100; i++) {
            ppc.add(new VPoint<Point2D.Double>(new Point2D.Double(Math.random(), Math.random())));
        }

        final Point2D.Double[] arr = new Point2D.Double[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Point2D.Double(-Math.random(), -Math.random());
        }
        ppc.add(new VPointSet<Point2D.Double>(arr, new BasicPointRenderer().fill(Color.blue).stroke(Color.white).radius(3)));

        final Point2D.Double[] arr2 = new Point2D.Double[100];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = new Point2D.Double(2-Math.random(), -Math.random());
        }
        ppc.add(new VPointSet<Point2D.Double>(arr2, new IndexedGetter<PointRenderer>() {
            public PointRenderer getElement(int i) { return new BasicPointRenderer()
                    .fill(new Color((2*i) % 255, (7*i) % 255, (17*i) %255))
                    .stroke(Color.red)
                    .radius(i % 10); }
            public void setElement(int i, PointRenderer point) { }
            public int getSize() { return arr2.length; }
        }));

        final Point2D.Double[] arr3 = new Point2D.Double[500];
        arr3[0] = new Point2D.Double(-1.0, 1.0);
        for (int i = 1; i < arr3.length; i++) {
            arr3[i] = new Point2D.Double(arr3[i-1].x + .02*Math.random(), arr3[i-1].y - .02*Math.random());
        }
        for (int i = 0; i < arr3.length; i++) {
            if (Math.random() < .5) arr3[i] = null;
        }
        ppc.add(new VPolygonalPath<Point2D.Double>(arr3));

        final Point2D.Double[] arr4 = new Point2D.Double[100];
        for (int i = 0; i < arr4.length; i++) {
            arr4[i] = new Point2D.Double(-Math.random(), 1+Math.random());
        }
        int[][] arr4e = new int[200][2];
        for (int i = 0; i < arr4e.length; i++) {
            arr4e[i] = new int[] { (int)(arr4.length*Math.random()), (int)(arr4.length*Math.random()) };
        }
        ppc.add(new VPointGraph<Point2D.Double>(arr4, arr4e, new IndexedGetter<PointRenderer>() {
            public PointRenderer getElement(int i) { return new BasicPointRenderer()
                    .fill(new Color((2*i)%255, (7*i)%255, (17*i)%255, 128+(29*i)%127))
                    .stroke(Color.red)
                    .radius(i % 10); }
            public void setElement(int i, PointRenderer point) { }
            public int getSize() { return arr2.length; }
        }));

        final Point2D.Double[] arr5 = new Point2D.Double[100];
        for (int i = 0; i < arr5.length; i++) {
            arr5[i] = new Point2D.Double(-1-Math.random(), Math.random());
        }
        int[][] arr5e = new int[200][2];
        Map<int[],BasicStrokeRenderer> arr5em = new HashMap<int[],BasicStrokeRenderer>();
        for (int i = 0; i < arr5e.length; i++) {
            arr5e[i] = new int[] { (int)(arr5.length*Math.random()), (int)(arr5.length*Math.random()) };
            arr5em.put(arr5e[i],
                    new BasicStrokeRenderer(
                        new Color((2*i)%255, (7*i)%255, (17*i)%255, 128+(29*i)%127),
                        2 + arr5e[i][0]/50
                    ));
        }
        VPointGraph vpg = new VPointGraph<Point2D.Double>(arr5, arr5e, new IndexedGetter<PointRenderer>() {
            public PointRenderer getElement(int i) { return new BasicPointRenderer()
                    .fill(new Color((2*i) % 255, (7*i) % 255, (17*i) %255))
                    .stroke(Color.red)
                    .radius(i % 10); }
            public void setElement(int i, PointRenderer point) { }
            public int getSize() { return arr2.length; }
        });
        ppc.add(vpg);
        vpg.setEdgeCustomizer(new MapGetter.MapInstance(arr5em));

        ppc.add(new VSegment<Point2D.Double>(new Point2D.Double(-3,-3), new Point2D.Double(3,3)));

        add(ppc);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestPlane().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
