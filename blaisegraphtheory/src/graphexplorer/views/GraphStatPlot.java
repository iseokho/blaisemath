/*
 * GraphStatPlot.java
 * Created on Dec 20, 2010, 6:27:39 PM
 */

package graphexplorer.views;

import graphexplorer.controller.GraphController;
import graphexplorer.controller.GraphControllerListener;
import graphexplorer.controller.GraphStatController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.List;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.YIntervalRenderer;
import org.jfree.data.xy.DefaultIntervalXYDataset;
import util.stats.views.FrequencyTableModel;

/**
 * Provides a view of the current graph's stats.
 * @author elisha
 */
public class GraphStatPlot extends javax.swing.JPanel
        implements GraphControllerListener {

    /** The stat controller */
    GraphStatController gsc = null;

    /** Chart displaying metric distribution */
    ChartPanel distributionCP;
    /** Distribution plot */
    XYPlot distributionPlot;

    /** Construct instance of the plot panel */
    public GraphStatPlot() {
        setLayout(new BorderLayout());
        initChart("Metric");
        distributionCP.setPreferredSize(new Dimension(400,300));
        add(distributionCP, BorderLayout.CENTER);
    }

    public void setController(GraphController gc) {
        if (gsc != gc.getStatController()) {
            if (gsc != null)
                gsc.removePropertyChangeListener(this);
            gsc = gc.getStatController();
            if (gsc != null)
                gsc.addPropertyChangeListener(this);
            updateValues();
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        updateValues();
    }

    /** Initializes the chart */
    void initChart(String metricString) {
        NumberAxis xAxis = new NumberAxis(metricString);
        xAxis.setAutoRangeIncludesZero(false);
        NumberAxis yAxis = new NumberAxis("Number of Nodes");
        YIntervalRenderer renderer = new YIntervalRenderer();
        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        distributionPlot = new XYPlot(new DefaultIntervalXYDataset(), xAxis, yAxis, renderer);
        distributionPlot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart chart = new JFreeChart(metricString + " distribution",
                JFreeChart.DEFAULT_TITLE_FONT,
                distributionPlot, false);
        if (distributionCP == null)
            distributionCP = new ChartPanel(chart);
        else
            distributionCP.setChart(chart);
    }

    /** Updates the display. */
    void updateValues() {
        List values = gsc.getValues();
        if (values == null || gsc.getMetric() == null) {
            distributionPlot.setDataset(null);
            distributionPlot.getDomainAxis().setLabel("Metric");
            distributionCP.getChart().setTitle("Metric distribution");
        } else {
            String metricString = gsc.getMetric().toString();

            if (distributionPlot == null)
                initChart(metricString);
            else {
                distributionPlot.getDomainAxis().setLabel(metricString);
                distributionCP.getChart().setTitle(metricString + " distribution");
            }

            FrequencyTableModel dtm = new FrequencyTableModel(values);
            int[] counts = dtm.getCounts();
            int nSamples = counts.length;
            double[][] data = new double[6][counts.length];
            double x, y;
            for (int i = 0; i < nSamples; i++) {
                x = ((Number)dtm.getValue(i)).doubleValue();
                y = counts[i];
                data[0][i] = data[1][i] = data[2][i] = x;
                data[3][i] = data[5][i] = y;
                data[4][i] = 0;
            }
            DefaultIntervalXYDataset chartData = new DefaultIntervalXYDataset();
            chartData.addSeries(metricString + " counts", data);
            distributionPlot.setDataset(chartData);
        }
    }
}
