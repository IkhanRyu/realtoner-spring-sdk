package net.realtoner.utils.graph;

import java.util.*;

/**
 * The graph using adjacent list. Adjacent list is
 * In this class , it does not use adjacent "list". Alternatively, it uses adjacent "map". The reason uses "map" is
 * that if use "list" , the name of vertex should be a "number".
 *
 * @author RyuIkHan
 */
public class AdjacentListGraph extends AbstractGraph implements DirectedGraph {

    /*
    *
    * */
    private final Map<String, Set<GraphEdge>> adjacentMap = new HashMap<>();

    /*
    *
    * */
    private final Map<String , Set<GraphEdge>> inverseAdjacentMap = new HashMap<>();

    /**
     * {@inheritDoc}
     * */
    @Override
    protected void addEdge(GraphEdge graphEdge) {
        adjacentMap.get(graphEdge.from).add(graphEdge);
        inverseAdjacentMap.get(graphEdge.to).add(graphEdge);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    protected void addVertexToGraph(String vertexName) {

        if (!adjacentMap.containsKey(vertexName)) {
            adjacentMap.put(vertexName, new HashSet<GraphEdge>());
            inverseAdjacentMap.put(vertexName , new HashSet<GraphEdge>());
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean contains(String vertexName) {
        return adjacentMap.containsKey(vertexName);
    }

    @Override
    public String[] getAdjacentVerticesArray(String vertexName) {

        Set<GraphEdge> adjacentVerticesSet = adjacentMap.get(vertexName);
        String[] verticesArr = new String[adjacentVerticesSet.size()];

        int i = 0;

        for(GraphEdge graphEdge : adjacentVerticesSet)
            verticesArr[i++] = graphEdge.to;

        return verticesArr;
    }

    @Override
    public Set<String> getAdjacentVerticesSet(String vertexName) {
        return null;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void removeVertex(String vertexName) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean isDirectedGraph() {
        return true;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean hasPath(String from, String to) {

        Set<String> visitLog = new HashSet<>();

        return findPathThoughDFS(visitLog, from, to);
    }

    @Override
    public boolean hasAdjacentVertex(String vertexName, String adjacentVertexName) {
        return false;
    }

    /**
     * recursive method for checking whether there is the path from given "vertexName" to given "targetVertex" or not.
     * In this method, it uses depth first search(DFS) to check existence of path.
     *
     * @param visitLog the log of exploring
     * @param vertexName the start vertex of this path
     * @param targetVertex the destination of this path
     * @return true -> there is at least a path from "vertexName" to "targetVertex" , false -> if not
     */
    private boolean findPathThoughDFS(Set<String> visitLog, String vertexName, String targetVertex) {

        visitLog.add(vertexName);

        Set<GraphEdge> graphEdgeSet = adjacentMap.get(vertexName);

        if (graphEdgeSet == null)
            return false;

        for (GraphEdge graphEdge : graphEdgeSet) {

            if (!visitLog.contains(graphEdge.to)) {
                if (graphEdge.to.equals(targetVertex) || findPathThoughDFS(visitLog, graphEdge.to, targetVertex))
                    return true;

            } else if (graphEdge.to.equals(targetVertex))
                return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public int getNumOfVertices() {
        return adjacentMap.size();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public List<String> getSourceList() {

        List<String> sourceList = new ArrayList<>();

        for(Map.Entry<String , Set<GraphEdge>> entry : inverseAdjacentMap.entrySet())
            if(entry.getValue().size() == 0)
                sourceList.add(entry.getKey());

        return sourceList;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public List<String> getSinkList() {

        List<String> sinkList = new ArrayList<>();

        for(Map.Entry<String , Set<GraphEdge>> entry : adjacentMap.entrySet())
            if(entry.getValue().size() == 0)
                sinkList.add(entry.getKey());

        return sinkList;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean isGivenVertexSource(String vertexName) {

        return inverseAdjacentMap.containsKey(vertexName) && inverseAdjacentMap.get(vertexName).size() == 0;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean isGivenVertexSink(String vertexName) {

        return adjacentMap.containsKey(vertexName) && adjacentMap.get(vertexName).size() == 0;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean hasCycle() {

        StronglyConnectedComponentProcessInfo info = createStronglyConnectedComponentProcessInfo();

        for(String vertexName : adjacentMap.keySet()){

            VertexWrapper vertexWrapper = info.getVertexWrapper(vertexName);

            if(vertexWrapper.index < 0 && detectCycle(info , vertexWrapper))
                return true;
        }

        return false;
    }

    /**
     *
     * @param info
     * @param vertexWrapper
     * @return
     * */
    private boolean detectCycle(StronglyConnectedComponentProcessInfo info , VertexWrapper vertexWrapper){

        vertexWrapper.low = vertexWrapper.index = info.getIndex();
        info.increaseIndex();

        info.push(vertexWrapper);
        vertexWrapper.onStack = true;

        for(GraphEdge graphEdge : adjacentMap.get(vertexWrapper.vertexName)){
            VertexWrapper tempWrapper = info.getVertexWrapper(graphEdge.to);

            if(tempWrapper.index < 0){
                boolean cycleFlag = detectCycle(info , tempWrapper);

                if(cycleFlag)
                    return true;

                vertexWrapper.low = Math.min(vertexWrapper.low , tempWrapper.low);

            }else if(tempWrapper.onStack)
                return true;
        }

        if(vertexWrapper.index == vertexWrapper.low)
            info.pop().onStack = false;

        return false;
    }

    @Override
    public List<DirectedGraph> getStronglyConnectedComponents() {

        StronglyConnectedComponentProcessInfo info = createStronglyConnectedComponentProcessInfo();

        for(String vertexName : adjacentMap.keySet()){

            VertexWrapper vertexWrapper = info.getVertexWrapper(vertexName);

            if(vertexWrapper.index < 0)
                findStronglyConnectedComponent(info, vertexWrapper);
        }

        return info.getComponentList();
    }

    /**
     * recursive method for finding strongly connected component. For finding strongly connected component ,
     * this method uses "Tarjan's algorithm".
     *
     * @param info hold the information of current finding strongly connected component process
     * @param vertexWrapper the name of current vertex
     */
    private void findStronglyConnectedComponent(StronglyConnectedComponentProcessInfo info,
                                                VertexWrapper vertexWrapper) {

        vertexWrapper.low = vertexWrapper.index = info.getIndex();
        info.increaseIndex();

        info.push(vertexWrapper);
        vertexWrapper.onStack = true;

        for (GraphEdge graphEdge : adjacentMap.get(vertexWrapper.vertexName)) {
            VertexWrapper tempWrapper = info.getVertexWrapper(graphEdge.to);

            if (tempWrapper.index < 0) {
                findStronglyConnectedComponent(info, tempWrapper);

                vertexWrapper.low = Math.min(vertexWrapper.low, tempWrapper.low);

            } else if (tempWrapper.onStack) {
                vertexWrapper.low = Math.min(vertexWrapper.low, tempWrapper.index);
            }

            info.addEdge(graphEdge);
        }

        //current vertex is root
        if(vertexWrapper.index == vertexWrapper.low){
            VertexWrapper top = info.pop();
            AdjacentListGraph graph = new AdjacentListGraph();

            while(!vertexWrapper.vertexName.equals(top.vertexName)){
                graph.addVertex(top.vertexName);
                top.onStack = false;

                top = info.pop();
            }

            graph.addVertex(top.vertexName);
            top.onStack = false;

            //add edges
            for(GraphEdge graphEdge : info.graphEdgeSet)
                graph.addEdge(graphEdge.from , graphEdge.to , graphEdge.weight);

            graph.clearInvalidEdges();

            info.addDirectedGraph(graph);
        }
    }

    public void printGraph() {

        System.out.println("------------------start------------------");

        for (Map.Entry<String, Set<GraphEdge>> entry : adjacentMap.entrySet()) {
            System.out.print(entry.getKey() + " : ");

            for (GraphEdge graphEdge : entry.getValue())
                System.out.print(graphEdge.to + " -> ");

            System.out.println();
        }

        System.out.println("------------------end------------------");
    }

    /**
     * The wrapper of vertex. It is for finding strongly connected component process.
     * */
    class VertexWrapper {

        int index = -1;
        int low;

        String vertexName = null;

        boolean onStack = false;

        boolean dead = false;

        VertexWrapper(String vertexName) {
            this.vertexName = vertexName;
        }
    }

    /**
     * have information of finding strongly connected component process.
     * */
    class StronglyConnectedComponentProcessInfo {

        int index = 0;
        final List<VertexWrapper> stack = new ArrayList<>();
        final Map<String, VertexWrapper> vertexWrapperMap = new HashMap<>();
        final List<DirectedGraph> componentList = new ArrayList<>();
        final Set<GraphEdge> graphEdgeSet = new HashSet<>();

        StronglyConnectedComponentProcessInfo() {

        }

        int getIndex(){
            return index;
        }

        void increaseIndex(){
            index++;
        }

        void putVertexWrapper(VertexWrapper vertexWrapper){
            vertexWrapperMap.put(vertexWrapper.vertexName, vertexWrapper);
        }

        VertexWrapper getVertexWrapper(String vertexName){
            return vertexWrapperMap.get(vertexName);
        }

        int push(VertexWrapper vertexWrapper) {

            stack.add(vertexWrapper);

            return stack.size();
        }

        VertexWrapper pop() {

            if(stack.size() == 0)
                return null;

            VertexWrapper vertexWrapper = stack.get(stack.size() - 1);

            stack.remove(stack.size() - 1);

            return vertexWrapper;
        }

        void addEdge(GraphEdge graphEdge){
            graphEdgeSet.add(graphEdge);
        }

        void addDirectedGraph(DirectedGraph directedGraph){
            componentList.add(directedGraph);
        }

        List<DirectedGraph> getComponentList(){
            return componentList;
        }
    }

    /**
     * create StronglyConnectedComponentProcessInfo and initializing vertex wrapper using vertices of this graph.
     *
     * @return initialized StrongConnectedComponentProcessInfo
     * */
    private StronglyConnectedComponentProcessInfo createStronglyConnectedComponentProcessInfo() {

        StronglyConnectedComponentProcessInfo info = new StronglyConnectedComponentProcessInfo();

        for(String vertexName : adjacentMap.keySet())
            info.putVertexWrapper(new VertexWrapper(vertexName));

        return info;
    }
}
