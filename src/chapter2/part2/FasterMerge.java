package chapter2.part2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Ex2.2.10
 */
public class FasterMerge {
    public static void sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        mergeSort(a, aux, 0, N - 1);
    }

    private static void mergeSort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi-lo)/2;
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        int k = lo, i = lo, j = hi;
        while (i <= mid) {
            aux[k++] = a[i++];
        }
        while (j > mid) {
            aux[k++] = a[j--];
        }
        i = k = lo;
        j = hi;
        while (k <= hi) {
            if (less(aux[j], aux[i])) a[k++] = aux[j--];
            else a[k++] = aux[i++];
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
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
        In in = new In("./testStrings.txt");
        String[] a = in.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
