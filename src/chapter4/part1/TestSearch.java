package chapter4.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestSearch {
    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        Search search = new BreadthFirstSearch(g, s); // can be replaced with BreadthFirstSearch

        for (int v = 0; v < g.v(); v++) {
            if (search.marked(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();

        if (search.count() != g.v()) {
            StdOut.print("NOT ");
        }
        StdOut.println("connected");
    }
}
