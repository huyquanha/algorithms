package web.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> treeSet;

    public PointSET() {
        treeSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    public int size() {
        return treeSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        treeSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return treeSet.contains(p);
    }

    public void draw() {
        for (Point2D p : treeSet) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> q = new Queue<>();
        for (Point2D p : treeSet) {
            if (rect.contains(p)) q.enqueue(p);
        }
        return q;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double minDist = Double.POSITIVE_INFINITY;
        Point2D champion = null;
        for (Point2D point : treeSet) {
            double dist = p.distanceSquaredTo(point);
            if (dist < minDist) {
                minDist = dist;
                champion = point;
            }
        }
        return champion;
    }
}
