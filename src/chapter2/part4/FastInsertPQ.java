package chapter2.part4;

import com.sun.scenario.effect.Brightpass;

public class FastInsertPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N = 0;

    public FastInsertPQ(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1];
    }

    public FastInsertPQ(Key[] a) {
        N = a.length;
        pq = (Key[]) new Comparable[N + 1];
        for (int i = 1; i <= N; i++) {
            pq[i] = a[i-1];
        }
        for (int i = N/2; i>=1; i--) {
            sink(i);
        }
    }

    public void insert(Key k) {
        N++;
        pq[N] = k;
        swim(N);
    }

    private void swim(int k) {
        if (k > 1 && less(k, k/2)) {
            int lo = 1, hi = k/2;
            while (lo <= hi) {
                int logBase2Hi = (int) (Math.log(hi)/Math.log(2));
                int logBase2Lo = (int) (Math.log(lo)/Math.log(2));
                int logBase2Mid = logBase2Lo + (logBase2Hi - logBase2Lo)/2;
                int midIndex = (int) (Math.pow(2, logBase2Mid));
                if (midIndex == 1 || (!less(midIndex, midIndex/2) && !less(midIndex * 2, midIndex))) {
                    exch(k, midIndex);
                }
            }
        }

        while (k > 1 && less(k, k/2)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(j + 1, j)) j++;
            if (!less(j, k)) break;
            exch(j, k);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }
}
