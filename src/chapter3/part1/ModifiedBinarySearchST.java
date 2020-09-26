package chapter3.part1;

import chapter1.part3.Queue;
import chapter3.OrderedST;

import java.util.NoSuchElementException;

/**
 * Ex3.1.12
 *
 * @param <Key>
 * @param <Value>
 */
public class ModifiedBinarySearchST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    private class Item {
        Key k;
        Value v;

        public Item(Key k, Value v) {
            this.k = k;
            this.v = v;
        }
    }

    private Item[] items;
    private int N;

    public ModifiedBinarySearchST(Item[] items) {
        // defensive copy
        N = items.length;
        this.items = (Item[]) new Object[N];
        for (int i = 0; i < N; i++) {
            this.items[i] = items[i];
        }
        sort();
    }

    public ModifiedBinarySearchST() {
        items = (Item[]) new Object[2];
        N = 0;
    }

    // merge sort this.items
    private void sort() {
        Item[] aux = (Item[]) new Comparable[N];
        sort(aux, 0, N - 1);
    }

    private void sort(Item[] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(aux, lo, mid);
        sort(aux, mid + 1, hi);
        merge(aux, lo, mid, hi);
    }

    private void merge(Item[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = items[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) items[k] = aux[j++];
            else if (j > hi) items[k] = aux[i++];
            else if (less(aux[j], aux[i])) items[k] = aux[j++];
            else items[k] = aux[i++];
        }
    }

    private boolean less(Item a, Item b) {
        return a.k.compareTo(b.k) < 0;
    }

    public int rank(Key key) {
        checkKey(key);
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            Key midKey = items[mid].k;
            int cmp = key.compareTo(midKey);
            if (cmp < 0) {
                // key < midKey
                hi = mid - 1;
            } else if (cmp > 0) {
                // key > midKey
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    public void put(Key key, Value val) {
        int r = rank(key);
        if (r < N && items[r].k.compareTo(key) == 0) {
            // key is already in the table
            if (val == null) {
                delete(key);
            } else {
                items[r].v = val;
            }
        } else {
            if (val != null) {
                if (N == items.length) {
                    resize(2 * N);
                }
                Item newItem = new Item(key, val);
                for (int i = N - 1; i >= r; i--) {
                    items[i + 1] = items[i];
                }
                items[r] = newItem;
                N++;
            }
        }
    }

    public Value get(Key key) {
        int r = rank(key);
        if (r < N && items[r].k.compareTo(key) == 0) {
            return items[r].v;
        }
        return null;
    }

    public void delete(Key key) {
        int r = rank(key);
        if (r < N && items[r].k.compareTo(key) == 0) {
            // key is in the table
            for (int i = r; i < N - 1; i++) {
                items[i] = items[i + 1];
            }
            items[--N] = null;
            if (N > 0 && N == items.length / 4) {
                resize(items.length / 2);
            }
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[0].k;
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[N - 1].k;
    }

    public Key floor(Key key) {
        int r = rank(key);
        if (r < N && items[r].k.compareTo(key) == 0) {
            // key is in the table
            return items[r].k;
        } else {
            // key is not in the table
            if (r == 0) {
                // key is smaller than all keys in the table => floor(key) doesn't exist
                return null;
            } else {
                // since key is between items[r-1] and items[r], floor(key) = items[r-1].k
                return items[r - 1].k;
            }
        }
    }

    public Key ceiling(Key key) {
        int r = rank(key);
        if (r < N) {
            // whether key is in the table or not, items[r].k is still correct
            // if key is in table, then items[r].k is of course correct
            // if key is not in table, items[r] is the next item after key => ceil(key) = items[r].k
            return items[r].k;
        } else {
            // key is larger than all keys in table => ceil(key) doesn't exist
            return null;
        }
    }

    public Key select(int k) {
        if (k < 0 || k >= N) {
            throw new NoSuchElementException();
        }
        return items[k].k;
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new Queue<>();
        int r_lo = rank(lo);
        int r_hi = rank(hi);
        for (int i = r_lo; i < r_hi; i++) {
            // not that lo <= items[r_lo].k => r_lo is always included
            q.enqueue(items[i].k);
        }
        if (r_hi < N && items[r_hi].k.compareTo(hi) == 0) {
            // hi is in the table and we should add items[r_hi].k as well
            q.enqueue(items[r_hi].k);
        }
        return q;
    }

    private void resize(int sz) {
        Item[] tmp = (Item[]) new Object[sz];
        for (int i = 0; i < N; i++) {
            tmp[i] = items[i];
        }
        items = tmp;
    }
}
