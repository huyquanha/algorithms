package chapter1.part3;

import java.util.Iterator;

public class RingBuffer<Item> implements Iterable<Item> {
    private Item[] buffer;
    private int N, size, head, tail; //N=max, size is current size

    public RingBuffer(int N) {
        this.N = N;
        buffer = (Item[]) new Object[N];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == N;
    }

    public void deposit(Item item) {
        if (isFull()) {
            throw new UnsupportedOperationException();
        }
        buffer[tail++] = item;
        size++;
        if (tail == N) {
            tail = 0;
        }
    }

    public Item consume() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        Item item = buffer[head];
        buffer[head] = null;
        head++;
        size--;
        if (head == N) {
            head = 0;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new BufferIterator();
    }

    private class BufferIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            Item item = buffer[(head + i) % N];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
