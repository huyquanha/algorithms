package chapter2.part5;

import java.util.NoSuchElementException;

/**
 * Ex2.5.24
 */
public class StableMaxPQ<Item extends Comparable<Item>> {
    private class Wrapper implements Comparable<Wrapper> {
        Item item;
        int insertOrder;

        public Wrapper(Item item, int insertOrder) {
            this.item = item;
            this.insertOrder = insertOrder;
        }

        public int compareTo(Wrapper that) {
            int cmp = this.item.compareTo(that.item);
            if (cmp < 0) {
                return -1;
            } else if (cmp > 0) {
                return 1;
            } else {
                /**
                 * for a max priority queue, we want that if 2 keys are the same, then the one with a smaller
                 * insertOrder to be LARGER than the one that has a higher insertOrder, since we want it to be removed first.
                 */
                return that.insertOrder - this.insertOrder;
            }
        }
    }

    private int N = 0, order = 0;
    private Wrapper[] pq;

    public StableMaxPQ() {
        pq = (Wrapper[]) new Comparable[2];
    }

    public StableMaxPQ(int maxN) {
        pq = (Wrapper[]) new Comparable[maxN + 1];
    }

    public StableMaxPQ(Item[] a) {
        N = a.length;
        pq = (Wrapper[]) new Comparable[N+1];
        for (int i = 1; i <= N; i++) {
            pq[i] = new Wrapper(a[i-1], order++);
        }
        //reconstruct heap
        for (int i = N/2; i>= 1; i--) {
            sink(i);
        }
    }

    public void insert(Item item) {
        if (N + 1 == pq.length) {
            resize(2* pq.length);
        }
        Wrapper w = new Wrapper(item, order++);
        N++;
        pq[N] = w;
        swim(N);
    }

    public Item max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1].item;
    }

    public Item delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item max = pq[1].item;
        exch(1, N--);
        pq[N+1] = null;
        sink(1);
        if (N + 1 == pq.length /4) {
            resize(pq.length /2);
        }
        return max;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (k*2 <= N) {
            int j = k *2;
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
        Wrapper tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    private void resize(int sz) {
        Wrapper[] tmp = (Wrapper[]) new Comparable[sz];
        for (int i = 1; i <= N; i++) {
            tmp[i] = pq[i];
        }
        pq = tmp;
    }
}
