package chapter4.part2;

import java.util.HashMap;
import java.util.Map;

public class Topological {
    private Iterable<Integer> order;

    public Topological(Digraph g) {
        DirectedCycle cycleFinder = new DirectedCycle(g);
        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder dfo = new DepthFirstOrder(g);
            order = dfo.reversePost();
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order != null;
    }

    /**
     * Ex4.2.9 check whether a permutation of g's vertices is a topological order of g
     * @param perm
     * @return
     */
    public boolean isTopological(Digraph g, int[] perm) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < perm.length; i++) {
            map.put(perm[i], i);
        }
        for (int v = 0; v < g.v(); v++) {
            for (int w : g.adj(v)) {
                if (map.get(v) > map.get(w)) {
                    return false;
                }
            }
        }
        return true;
    }
}
