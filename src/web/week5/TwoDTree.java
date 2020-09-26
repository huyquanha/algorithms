package web.week5;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;

public class TwoDTree {
    private static class Node {
        double x, y;
        Node left, right;

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private Node root;
    private int n;

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return n;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (contains(p)) return;
        root = insert(root, p, 0);
    }

    private Node insert(Node h, Point2D p, int lvl) {
        double x = p.x();
        double y = p.y();
        if (h == null) {
            n++;
            return new Node(x, y);
        }
        if (lvl % 2 == 0) {
            // use x to compare
            if (x <= h.x) {
                h.left = insert(h.left, p, lvl + 1);
            } else {
                h.right = insert(h.right, p, lvl + 1);
            }
        } else {
            // use y to compare
            if (y <= h.y) {
                h.left = insert(h.left, p, lvl + 1);
            } else {
                h.right = insert(h.right, p, lvl + 1);
            }
        }
        return h;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double x = p.x();
        double y = p.y();
        Node cur = root;
        int lvl = 0;
        while (cur != null) {
            if (x == cur.x && y == cur.y) {
                return true;
            }
            if (lvl % 2 == 0) {
                // use x
                if (x <= cur.x) cur = cur.left;
                else cur = cur.right;
            } else {
                // use y
                if (y <= cur.y) cur = cur.left;
                else cur = cur.right;
            }
            lvl++;
        }
        return false;
    }

    public void draw() {
        StdDraw.setScale(0, 1);
        draw(root, 0, 0, 0, 1, 1);
    }

    private void draw(Node h, int lvl, double xMin, double yMin, double xMax, double yMax) {
        if (h == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(h.x, h.y);
        StdDraw.setPenRadius(0.005);
        if (lvl % 2 == 0) {
            // vertical line
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(h.x, yMin, h.x, yMax);
            draw(h.left, lvl + 1, xMin, yMin, h.x, yMax);
            draw(h.right, lvl + 1, h.x, yMin, xMax, yMax);
        } else {
            // horizontal line
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xMin, h.y, xMax, h.y);
            draw(h.left, lvl + 1, xMin, yMin, xMax, h.y);
            draw(h.right, lvl + 1, xMin, h.y, xMax, yMax);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> q = new Queue<>();
        RectHV rootRect = new RectHV(0, 0, 1, 1);
        range(q, rect, root, 0, rootRect);
        return q;
    }

    private RectHV[] getSplittingRects(RectHV parentRect, Node h, int lvl) {
        RectHV leftRect, rightRect;
        if (lvl % 2 == 0) {
            // split vertically
            leftRect = new RectHV(parentRect.xmin(), parentRect.ymin(), h.x, parentRect.ymax());
            rightRect = new RectHV(h.x, parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
        } else {
            // split horizontally, left is below and right is above
            leftRect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), h.y);
            rightRect = new RectHV(parentRect.xmin(), h.y, parentRect.xmax(), parentRect.ymax());
        }
        return new RectHV[]{leftRect, rightRect};
    }

    /**
     * Typical case: R + logN
     * Worst case (assuming tree is balanced): R + sqrt(N)
     * @param q
     * @param rect
     * @param h
     * @param lvl
     * @param nodeRect
     */
    private void range(Queue<Point2D> q, RectHV rect, Node h, int lvl, RectHV nodeRect) {
        if (h == null) return;
        Point2D p = new Point2D(h.x, h.y);
        if (rect.contains(p)) q.enqueue(p);
        RectHV[] rects = getSplittingRects(nodeRect, h, lvl);
        RectHV leftRect = rects[0];
        RectHV rightRect = rects[1];
        if (rect.intersects(leftRect)) {
            range(q, rect, h.left, lvl + 1, leftRect);
        }
        if (rect.intersects(rightRect)) {
            range(q, rect, h.right, lvl + 1, rightRect);
        }
    }

    /**
     * Typical case : log N
     * Worst case (even if tree is balanced): N
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        RectHV rootRect = new RectHV(0, 0, 1, 1);
        return nearest(p, root, 0, rootRect, null);
    }

    private Point2D nearest(Point2D p, Node h, int lvl, RectHV nodeRect, Point2D champion) {
        if (h == null) return champion;
        double champDist = champion == null ? Double.POSITIVE_INFINITY : p.distanceSquaredTo(champion);
        Point2D curPoint = new Point2D(h.x, h.y);
        double curDist = p.distanceSquaredTo(curPoint);
        if (curDist < champDist) {
            champion = curPoint;
            champDist = curDist;
        }
        RectHV[] rects = getSplittingRects(nodeRect, h, lvl);
        RectHV leftRect = rects[0], rightRect = rects[1];
        double distToLeft = leftRect.distanceSquaredTo(p);
        double distToRight = rightRect.distanceSquaredTo(p);
        if (distToLeft < champDist && distToRight < champDist) {
            // can go down both. Choose the one that is on the same side with p with respect to the splitting line
            boolean splitByX = lvl % 2 == 0;
            boolean nextIsLeft = splitByX ? (p.x() <= h.x) : (p.y() <= h.y);
            RectHV next = nextIsLeft ? leftRect : rightRect;
            RectHV other = nextIsLeft ? rightRect : leftRect;
            double otherDist = nextIsLeft ? distToRight : distToLeft;
            champion = nearest(p, nextIsLeft ? h.left : h.right, lvl + 1, next, champion);
            champDist = p.distanceSquaredTo(champion);
            if (otherDist < champDist) {
                return nearest(p, nextIsLeft ? h.right : h.left, lvl + 1, other, champion);
            } else {
                return champion;
            }
        } else if (distToLeft < champDist) {
            return nearest(p, h.left, lvl + 1, leftRect, champion);
        } else if (distToRight < champDist) {
            return nearest(p, h.right, lvl + 1, rightRect, champion);
        } else {
            return champion;
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        TwoDTree kdtree = new TwoDTree();
        Queue<Point2D> points = new Queue<>();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            points.enqueue(p);
            kdtree.insert(p);
        }
        for (Point2D p : points) {
            StdOut.println(kdtree.contains(p));
        }
        StdOut.println(kdtree.contains(new Point2D(0.125, 0.126)));
        kdtree.draw();
        Point2D queryPoint = new Point2D(0.87, 0.31);
        Point2D nearest = kdtree.nearest(queryPoint);
        System.out.println(nearest);
    }
}
