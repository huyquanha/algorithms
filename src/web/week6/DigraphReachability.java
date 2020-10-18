package web.week6;

import chapter4.part2.Digraph;
import chapter4.part2.KosarajuSCC;

public class DigraphReachability {
    private int sinkVertex;

    public DigraphReachability(Digraph g) {
        KosarajuSCC scc = new KosarajuSCC(g);
        int[] outDegrees = new int[scc.count()];
        int[] representatives = new int[scc.count()];
        for (int v = 0; v < g.v(); v++) {
            int componentId = scc.id(v);
            representatives[componentId] = v;
            for (int w : g.adj(v)) {
                if (!scc.stronglyConnected(v, w)) {
                    // increase out-degree of id[v]
                    outDegrees[componentId]++;
                }
            }
        }
        // contract each strongly connected component into a single vertex.
        // A vertex that is reachable by all other vertices only exists if there's only one strongly connected component with out-degree = 0
        int sinkComponentId = -1;
        sinkVertex = -1;
        for (int i = 0; i < scc.count(); i++) {
            if (outDegrees[i] == 0) {
                if (sinkComponentId == -1) {
                    sinkComponentId = i;
                } else {
                    sinkComponentId = -1;
                    break;
                }
            }
        }
        if (sinkComponentId != -1) {
            sinkVertex = representatives[sinkComponentId];
        }
    }

    public boolean hasReachableVertex() { return sinkVertex != -1; }

    public int getReachableVertex() { return sinkVertex; }
}
