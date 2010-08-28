/*
 * GraphTableModel.java
 * Created May 15, 2010
 */

package graphexplorer;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.bm.blaise.scio.graph.Graph;
import org.bm.blaise.scio.graph.ValuedGraph;
import org.bm.blaise.scio.graph.metrics.NodeMetric;

/**
 * Provides a model for displaying a table of vertices within a graph. Displays the index of a vertex, its label
 * (if a labeled graph), its screen location, the degree, and (optionally) its value for a particular metric.
 *
 * @see Graph
 *
 * @author Elisha Peterson
 */
public final class GraphTableModel extends AbstractTableModel
        implements PropertyChangeListener {

    /** The graph being tracked */
    GraphController gc = null;
    /** Tracks the values for the metric for each vertex */
    transient List<Number> values;

    /** Constructs without a graph */
    public GraphTableModel() {
    }

    /** Constructs with specified graph. */
    public GraphTableModel(GraphController gc) {
        setController(gc);
    }

    /** Sets the controller that contains the data backed by this model. */
    public void setController(GraphController gc) {
        if (this.gc != gc) {
            if (this.gc != null)
                this.gc.removePropertyChangeListener(this);
            this.gc = gc;
            if (gc != null)
                gc.addPropertyChangeListener(this);
            updateValues();
        }
    }

    /** Updates table values based on current GraphController */
    private void updateValues() {
        values = gc == null ? null : gc.getMetricValues();
        fireTableDataChanged();
    }

    //
    // TableModel METHODS
    //

    public int getRowCount() {
        if (gc == null) return 0;
        Graph graph = gc.getActiveGraph();
        return graph == null ? 0 : graph.order();
    }

    static final int                     COL_NODE = 0,   COL_LABEL = 1, COL_LOC = 2,  COL_DEGREE = 3, COL_METRIC = 4;
    protected String[] colNames =       { "Node",       "Label",       "Location",   "Degree",       "Metric" };
    protected Class[] colTypes =        { Object.class, String.class,  String.class, Integer.class,  Number.class };
    static final int[] PREFERRED_WIDTH = { 20,           60,            50,           20,             30 };

    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int col) {
        return colNames[col];
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return colTypes[col];
    }

    public Object getValueAt(int row, int col) {
        Graph graph = gc.getActiveGraph();
        Object node = graph.nodes().get(row);
        switch (col) {
            case COL_NODE :
                return node;
            case COL_LABEL :
                Object value = graph instanceof ValuedGraph ? ((ValuedGraph)graph).getValue(node) : null;
                if (value == null)
                    return null;
                else if (value.getClass().isArray()) {
                    Object[] value2 = (Object[]) value;
                    for (Object o : value2)
                        if (o instanceof String)
                            return o;
                    return Arrays.deepToString(value2);
                } else {
                    return value.toString();
                }
            case COL_LOC:
                Point2D.Double pos = gc.getPositions().get(node);
                return String.format("(%.3f,%.3f)", pos.x, pos.y);
            case COL_DEGREE :
                return graph.degree(node);
            case COL_METRIC:
                if (values == null || row >= values.size())
                    values = gc.getMetricValues();
                return values == null ? null : values.get(row);
        }
        throw new IllegalArgumentException("Graph's table does not contain entry at (row, col) = (" + row + ", " + col + ").");
    }

    public boolean isLabelColumn(int col) {
        return col == COL_LABEL;
    }

    @Override public boolean isCellEditable(int row, int col) {
        return gc.getActiveGraph() instanceof ValuedGraph && col == COL_LABEL;
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int col) {
        if (!isCellEditable(row, col))
            throw new IllegalArgumentException("GraphTableModel: cannot set value at (row, col) = (" + row + ", " + col + ") to " + aValue);
        gc.setActiveGraphLabel(gc.getActiveGraph().nodes().get(row), aValue.toString());
    }

    public void propertyChange(PropertyChangeEvent evt) {
//        System.out.println("gtm pc: " + evt.getSource() + " " + evt.getNewValue());
        if (evt.getSource() == gc) {
            if (evt.getPropertyName().equals("metric")) {
                NodeMetric m = gc.getMetric();
                String curTitle = colNames[COL_METRIC];
                String newTitle = m == null ? "Metric" : m.toString();
                if (!newTitle.equals(curTitle))
                    colNames[COL_METRIC] = newTitle;
                updateValues();
            } else if (evt.getPropertyName().equals("primary")
                    || evt.getPropertyName().equals("time") || evt.getPropertyName().equals("values"))
                updateValues();
            else if (evt.getPropertyName().equals("positions"))
                fireTableDataChanged();
        } else if (evt.getSource() instanceof GraphControllerMaster) {
            if (evt.getPropertyName().equals("active")) {
                setController(((GraphControllerMaster)evt.getSource()).getActiveController());
            }
        }
    }
}