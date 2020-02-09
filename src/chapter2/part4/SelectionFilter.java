package chapter2.part4;

import chapter1.part3.Stack;
import edu.princeton.cs.algs4.StdIn;

/**
 * Ex2.4.28
 */
public class SelectionFilter {
    private static class Point implements Comparable<Point> {
        double x, y, z;
        double euDist;

        public Point(String line) {
            String[] numbers = line.split(",");
            x = Double.parseDouble(numbers[0]);
            y = Double.parseDouble(numbers[1]);
            z = Double.parseDouble(numbers[2]);
            euDist = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
        }

        public String toString() {
            return "(" + x + "," + y + "," + z + "," + euDist + ")";
        }

        public int compareTo(Point that) {
            if (this.euDist < that.euDist) {
                return -1;
            } else if (this.euDist > that.euDist) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * For N = 10^8 and M = 10^4
     * For the first M insertions, we don't need to delete the max. Each insert involving swim()
     * => ~(lg1 + lg2 + lg3 + ... + lgM) exchanges and compares in the worst case
     * For M + 1 -> N insertions, we insert and then delete the max. The array size is consistently kept
     * at M. Inserting takes ~lgM compares and ~lgM exchanges worst case.
     * Deleting the max (involving sink()) takes ~lgM exchanges and ~2lgM compares in the worst case
     * We do this for every number from M + 1 to N => (N - M) * 2lgM exchanges and (N-M) * 3lgM compares in worst case
     * Since N is substantially larger than M, N - M is substantially larger than M itself and we can dismiss the cost
     * of the first M insertions and N - M ~ N
     * The estimated cost is: 2NlgM exchanges and 3NlgM compares in the worst case
     * @param args
     */
    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]);
        MaxPQ<Point> pq = new MaxPQ<>(M + 1);
        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            Point p = new Point(line);
            pq.insert(p);
            if (pq.size() > M) {
                pq.delMax();
            }
        }
        Stack<Point> reverse = new Stack<>();
        while (!pq.isEmpty()) {
            reverse.push(pq.delMax());
        }
        for (Point p : reverse) {
            System.out.println(p);
        }
    }
}
