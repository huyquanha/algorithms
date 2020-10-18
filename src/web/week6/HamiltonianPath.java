package web.week6;

import chapter1.part3.Queue;
import chapter4.part2.DepthFirstOrder;
import chapter4.part2.Digraph;
import chapter4.part2.DirectedCycle;

public class HamiltonianPath {
    private boolean hasPath;
    private Queue<Integer> path;

    public HamiltonianPath(Digraph g) {
        // throws error if the graph is not acyclic
        DirectedCycle cycleFinder = new DirectedCycle(g);
        if (cycleFinder.hasCycle()) {
            throw new IllegalArgumentException("Graph has cycles");
        }
        DepthFirstOrder dfo = new DepthFirstOrder(g);
        // a hamiltonian path only exists if there's an edge between any 2 consecutive vertices in the reverse post order
        int prev = -1;
        hasPath = true;
        path = new Queue<>();
        for (int v : dfo.reversePost()) {
            if (prev != -1) {
                if (!g.hasEdge(prev, v)) {
                    hasPath = false;
                    break;
                }
            }
            path.enqueue(v);
            prev = v;
        }
    }

    public boolean hasPath() { return hasPath; }

    public Iterable<Integer> path() { return path; }
}
