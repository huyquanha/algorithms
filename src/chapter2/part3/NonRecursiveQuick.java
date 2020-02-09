package chapter2.part3;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Ex2.3.20
 */
public class NonRecursiveQuick {
    public static void sort(Comparable[] a) {
        int N = a.length;
        StdRandom.shuffle(a);
        Stack<Integer> los = new Stack<>();
        Stack<Integer> his = new Stack<>();
        los.push(0);
        his.push(N - 1);
        while (!los.isEmpty()) {
            int lo = los.pop();
            int hi = his.pop();
            if (lo >= hi) continue;
            int lt = lo, gt = hi, i = lo;
            Comparable v = a[lo];
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
            if (lt - lo > hi - gt) {
                los.push(lo);
                his.push(lt - 1);
                los.push(gt + 1);
                his.push(hi);
            } else {
                los.push(gt + 1);
                his.push(hi);
                los.push(lo);
                his.push(lt - 1);
            }
        }
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean less(Comparable v, Comparable w)
    {
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

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Integer[] a = new Integer[N];
        for (int i = 0; i< N; i++) {
            a[i] = (int) (Math.random() * N);
        }
        StdRandom.shuffle(a);
        show(a);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
