package chapter2.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/*
Ex2.1.11
 */
public class ShellArray {
    public static void sort(Comparable[] a) {
        int N = a.length;
        List<Integer> hSeq = new ArrayList<>();
        int h = 1;
        do {
            hSeq.add(h);
            h = h * 3 + 1;
        } while (h < N / 3);
        for (int i = hSeq.size() - 1; i >= 0; i--) {
            int curH = hSeq.get(i);
            for (int j = curH; j < N; j++) {
                for (int k = j; k >= curH && less(a[k], a[k - curH]); k -= curH) {
                    exch(a, k, k - curH);
                }
            }
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
        String[] a = In.readStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
