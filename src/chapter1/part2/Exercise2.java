package chapter1.part2;

import edu.princeton.cs.algs4.Interval1D;

public class Exercise2 {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Interval1D[] intervals = new Interval1D[N];
        for (int i = 0; i < N; i++) {
            double lo = Math.random();
            double hi = Math.random();
            if (lo > hi) {
                double tmp = lo;
                lo = hi;
                hi = tmp;
            }
            intervals[i] = new Interval1D(lo, hi);
            for (int j = i - 1; j >= 0; j--) {
                if (intervals[i].intersects(intervals[j])) {
                    System.out.println(i + "-" + j);
                }
            }
        }
    }
}
