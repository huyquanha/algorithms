package chapter4.part1.creativeProblems;

import chapter1.part3.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

/**
 * Ex4.1.37
 */
public class EuclideanGraph {
    private Bag<Integer>[] adj;
    private Point2D[] points;
    private int v;
    private int e;

    public EuclideanGraph(int v) {
        this.v = v;
        adj = (Bag<Integer>[]) new Bag[v];
        points = new Point2D[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    public EuclideanGraph(In in) {
        this(in.readInt());
        // read the coordinates of points in
        for (int i = 0; i < v; i++) {
            points[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        int e = in.readInt();
        // read the edges in
        for (int i = 0; i < e; i++) {
            this.addEdge(in.readInt(), in.readInt());
        }
    }

    public void addEdge(int v, int w) {
        if (v < 0 || v >= this.v || w < 0 || w >= this.v) throw new IllegalArgumentException("out of bound");
        if (v == w) throw new IllegalArgumentException("No self-loop");
        if (hasEdge(v, w)) throw new IllegalArgumentException("No parallel edges");
        adj[v].add(w);
        adj[w].add(v);
        e++;
    }

    public Iterable<Integer> adj(int v) {
        if (v < 0 || v >= this.v) throw new IllegalArgumentException("out of bound");
        return adj[v];
    }

    public boolean hasEdge(int v, int w) {
        if (v < 0 || v >= this.v || w < 0 || w >= this.v) throw new IllegalArgumentException("out of bound");
        for (int t : adj(v)) {
            if (t == w) return true;
        }
        return false;
    }

    public int v() { return v; }

    public int e() { return e; }

    public void show() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setScale(0, 1);
        StdDraw.setPenRadius(0.005);
        for (int v = 0; v < this.v; v++) {
            drawVertex(v);
            for (int w : adj[v]) {
                drawEdge(v, w);
            }
        }
    }

    private void drawVertex(int v) {
        StdDraw.setPenRadius(0.01);
        StdDraw.point(points[v].x(), points[v].y());
    }

    private void drawEdge(int v, int w) {
        StdDraw.setPenRadius(0.001);
        StdDraw.line(points[v].x(), points[v].y(), points[w].x(), points[w].y());
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EuclideanGraph g = new EuclideanGraph(in);
        g.show();
    }
}
