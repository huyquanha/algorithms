package chapter4.part3;

import chapter1.part3.Bag;
import edu.princeton.cs.algs4.In;

public class EdgeWeightedGraph {
    private final int v;
    private int e;
    private final Bag<Edge>[] adj;

    public EdgeWeightedGraph(int v) {
        this.v = v;
        adj = (Bag<Edge>[]) new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    /**
     * Ex4.3.9
     * @param in
     */
    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int e = in.readInt();
        for (int i = 0; i < e; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge edge = new Edge(v, w, weight);
            addEdge(edge);
        }
    }

    public void addEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        adj[v].add(edge);
        adj[w].add(edge);
        e++;
    }

    public int v() { return v; }

    public int e() { return e; }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> bag = new Bag<>();
        for (int v = 0; v < this.v; v++) {
            for (Edge e : adj[v]) {
                if (e.other(v) > v) bag.add(e);
            }
        }
        return bag;
    }

    /**
     * Ex4.3.17
     * @return
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(v + " vertices, " + e + " edges\n");
        for (int i = 0; i < v; i++) {
            stringBuilder.append(i + ": ");
            for (Edge e: adj(i)) {
                stringBuilder.append(e + ",");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
