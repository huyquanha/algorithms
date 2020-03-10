package chapter2.part5;

public class Select {
    //used to select the k-th smallest element in the array (0-based index, so equivalent to (k+ 1)th if started from 1)
    public static Comparable select(Comparable[] a, int k) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int j = partition(a, lo, hi);
            if (j == k) break;
            if (j < k) lo = j + 1;
            if (j > k) hi = j - 1;
        }
        return a[k];
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        Comparable v = a[lo];
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) break;
            }
            while (less(v, a[--j])) {
                if (j == lo) break; //actually redundant, since when j == lo, a[j] = a[lo] = v => the while is already false
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
