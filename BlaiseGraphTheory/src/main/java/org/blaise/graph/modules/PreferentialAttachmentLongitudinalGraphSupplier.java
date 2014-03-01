/*
 * PreferentialAttachmentGraphSupplier.java
 * Created May 27, 2010
 */
package org.blaise.graph.modules;

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

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import org.blaise.graph.Graph;
import org.blaise.graph.longitudinal.IntervalLongitudinalGraph;
import org.blaise.graph.longitudinal.LongitudinalGraph;
import org.blaise.graph.longitudinal.LongitudinalGraphSupplierSupport;

/**
 * Provides static utility methods for generating graphs using preferential
 * attachment.
 *
 * @author Elisha Peterson
 */
public final class PreferentialAttachmentLongitudinalGraphSupplier extends LongitudinalGraphSupplierSupport<Integer> {

    private Graph<Integer> seed;
    private int edgesPerStep = 1;
    private float[] connectProbs = null;

    public PreferentialAttachmentLongitudinalGraphSupplier(Graph<Integer> seed) {
        this.seed = seed;
    }

    public PreferentialAttachmentLongitudinalGraphSupplier(Graph<Integer> seed, int nodes, int edgesPerStep) {
        super(seed.isDirected(), nodes);
        setSeed(seed);
        setEdgesPerStep(edgesPerStep);
    }

    public PreferentialAttachmentLongitudinalGraphSupplier(Graph<Integer> seed, int nodes, float[] probs) {
        super(seed.isDirected(), nodes);
        setSeed(seed);
        this.connectProbs = probs;
    }

    public Graph<Integer> getSeed() {
        return seed;
    }

    public void setSeed(Graph<Integer> seed) {
        if (seed == null) {
            throw new NullPointerException();
        } else if (seed.edgeCount() == 0) {
            throw new IllegalArgumentException("PreferentialAttachment seed must be non-empty: " + seed);
        } else if (seed.isDirected()) {
            throw new UnsupportedOperationException("Algorithm not supported for directed graphs: " + seed);
        }
        this.seed = seed;
    }

    public int getEdgesPerStep() {
        return edgesPerStep;
    }

    public void setEdgesPerStep(int edgesPerStep) {
        if (edgesPerStep < 0) {
            throw new IllegalArgumentException();
        }
        this.edgesPerStep = edgesPerStep;
    }

    public float[] getConnectProbabilities() {
        return connectProbs;
    }

    /**
     * Probabilities of initial #s of connections; the i'th entry is the
     * probability that a new node will have i connections, starting at 0
     */
    public void setConnectProbabilities(float[] connectProbs) {
        this.connectProbs = connectProbs;
    }

    public LongitudinalGraph<Integer> get() {
        return connectProbs == null ? generateLongitudinal(seed, nodes, edgesPerStep)
                : generateLongitudinal(seed, nodes, connectProbs);
    }

    /**
     * Common method to return longitudinal version of the randomly generated
     * graph.
     */
    private static LongitudinalGraph<Integer> generateLongitudinal(Graph<Integer> seedGraph, final int nVertices, Object edgesPerStep) {
        // prepare parameters for graph to be created
        int nSeed = seedGraph.nodeCount();
        Map<Integer, double[]> nodeTimes = new TreeMap<Integer, double[]>();
        Map<Integer, Map<Integer, double[]>> edgeTimes = new TreeMap<Integer, Map<Integer, double[]>>();
        int[] degrees = new int[nVertices];
        Arrays.fill(degrees, 0);
        int degreeSum = 0;
        double time = 0;

        // initialize with values from seed graph
        final double timeMax = nVertices - nSeed + 1;
        final double[] allTime = new double[]{0, timeMax};
        for (Integer i : seedGraph.nodes()) {
            nodeTimes.put(i, allTime);
        }

        for (Integer i1 : nodeTimes.keySet()) {
            for (Integer i2 : nodeTimes.keySet()) {
                if (seedGraph.adjacent(i1, i2)) {
                    degreeSum += addEdge(edgeTimes, allTime, degrees, i1, i2);
                }
            }
        }

        int cur = 0;
        boolean variableEdgeNumber = edgesPerStep instanceof float[];
        int numberEdgesToAdd = variableEdgeNumber ? 0 : (Integer) edgesPerStep;
        float[] connectionProbs = variableEdgeNumber ? (float[]) edgesPerStep : new float[]{};

        while (nodeTimes.size() < nVertices) {
            time++;
            while (nodeTimes.containsKey(cur)) {
                cur++;
            }
            nodeTimes.put(cur, new double[]{time, timeMax});
            if (variableEdgeNumber) {
                numberEdgesToAdd = sampleRandom(connectionProbs);
            }
            degreeSum += addEdge(edgeTimes, new double[]{time, timeMax}, degrees, cur,
                    weightedRandomVertex(degrees, degreeSum, numberEdgesToAdd));
        }

        return IntervalLongitudinalGraph.getInstance(false, (int) (timeMax), nodeTimes, edgeTimes);
    }

    /**
     * Utility to add specified vertices to the edge set and increment the
     * corresponding degrees.
     *
     * @param edges current list of edges
     * @param timeInterval time interval to use for the edge
     * @param degrees current list of degrees
     * @param v1 first vertex of edge to add
     * @param attachments second vertex (vertices) of edges to add
     * @return number of new degrees added
     */
    private static int addEdge(Map<Integer, Map<Integer, double[]>> edges,
            double[] timeInterval, int[] degrees, int v1, int... attachments) {
        for (int node : attachments) {
            if (!edges.containsKey(v1)) {
                edges.put(v1, new TreeMap<Integer, double[]>());
            }
            edges.get(v1).put(node, timeInterval);
            degrees[node]++;
        }
        degrees[v1] += attachments.length;
        return attachments.length * 2;
    }

    /**
     * Utility to return random vertices in a graph, whose weights are specified
     * by the given array
     *
     * @param weights array describing the weights of vertices in the graph
     * @param sumWeights the sum of weights
     * @param num the number of results to return
     * @return indices of randomly chosen vertex; will be distinct
     */
    private static int[] weightedRandomVertex(int[] weights, int sumWeights, int num) {
        if (num < 0) {
            throw new IllegalArgumentException("weightedRandomVertex: requires positive # of results: " + num);
        }
        if (num == 0) {
            return new int[]{};
        }

        int[] result = new int[num];
        int nFound = 0;
        double[] random = new double[num];
        for (int i = 0; i < num; i++) {
            random[i] = Math.random() * sumWeights;
        }
        double partialSum = 0;
        for (int i = 0; i < weights.length; i++) {
            partialSum += weights[i];
            for (int j = 0; j < num; j++) {
                if (partialSum > random[j]) {
                    result[j] = i;
                    if (++nFound == num) {
                        return result;
                    }
                }
            }
        }
        throw new IllegalStateException("weightedRandomVertex: should not be here since sum random is less than total degree\n"
                + "weights = " + Arrays.toString(weights) + ", sumWeights = " + sumWeights + ", num = " + num);
    }

    /**
     * @return index of a randomly chosen # in provided array of probabilities
     */
    private static int sampleRandom(float[] probs) {
        double rand = Math.random();
        float sum = 0f;
        for (int i = 0; i < probs.length; i++) {
            sum += probs[i];
            if (sum > rand) {
                return i;
            }
        }
        throw new IllegalStateException("Should not be here since sum random is less than total");
    }
}
