/**
 * PlaneGrid.java
 * Created on Aug 3, 2009
 */

package org.bm.blaise.specto.plane;

import java.awt.geom.Point2D;
import org.bm.blaise.specto.primitive.BlaisePalette;
import org.bm.blaise.specto.visometry.AbstractPlottable;
import org.bm.blaise.specto.visometry.Visometry;
import org.bm.blaise.specto.visometry.VisometryGraphics;
import org.bm.blaise.specto.primitive.PathStyle;
import org.bm.blaise.specto.visometry.VisometryChangeListener;
import org.bm.utils.NiceRangeGenerator;

/**
 * <p>
 *   <code>PlaneGrid</code> is a grid on a plot.
 * </p>
 *
 * @author Elisha Peterson
 */
public class PlaneGrid extends AbstractPlottable<Point2D.Double> implements VisometryChangeListener {

    /** Default approximate spacing between the grid. */
    private static final int PIXEL_SPACING = 80;

    /** Style for the grid elements. */
    PathStyle style = new PathStyle( BlaisePalette.STANDARD.grid() );

    /** Construct a default grid. */
    public PlaneGrid() {
    }

    public PathStyle getStrokeStyle() {
        return style;
    }

    public void setStrokeStyle(PathStyle style) {
        this.style = style;
    }

    // PAINT METHODS

    transient double[] xValues;
    transient double[] yValues;

    public void visometryChanged(Visometry vis, VisometryGraphics canvas) {
        if (canvas instanceof PlaneGraphics) {
            PlaneGraphics pg = ((PlaneGraphics) canvas);
            Point2D.Double min = pg.getMinimumVisible();
            Point2D.Double max = pg.getMaximumVisible();
            xValues = NiceRangeGenerator.STANDARD.niceRange(min.x, max.x, pg.getIdealHStepForPixelSpacing(PIXEL_SPACING));
            yValues = NiceRangeGenerator.STANDARD.niceRange(min.y, max.y, pg.getIdealVStepForPixelSpacing(PIXEL_SPACING));
        }
    }

    @Override
    public void paintComponent(VisometryGraphics<Point2D.Double> canvas) {
        PlaneGraphics pg = (PlaneGraphics) canvas;
        canvas.setPathStyle(style);
        for (Double x : xValues) {
            pg.drawVerticalLine(x);
        }
        for (Double y : yValues) {
            pg.drawHorizontalLine(y);
        }
    }
}
