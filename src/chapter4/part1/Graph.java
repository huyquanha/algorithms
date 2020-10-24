package chapter4.part1;

import chapter1.part3.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Graph {
    private final int v;
    private int e;
    private final Bag<Integer>[] adj;

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

    /**
     * Ex4.1.15
     * @param in
     */
//    public Graph(In in) {
//        this(in.readInt());
//        in.readInt(); // we don't really need to read e in. It will be incremented by adding edges
//        while (in.hasNextLine()) {
//            String[] vertices = in.readLine().split(" ");
//            int v = Integer.parseInt(vertices[0]);
//            for (int i = 1; i < vertices.length; i++) {
//                addEdge(v, Integer.parseInt(vertices[i]));
//            }
//        }
//    }

    /**
     * Ex 4.1.3
     * @param g
     */
    public Graph(Graph g) {
        this(g.v());
        for (int i = 0; i < v; i++) {
            for (int w : g.adj(i)) {
                addEdge(i, w);
            }
        }
    }

    /**
     * Ex 4.1.5: add checks for self-loop and parallel edges
     * @param v
     * @param w
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= this.v || w < 0 || w >= this.v) throw new IllegalArgumentException("out of bound");
        if (v == w) throw new IllegalArgumentException("No self-loop");
        if (hasEdge(v, w)) throw new IllegalArgumentException("No parallel edges");
        adj[v].add(w);
        adj[w].add(v);
        e++;
    }

    /**
     * Ex 4.1.4: add method to check if an edge already exists
     * @param v
     * @param w
     * @return
     */
    public boolean hasEdge(int v, int w) {
        if (v < 0 || v >= this.v || w < 0 || w >= this.v) throw new IllegalArgumentException("out of bound");
        for (int x : adj(v)) {
            if (x == w) return true;
        }
        return false;
    }

    public int v() {
        return v;
    }

    public int e() {
        return e;
    }

    public Bag<Integer> adj(int v) {
        if (v < 0 || v >= this.v) throw new IllegalArgumentException("out of bound");
        return adj[v];
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

    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));
        StdOut.println(g.toString());
    }
}
