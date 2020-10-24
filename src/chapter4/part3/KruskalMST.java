package chapter4.part3;

import chapter1.part3.Queue;
import chapter1.part5.WeightedQuickUnionUF;
import chapter2.part4.MinPQ;

public class KruskalMST extends MST {
    private Queue<Edge> mst;
    private double weight;

    public KruskalMST(EdgeWeightedGraph g) {
        super(g);
        mst = new Queue<>();
        weight = 0;

        MinPQ<Edge> pq = new MinPQ<>(g.e());
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(g.v());
        for (Edge e : g.edges()) {
            pq.insert(e);
        }
        while (!pq.isEmpty() && mst.size() < g.v() - 1) { // or uf.count > 1
            Edge min = pq.delMin();
            int v = min.either(), w = min.other(v);
            if (uf.connected(v, w)) continue;
            uf.union(v, w);
            mst.enqueue(min);
            weight += min.weight();
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
