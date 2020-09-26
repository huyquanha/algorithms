package web.week5;

import chapter3.part5.RedBlackBSSet;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class LineSegmentIntersection {
    private enum EventType {
        H_START, H_END, V
    }

    private static class Event implements Comparable<Event> {
        double x;
        EventType eventType;

        public Event(double x, EventType eventType) {
            this.x = x;
            this.eventType = eventType;
        }

        public int compareTo(Event that) {
           return Double.compare(this.x, that.x);
        }
    }

    private static class HEvent extends Event {
        double y;

        public HEvent(double x, double y, EventType eventType) {
            super(x, eventType);
            this.y = y;
        }
    }

    private static class VEvent extends Event {
        double lo, hi;

        public VEvent(double x, double lo, double hi) {
            super(x, EventType.V);
            this.lo = lo;
            this.hi = hi;
        }
    }

    private final Queue<Point> intersections;

    public LineSegmentIntersection(Line[] lines) {
        MinPQ<Event> events = new MinPQ<>();
        for (Line line: lines) {
            if (line.isHorizontal()) {
                events.insert(new HEvent(line.getStart(), line.getCommon(), EventType.H_START));
                events.insert(new HEvent(line.getEnd(), line.getCommon(), EventType.H_END));
            } else {
                events.insert(new VEvent(line.getCommon(), line.getStart(), line.getEnd()));
            }
        }
        this.intersections = new Queue<>();
        RedBlackBSSet<Double> treeSet = new RedBlackBSSet<>();
        while (!events.isEmpty()) {
            Event min = events.delMin();
            switch(min.eventType) {
                case H_START:
                    treeSet.add(((HEvent) min).y);
                    break;
                case H_END:
                    treeSet.delete(((HEvent) min).y);
                    break;
                case V:
                    VEvent vEvent = (VEvent) min;
                    Iterable<Double> inRanges = treeSet.rangeSearch(vEvent.lo, vEvent.hi);
                    for (double y : inRanges) {
                        intersections.enqueue(new Point(vEvent.x, y));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public int getIntersectionCount() {
        return intersections.size();
    }

    public Iterable<Point> getIntersections() {
        Queue<Point> copy = new Queue<>();
        for (Point intersection : intersections) {
            copy.enqueue(intersection);
        }
        return copy;
    }

    public static void main(String[] args) {

    }
}
