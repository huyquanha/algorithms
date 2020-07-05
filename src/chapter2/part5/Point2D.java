package chapter2.part5;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point2D {
    public static final Comparator<Point2D> X_ORDER = new X_Order();
    public static final Comparator<Point2D> Y_ORDER = new Y_Order();
    public static final Comparator<Point2D> R_ORDEr = new R_Order();

    private static class X_Order implements Comparator<Point2D> {
        public int compare(Point2D p1, Point2D p2) {
            if (p1.x < p2.x) return -1;
            if (p1.x > p2.x) return 1;
            return 0;
        }
    }

    private static class Y_Order implements Comparator<Point2D> {
        public int compare(Point2D p1, Point2D p2) {
            if (p1.y < p2.y) return -1;
            if (p1.y > p2.y) return 1;
            return 0;
        }
    }

    private static class R_Order implements Comparator<Point2D> {
        public int compare(Point2D p1, Point2D p2) {
            if (p1.r() < p2.r()) return -1;
            if (p1.r() > p2.r()) return 1;
            return 0;
        }
    }

    private double x, y;
    private int hash;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double r() {
        return distTo(new Point2D(0,0));
    }

    public double theta() {
        return Math.atan2(y, x);
    }

    public double theta(Point2D that) {
        double relativeY = that.y - this.y;
        double relativeX = that.x - this.x;
        return Math.atan2(relativeY, relativeX);
    }

    public double distTo(Point2D that) {
        return Math.sqrt(Math.pow(that.x - this.x, 2) + Math.pow(that.y - this.y, 2));
    }

    public Comparator<Point2D> distComparator() {
        return new Comparator<Point2D>() {
            @Override
            public int compare(Point2D p1, Point2D p2) {
                double distTo1 = distTo(p1);
                double distTo2 = distTo(p2);
                if (distTo1 < distTo2) return -1;
                if (distTo1 > distTo2) return 1;
                return 0;
            }
        };
    }

    public Comparator<Point2D> thetaComparator() {
        return (p1, p2) -> {
            double thetaTo1 = theta(p1);
            double thetaTo2 = theta(p2);
            if (thetaTo1 < thetaTo2) return -1;
            if (thetaTo1 > thetaTo2) return 1;
            return 0;
        };
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Ex3.4.22
     * saves the value in hash instance variable for caching as well
     * @return
     */
    @Override
    public int hashCode() {
        int h = hash;
        if (h == 0) {
            h = 17;
            h = 31 * h + ((Double) x).hashCode();
            h = 31 * h + ((Double) y).hashCode();
            hash = h;
        }
        return h;
    }
}
