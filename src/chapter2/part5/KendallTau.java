package chapter2.part5;

/**
 * Ex2.5.19
 */
public class KendallTau {
    public static int dist(int[] a1, int[] a2) {
        assert a1.length == a2.length;
        int N = a1.length;
        // the trick here is we consider a1 to be in order already , and a2's ranking is relative to that order
        int[] order1 = new int[N];
        for (int i = 0; i < N; i++) {
            order1[a1[i]] = i; //this means, considering a1[i] to be in ith position instead of its actual position
        }
        int[] order2 = new int[N];
        for (int i = 0; i < N; i++) {
            order2[i] = order1[a2[i]];
        }
        // now the task of computing distance between a1 and a2 becomes comparing order2 with the identity permutation
        // or, to find the number of inversions in order2
        // we pass a1 in to print out the pairs that are in different relative order
        return countInversion(a1, order2);
    }

    private static int countInversion(int[] a1, int[] a) {
        int[] aux = new int[a.length];
        return count(a1, a, aux, 0, a.length - 1);
    }

    private static int count(int[] a1, int[] a, int[] aux, int lo, int hi) {
        if (lo >= hi) {
            return 0;
        }
        int mid = lo + (hi - lo)/2;
        return count(a1, a, aux, lo, mid) + count(a1, a, aux, mid + 1, hi) + mergeCount(a1, a, aux, lo, mid, hi);
    }

    private static int mergeCount(int[] a1, int[] a, int[] aux, int lo, int mid, int hi) {
        for (int i = lo; i <= hi; i++) {
            aux[i] = a[i];
        }
        int i = lo, j = mid + 1, mergeCount = 0;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j] < aux[i]) {
                System.out.println("Found an inversion: " + a1[aux[j]] + " " + a1[aux[i]]);
                mergeCount++;
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
        return mergeCount;
    }

    public static void main(String[] args) {
        int[] a1 = {0,3,1,6,2,5,4};
        int[] a2 = {1,0,3,6,4,2,5};
        System.out.println(dist(a1, a2));
    }
}
