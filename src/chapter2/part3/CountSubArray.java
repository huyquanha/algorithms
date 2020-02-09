package chapter2.part3;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Ex2.3.7
 * By experiment, we can see that the number of sub-arrays of size 0 and 1 is about N/3, and the number of
 * sub-arrays of size 2 is about N/6
 */
public class CountSubArray {
    private static int num0, num1, num2;

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo > hi) {
            num0++;
            return;
        } else if (lo == hi) {
            num1++;
            return;
        } else if (lo == hi - 1) {
            num2++;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
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

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
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
        System.out.println(num0);
        System.out.println(num1);
        System.out.println(num2);
        show(a);
    }
}
