package chapter1.part3;

import java.util.Iterator;

public class RandomBag<Item> implements Iterable<Item> {
    private Item[] bag = (Item[]) new Object[1];
    private int N;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void add(Item item) {
        if (N == bag.length) {
            resize(2 * bag.length);
        }
        bag[N++] = item;
    }

    private void resize(int newSize) {
        assert newSize >= N;
        Item[] tmp = (Item[]) new Object[newSize];
        for (int i = 0; i < N; i++) {
            tmp[i] = bag[i];
        }
        bag = tmp;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int cur = 0;

        public ArrayIterator() {
            for (int i = 0; i < N; i++) {
                int r = (int) (Math.random() * (N - i)) + i;
                Item tmp = bag[r];
                bag[r] = bag[i];
                bag[i] = tmp;
            }
        }

        public boolean hasNext() {
            return cur < N;
        }

        public Item next() {
            Item item = bag[cur++];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
