package net.realtoner.utils.graph;

import java.util.*;

/**
 * <p>
 * This class defines basic methods for graph operation.
 * </p>
 * <p/>
 * <p>Some terminologies is proposed below.</p>
 * <p>
 * "valid edge" : the edge which both two vertices are exists in graph.
 * </p>
 * <p>
 * "invalid edge" : the edge which one or t
 * </p>
 *
 * @author RyuIkHan
 */
public abstract class AbstractGraph implements Graph {

    private static final int DEFAULT_EDGE_WEIGHT = 0;

    private final Map<String, Set<GraphEdge>> fromVertexNameInvalidEdgeSetMap = new HashMap<>();
    private final Map<String, Set<GraphEdge>> toVertexNameInvalidEdgeSetMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addEdge(String from, String to) {
        addEdge(from, to, DEFAULT_EDGE_WEIGHT);
    }

    /**
     * add edge to graph. If there is neither vertex "from" nor vertex "to" , given edge
     * will be "invalid edge". If it is valid , call addEdge(GraphEdge graphEdge).
     *
     * @param from   the name of vertex name where this edge starts
     * @param to     the name of vertex name where this edge reaches
     * @param weight the weight of this weight
     */
    @Override
    public final void addEdge(String from, String to, int weight) {

        GraphEdge graphEdge = new GraphEdge(from, to, weight);

        if (!contains(from) || !contains(to))
            addInvalidEdge(graphEdge);
        else
            addEdge(graphEdge);
    }

    /**
     * @param graphEdge
     */
    private void addInvalidEdge(GraphEdge graphEdge) {

        Set<GraphEdge> graphEdgeSet;

        graphEdgeSet = fromVertexNameInvalidEdgeSetMap.get(graphEdge.from);

        if (graphEdgeSet == null) {
            graphEdgeSet = new HashSet<>();

            graphEdgeSet.add(graphEdge);

            fromVertexNameInvalidEdgeSetMap.put(graphEdge.from, graphEdgeSet);
        } else
            graphEdgeSet.add(graphEdge);

        graphEdgeSet = toVertexNameInvalidEdgeSetMap.get(graphEdge.to);

        if (graphEdgeSet == null) {
            graphEdgeSet = new HashSet<>();

            graphEdgeSet.add(graphEdge);

            toVertexNameInvalidEdgeSetMap.put(graphEdge.to, graphEdgeSet);

        } else
            graphEdgeSet.add(graphEdge);

    }

    /**
     *
     * */
    protected void clearInvalidEdges() {
        fromVertexNameInvalidEdgeSetMap.clear();
        toVertexNameInvalidEdgeSetMap.clear();
    }

    /**
     * @param graphEdge
     */
    protected abstract void addEdge(GraphEdge graphEdge);

    /**
     * @param vertexName
     */
    @Override
    public final void addVertex(String vertexName) {

        if (contains(vertexName))
            return;

        addVertexToGraph(vertexName);

        Set<GraphEdge> invalidEdges = getGraphEdgeSetContainsGivenVertexName(vertexName);

        for (GraphEdge graphEdge : invalidEdges)
            addEdge(graphEdge);

        removeGraphEdgesInInvalidMap(invalidEdges);
    }

    /**
     * @param vertexName
     */
    protected abstract void addVertexToGraph(String vertexName);


    protected Set<GraphEdge> getGraphEdgeSetContainsGivenVertexName(String vertexName) {

        Set<GraphEdge> resultSet = new HashSet<>();

        if (fromVertexNameInvalidEdgeSetMap.containsKey(vertexName)) {

            for (GraphEdge graphEdge : fromVertexNameInvalidEdgeSetMap.get(vertexName))
                if (contains(graphEdge.to))
                    resultSet.add(graphEdge);
        }

        if (toVertexNameInvalidEdgeSetMap.containsKey(vertexName)) {

            for (GraphEdge graphEdge : toVertexNameInvalidEdgeSetMap.get(vertexName)) {
                if (contains(graphEdge.from))
                    resultSet.add(graphEdge);
            }
        }

        return resultSet;
    }

    /**
     * @param graphEdgeSet
     */
    protected void removeGraphEdgesInInvalidMap(Set<GraphEdge> graphEdgeSet) {

        for (GraphEdge graphEdge : graphEdgeSet) {

            if (fromVertexNameInvalidEdgeSetMap.containsKey(graphEdge.from))
                fromVertexNameInvalidEdgeSetMap.get(graphEdge.from).remove(graphEdge);

            if (toVertexNameInvalidEdgeSetMap.containsKey(graphEdge.to))
                toVertexNameInvalidEdgeSetMap.get(graphEdge.to).remove(graphEdge);
        }
    }


    /**
     *
     * */
    protected class GraphEdge {

        String from = null;
        String to = null;

        int weight;

        private int hashCode;

        GraphEdge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;

            hashCode = (this.from + "," + this.to).hashCode();
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof GraphEdge))
                return false;

            GraphEdge graphEdge = (GraphEdge) obj;

            return graphEdge.from.equals(from) && graphEdge.to.equals(to);
        }
    }
}
