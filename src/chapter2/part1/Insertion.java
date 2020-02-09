package chapter2.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
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
        In in = new In();
        String[] a = in.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
