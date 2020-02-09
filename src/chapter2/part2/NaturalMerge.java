package chapter2.part2;

import chapter1.part3.Queue;
import edu.princeton.cs.algs4.In;

public class NaturalMerge {
    private static Comparable[] aux;

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        int lo = 0, mid = -1, hi = -1;
        Queue<Integer> his = new Queue<>();
        boolean foundOne = false;
        int i = 1;
        while (i < N) {
            if (less(a[i], a[i-1])) {
                if (!foundOne) {
                    foundOne = true;
                    mid = i - 1;
                } else {
                    //means we have found one sorted sub-array, and this is the second one. Ready to merge
                    hi = i - 1;
                    merge(a, lo, mid, hi);
                    foundOne = false;
                    lo = i;
                    his.enqueue(hi);
                }
            }
            if (i == N - 1) {
                hi = N - 1;
                if (foundOne) {
                    merge(a, lo, mid, hi);
                }
                his.enqueue(hi);
            }
            i++;
        }
        // if his.size() == 1 it means the array is already sorted
        lo = 0;
        while (his.size() > 1) {
            int firstHi = his.dequeue();
            int secondHi = his.dequeue();
            if (firstHi < secondHi) {
                merge(a, lo, firstHi, secondHi);
                his.enqueue(secondHi);
                lo = secondHi + 1;
            } else {
                his.enqueue(firstHi);
                lo = firstHi + 1;
            }
            if (lo >= N) {
                lo = 0;
            }
        }
    }

    private static void merge(Comparable[] a, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
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

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Read strings from standard input, sort them, and print.
        In in = new In("testStrings.txt");
        String[] a = in.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
