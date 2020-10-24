package chapter4.part3.exercises;

import chapter1.part3.Stack;
import chapter4.part3.Edge;
import chapter4.part3.EdgeWeightedGraph;
import chapter4.part3.PrimMST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Ex4.3.16
 * Get the weight range of the new edge e to be included in the MST
 * Basically, e.weight() must be smaller than the maximum-weight edge in the cycle it creates
 */
public class WeightRange {
    private Stack<Edge> cycle;
    private boolean[] marked;
    private Edge[] edgeTo;
    private double range;

    public WeightRange(Iterable<Edge> mst, Edge e) {
        int size = 0;
        for (Edge edge : mst) size++;
        // build a graph with just these edges
        EdgeWeightedGraph g = new EdgeWeightedGraph(size + 1);
        for (Edge edge: mst) {
            g.addEdge(edge);
        }
        int v = e.either(), w = e.other(v);
        g.addEdge(e); // this will create an only cycle in g
        marked = new boolean[g.v()];
        edgeTo = new Edge[g.v()];
        dfs(g, v, v);
        range = Double.NEGATIVE_INFINITY;
        for (Edge edge : cycle) {
            if (!edge.equals(e) && edge.weight() > range) {
                range = edge.weight();
            }
        }
    }

    public double getRange() { return range; }

    private void dfs(EdgeWeightedGraph g, int v, int u) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            if (cycle != null) return;
            int w = e.other(v);
            if (!marked[w]) {
                edgeTo[w] = e;
                dfs(g, w, v);
            } else if (w != u) {
                // w is marked but not u => cycle
                cycle = new Stack<>();
                cycle.push(e);
                int x = v;
                while (x != w) {
                    cycle.push(edgeTo[x]);
                    x = edgeTo[x].other(x);
                }
            }
        }
    }

    public static void main(String[] args) {
        EdgeWeightedGraph g = new EdgeWeightedGraph(new In(args[0]));
        PrimMST primMST = new PrimMST(g);
        Iterable<Edge> mst = primMST.edges();
        In in = new In();
        while (!in.isEmpty()) {
            String[] parts = in.readLine().split(" ");
            int v = Integer.parseInt(parts[0]);
            int w = Integer.parseInt(parts[1]);
            Edge e = new Edge(v, w, 0); // weight doesn't matter here, we just need to find the range
            WeightRange weightRange = new WeightRange(mst, e);
            StdOut.println("Weight range is < " + weightRange.getRange());
        }
    }
}
