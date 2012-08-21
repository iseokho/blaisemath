/*
 * GraphMetrics.java
 * Created May 12, 2010
 */

package org.blaise.graph.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.blaise.graph.Graph;

/**
 * Utility library for compiling information about metrics in a graph.
 * 
 * @author Elisha Peterson
 */
public class GraphMetrics {

    //
    // METHODS FOR COMPILING INFORMATION ABOUT METRICS IN A GRAPH (ALL NODES)
    //
    
    public static <N> List<N> computeValues(Graph graph, GraphNodeMetric<N> metric) {
        List<N> result = new ArrayList<N>();
        for (Object node : graph.nodes()) {
            result.add((N) metric.value(graph, node));
        }
        return result;
    }

    /**
     * Returns computeDistribution of the values of a particular metric
     * @param graph the graph
     * @param metric metric used to generate values
     */
    public static <N> Map<N,Integer> computeDistribution(Graph graph, GraphNodeMetric<N> metric) {
        return distribution(computeValues(graph, metric));
    }

    /**
     * Computes the computeDistribution associated with a given map, i.e. the number of entries corresponding
     * to each particular value. If the values are of a <code>Comparable</code> type, the map is sorted
     * according to that order.
     * @param values the values to consolidate
     * @return a mapping from the values to the count of those values
     */
    public static <N> Map<N, Integer> distribution(Collection<N> values) {
        if (values.isEmpty()) {
            return Collections.emptyMap();
        }
        boolean comparable = false;
        for (N en : values) { comparable = en instanceof Comparable; break; }
        Map<N, Integer> result = comparable ? new TreeMap<N, Integer>() : new HashMap<N, Integer>();

        for (N en : values) {
            result.put(en, result.containsKey(en) ? result.get(en) + 1 : 1);
        }

        return result;
    }
}
