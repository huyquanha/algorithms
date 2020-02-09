package chapter1.part4;

import edu.princeton.cs.algs4.*;

import java.awt.*;

public class DoublingTestWithGraph {
    private static final int MAXIMUM_INTEGER = 1000000;

    public static double timeTrial(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(-MAXIMUM_INTEGER, MAXIMUM_INTEGER);
        }
        Stopwatch timer = new Stopwatch();
        ThreeSum.count(a);
        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        double maxN = 10000;
        double maxTime=50;
        StdDraw.setXscale(0, maxN);
        StdDraw.setYscale(-maxTime, maxTime);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.BLACK);
        for (int n = 250; true; n += n) {
            double time = timeTrial(n);
            StdDraw.point(Math.log(n)/Math.log(2), Math.log(time)/Math.log(2));
            StdOut.printf("%7d %7.1f\n", n, time);
        }
    }
}
