package chapter2.part3;

/**
 * Ex 2.3.17
 */
public class QuickWithSentinel {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i));
            Comparable tmp = a[i];
            a[i] = a[r];
            a[r] = tmp;
        }
        int max = 0;
        for (int i = 0; i < N; i++) {
            if (a[i].compareTo(a[max]) > 0) {
                max = i;
            }
        }
        //put the largest item to the end of array
        exch(a, max, N - 1);
        sort(a, 0, N - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j -1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while(less(a[++i], v)) {
                /*
                    a bound check is no longer needed because for sub-arrays involving the end of array,
                    we already put the largest element to the end so it cannot be less than v
                    For interior sub-arrays, the bound check is also not needed because the whole sub-array
                    is guaranteed to be <= the previous partitioning element.
                 */
            }
            while(less(v, a[--j])) {
                /*
                     the bound check is always redundant here because when j is reduced to lo, of course v is not
                     less than itself.
                 */
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
