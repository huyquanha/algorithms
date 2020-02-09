package chapter2.part2;

/**
 * Ex 2.2.19
 */
public class InversionCount {
    private static Comparable[] aux;

    public static int count(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        return count(a, 0, N -1);
    }

    private static int count(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return 0;
        }
        int mid = lo + (hi-lo)/2;
        int countLeft = count(a, lo, mid);
        int countRight = count(a, mid + 1, hi);
        return countLeft + countRight + mergeCount(a, lo, mid, hi);
    }

    private static int mergeCount(Comparable[] a, int lo, int mid, int hi) {
        int inversions = 0;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k<= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) {
                inversions += mid - i + 1;
                a[k] = aux[j++];
            }
            else a[k] = aux[i++];
        }
        return inversions;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Integer[] a = new Integer[N];
        for (int i = 0; i< N; i++) {
            a[i] = (int) (Math.random() * N);
            System.out.print(a[i] + " ");
        }
        System.out.println();
        System.out.println(count(a));
    }
}
