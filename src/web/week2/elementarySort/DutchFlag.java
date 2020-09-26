package web.week2.elementarySort;

public class DutchFlag {
    private enum Color {
        RED, WHITE, BLUE;
    }

    public static void sort(Color[] a, int n) {
        Color v = a[0];
        int lt = 0, gt = n - 1, i = 1;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                // a[i] < v
                exch(a, lt++, i++);
            } else if (cmp > 0) {
                // a[i] > v
                exch(a, i, gt--);
            } else {
                // a[i] = v
                i++;
            }
        }
    }

    private static void exch(Color[] a, int i, int j) {
        Color tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
