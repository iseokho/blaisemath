/*
 * LongitudinalGraphComponent.java
 * Created July 7, 2010
 */

package com.googlecode.blaisemath.graph.view;

/*
 * #%L
 * BlaiseGraphTheory
 * --
 * Copyright (C) 2009 - 2014 Elisha Peterson
 * --
 * Licensed under the Apache License, Version 2.0.
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.googlecode.blaisemath.graph.Graph;
import com.googlecode.blaisemath.graph.longitudinal.LongitudinalGraph;
import com.googlecode.blaisemath.graph.view.GraphComponent;
import com.googlecode.blaisemath.graph.layout.GraphLayoutManager;
import com.googlecode.blaisemath.graph.view.PlaneGraphAdapter;
import org.jdesktop.layout.GroupLayout;

/**
 * This panel displays a longitudinal graph together with a slider bar that can
 * be used to transition across various time slices of the graph. This panel also
 * keeps track of the layout mechanism, which can then apply consistently across each
 * slice of the graph.
 *
 * @author Elisha Peterson
 */
public final class LongitudinalGraphComponent extends JPanel
        implements PropertyChangeListener {

    /** Time graph manager */
    private LongitudinalGraphManager manager;
    /** Time slider */
    private final LongitudinalGraphSlider slider;
    /** Currently active graph component */
    private final GraphComponent plot;
    /** Flag telling component whether to update its own graph upon receiving a property time change */
    private boolean updateWithTime = true;

    /** Time label (overlays on plot) */
    private final JLabel timeLabel;


    // <editor-fold defaultstate="collapsed" desc="Constructors & Initializers">

    /** Constructs a longitudinal graph panel without an actual graph. */
    public LongitudinalGraphComponent() {
        this(null, null);
    }

    /** Construct instance with specified graph manager */
    public LongitudinalGraphComponent(LongitudinalGraphManager m) {
        this(m, null);
    }

    /** Construct instance with specified graph manager */
    public LongitudinalGraphComponent(LongitudinalGraphManager m, GraphLayoutManager gm) {
        super(new java.awt.BorderLayout());
        plot = new GraphComponent();
        slider = new LongitudinalGraphSlider();
        timeLabel = new JLabel("Slice t=??");
        initComponents();
        setManager(m, gm);
    }

    private void initComponents() {
        timeLabel.setFont(timeLabel.getFont().deriveFont(Font.ITALIC, 10f));
        timeLabel.setForeground(Color.DARK_GRAY);
        timeLabel.setOpaque(false);
        GroupLayout layout = new GroupLayout(plot);
        plot.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
                .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(213, Short.MAX_VALUE)
                .add(timeLabel)
                .addContainerGap()) );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
                .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(206, Short.MAX_VALUE)
                .add(timeLabel)
                .addContainerGap())
        );

        add(plot, BorderLayout.CENTER);
        add(slider, BorderLayout.SOUTH); // will later be replaced
        setPreferredSize(new Dimension(600, 600));
        validate();
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Property Patterns">

    /** Sets the underlying graph */
    public void setTimeGraph(LongitudinalGraph g) {
        setManager(new LongitudinalGraphManager(g));
    }

    /** @return plot on which graph is displayed */
    public GraphComponent getGraphComponent() { return plot; }

    /** @return graph adapter */
    public PlaneGraphAdapter getGraphAdapter() { return plot.getAdapter(); }

    /** @return manager for the longitudinal graph */
    public LongitudinalGraphManager getManager() { return manager; }

    /** @return manager for graph */
    public GraphLayoutManager getGraphManager() { return plot.getGraphManager(); }

    /** Changes the manager for the longitudinal graph */
    public void setManager(LongitudinalGraphManager m) { setManager(m, null); }

    /** Changes the manager for the longitudinal graph */
    private void setManager(LongitudinalGraphManager m, GraphLayoutManager gm) {
        if (this.manager != m) {
            if (this.manager != null)
                this.manager.removePropertyChangeListener(this);
            slider.setManager(m);
            this.manager = m;
            if (m != null)
                m.addPropertyChangeListener(this);
            if (gm == null) {
                Graph g = m == null ? null : m.getSlice();
                plot.setGraphManager(g == null ? null : new GraphLayoutManager(g));
            } else {
                plot.setGraphManager(gm);
            }
        }
    }

    public boolean isUpdateWithTime() {
        return updateWithTime;
    }

    public void setUpdateWithTime(boolean updateWithTime) {
        this.updateWithTime = updateWithTime;
    }

    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Event Handling">
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == manager) {
            if (evt.getPropertyName().equals("timeData")) {
                Object[] arr = (Object[]) evt.getNewValue();
                Double time = (Double) arr[0];
                Map<Object,Point2D.Double> pos = (Map<Object, Point2D.Double>) arr[1];
                if (updateWithTime) {
                    Graph gr = manager.getSlice();
                    plot.getGraphManager().setGraph(gr);
                }
                if (pos != null)
                    plot.getGraphManager().requestLocations(pos);
                timeLabel.setText("Slice t="+time);
            } else if (evt.getPropertyName().equals("nodePositions")) {
                plot.getGraphManager().requestLocations((Map<Object, Point2D.Double>) evt.getNewValue());
            }
        }
    }

    // </editor-fold>


// <editor-fold defaultstate="collapsed" desc="Show/Hide Label">
    private static JLabel hideNote;

    public void hidePlot() {
        remove(plot);
        add(hideNote = new JLabel("Exporting..."));
        repaint();
    }

    public void showPlot() {
        remove(hideNote);
        for (Component c : getComponents())
            if (c == plot)
                return;
        add(plot, java.awt.BorderLayout.CENTER);
        repaint();
    }
// </editor-fold>

}