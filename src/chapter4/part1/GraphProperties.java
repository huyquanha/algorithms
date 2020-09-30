package chapter4.part1;

import chapter1.part3.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class GraphProperties {
    private int[] eccentricities;
    private int[] girths;
    private int diameter;
    private int radius;
    private int center;
    private int girth;

    public GraphProperties(Graph g) {
        Search search = new DepthFirstSearch(g, 0);
        if (search.count() != g.v()) {
            throw new IllegalArgumentException("Graph is not connected");
        }
        diameter = Integer.MIN_VALUE;
        radius = Integer.MAX_VALUE;
        girth = Integer.MAX_VALUE;
        eccentricities = new int[g.v()];
        girths = new int[g.v()];
        for (int v = 0; v < g.v(); v++) {
            eccentricities[v] = computeEccentricity(g, v);
            if (eccentricities[v] > diameter) {
                diameter = eccentricities[v];
            }
            if (eccentricities[v] < radius) {
                radius = eccentricities[v];
                center = v;
            }
            girths[v] = computeGirth(g, v);
            if (girths[v] < girth) {
                girth = girths[v];
            }
        }
    }

    public int eccentricity(int v) {
        return eccentricities[v];
    }

    public int girth(int v) {
        return girths[v];
    }

    public int diameter() {
        return diameter;
    }

    public int radius() {
        return radius;
    }

    public int center() {
        return center;
    }

    public int girth() {
        return girth;
    }

    private int computeEccentricity(Graph g, int s) {
        Queue<Integer> q = new Queue<>();
        int dist = Integer.MIN_VALUE;
        boolean[] marked = new boolean[g.v()];
        int[] distTo = new int[g.v()];
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            if (distTo[v] > dist) {
                dist = distTo[v];
            }
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
        return dist;
    }

    private int computeGirth(Graph g, int s) {
        Queue<Integer> q = new Queue<>();
        boolean[] marked = new boolean[g.v()];
        int[] distTo = new int[g.v()];
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                } else if (w != s) {
                    // w is already marked previously. distTo[w] is distance from s to w
                    // the cycle length is then distTo[v] + distTo[w] + the edge that create the cycle (v-w)
                    return distTo[v] + distTo[w] + 1;
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));
        GraphProperties graphProperties = new GraphProperties(g);
        for (int v = 0; v < g.v(); v++) {
            StdOut.println("Eccentricity of " + v + ": " + graphProperties.eccentricity(v));
            StdOut.println("Girth of " + v + ": " + graphProperties.girth(v));
        }
        StdOut.println("Diameter: " + graphProperties.diameter());
        StdOut.println("Radius: " + graphProperties.radius());
        StdOut.println("Center: " + graphProperties.center());
        StdOut.println("Girth: " + graphProperties.girth());
    }
}
