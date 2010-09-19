/*
 * VAxis.java
 * Created Sep 13, 2010
 */

package visometry.plottable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import primitive.GraphicRuledLine;
import primitive.GraphicString;
import primitive.style.ArrowStyle;
import primitive.style.ArrowStyle.ArrowShape;
import primitive.style.RuledLineStyle;
import primitive.style.StringStyle;
import visometry.VPrimitiveEntry;

/**
 * A line with (possibly labeled) tick marks, and also an optional label.
 *
 * @param <C> base coordinate system
 *
 * @author Elisha
 */
public class VAxis<C> extends Plottable<C> {

    /** Ruled lines */
    protected GraphicRuledLine<C> rules;
    /** Ruled lines entry. */
    protected VPrimitiveEntry enRules;

    /** Axis */
    protected C[] axis;
    /** Axis entry */
    protected VPrimitiveEntry enAxis;

    /** Label */
    protected GraphicString<C> label;
    /** Axis label entry */
    protected VPrimitiveEntry enLabel;

    /** Whether to use multiples of pi for the tick marks. */
    protected boolean usePi = false;

    /**
     * Construct with specified label.
     * @param label axis label (may be null)
     * @param ends endpoints of the axis
     */
    public VAxis(String label, C[] ends) {
        addPrimitive( 
                enRules = new VPrimitiveEntry(
                    rules = new GraphicRuledLine<C>(ends[0], ends[1], null, null),
                    new RuledLineStyle()),
                enAxis = new VPrimitiveEntry(
                    axis = ends, 
                    new ArrowStyle(new Color(96, 96, 160), 1f, ArrowShape.REGULAR, ArrowShape.REGULAR, 10)),
                enLabel = new VPrimitiveEntry(
                    this.label = new GraphicString<C>(ends[1], label),
                    new StringStyle()) );
    }

    //
    // BEAN PROPERTY PATTERNS
    //

    /** @return first endpoint of axis */
    protected C getPoint1() { return axis[0]; }
    /** @return second endpoint of axis */
    protected C getPoint2() { return axis[1]; }
    /** Sets first endpoint of axis */
    protected void setPoint1(C newValue) {
        if (!axis[0].equals(newValue)) {
            axis[0] = newValue;
            rules.start = newValue;
            firePlottableChanged();
        }
    }
    /** Sets second endpoint of axis */
    protected void setPoint2(C newValue) {
        if (!axis[1].equals(newValue)) {
            axis[1] = newValue;
            rules.end = newValue;
            firePlottableChanged();
        }
    }

    /** @return style of axis */
    public ArrowStyle getAxisStyle() { return (ArrowStyle) enAxis.style; }
    /** Sets style of axis */
    public void setAxisStyle(ArrowStyle style) { enAxis.style = style; firePlottableStyleChanged(); }

    /** @return true if first axis uses multiples of pi. */
    public boolean isUsePi() { return usePi; }
    /** Sets pi status */
    public void setUsePi(boolean value) { if (usePi != value) { usePi = value; firePlottableStyleChanged(); } }

    /** @return style of rules */
    public RuledLineStyle getRuleStyle() { return (RuledLineStyle) enRules.style; }
    /** Sets style of rules */
    public void setRuleStyle(RuledLineStyle style) { enRules.style = style; firePlottableStyleChanged(); }

    /** Returns horizontal label. */
    public String getLabel() { return label.string; }
    /** Sets horizontal label. */
    public void setLabel(String label) {
        if (!this.label.string.equals(label)) {
            this.label.setString(label);
            firePlottableStyleChanged();
        }
    }

    /** @return label style */
    public StringStyle getLabelStyle() { return (StringStyle) enLabel.style; }
    /** Sets label style */
    public void setLabelStyle(StringStyle style) { enLabel.style = style; firePlottableStyleChanged(); }

    /** @return visibility of label */
    public boolean isLabelVisible() { return enLabel.visible; }
    /** Sets visibility of label */
    public void setLabelVisible(boolean val) { enLabel.visible = val; firePlottableStyleChanged(); }

    /** Sets all styles */
    public void setStyles(StringStyle lStyle, ArrowStyle aStyle, RuledLineStyle rStyle) {
        enLabel.style = lStyle;
        enAxis.style = aStyle;
        enRules.style = rStyle;
        firePlottableStyleChanged();
    }

    //
    // COMPUTATION
    //

    /**
     * Updates axis elements to specified coordinates/ticks/labels
     * @param start first endpoint of axis
     * @param end second endpoint of axis
     * @param lPos position of label anchor
     * @param samples sample double values generated by the axis
     * @param xStart starting double value of axis
     * @param xEnd ending double value of axis
     * @param xLabelStart start for first tick/label
     * @param xLabelEnd end for first tick/label
     * @param showZero whether a 0 value should be shown
     */
    public void updateAxis(C start, C end, C lPos,
            List<Double> samples,
            double xStart, double xEnd, double xLabelStart, double xLabelEnd,
            boolean showZero) {
        double length = xEnd - xStart;
        ArrayList<Double> xx2 = new ArrayList<Double>();
        for(Double x : samples)
            if (x >= xLabelStart && x <= xLabelEnd && (showZero || x != 0.0))
                xx2.add(x);
        int size = xx2.size();
        double[] ticks = new double[size];
        String[] tickLabels = new String[size];
        for(int i = 0; i < size; i++) {
            ticks[i] = (xx2.get(i) - xStart) / length;
            tickLabels[i] = PlottableConstants.FLOAT_FORMAT.format(xx2.get(i));
        }

        rules.start = start;
        rules.end = end;
        rules.ticks = ticks;
        rules.tickLabels = tickLabels;
        axis[0] = start;
        axis[1] = end;
        label.anchor = lPos;
        enRules.needsConversion = enAxis.needsConversion = enLabel.needsConversion = true;
    }
}
