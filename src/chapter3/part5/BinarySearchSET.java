package chapter3.part5;

import chapter1.part3.Queue;

import java.util.NoSuchElementException;

/**
 * Ex3.5.3
 * @param <Key>
 */
public class BinarySearchSET<Key extends Comparable<Key>> {
    private Key[] keys;
    private int N;

    public BinarySearchSET() {
        keys = (Key[]) new Object[2];
        N = 0;
    }

    public void add(Key key) {
        if (contains(key)) return;
        if (N == keys.length) resize(2 * keys.length);
        int r = rank(key);
        for (int i = N; i > r; i--) {
            keys[i] = keys[i - 1];
        }
        keys[r] = key;
        N++;
    }

    public void delete(Key key) {
        if (!contains(key)) return;
        int r = rank(key);
        for (int i = r; i + 1 < N; i++) {
            keys[i] = keys[i+1];
        }
        keys[--N] = null;
        if (N > 0 && N == keys.length / 4) resize(keys.length / 2);
    }

    public boolean contains(Key key) {
        int r = rank(key);
        return r < N && key.compareTo(keys[r]) == 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException();
        return keys[N - 1];
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException();
        return keys[0];
    }

    // largest key <= the parameter
    public Key floor(Key key) {
        if (isEmpty()) throw new NoSuchElementException();
        if (key.compareTo(min()) < 0) return null;
        if (key.compareTo(max()) > 0) return max();
        int r = rank(key);
        if (key.compareTo(keys[r]) == 0) {
            return keys[r];
        }
        return keys[r - 1];
    }

    // smallest key >= the parameter
    public Key ceiling(Key key) {
        if (isEmpty()) throw new NoSuchElementException();
        if (key.compareTo(max()) > 0) return null;
        if (key.compareTo(min()) < 0) return min();
        return keys[rank(key)];
    }

    public Key select(int k) {
        if (k < 0 || k >= N) throw new IllegalArgumentException();
        return keys[k];
    }

    public int size(Key lo, Key hi) {
        int rankLo = rank(lo);
        int rankHi = rank(hi);
        if (!contains(hi)) rankHi--;
        return rankHi - rankLo + 1;
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new Queue<>();
        int rankLo = rank(lo);
        int rankHi = rank(hi);
        if (!contains(hi)) rankHi--;
        for (int i = rankLo; i <= rankHi; i++) {
            q.enqueue(keys[i]);
        }
        return q;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public int rank(Key key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    private void resize(int cap) {
        Key[] tmp = (Key[]) new Comparable[cap];
        for (int i = 0; i < N; i++) {
            tmp[i] = keys[i];
        }
        keys = tmp;
    }
}
