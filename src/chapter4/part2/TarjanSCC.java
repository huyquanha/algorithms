package chapter4.part2;

import chapter1.part3.Queue;
import chapter1.part3.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * See https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm
 * for more information
 */
public class TarjanSCC extends SCC {
    private int[] id; // each vertex's order of traversal by DFS
    private boolean[] marked;
    private boolean[] onStack;
    private int[] lowLink;
    private int count;
    private int curIdx;
    private Stack<Integer> s;
    private Queue<Iterable<Integer>> components;

    public TarjanSCC(Digraph g) {
        super(g);
        components = new Queue<>();
        s = new Stack<>();
        id = new int[g.v()];
        marked = new boolean[g.v()];
        onStack = new boolean[g.v()];
        lowLink = new int[g.v()];
        for (int v = 0; v < g.v(); v++) {
            if (!marked[v]) dfs(g, v);
        }
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        id[v] = curIdx;
        lowLink[v] = curIdx;
        curIdx++;
        s.push(v);
        onStack[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
                lowLink[v] = Math.min(lowLink[v], lowLink[w]);
            } else if (onStack[w]) {
                lowLink[v] = Math.min(lowLink[v], id[w]);
            }
        }
        if (lowLink[v] == id[v]) {
            // this is the root of a SCC, together with all the vertices above it in the stack
            Queue<Integer> scc = new Queue<>();
            int w;
            do {
                w = s.pop();
                scc.enqueue(w);
                id[w] = count;
                onStack[w] = false;
            } while (w != v);
            components.enqueue(scc); // SCCs are added to components in reverse topological order
            count++;
        }
    }

    public boolean stronglyConnected(int v, int w) { return id[v] == id[w]; }

    public int count() { return count; }

    public int id(int v) { return id[v]; }

    public Iterable<Integer>[] getComponents() {
        Queue<Integer>[] result = (Queue<Integer>[]) new Queue[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Queue<>();
            for (int v : components.dequeue()) {
                result[i].enqueue(v);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        TarjanSCC scc = new TarjanSCC(g);
        StdOut.println(scc.count());
        for (int v = 0; v < g.v(); v++) {
            StdOut.println(v + ": " + scc.id(v));
        }
    }
}
