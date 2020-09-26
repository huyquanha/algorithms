package web.week2.elementarySort;

public class Permutation {
    public static boolean isPermutation(int[] a, int[] b, int n) {
        sort(a, n);
        sort(b, n);

        for (int i = 0; i < n; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    private static void sort(int[] a, int n) {
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && a[j] < a[j - h]; j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    private static void exch(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
