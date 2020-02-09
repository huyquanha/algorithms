package chapter2.part2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * If we draw a tree like with a normal recursive merge sort, the tree has n levels, with n = lg3 N
 * For k from 0 to n-1, the kth level from the top depicts 3^k sub-arrays, each with 3^(n-k) elements
 * Each of these sub-arrays requires:
 * - at most: 3 compares to find the smallest element for each iteration in the loop in merge() => 3*3^(n-k)
 * - at least: 2 compares to clear one third, and then 1 compare to clear the next one third => 2*3^(n-k)/3 + 3^(n-k)/3 = 3^(n-k)
 * => for each level, 3^k such sub-arrays will cost between 3^(n-k) * 3^k = 3^n = N and 3*3^(n-k)*3^k = 3N compares for the merge
 * => with n levels we have the total compares will bet between n*N and n*3N => between Nlg3N and 3Nlg3N
 */
public class ThreeWayMerge {
    private static Comparable[] aux;

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        sort(a, 0, N - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int first = lo + (hi - lo) / 3;
        int sec = lo + 2 * (hi - lo) / 3;
        sort(a, lo, first);
        sort(a, first + 1, sec);
        sort(a, sec + 1, hi);
        merge(a, lo, first, sec, hi);
    }

    private static void merge(Comparable[] a, int lo, int first, int sec, int hi) {
        for (int i = lo; i <= hi; i++) {
            aux[i] = a[i];
        }
        int i = lo, j = first + 1, k = sec + 1;
        for (int l = lo; l <= hi; l++) {
            if (i > first) {
                if (j > sec) {
                    a[l] = aux[k++];
                } else if (k > hi) {
                    a[l] = aux[j++];
                } else {
                    //compare aux[j] and aux[k]
                    if (less(aux[k], aux[j])) {
                        a[l] = aux[k++];
                    } else {
                        a[l] = aux[j++];
                    }
                }
            } else if (j > sec) {
                if (k > hi) {
                    a[l] = aux[i++];
                } else {
                    //compare aux[i] and aux[k]
                    if (less(aux[k], aux[i])) {
                        a[l] = aux[k++];
                    } else {
                        a[l] = aux[i++];
                    }
                }
            } else if (k > hi) {
                //compare aux[i] and aux[j]
                if (less(aux[j], aux[i])) {
                    a[l] = aux[j++];
                } else {
                    a[l] = aux[i++];
                }
            } else {
                //both i, j, k are in range
                if (less(aux[k], aux[j])) {
                    if (less(aux[j], aux[i])) {
                        a[l] = aux[k++];
                    } else {
                        if (less(aux[k], aux[i])) {
                            a[l] = aux[k++];
                        } else {
                            a[l] = aux[i++];
                        }
                    }
                } else {
                    if (less(aux[j], aux[i])) {
                        a[l] = aux[j++];
                    } else {
                        a[l] = aux[i++];
                    }
                }
            }
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
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
