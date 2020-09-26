package chapter2.part4;

import edu.princeton.cs.algs4.StdOut;

public class Heap {
    public static void sort(Comparable[] a) {
        int N = a.length;
        //create a max heap
        for (int i = N / 2; i >= 1; i--) {
            sink(a, i, N);
        }
        //exchange the max to the end position, and then sink
        while (N > 1) {
            exch(a, 1, N--);
            sink(a, 1, N);
        }
    }

    private static void sink(Comparable[] a, int i, int N) {
        while (i * 2 <= N) {
            int j = i * 2;
            if (j < N && less(a, j, j + 1)) j++;
            if (!less(a, i, j)) break;
            exch(a, i, j);
            i = j;
        }
    }

    /**
     * Since a[] goes from 0 to N - 1, but for math simplicity, the heap notation
     * goes from 1 to N, therefore i-th and j-th elements here corresponds to a[i -1] and a[j-1]
     * in the actual array
     * @param a
     * @param i
     * @param j
     * @return
     */
    private static boolean less(Comparable[] a, int i, int j) {
        return a[i - 1].compareTo(a[j - 1]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = tmp;
    }

    private static void show(Comparable[] a) {
        // Print the array, on a single line.
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + " ");
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) {
        // Test whether the array entries are in order.
        for (int i = 2; i <= a.length; i++)
            if (less(a, i, i - 1)) return false;
        return true;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = (int) (Math.random() * N);
        }
        show(a);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
