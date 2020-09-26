package web.week5;

import chapter1.part3.Queue;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.RectHV;

import java.util.HashMap;
import java.util.Map;

public class RectangleIntersection {
    private static class Event implements Comparable<Event> {
        double lo, hi, x;
        int val;
        boolean isStart;

        public Event(double lo, double hi, double x, int val, boolean isStart) {
            this.lo = lo;
            this.hi = hi;
            this.x = x;
            this.val = val;
            this.isStart = isStart;
        }

        public int compareTo(Event that) {
            return Double.compare(this.x, that.x);
        }
    }

    // each rectangle will be given an id (its index in the array)
    // this will be the map from a rectangle id to all other ids that it intersects with
    private final Map<Integer, Queue<Integer>> intersections;

    public RectangleIntersection() {
        intersections = new HashMap<>();
    }

    public void getIntersections(RectHV[] rects) {
        if (rects == null) throw new IllegalArgumentException();
        MinPQ<Event> minPQ = new MinPQ<>();
        IntervalST<Double, Integer> st = new IntervalST<>();
        for (int i = 0; i < rects.length; i++) {
            minPQ.insert(new Event(rects[i].ymin(), rects[i].ymax(), rects[i].xmin(), i, true));
            minPQ.insert(new Event(rects[i].ymin(), rects[i].ymax(), rects[i].xmax(), i, false));
        }
        while (!minPQ.isEmpty()) {
            Event next = minPQ.delMin();
            if (next.isStart) {
                // do an interval search
                Iterable<Integer> intersectIds = st.intersects(next.lo, next.hi);
                if (!intersections.containsKey(next.val)) {
                    intersections.put(next.val, new Queue<>());
                }
                Queue<Integer> q = intersections.get(next.val);
                for (int id : intersectIds) {
                    q.enqueue(id);
                }
                // then insert
                st.put(next.lo, next.hi, next.val);
            } else {
                // remove from the tree
                st.delete(next.lo, next.hi);
            }
        }
    }

    public Map<Integer, Queue<Integer>> getIntersections() {
        // make a copy of the map
        Map<Integer, Queue<Integer>> copy = new HashMap<>();
        for (Map.Entry<Integer, Queue<Integer>> entry: intersections.entrySet()) {
            Queue<Integer> copyQueue = new Queue<>();
            for (int i : entry.getValue()) {
                copyQueue.enqueue(i);
            }
            copy.put(entry.getKey(), copyQueue);
        }
        return copy;
    }
}
