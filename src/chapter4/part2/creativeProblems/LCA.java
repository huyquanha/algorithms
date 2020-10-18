package chapter4.part2.creativeProblems;

import chapter1.part3.Queue;
import chapter4.part2.Digraph;
import chapter4.part2.TransitiveClosure;
import chapter4.part2.exercises.Degrees;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * ex4.2.21
 */
public class LCA {
    private boolean[] marked;
    private int[] height;
    private TransitiveClosure transitiveClosure;

    public LCA(Digraph g) {
        marked = new boolean[g.v()];
        height = new int[g.v()];
        Degrees d = new Degrees(g);
        // we only iterate through the sources (in-degree = 0)
        for (int s : d.sources()) {
            bfs(g, s);
        }
        transitiveClosure = new TransitiveClosure(g.reverse());
    }

    private void bfs(Digraph g, int s) {
        Queue<Integer> q = new Queue<>();
        marked[s] = true;
        height[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    height[w] = height[v] + 1;
                    q.enqueue(w);
                }
            }
        }
    }

    public int lca(int v, int w) {
        int maxHeight = -1;
        int lca = -1;
        for (int i = 0; i < height.length; i++) {
            if (transitiveClosure.reachable(v, i) && transitiveClosure.reachable(w, i) &&
                maxHeight < height[i]) {
                maxHeight = height[i];
                lca = i;
            }
        }
        return lca;
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        LCA lca = new LCA(g);
        In in = new In();
        while (!in.isEmpty()) {
            int lowestCommonAncestor = lca.lca(in.readInt(), in.readInt());
            StdOut.println(lowestCommonAncestor);
        }
    }
}
