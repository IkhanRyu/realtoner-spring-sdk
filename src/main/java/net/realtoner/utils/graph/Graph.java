package net.realtoner.utils.graph;

import java.util.Set;

/**
 * <p>
 *      The data structure which consists of a finite (and possibly mutable) set
 *      of vertices or nodes or points, together with a set of unordered pairs of
 *      these vertices for an undirected graph or a set of ordered pairs for a
 *      directed graph. These pairs are known as edges or arcs or lines for an
 *      undirected graph and as arrows or directed edges or directed arcs or
 *      directed lines for a directed graph.
 * </p>
 * <br />
 * <p>
 *     The vertex is also called "node" or "point". In this interface and any sub
 *     classes, use just the name "vertex". And the edge is called "point" , "line"
 *     and so on. Like vertex , use just the name "edge". A edge must have  two vertex name.
 *     These vertex names relatively, one is the vertex name which this edge start
 *     from and other one is the vertex name for which this edge reach to.
 * </p>
 * <br />
 * <p>
 *     {@link Graph} provides operations for graph. Basically, it provides adding a vertex
 *     operation and removing a vertex operation. And it also provides adding a edge operation
 *     and removing edge operation. Adding or removing a vertex operation just use
 *     vertex name. When user add or remove a edge to graph , must enter two names of vertex
 *     which one for new edge start from and another one for reach to. Optionally,
 *     enter weight. If does not enter weight, new edge will have "0" weight.
 * </p>
 *
 * @see AbstractGraph
 * @author RyuIkHan
 */
public interface Graph {

    /**
     * add a vertex to graph
     *
     * @param vertexName vertex name to be added
     */
    void addVertex(String vertexName);

    /**
     * add a edge without weight. It sets the edge weight "0".
     *
     * @param from the name of vertex name where this edge starts
     * @param to the name of vertex where this edge reaches
     */
    void addEdge(String from, String to);

    /**
     * add a edge with weight.
     *
     * @param from the name of vertex name where this edge starts
     * @param to the name of vertex where this edge reaches
     * @param weight the weight of this edge
     * */
    void addEdge(String from, String to, int weight);

    /**
     * check given vertex name exists in this graph.
     *
     * @param vertexName the vertex name
     * @return true if given vertex name exists in graph , false if not.
     */
    boolean contains(String vertexName);

    /**
     *
     * @param vertexName
     * @return
     * */
    String[] getAdjacentVerticesArray(String vertexName);

    /**
     *
     * @param vertexName
     * @return
     * */
    Set<String> getAdjacentVerticesSet(String vertexName);

    /**
     * remove vertex using vertex name.
     *
     * @param vertexName the name of vertex which will be removed.
     * */
    void removeVertex(String vertexName);

    /**
     * check this graph is directed graph or not.
     *
     * @return true if it's directed graph. false if not.
     */
    boolean isDirectedGraph();

    /**
     * check there is path start from given "from" vertex and reach to given "to"
     * vertex.
     *
     * @param from the name of vertex which the path starts from.
     * @param to the name of vertex which the path reaches to.
     * @return true if there is such path. false if not.
     */
    boolean hasPath(String from, String to);

    /**
     *
     * @param vertexName
     * @param adjacentVertexName
     * @return
     * */
    boolean hasAdjacentVertex(String vertexName , String adjacentVertexName);

    /**
     * return the number of vertices in graph.
     *
     * @return the number of vertices
     * */
    int getNumOfVertices();


}
