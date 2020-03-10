package chapter2.part5;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Comparator;

/**
 * Ex2.5.26
 */
public class SimplePolygon {
    public static void draw(Point2D[] points, int N) {
        int originIdx = 0;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE, minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        for (int i = 1; i < N; i++) {
            int cmp = Point2D.Y_ORDER.compare(points[i], points[originIdx]);
            if (cmp < 0) {
                // points[i]'s y is smaller than origin's y
                originIdx = i;
            } else if (cmp == 0) {
                // break tie using X_Order
                if (Point2D.X_ORDER.compare(points[i], points[originIdx]) < 0) {
                    originIdx = i;
                }
            }
            // get the ranges of axis
            if (points[i].x() < minX) {
                minX = points[i].x();
            }
            if (points[i].x() > maxX) {
                maxX = points[i].x();
            }
            if (points[i].y() < minY) {
                minY = points[i].y();
            }
            if (points[i].y() > maxY) {
                maxY = points[i].y();
            }
        }
        Point2D remain[] = new Point2D[N - 1];
        int i = 0;
        for (int j = 0; j < N; j++) {
            if (j != originIdx) {
                remain[i++] = points[j];
            }
        }
        // now origin is the point with the smallest x and smallest y
        Point2D origin = points[originIdx];
        sort(remain, 0, N - 2, origin.thetaComparator());
        StdDraw.setXscale(minX - 1, maxX + 1);
        StdDraw.setYscale(minY - 1, maxY + 1);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.02);
        origin.draw();
        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.005);
        Point2D cur = origin;
        for (i = 0; i < N - 1; i++) {
            StdDraw.line(cur.x(), cur.y(), remain[i].x(), remain[i].y());
            cur = remain[i];
        }
        StdDraw.line(cur.x(), cur.y(), origin.x(), origin.y());
    }

    private static void sort(Point2D[] points, int lo, int hi, Comparator<Point2D> comp) {
        // stability is not important here, so we use 3 way quick sort (most optimal in most cases)
        if (lo >= hi) {
            return;
        }
        int lt = lo, gt = hi, i = lo + 1;
        Point2D v = points[lo];
        while (i <= gt) {
            int cmp = comp.compare(points[i], v);
            if (cmp < 0) {
                // points[i] is smaller than v, exch i with lt and increment both
                exch(points, lt++, i++);
            } else if (cmp > 0) {
                // points[i] > v, exch i with gt and decrement gt, but keep i the same
                exch(points, gt--, i);
            } else {
                i++;
            }
        }
        sort(points, lo, lt - 1, comp);
        sort(points, gt + 1, hi, comp);
    }

    private static void exch(Point2D[] points, int i, int j) {
        Point2D tmp = points[i];
        points[i] = points[j];
        points[j] = tmp;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i ++) {
            points[i] = new Point2D(Math.random() * N, Math.random() * N);
        }
        SimplePolygon.draw(points, N);
    }
}
