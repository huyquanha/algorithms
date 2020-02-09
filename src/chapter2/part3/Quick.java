package chapter2.part3;

import edu.princeton.cs.algs4.StdRandom;

public class Quick {
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo]; //the partitioning item
        while (true) {
            //scan to the right so long as a[i] is smaller than the partitioning item
            while(less(a[++i], v)) {
                //check to make sure it does not go out of bound
                if (i == hi) break;
            }
            //scan to the left so long as a[j] is larger than the partitioning item
            while (less(v, a[--j])) {
                //check to make sure it does not go out of bound
                //this is actually redundant because when j == lo, a[j] = a[lo] = v, so less() returns false before evaluating the if
                if (j == lo) break;
            }
            //when we come here, a[i] >= v and a[j] <= v
            /*
                if i == j, that means a[i] = a[j] = v. Furthermore, we know that all elements with indices smaller than j(or i)
                will be <= v, and all elements with indices > j (or i) will be >= v => j or i is
                the position for the partitioning item*/
            /*
                if i > j, and a[i] >= v and a[j] <= v, that means i has come to the right partition where all elements >= v,
                and j has come to the left partition, where all elements <= v.
                Since a[j] <= v, exchanging a[j] with a[lo] will make sure the partitioning condition is honored (all elements
                to the left <= v). If we were to exchange a[i] with a[lo] instead, the partitioning condition might be violated,
                because a[i] might be larger than v.
             */
            if (i >= j) break;
            //after the exchange, a[i] <= v and a[j] >= v, which satisfies the partitioning condition
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
