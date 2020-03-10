package chapter2.part1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Ex2.1.6 + Ex2.5.17 (adding the stability check)
 */
public class Certification {
    public static boolean check(Comparable[] a) {
        Comparable[] tmp = new Comparable[a.length];
        boolean[] certified = new boolean[a.length];
        for (int i = 0; i < a.length; i++) {
            tmp[i] = a[i]; //copy references to tmp[i]
        }
        Arrays.sort(a);
        int prevOriginIndex = -1;
        for (int i = 0; i < a.length; i++) {
            boolean found = false;
            int originIndex = -1;
            for (int j = 0; j < tmp.length; j++) {
                if (tmp[j] == a[i]) {
                    if (found) {
                        // this means we already found another tmp[j] that equals a[i] (2 tmp[j] both equal 1 a[i])
                        return false;
                    } else {
                        // if everything goes right, execution should enter here exactly once
                        found = true;
                    }
                    if (certified[j]) {
                        // this mean tmp[j] is already located (2 a[i] both equal 1 tmp[j])
                        return false;
                    } else {
                        // mark that tmp[j] is located in the new array at i
                        certified[j] = true;
                        originIndex = j;
                    }
                }
            }
            if (!found) {
                // we don't find any tmp[j] that == a[i]
                return false;
            }
            if (i > 0) {
                int cmp = a[i].compareTo(a[i-1]);
                if (cmp < 0) {
                    return false;
                } else if (cmp == 0) {
                    // a[i] equals a[i-1]. We have to check for stability
                    if (originIndex < prevOriginIndex) {
                        return false;
                    }
                }
            }
            prevOriginIndex = originIndex;
        }
        return true;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void main(String[] args) {
        int N = 10;
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = new Integer((int) (Math.random() * N));
        }
        System.out.println(check(a));
    }
}
