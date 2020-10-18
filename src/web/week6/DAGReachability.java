package web.week6;

import chapter4.part2.Digraph;

/**
 * find a vertex that is reachable from all other vertices in a DAG
 */
public class DAGReachability {
    private int sinkVertex;

    public DAGReachability(Digraph g) {
        // g has such a vertex <=> there exists only one vertex in g with out-degree = 0
        sinkVertex = -1;
        for (int v = 0; v < g.v(); v++) {
            if (g.adj(v).size() == 0) {
                if (sinkVertex == -1) {
                    sinkVertex = v;
                } else {
                    sinkVertex = -1;
                    break;
                }
            }
        }
    }

    public boolean hasReachableVertex() { return sinkVertex != -1; }

    public int getReachableVertex() { return sinkVertex; }
}
