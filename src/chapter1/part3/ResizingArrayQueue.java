package chapter1.part3;

import java.util.Iterator;

public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private Item[] q = (Item[]) new Object[2];
    private int N;
    private int head, tail;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (N == q.length) {
            resize(2 * q.length);
        }
        q[tail++] = item;
        if (tail == q.length) {
            tail = 0;
        }
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        Item item = q[head];
        q[head] = null;
        head++;
        if (head == q.length) {
            head = 0;
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
            tmp[i] = q[(head + i) % q.length];
        }
        q = tmp;
        head = 0;
        tail = N;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < N;
        }

        public Item next() {
            Item item = q[(head + i) % q.length];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
