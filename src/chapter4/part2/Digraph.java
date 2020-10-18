package chapter4.part2;

import chapter1.part3.Bag;
import edu.princeton.cs.algs4.In;

public class Digraph {
    private Bag<Integer>[] adj;
    private final int v;
    private int e;

    public Digraph(int v) {
        this.v = v;
        adj = (Bag<Integer>[]) new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    public Digraph(In in) {
        this(in.readInt());
        int e = in.readInt();
        for (int i = 0; i < e; i++) {
            addEdge(in.readInt(), in.readInt());
        }
    }

    /**
     * Ex4.2.3
     * @param g
     */
    public Digraph(Digraph g) {
        this(g.v());
        for (int v = 0; v < g.v(); v++) {
            for (int w : g.adj(v)) {
                addEdge(v, w);
            }
        }
    }

    /**
     * Ex4.2.5: no self-loop and parallel edges
     * @param v
     * @param w
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= this.v || w < 0 || w >= this.v) throw new IllegalArgumentException("out of bound");
        if (v == w) throw new IllegalArgumentException("No self-loop");
        if (hasEdge(v, w)) throw new IllegalArgumentException("No parallel edges");
        adj[v].add(w);
        e++;
    }

    /**
     * Ex4.2.4
     * @param v
     * @param w
     * @return
     */
    public boolean hasEdge(int v, int w) {
        if (v < 0 || v >= this.v || w < 0 || w >= this.v) throw new IllegalArgumentException("out of bound");
        for (int t : adj[v]) {
            if (t == w) return true;
        }
        return false;
    }

    public int v() { return v; }

    public int e() { return e; }

    public Bag<Integer> adj(int v) {
        if (v < 0 || v >= this.v) throw new IllegalArgumentException("out of bound");
        return adj[v];
    }

    public Digraph reverse() {
        Digraph reverse = new Digraph(v);
        for (int v = 0; v < this.v; v++) {
            for (int w : adj[v]) {
                // add the reverse edge from w to v instead of v to w
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(v + " vertices, " + e + " edges\n");
        for (int i = 0; i < v; i++) {
            stringBuilder.append(i + ": ");
            for (int w: adj(i)) {
                stringBuilder.append(w + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
