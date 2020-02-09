package chapter1.part3;

import org.omg.CORBA.UNSUPPORTED_POLICY;

import java.util.Iterator;

public class ArrayGeneralizedQueue<Item> implements Iterable<Item> {
    Item[] q = (Item[]) new Object[2];
    private int N, first, last;

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Item item) {
        if (N == q.length) {
            resize(2 * q.length);
        }
        q[last++] = item;
        N++;
        if (last == q.length) {
            last = 0;
        }
    }

    public Item delete(int k) {
        if (isEmpty() || k < 1 || k > N) {
            throw new UnsupportedOperationException();
        }
        Item item = q[(first + k - 1) % q.length];
        if (k <= N / 2) {
            //move the left part up is faster
            for (int i = k - 1; i > 0; i--) {
                q[(first + i) % q.length] = q[(first + i - 1) % q.length];
            }
            q[first] = null;
            first = (first + 1) % q.length;
        } else {
            //move the right part down is faster
            for (int i = k - 1; i < N - 1; i++) {
                q[(first + i) % q.length] = q[(first + i + 1) % q.length];
            }
            last--;
            if (last < 0) {
                last = q.length - 1;
            }
            q[last] = null;
        }
        N--;
        if (N > 0 && N == q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }

    private void resize(int newSize) {
        assert newSize >= N;
        Item[] tmp = (Item[]) new Object[newSize];
        for (int i = 0; i < N; i++) {
            tmp[i] = q[(first + i) % q.length];
        }
        q = tmp;
        first = 0;
        last = N;
    }

    public Iterator<Item> iterator() {
        return new GeneralizedArrayIterator();
    }

    private class GeneralizedArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < N;
        }

        public Item next() {
            Item item = q[(first + i) % q.length];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
