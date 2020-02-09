package chapter2.part4;

import java.util.NoSuchElementException;

public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N = 0;

    public MaxPQ() {
        pq = (Key[]) new Comparable[2];
    }

    public MaxPQ(int max) {
        //we add one more to pq length because pq[0] is unused
        pq = (Key[]) new Comparable[max + 1];
    }

    public MaxPQ(Key[] a) {
        N = a.length;
        //we add one more to pq length because pq[0] is unused
        pq = (Key[]) new Comparable[N + 1];
        for (int i = 1; i <= N; i++) {
            pq[i] = a[i - 1];
        }
        for (int i = N / 2; i >= 1; i--) {
            sink(i);
        }
    }

    public void insert(Key v) {
        if (N + 1 == pq.length) {
            resize(2 * pq.length);
        }
        pq[++N] = v;
        swim(N);
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1];
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Key max = pq[1];
        exch(1, N--);
        //avoid loitering
        pq[N + 1] = null;
        sink(1);
        if (N + 1 == pq.length / 4) {
            resize(pq.length / 2);
        }
        return max;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
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

    private void resize(int sz) {
        Key[] tmp = (Key[]) new Comparable[sz];
        for (int i = 1; i <= N; i++) {
            tmp[i] = pq[i];
        }
        pq = tmp;
    }
}
