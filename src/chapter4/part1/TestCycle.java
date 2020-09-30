package chapter4.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestCycle {
    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));

        Cycle c = new Cycle(g);
        if (c.hasCycle()) {
            for (Iterable<Integer> cycle : c.getCycles()) {
                for (int v : cycle) {
                    StdOut.print(v + " ");
                }
                StdOut.println();
            }
            StdOut.println();
        } else {
            StdOut.println("No cycles");
        }
    }
}
