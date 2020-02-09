package chapter2.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;

public class AnimatedInsertion {
    public static void sort(Double[] a)
    {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
            show(a);
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

    private static void show(Double[] a)
    {
        int N = a.length;
        StdDraw.clear();
        // Print the array, on a single line.
        for (int i = 0; i < N; i++)
            StdDraw.filledRectangle(i, a[i]/2, 0.3, a[i]/2);
        StdDraw.show();
        StdDraw.pause(20);
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
        StdDraw.setCanvasSize(512, 512);
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();
        sort(a);
        assert isSorted(a);
    }
}
