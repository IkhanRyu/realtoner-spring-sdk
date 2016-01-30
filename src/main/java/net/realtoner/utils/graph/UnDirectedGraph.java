package net.realtoner.utils.graph;

import java.util.List;

/**
 *
 * @author RyuIkHan
 */
public interface UnDirectedGraph extends Graph{

    /**
     *
     * @return
     * */
    List<UnDirectedGraph> getConnectedComponent();
}
