package chapter2.part1;

import edu.princeton.cs.algs4.In;

import java.io.File;

/*
    Ex2.1.24
 */
public class InsertionWithSentinel {
    public static void sort(Comparable[] a) {
        int min = 0;
        int N = a.length;
        for (int i = 1; i < N; i++) {
            if (less(a[i], a[min])) {
                min = i;
            }
        }
        exch(a, 0, min);
        for (int i = 1; i < N; i++) {
            for (int j = i; less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
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

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        In in = new In("./testStrings.txt");
        String[] a = in.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
