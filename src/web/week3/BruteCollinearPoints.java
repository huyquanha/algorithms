package web.week3;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private final Queue<LineSegment> segments;

    public BruteCollinearPoints(Point[] pointInput) {
        if (pointInput == null) {
            throw new IllegalArgumentException("points cannot be null");
        }
        int n = pointInput.length;
        for (int i = 0; i < n; i++) {
            if (pointInput[i] == null) {
                throw new IllegalArgumentException("each point cannot be null");
            }
        }
        Point[] points = Arrays.copyOf(pointInput, n);
        Arrays.sort(points);
        for (int i = 1; i < n; i++) {
            if (points[i].compareTo(points[i -1]) == 0) {
                throw new IllegalArgumentException("cannot contain repeated point");
            }
        }
        segments = new Queue<>();
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            for (int j = i + 1; j < n; j++) {
                Point q = points[j];
                for (int k = j + 1; k < n; k++) {
                    Point r = points[k];
                    for (int m = k + 1; m < n; m++) {
                        Point s = points[m];
                        Comparator<Point> cmp = p.slopeOrder();
                        if (cmp.compare(q, r) == 0 && cmp.compare(r, s) == 0) {
                            // p is the smallest, and s is the largest
                            segments.enqueue(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment segment : segments) {
            result[i++] = segment;
        }
        return result;
    }
}
