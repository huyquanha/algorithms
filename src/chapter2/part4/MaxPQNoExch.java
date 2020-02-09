package chapter2.part4;

import java.util.NoSuchElementException;

/**
 * Ex2.4.26
 * @param <Key>
 */
public class MaxPQNoExch<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N = 0;

    public MaxPQNoExch() {
        pq = (Key[]) new Comparable[2];
    }

    public MaxPQNoExch(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1]; //because we don't use the 0th slot
    }

    public MaxPQNoExch(Key[] a) {
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
        if (N + 1 == pq.length) {
            resize(2 * pq.length);
        }
        N++;
        pq[N] = k;
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
        pq[1] = pq[N];
        pq[N] = max;
        N--;
        pq[N+1] = null;
        sink(1);
        if (N + 1 == pq.length / 4) {
            resize(pq.length /2);
        }
        return max;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    private void sink(int k) {
        Key tmp = pq[k];
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(j, j + 1)) j++;
            if (tmp.compareTo(pq[j]) >= 0) break;
            pq[k] = pq[j];
            k = j;
        }
        pq[k] = tmp;
    }

    private void swim(int k) {
        Key tmp = pq[k];
        while (k > 1 && pq[k/2].compareTo(tmp) < 0) {
            pq[k] = pq[k/2];
            k = k/2;
        }
        pq[k] = tmp;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void resize(int size) {
        Key[] tmp = (Key[]) new Comparable[size];
        for (int i = 1; i <= N; i++) {
            tmp[i] = pq[i];
        }
        pq = tmp;
    }
}
