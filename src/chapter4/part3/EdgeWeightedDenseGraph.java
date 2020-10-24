package chapter4.part3;

import edu.princeton.cs.algs4.In;

/**
 * Ex4.3.10
 * Dense graph => use adjacency matrix instead of adjacency list
 */
public class EdgeWeightedDenseGraph {
    private final int v;
    private int e;
    private double[][] adj;

    public EdgeWeightedDenseGraph(int v) {
        this.v = v;
        e = 0;
        adj = new double[v][v];
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                adj[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public EdgeWeightedDenseGraph(In in) {
        this(in.readInt());
        int e = in.readInt();
        for (int i = 0; i < e; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(v, w, weight);
        }
    }

    public void addEdge(int v, int w, double weight) {
        if (adj[v][w] != Double.POSITIVE_INFINITY) throw new IllegalArgumentException("No parallel edges");
        adj[v][w] = adj[w][v] = weight;
        e++;
    }

    public int v() { return v; }

    public int e() { return e; }

    public double[] adj(int v) {
        return adj[v];
    }

    public double[][] edges() {
        return adj;
    }
}
