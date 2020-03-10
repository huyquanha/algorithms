package chapter2.part3;

import chapter2.part1.Insertion;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Ex2.3.24
 */
public class SampleSort {
    public static void sort(Comparable[] a, int k) {
        int N = a.length;
        StdRandom.shuffle(a);
        Integer[] samples = new Integer[(int) Math.pow(2, k) - 1];
        for (int i = 0; i < samples.length; i++) {
            //samples[i] is the index of the selected number
            samples[i] = (int) (Math.random() * N);
        }
        Insertion.sort(samples);
        //perform insertion sort on samples
        for (int i = 1; i< samples.length; i++) {
            for (int j = i; j > 0 && less(a[samples[j]], a[samples[j-1]]); j--) {
                exch(a, samples[j], samples[j - 1]);
            }
        }
        sort(a, samples, 0, N - 1, 0, samples.length - 1);
    }

    private static void sort(Comparable[] a, Integer[] samples, int lo, int hi, int sampleLo, int sampleHi) {
        if (lo >= hi) return;
        int sampleMid = sampleLo + (sampleHi - sampleLo)/2;
        if (sampleLo <= sampleHi) {
            int pivot = samples[sampleMid];
            exch(a, lo, pivot);
            samples[sampleMid] = lo;
        }
        Comparable v = a[lo];
        int i = lo + 1, j = hi, lt = lo, gt = hi;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                exch(a, i++, lt++);
            } else if (cmp > 0) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, samples, lo, lt - 1, sampleLo, sampleMid - 1);
        sort(a, samples, gt + 1, hi, sampleMid + 1, sampleHi);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
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

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        //Integer[] a = {9,17,14,3,6,0,16,3,12,7,18,10,18,15,3,0,5,14,15,16};
        Integer[] a = new Integer[N];
        for (int i = 0; i< N; i++) {
            a[i] = (int) (Math.random() * N);
        }
        show(a);
        sort(a, k);
        //assert isSorted(a);
        show(a);
    }
}
