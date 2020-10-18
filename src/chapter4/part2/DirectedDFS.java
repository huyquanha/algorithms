package chapter4.part2;

import chapter1.part3.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import static java.lang.Integer.parseInt;

public class DirectedDFS {
    private boolean[] marked;

    public DirectedDFS(Digraph g, int s) {
        marked = new boolean[g.v()];
        dfs(g, s);
    }

    public DirectedDFS(Digraph g, Iterable<Integer> sources) {
        marked = new boolean[g.v()];
        for (int s : sources) {
            if (!marked[s]) {
                dfs(g, s);
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        Bag<Integer> sources = new Bag<>();
        for (int i = 1; i < args.length; i++) {
            sources.add(parseInt(args[i]));
        }
        DirectedDFS dfs = new DirectedDFS(g, sources);
        for (int v = 0; v < g.v(); v++) {
            if (dfs.marked(v)) StdOut.print(v + " ");
        }
        StdOut.println();
    }
}
