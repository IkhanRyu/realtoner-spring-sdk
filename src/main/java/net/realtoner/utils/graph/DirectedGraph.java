package net.realtoner.utils.graph;

import java.util.List;

/**
 *
 * @author RyuIkHan
 * */
public interface DirectedGraph extends Graph{

    /**
     * return source list of graph. Source is the vertex which does not have any
     * in-bound edge.
     *
     * @return the source list of graph. If there is no source , return empty list. never return null.
     * */
    List<String> getSourceList();

    /**
     *
     * @return
     * */
    List<String> getSinkList();

    /**
     *
     * @param vertexName
     * @return
     * */
    boolean isGivenVertexSource(String vertexName);

    /**
     * @param vertexName
     * @return
     * */
    boolean isGivenVertexSink(String vertexName);

    /**
     *
     * @return
     * */
    boolean hasCycle();

    /**
     *
     * @return the list of strongly connected components. If there is no strongly connected component,
     * return empty list. never return null.
     * */
    List<DirectedGraph> getStronglyConnectedComponents();
}
