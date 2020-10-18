package web.week6;

import chapter1.part3.Stack;
import chapter4.part1.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EulerCycle {
    private Stack<Integer> cycle;
    private boolean[] marked;
    private boolean[] onStack;

    public EulerCycle(Graph g) {
        // first check that the graph is connected and every vertex has even degree
        NonRecursiveDFS dfs = new NonRecursiveDFS(g, 0);
        if (dfs.count() != g.v()) {
            throw new IllegalArgumentException("g is not connected");
        }
        for (int v = 0; v < g.v(); v++) {
            if (g.adj(v).size() % 2 != 0) {
                throw new IllegalArgumentException(v + " has odd degree => cannot have Eulerian cycle");
            }
        }
        cycle = new Stack<>();
        // transform the graph by using Set as the adjacency list instead of Bag for near constant time removal
        Set<Integer>[] adj = (Set<Integer>[]) new HashSet[g.v()];
        for (int v = 0; v < g.v(); v++) {
            adj[v] = new HashSet<>();
            for (int w : g.adj(v)) {
                adj[v].add(w);
            }
        }
        Stack<Integer> path = new Stack<>();
        path.push(0);
        while (!path.isEmpty()) {
            int v = path.peek();
            Iterator<Integer> itr = adj[v].iterator();
            if (itr.hasNext()) {
                int w = itr.next();
                itr.remove();
                adj[w].remove(v);
                path.push(w);
            } else {
                cycle.push(path.pop());
            }
        }
    }

    public boolean hasCycle() { return cycle != null; }

    public Iterable<Integer> cycle() { return cycle; }

    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));
        EulerCycle eulerCycle = new EulerCycle(g);
        if (eulerCycle.hasCycle()) {
            for (int v : eulerCycle.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
