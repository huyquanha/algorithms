package chapter2.part1;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;

public class VisualInsertion {
    public static void sort(Double[] a)
    {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            int j = i;
            while(j > 0 && less(a[j], a[j-1])) {
                exch(a, j, j-1);
                j--;
            }
            show(a, i, j);
        }
    }

    private static boolean less(Double v, Double w)
    {
        return v.compareTo(w) < 0;
    }

    private static void exch(Double[] a, int i, int j)
    {
        Double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Double[] a, int startPos, int endPos)
    {
        int N = a.length;
        double baseline = N * (N - startPos);
        for (int i = 0; i < N; i++) {
            if (i < endPos || i > startPos) {
                StdDraw.setPenColor(Color.GRAY);
            } else if (i == endPos) {
                StdDraw.setPenColor(Color.RED);
            } else {
                StdDraw.setPenColor(Color.BLACK);
            }
            StdDraw.filledRectangle(i, baseline + a[i]/2, 0.3, a[i]/2);
        }
        StdDraw.show();
        StdDraw.pause(1000);
    }

    public static boolean isSorted(Double[] a)
    {
        // Test whether the array entries are in order.
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static void main(String[] args)
    {
        int N = Integer.parseInt(args[0]);
        Double[] a = new Double[N];
        for (int i=0; i< N; i++) {
            a[i] = StdRandom.uniform() * (N-1) + 1;
        }
        StdDraw.setCanvasSize(512, 900);
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N * N);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();
        sort(a);
        assert isSorted(a);
    }
}
