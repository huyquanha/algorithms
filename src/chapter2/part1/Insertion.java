package chapter2.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/*
    Running time is
        - dependent of the input. For example:
            - Insertion sort running time on a sorted array is linear (N-1 compares, no exchanges)
            - Insertion sort running tim on a reverse array is ~N^2/2 compares and ~N^2/2 exchanges
            - Insertion sort on a randomly ordered array with distinct keys is about ~N^2/4 compares and ~N^2/4 exchanges
        -
 */
public class Insertion {
    public static void sort(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j],a[j-1]); j--) {
                exch(a, j, j-1);
            }
        }
    }

    public static void sort(Comparable[] a)
    {
        int N = a.length;
        sort(a, 0, N -1);
    }

    public static void sort(Object[] a, Comparator c) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(c, a[j], a[j-1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    /**
     * Ex2.5.27
     * @param a
     * @return
     */
    public static int[] indirectSort(Comparable[] a) {
        //return an array index[]: a[index[0]] through to a[index[N - 1]] are sorted
        int N = a.length;
        int[] idx = new int[N];
        for (int i = 0; i < N; i++) {
            idx[i] = i;
        }
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && indirectLess(a, idx, j, j - 1); j--) {
                indirectExch(idx, j, j - 1);
            }
        }
        return idx;
    }

    public static boolean isIndirectSorted(Comparable[] a, int[] idx) {
        for (int i = 1; i < a.length; i++) {
            if (indirectLess(a, idx, i, i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean indirectLess(Comparable[] a, int[] idx, int i, int j) {
        return a[idx[i]].compareTo(a[idx[j]]) < 0;
    }

    private static void indirectExch(int[] idx, int i, int j) {
        int tmp = idx[i];
        idx[i] = idx[j];
        idx[j] = tmp;
    }

    private static boolean less(Comparator c, Object v, Object w) {
        return c.compare(v, w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean less(Comparable v, Comparable w)
    {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j)
    {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
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

    public static void main(String[] args)
    {
        // Read strings from standard input, sort them, and print.
        /*In in = new In();
        String[] a = in.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);*/
        /**
         * This is the test for indirect sort
         */
        int N = Integer.parseInt(args[0]);
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = (int) (Math.random() * N);
        }
        show(a);
        int[] idx = indirectSort(a);
        for (int i : idx) {
            System.out.print(i + " ");
        }
        System.out.println();
        assert isIndirectSorted(a, idx);
    }
}
