package chapter2.part4;

import java.util.NoSuchElementException;

/**
 * Ex2.4.33
 */
public class IndexMaxPQ<Item extends Comparable<Item>> {
    private int N = 0;
    private int[] pq;
    private int[] qp;
    private Item[] items;

    /**
     * We should choose maxN so that any index k to be inserted is < maxN
     * Ideally, maxN should be the length of the client array to be inserted into the index priority queue
     * @param maxN
     */
    public IndexMaxPQ(int maxN) {
        // indices run from 0 to maxN - 1 => keys' size is maxN
        items = (Item[]) new Comparable[maxN];
        // pq will hold the indices. since we don't use the first slot, pq should have a size of maxN + 1
        pq = new int[maxN + 1];
        // qp[i] is the position of i in pq (i is one of the indices), and since i runs from 0 to maxN - 1, qp's size can be maxN
        qp = new int[maxN];
        //initially, all the indices are not there, so we initialize qp[0..maxN-1] to -1
        for (int i = 0; i < maxN; i++) {
            qp[i] = -1;
        }
    }

    public void insert(int k, Item item) {
        pq[++N] = k;
        qp[k] = N;
        items[k] = item;
        swim(N);
    }

    public void change(int k, Item item) {
        if (!contains(k)) {
            throw new NoSuchElementException();
        }
        items[k] = item;
        swim(qp[k]);
        sink(qp[k]);
    }

    public void delete(int k) {
        if (!contains(k)) {
            throw new NoSuchElementException();
        }
        int i = qp[k];
        exch(i, N--);
        swim(i);
        sink(i);
        items[k] = null;
        qp[k] = -1;
    }

    public Item max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[pq[1]];
    }

    public int maxIndex() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1];
    }

    public int delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int maxIndex = pq[1];
        exch(1, N--);
        sink(1);
        items[maxIndex] = null;
        qp[maxIndex] = -1;
        return maxIndex;
    }

    public boolean contains(int k) {
        return qp[k] != -1;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
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
        return items[pq[i]].compareTo(items[pq[j]]) < 0;
    }

    private void exch(int i, int j) {
        int tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
        qp[pq[i]] = j;
        qp[pq[j]] = i;
    }
}
