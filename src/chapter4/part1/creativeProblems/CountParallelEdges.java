package chapter4.part1.creativeProblems;

import chapter4.part1.Graph;

import java.util.HashSet;

/**
 * Ex4.1.32
 */
public class CountParallelEdges {
    public static int count(Graph g) {
        HashSet<Integer>[] edgeSet = (HashSet<Integer>[]) new HashSet[g.v()];
        // each parallel edge will be counted twice, from v to w and from w to v
        int doubleCount = 0;
        for (int v = 0; v < g.v(); v++) {
            for (int w : g.adj(v)) {
                if (edgeSet[v].contains(w)) {
                    doubleCount++;
                } else {
                    edgeSet[v].add(w);
                }
            }
        }
        return doubleCount / 2;
    }
}
