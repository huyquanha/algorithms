package chapter2.part2;

import chapter2.part1.Insertion;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Ex2.2.11
 */
public class ImprovedRecMerge {
    public static void sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        for (int i = 0; i< N; i++) {
            aux[i] = a[i];
        }
        int lo = 0;
        int hi = N - 1;
        mergeSort(a, aux, lo, hi);
    }

    private static void mergeSort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi - lo <= 15) {
            //cutoff to use Insertion sort for small sub-arrays
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        //switch the role of aux and a, so we don't need to copy the content from a to aux every time we merge()
        mergeSort(aux, a, lo, mid);
        mergeSort(aux, a, mid + 1, hi);
        if (less(aux[mid + 1], aux[mid])) {
            merge(a, aux, lo, mid, hi);
        } else {
            // if the largest of left half a[mid] is <= smallest of right half a[mid+1], we don't need to merge.
            for (int i = lo; i <= hi; i++) {
                a[i] = aux[i];
            }
        }
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
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
        // Read strings from standard input, sort them, and print.
        In in = new In("./testStrings.txt");
        String[] a = in.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
