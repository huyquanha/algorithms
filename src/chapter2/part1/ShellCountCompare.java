package chapter2.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/*
    Ex2.1.12
 */
public class ShellCountCompare {
    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            int compCount = 0;
            for (int i = h; i < N; i++) {
                int j = i;
                while (j >= h && less(a[j], a[j - h])) {
                    compCount++;
                    exch(a, j, j - h);
                    j -= h;
                }
                if (j >= h) {
                    //this means the loop exits because of the second condition => one more compare is not counted yet
                    compCount++;
                }
            }
            System.out.println("Number of compares/array size for h = " + h + " is " + (double) compCount / N);
            h = h / 3;
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
        for (int N = 100; true; N *= 10) {
            System.out.println("Array size: " + N);
            Double[] a = new Double[N];
            for (int i = 0; i < N; i++) {
                a[i] = StdRandom.uniform();
            }
            sort(a);
            assert isSorted(a);
            System.out.println("------------------------DONE-----------------------");
        }
        //show(a);
    }
}
