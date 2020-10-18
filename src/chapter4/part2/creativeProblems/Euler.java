package chapter4.part2.creativeProblems;

import chapter1.part3.Queue;
import chapter1.part3.Stack;
import chapter4.part2.Digraph;
import chapter4.part2.KosarajuSCC;
import chapter4.part2.exercises.Degrees;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Ex4.2.20
 */
public class Euler {
    private Stack<Integer> cycle;
    private Stack<Integer> path;

    public Euler(Digraph g) {
        boolean hasCycle = true;
        // check that g is connected
        KosarajuSCC scc = new KosarajuSCC(g);
        if (scc.count() != 1) {
            StdOut.println("Digraph is not connected");
            hasCycle = false;
        }
        if (hasCycle) {
            // check that every vertex has the same in-degree and out-degree
            Degrees degrees = new Degrees(g);
            for (int v = 0; v < g.v() && hasCycle; v++) {
                if (degrees.indegree(v) != degrees.outdegree(v)) {
                    StdOut.println("In-degree and out-degree of vertex " + v + " are not equal");
                    hasCycle = false;
                }
            }
        }
        if (hasCycle) {
            path = new Stack<>();
            cycle = new Stack<>();
            // we want to copy the digraph into a Queue<>[], easier to work with than Bag since we can remove the edges
            Queue<Integer>[] adj = (Queue<Integer>[]) new Queue[g.v()];
            for (int v = 0; v < g.v(); v++) {
                adj[v] = new Queue<>();
                for (int w : g.adj(v)) {
                    adj[v].enqueue(w);
                }
            }
            path.push(0);
            // this loop is similar to recursive DFS, just that we remove each edge after we use it
            while (!path.isEmpty()) {
                int v = path.peek();
                if (!adj[v].isEmpty()) {
                    int w = adj[v].dequeue();
                    path.push(w);
                } else {
                    // all edges removed => backtrack by popping the stuck vertex out of path and push to cycle
                    cycle.push(path.pop());
                }
            }
        }
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public boolean hasCycle() { return cycle != null; }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        Euler euler = new Euler(g);
        StdOut.println(euler.hasCycle());
        for (int v : euler.cycle()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
    }
}
