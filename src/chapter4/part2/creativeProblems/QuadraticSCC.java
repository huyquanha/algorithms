package chapter4.part2.creativeProblems;

import chapter4.part2.Digraph;
import chapter4.part2.SCC;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ex4.2.23
 */
public class QuadraticSCC extends SCC {
    private int[] id;
    private int count;

    public QuadraticSCC(Digraph g) {
        super(g);
        id = new int[g.v()];
        Arrays.fill(id, -1);
        for (int v = 0; v < g.v(); v++) {
            if (id[v] == -1) {
                Map<Integer, Boolean> intersection = new HashMap<>();
                dfs(g, v, new boolean[g.v()], intersection);
                dfs(g.reverse(), v, new boolean[g.v()], intersection);
                for (int w : intersection.keySet()) {
                    if (intersection.get(w)) {
                        id[w] = count;
                    }
                }
                count++;
            }
        }
    }

    private void dfs(Digraph g, int v, boolean[] marked, Map<Integer, Boolean> intersection) {
        marked[v] = true;
        if (!intersection.containsKey(v)) {
            intersection.put(v, false);
        } else {
            // this is the second traversal, because in the first traversal we never re-encounter a vertex
            intersection.put(v, true);
        }
        for (int w : g.adj(v)) {
            if (!marked[w]) dfs(g, w, marked, intersection);
        }
    }

    public boolean stronglyConnected(int v, int w) { return id[v] == id[w]; }

    public int count() { return count; }

    public int id(int v) { return id[v]; }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        QuadraticSCC scc = new QuadraticSCC(g);
        StdOut.println(scc.count());
        for (int v = 0; v < g.v(); v++) {
            StdOut.println(v + ": " + scc.id(v));
        }
    }
}
