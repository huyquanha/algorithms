package chapter2.part3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickWithMedian3 {
    public static void sort(Comparable[] a) {
        int N = a.length;
        StdRandom.shuffle(a);
        sort(a, 0, N - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        int idx = median(a, lo, mid, hi);
        exch(a, lo, idx);
        Comparable v = a[lo];
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) break;
            }
            while (less(v, a[--j])) {
                if (j == lo) break;
            }
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static int median(Comparable[] a, int lo, int mid, int hi) {
        int v1 = a[lo].compareTo(a[mid]);
        int v2 = a[mid].compareTo(a[hi]);
        int v3 = a[lo].compareTo(a[hi]);
        if (v1 < 0) {
            if (v2 < 0) {
                return mid;
            } else {
                if (v3 < 0) {
                    return hi;
                } else {
                    return lo;
                }
            }
        } else {
            if (v2 > 0) {
                return mid;
            } else {
                if (v3 < 0) {
                    return lo;
                } else {
                    return hi;
                }
            }
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void show(Comparable[] a) {
        // Print the array, on a single line.
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + " ");
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) {
        // Test whether the array entries are in order.
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = i;
        }
        StdRandom.shuffle(a);
        show(a);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
