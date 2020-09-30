package chapter4.part1;

import chapter1.part3.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestCC {
    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));
        CC cc = new CC(g);

        int m = cc.count();
        StdOut.println(m + " components");

        Bag<Integer>[] components = (Bag<Integer>[]) new Bag[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Bag<>();
        }
        for (int v = 0; v < g.v(); v++) {
            components[cc.id(v)].add(v);
        }
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
