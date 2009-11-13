/**
 * PlaneParametricFunction.java
 * Created on Aug 9, 2009
 */
package org.bm.blaise.specto.plane.function;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import org.apache.commons.math.FunctionEvaluationException;
import org.bm.blaise.specto.visometry.VisometryGraphics;
import org.bm.blaise.specto.visometry.VisometryMouseEvent;
import org.bm.blaise.specto.visometry.Visometry;
import org.bm.blaise.specto.plottable.VComputedPath;
import org.bm.blaise.specto.plottable.VRectangle;
import org.bm.utils.LineSampleSet;
import scio.function.MultivariateVectorialFunction;

/**
 * <p>
 *   <code>PlaneParametricFunction</code> plots a 2d array of curves depending
 *   upon two functions that each depend upon two input parameters... So the
 *   input region is a square. The output is displayed as a grid.
 * </p>
 *
 * @author Elisha Peterson
 */
public class PlaneParametricFunction2 extends VComputedPath<Point2D.Double> {

    /** The underlying function, 2 inputs, 2 outputs */
    MultivariateVectorialFunction func;
    /** Range of x-values for display purposes */
    LineSampleSet rangeX;
    /** Range of y-valeus for display purposes */
    LineSampleSet rangeY;
    /** Stores underlying rectangle */
    VRectangle<Point2D.Double> domain;

    /** Initializes with an underlying function and a step rate for going through parameter values.
     * @param func the function
     * @param parameterStep the step rate for parameters
     */
    public PlaneParametricFunction2(MultivariateVectorialFunction func, Point2D.Double min, Point2D.Double max) {
        setFunc(func);
        setXRange(new LineSampleSet(min.x, max.x));
        setYRange(new LineSampleSet(min.x, max.x));
        domain = new VRectangle<Point2D.Double>(min, max);
        domain.addChangeListener(this);
    }

    //
    //
    // BEAN PATTERNS
    //
    //

    public MultivariateVectorialFunction getFunc() {
        return func;
    }

    public void setFunc(MultivariateVectorialFunction func) {
        if (func != null && this.func != func) {
            this.func = func;
            needsComputation = true;
        }
    }

    public LineSampleSet getXRange() {
        return rangeX;
    }

    public void setXRange(LineSampleSet range) {
        this.rangeX = range;
        needsComputation = true;
    }

    public LineSampleSet getYRange() {
        return rangeY;
    }

    public void setYRange(LineSampleSet range) {
        this.rangeY = range;
        needsComputation = true;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == domain) {
            rangeX.setMin(domain.getPoint1().x);
            rangeX.setMax(domain.getPoint2().x);
            rangeY.setMin(domain.getPoint1().y);
            rangeY.setMax(domain.getPoint2().y);
        }
        super.stateChanged(e);
    }

    //
    //
    // DRAW METHODS
    //
    //



    public void visometryChanged(Visometry vis, VisometryGraphics canvas) {
        // MAY EVENTUALLY REQUIRE RECOMPUTATION, BUT NOT RIGHT NOW
    }

    /** Recomputes the visual path for the function. */
    protected void recompute(VisometryGraphics<Point2D.Double> vg) {
        try {
            if (path == null) {
                path = new GeneralPath();
            } else {
                path.reset();
            }
            List<Double> xx = rangeX.getSamples();
            List<Double> yy = rangeY.getSamples();

            double[] fval = new double[]{0, 0};

            // add path for each x value in sample
            for (Double x : xx) {
                fval = func.value(new double[]{x, rangeY.getMin()});
                path.moveTo((float) fval[0], (float) fval[1]);
                for (Double y : yy) {
                    fval = func.value(new double[]{x, y});
                    path.lineTo((float) fval[0], (float) fval[1]);
                }
            }
            // add path for each y value in sample
            for (Double y : yy) {
                fval = func.value(new double[]{rangeX.getMin(), y});
                path.moveTo((float) fval[0], (float) fval[1]);
                for (Double x : xx) {
                    fval = func.value(new double[]{x, y});
                    path.lineTo((float) fval[0], (float) fval[1]);
                }
            }
        } catch (FunctionEvaluationException ex) {
            Logger.getLogger(PlaneParametricFunction2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(VisometryGraphics<Point2D.Double> vg) {
        domain.paintComponent(vg);
        super.paintComponent(vg);
    }

    public boolean isClickablyCloseTo(VisometryMouseEvent<Point2D.Double> e) {
        return domain.isClickablyCloseTo(e);
    }

    @Override
    public void mouseDragged(VisometryMouseEvent<Point2D.Double> e) {
        domain.mouseDragged(e);
    }

    @Override
    public void mousePressed(VisometryMouseEvent<Point2D.Double> e) {
        domain.mousePressed(e);
    }

    @Override
    public void mouseReleased(VisometryMouseEvent<Point2D.Double> e) {
        domain.mouseReleased(e);
    }



    @Override
    public String toString() {
        return "Plane Function";
    }
}
