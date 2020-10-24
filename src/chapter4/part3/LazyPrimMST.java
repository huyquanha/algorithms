package chapter4.part3;

import chapter1.part3.Queue;
import chapter2.part4.MinPQ;

/**
 * This is a lazy implementation of Prim MST because we don't delete the ineligible edges
 * from the priority queue right away (edges that used to be crossing edges, but no longer be because both
 * ends were marked before the edge is dequeued), but wait until we encounter them to test the ineligibility
 */
public class LazyPrimMST extends MST {
    private boolean[] marked; // if marked[v] is true, v is in the MST
    private Queue<Edge> mst; // edgeTo[v] is the edge that connects v to the MST
    private MinPQ<Edge> pq; // keep track of crossing edges, ordered by weights
    private double weight;

    public LazyPrimMST(EdgeWeightedGraph g) {
        super(g);
        marked = new boolean[g.v()];
        mst = new Queue<>();
        pq = new MinPQ<>();
        visit(g, 0);
        while (!pq.isEmpty()) {
            Edge min = pq.delMin();
            int v = min.either(), w = min.other(v);
            if (marked[v] && marked[w]) {
                // this could happen even if we only insert edges to unmarked vertices to pq, because at the time
                // we inserted the edge the vertex might not be marked, but then it was marked by another edge
                continue;
            }
            mst.enqueue(min);
            weight += min.weight();
            // we have to check both here because we can't be sure if v or w is a tree vertex
            if (!marked[v]) visit(g, v);
            if (!marked[w]) visit(g, w);
        }
    }

    private void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            // only insert the edges to unmarked vertices
            if (!marked[e.other(v)]) pq.insert(e);
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
