package web.week6;

import chapter1.part3.Stack;
import chapter4.part2.BreadthFirstDirectedPaths;
import chapter4.part2.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class ShortestDirectedCycle {
    private Stack<Integer> shortestCycle;

    public ShortestDirectedCycle(Digraph g) {
        Digraph r = g.reverse();
        int length = g.v() + 1;
        for (int v = 0; v < g.v(); v++) {
            BreadthFirstDirectedPaths paths = new BreadthFirstDirectedPaths(r, v);
            for (int w : g.adj(v)) {
                if (paths.hasPathTo(w) && (paths.distTo(w) + 1 < length)) {
                    length = paths.distTo(w) + 1;
                    shortestCycle = new Stack<>();
                    for (int x : paths.pathTo(w)) {
                        shortestCycle.push(x);
                    }
                    shortestCycle.push(v);
                }
            }
        }
    }

    public boolean isDAG() {
        return shortestCycle == null;
    }

    public Iterable<Integer> shortestCycle () {
        return shortestCycle;
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        ShortestDirectedCycle sdc = new ShortestDirectedCycle(g);
        if (!sdc.isDAG()) {
            for (int v : sdc.shortestCycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
