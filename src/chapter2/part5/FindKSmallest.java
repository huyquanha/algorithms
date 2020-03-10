package chapter2.part5;

/**
 * Find the k smallest element in an array (k is 0-based order, so the first smallest would be euivalent to k = 0)
 */
public class FindKSmallest {
    public static Comparable select(Comparable[] a, int k) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int j = partition(a, lo, hi);
            if (j == k) return a[j];
            else if (j > k) hi = j - 1;
            else lo = j + 1;
        }
        return a[k];
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) break;
            }
            while (less(v, a[--j])) {
                // actually redundant, since when j = lo, a[j] = v and v is not smaller than v
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
