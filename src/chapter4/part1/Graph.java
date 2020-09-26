package chapter4.part1;

import chapter1.part3.Bag;
import edu.princeton.cs.algs4.In;

public class Graph {
    private int v;
    private int e;
    private Bag<Integer>[] adj;

    public Graph(int v) {
        this.v = v;
        this.e = 0;
        adj = (Bag<Integer>[]) new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    public Graph(In in) {
        this(in.readInt());
        int e = in.readInt();
        for (int i = 0; i < e; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        e++;
    }

    public int v() {
        return v;
    }

    public int e() {
        return e;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(v + " vertices, " + e + " edges\n");
        for (int i = 0; i < v; i++) {
            stringBuilder.append(v + ": ");
            for (int w: adj(v)) {
                stringBuilder.append(w + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
