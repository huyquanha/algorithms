package chapter2.part4;

import java.util.NoSuchElementException;

/**
 * Ex2.4.33
 * @param <Item>
 * int[] pq will hold the indices that correspond to the items being inserted into the priority queue
 * For example, insert(k, item) will insert k into the next slot in pq (pq[++N] = k),
 * and insert item at the kth position in the "keys" array: keys[k] = item. Then, the item can be swimmed
 * to its correct position, by performing exchanges on pq[], but the comparision is done via items on the keys[] array
 *
 * Reversely, when we do something like change(k, newItem), we need a way to find out the position in pq[] of k, so that
 * after we set keys[k] = newItem, we can either swim or sink the newItem to its correct position. In order to do that,
 * we introduce another array qp[], with the property: qp[pq[i]] = i. So if k = pq[i], then qp[k] is i, and we get back
 * the position of k in pq[] array.
 */
public class IndexMinPQ<Item extends Comparable<Item>> {
    private int N = 0;
    private int[] pq;
    private Item[] keys;
    private int[] qp;

    public IndexMinPQ(int maxN) {
        // since we don't use the first slot, the size we allocate for pq are maxN + 1
        pq = new int[maxN + 1];
        // for the keys, since the indices run from 0 to maxN - 1, we only need an array of size maxN
        keys = (Item[]) new Comparable[maxN];
        //qp[i] is the position of i in pq. Since i runs from 0 to maxN - 1, qp's size is also maxN
        qp = new int[maxN];
        for (int i = 0; i < maxN; i++) {
            qp[i] = -1;
        }
    }

    public void insert(int k, Item item) {
        if (contains(k)) {
            throw new IllegalArgumentException("Index is already in priority queue");
        }
        N++;
        pq[N] = k;
        qp[k] = N;
        keys[k] = item;
        swim(N);
    }

    public void change(int k, Item item) {
        if (!contains(k)) {
            throw new NoSuchElementException("Index is not in priority queue");
        }
        int itemPos = qp[k];
        Item currentItem = keys[k];
        keys[k] = item;
        int cmp = item.compareTo(currentItem);
        if (cmp < 0) {
            swim(itemPos);
        } else if (cmp > 0) {
            sink(itemPos);
        }
    }

    public boolean contains(int k) {
        return qp[k] != -1;
    }

    public void delete(int k) {
        if (!contains(k)) {
            throw new NoSuchElementException("Index is not in priority queue");
        }
        int itemPos = qp[k];
        exch(itemPos, N--);
        swim(itemPos);
        sink(itemPos);
        keys[k] = null;
        qp[k] = -1;
    }

    public Item min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return keys[pq[1]];
    }

    public int minIndex() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1];
    }

    public int delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int minIndex = pq[1];
        exch(1, N--);
        keys[minIndex] = null;
        qp[minIndex] = -1;
        sink(1);
        return minIndex;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
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
            if (!less(j, k)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    private void exch(int i, int j) {
        int iIndex = pq[i];
        int jIndex = pq[j];
        pq[i] = jIndex;
        pq[j] = iIndex;
        qp[jIndex] = i;
        qp[iIndex] = j;
    }
}
