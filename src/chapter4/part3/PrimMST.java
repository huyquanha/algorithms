package chapter4.part3;

import chapter1.part3.Bag;

/**
 * Eager Implementation of Prim's MST algorithm
 */
public class PrimMST extends MST {
    private boolean[] marked;
    private Edge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;
    private double weight;

    public PrimMST(EdgeWeightedGraph g) {
        super(g);
        marked = new boolean[g.v()];
        edgeTo = new Edge[g.v()];
        distTo = new double[g.v()];
        pq = new IndexMinPQ<>(g.v());
        for (int v = 0; v < g.v(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[0] = 0.0;
        pq.insert(0, 0.0);
        while(!pq.isEmpty()) {
            // the next vertex to be added to the tree
            int v = pq.delMin();
            weight += distTo[v];
            visit(g, v);
        }
    }

    private void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w)) pq.change(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        Bag<Edge> bag = new Bag<>();
        for (int v = 1; v < edgeTo.length; v++) {
            bag.add(edgeTo[v]);
        }
        return bag;
    }

    public double weight() {
        return weight;
    }
}
