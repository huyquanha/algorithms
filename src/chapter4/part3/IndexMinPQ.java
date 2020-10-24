package chapter4.part3;

import java.util.NoSuchElementException;

public class IndexMinPQ<Item extends Comparable<Item>> {
    private int[] pq;
    private Item[] items;
    private int[] qp;
    private int N;

    public IndexMinPQ(int maxN) {
        pq = new int[maxN + 1];
        qp = new int[maxN];
        items = (Item[]) new Comparable[maxN];
        for (int i = 0; i < maxN; i++) {
            qp[i] = -1;
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(int k, Item item) {
        pq[++N] = k;
        qp[k] = N;
        items[k] = item;
        swim(N);
    }

    public void change(int k, Item item) {
        if (!contains(k)) throw new NoSuchElementException();
        else {
            items[k] = item;
            swim(qp[k]);
            sink(qp[k]);
        }
    }

    public void delete(int k) {
        if (!contains(k)) throw new NoSuchElementException();
        int i = qp[k];
        exch(i, N--);
        swim(i);
        sink(i);
        items[k] = null;
        qp[k] = -1;
    }

    public Item min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[pq[1]];
    }

    public int minIndex() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1];
    }

    public boolean contains(int k) {
        return qp[k] != -1;
    }

    public int delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int minIndex = pq[1];
        exch(1, N--);
        sink(1);
        qp[minIndex] = -1;
        items[minIndex] = null;
        return minIndex;
    }

    private void swim(int k) {
        while (k > 1 && less(k, k / 2)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(j + 1, j)) j++;
            if (!less(j, k)) return;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return items[pq[i]].compareTo(items[pq[j]]) < 0;
    }

    private void exch(int i, int j) {
        int tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
}
