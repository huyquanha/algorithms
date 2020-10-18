package chapter4.part2.creativeProblems;

import chapter4.part2.DepthFirstOrder;
import chapter4.part2.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Ex4.2.24
 */
public class HamiltonianPaths {
    private Iterable<Integer> path;

    public HamiltonianPaths(Digraph g) {
        DepthFirstOrder dfo = new DepthFirstOrder(g);
        int prev = -1;
        boolean hasPath = true;
        for (int v : dfo.reversePost()) {
            if (prev != -1) {
                if (!g.hasEdge(prev, v)) {
                    hasPath = false;
                    break;
                }
            }
            prev = v;
        }
        if (hasPath) {
            path = dfo.reversePost();
        }
    }

    public boolean hasPath() { return path != null; }

    public Iterable<Integer> path() { return path; }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        HamiltonianPaths paths = new HamiltonianPaths(g);
        if (paths.hasPath()) {
            for (int v : paths.path()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else {
            StdOut.println("No Hamiltonian path");
        }
    }
}
