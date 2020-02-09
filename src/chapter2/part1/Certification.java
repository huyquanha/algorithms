package chapter2.part1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Certification {
    public static boolean check(Comparable[] a) {
        Set<Comparable> set = new HashSet<>();
        for (Comparable item : a) {
            set.add(item);
        }
        Arrays.sort(a);
        for (int i = 1; i < a.length; i++) {
            if (less(a[i],a[i-1]) || !set.contains(a[i])) {
                return false;
            }
        }
        if (!set.contains(a[0])) {
            return false;
        }
        return true;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
}
