package chapter2.part3;

public class TwoKeySort {
    public static void sort(Comparable[] a) {
        int lo = 0;
        int hi = a.length - 1;
        Comparable v = a[lo];
        int lt = lo, gt = hi, i = lo;
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
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i< a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Comparable[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = Math.random() >= 0.5 ? 1 : 0;
        }
        show(a);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
