package web.week3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;

public class FastCollinearPoints {
    private static class SegmentComparator implements Comparator<Point[]> {
        @Override
        public int compare(Point[] o1, Point[] o2) {
            int cmp = o1[1].compareTo(o2[1]);
            if (cmp != 0) {
                return cmp;
            }
            // the end point of 2 segments are the same
            Point endPoint = o1[1];
            double slope1 = endPoint.slopeTo(o1[0]);
            double slope2 = endPoint.slopeTo(o2[0]);
            int slopeCmp = Double.compare(slope1, slope2);
            if (slopeCmp != 0) {
                return slopeCmp;
            }
            // slopComp equal => 2 segments overlap. We prioritize the one with the smaller origin
            return o1[0].compareTo(o2[0]);
        }
    }

    private final List<Point[]> segments;

    public FastCollinearPoints(Point[] pointInput) {
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
        List<Point[]> lines = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            Point[] tmp = Arrays.copyOfRange(points, i + 1, n);
            if (tmp.length > 0) {
                Arrays.sort(tmp, p.slopeOrder());
                Point q = tmp[0];
                double currentSlope = p.slopeTo(tmp[0]);
                int count = 1;
                for (int j = 1; j < n - i - 1; j++) {
                    double slope = p.slopeTo(tmp[j]);
                    if (slope == currentSlope) {
                        count++;
                    } else {
                        if (count >= 3) {
                            lines.add(new Point[]{p, q});
                        }
                        currentSlope = slope;
                        count = 1;
                    }
                    q = tmp[j];
                }
                if (count >= 3) {
                    lines.add(new Point[]{p, q});
                }
            }
        }
        // the segments can still overlap, we need to get rid of them and only take the longest ones
        if (!lines.isEmpty()) {
            SegmentComparator segmentCmp = new SegmentComparator();
            lines.sort(segmentCmp);
            List<Point[]> temp = new ArrayList<>();
            temp.add(lines.get(0));
            for (int i = 1; i < lines.size(); i++) {
                if (lines.get(i)[1].compareTo(lines.get(i - 1)[1]) == 0) {
                    Point endPoint = lines.get(i)[1];
                    double prevSlope = endPoint.slopeTo(lines.get(i - 1)[0]);
                    double curSlope = endPoint.slopeTo(lines.get(i)[0]);
                    if (curSlope == prevSlope) {
                        continue;
                    }
                }
                temp.add(lines.get(i));
            }
            segments = temp;
        } else {
            segments = new ArrayList<>();
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segments.size()];
        int i = 0;
        for (Point[] segment : segments) {
            result[i++] = new LineSegment(segment[0], segment[1]);
        }
        return result;
    }
}
