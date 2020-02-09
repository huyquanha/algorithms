package chapter2.part2;

public class IndirectSort {
    private static int[] aux;

    public static int[] sort(Comparable[] a) {
        int N = a.length;
        int[] perm = new int[N];
        for (int i = 0; i < N; i++) {
            perm[i] = i;
        }
        aux = new int[N];
        sort(a, perm, 0, N - 1);
        return perm;
    }

    private static void sort(Comparable[] a, int[] perm, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, perm, lo, mid);
        sort(a, perm, mid + 1, hi);
        merge(a, perm, lo, mid, hi);
    }

    private static void merge(Comparable[] a, int[] perm, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = perm[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                perm[k] = aux[j++];
            } else if (j > hi) {
                perm[k] = aux[i++];
            } else if (less(a[aux[j]], a[aux[i]])) {
                perm[k] = aux[j++];
            } else {
                perm[k] = aux[i++];
            }
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a, int[] perm) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            if (less(a[perm[i]], a[perm[i - 1]])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = (int) (Math.random() * N);
            System.out.print(a[i] + " ");
        }
        System.out.println();
        int[] perm = sort(a);
        for(int i : perm) {
            System.out.print(i + " ");
        }
        System.out.println();
        assert isSorted(a, perm);
    }
}
