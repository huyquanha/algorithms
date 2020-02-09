package chapter2.part4;

import java.util.NoSuchElementException;

public class MinPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N = 0; //N is the number of elements in pq

    public MinPQ() {
        pq = (Key[]) new Comparable[2];
    }

    public MinPQ(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1];
    }

    public MinPQ(Key[] a) {
        N = a.length;
        pq = (Key[]) new Comparable[N + 1];
        for (int i = 1; i <= N; i++) {
            pq[i] = a[i - 1];
        }
        for (int i = N / 2; i >= 1; i--) {
            sink(i);
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(Key v) {
        if (N + 1 == pq.length) {
            resize(2 * pq.length);
        }
        pq[++N] = v;
        swim(N);
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1];
    }

    public Key delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Key min = pq[1];
        exch(1, N--);
        pq[N + 1] = null;
        sink(1);
        if (N + 1 == pq.length / 4) {
            resize(pq.length / 2);
        }
        return min;
    }

    public Key[] getKeys() {
        return pq;
    }

    private void swim(int k) {
        //min heap order is violated when pq[k] is less than the parent, hence the children needs to swim up
        while (k > 1 && less(k, k / 2)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        //min heap order is violated when pq[k] is larger than the children => exchange with the smaller of the 2 children
        //while k is not at the bottom of the tree
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(j + 1, j)) j++;
            if (!less(j, k)) break;
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
