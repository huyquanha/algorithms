package web.week2.elementarySort;

import edu.princeton.cs.algs4.Point2D;

public class SetIntersection {
    public static int countIntersects(Point2D[] a, Point2D[] b, int n) {
        sort(a, n);
        sort(b, n);
        int i = 0, j = 0, count = 0;
        while (i < n && j < n) {
            int cmp = a[i].compareTo(a[j]);
            if (cmp < 0) {
                // a[i] < a[j]
                i++;
            } else if (cmp > 0) {
                // a[i] > a[j]
                j++;
            } else {
                count++;
            }
        }
        return count;
    }

    private static void sort(Point2D[] a, int n) {
        // shell sort
        int h = 1;
        while (h < n/3) {
            // this is to limit the value of the largest h to be N at max
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a, j, j - h); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h/3;
        }
    }

    private static boolean less(Point2D[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    private static void exch(Point2D[] a, int i, int j) {
        Point2D tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
