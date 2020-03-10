package chapter2.part5;

/**
 * Ex2.5.6
 */
public class RecursiveSelect {
    public static Comparable select(Comparable[] a, int k) {
        int lo = 0, hi = a.length - 1;
        return select(a, k, lo, hi);
    }

    private static Comparable select(Comparable[] a, int k, int lo, int hi) {
        if (lo >= hi) {
            return a[k];
        }
        int j = partition(a, lo, hi);
        if (j == k) {
            return a[k];
        } else if (j > k) {
            return select(a, k, lo, j - 1);
        } else {
            return select(a, k, j  + 1, hi);
        }
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
}
