package chapter2.part3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Ex2.3.22
 */
public class QuickFast3Way {
    public static void sort(Comparable[] a) {
        int N = a.length;
        StdRandom.shuffle(a);
        sort(a, 0, N - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        Comparable v = a[lo];
        int p = lo + 1, q = hi;
        int i = lo, j = hi + 1;
        while (true) {
            while (!less(v, a[++i])) {
                if (i == q + 1 || i == hi) break;
                if (a[i].compareTo(v) == 0) {
                    exch(a, i, p++);
                }
            }
            while (!less(a[--j], v)) {
                if (j == p - 1 || j == lo) break;
                if (a[j].compareTo(v) == 0) {
                    exch(a, j, q--);
                }
            }
            if (i >= j) break;
            exch(a, i, j);
        }
        while (p > lo) {
            exch(a, j--, p-- -1);
        }
        while (q < hi) {
            exch(a, i++, q++ + 1);
        }
        sort(a, lo, j);
        sort(a, i, hi);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void show(Comparable[] a)
    {
        // Print the array, on a single line.
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + " ");
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a)
    {
        // Test whether the array entries are in order.
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        //Integer[] a = {4, 7, 6, 1, 7, 1, 1, 8, 4, 9};
        Integer[] a = new Integer[N];
        for (int i = 0; i< N; i++) {
            a[i] = (int) (Math.random() * N);
        }
        //show(a);
        sort(a);
        assert isSorted(a);
        //show(a);
    }
}
