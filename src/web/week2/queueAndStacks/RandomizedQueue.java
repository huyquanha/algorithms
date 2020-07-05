import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        if (n == items.length) {
            resize(2 * items.length);
        }
        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        int randomIdx = StdRandom.uniform(n);
        Item removed = items[randomIdx];
        items[randomIdx] = items[n - 1];
        // avoid loitering
        items[--n] = null;
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }
        return removed;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        return items[StdRandom.uniform(n)];
    }

    private void resize(int sz) {
        Item[] tmp = (Item[]) new Object[sz];
        for (int i = 0; i < n; i++) {
            if (items[i] != null) {
                tmp[i] = items[i];
            }
        }
        items = tmp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private final int[] order;
        private int cur;

        public RandomizedIterator() {
            order = new int[n];
            for (int i = 0; i < n; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
            cur = 0;
        }

        public boolean hasNext() {
            return cur < n;
        }

        public Item next() {
            if (hasNext()) {
                Item item = items[order[cur++]];
                return item;
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue("a");
        for (String item : randomizedQueue) {
            System.out.println(item);
        }
        System.out.println(randomizedQueue.size());
        System.out.println("-----------------");

        randomizedQueue.enqueue("b");
        for (String item : randomizedQueue) {
            System.out.println(item);
        }
        System.out.println(randomizedQueue.size());
        System.out.println("-----------------");

        randomizedQueue.enqueue("c");
        for (String item : randomizedQueue) {
            System.out.println(item);
        }
        System.out.println(randomizedQueue.size());
        System.out.println("-----------------");

        randomizedQueue.enqueue("d");
        for (String item : randomizedQueue) {
            System.out.println(item);
        }
        System.out.println(randomizedQueue.size());
        System.out.println("-----------------");

        // sample
        String sample = randomizedQueue.sample();
        System.out.println(sample);
        System.out.println("-----------------");

        sample = randomizedQueue.sample();
        System.out.println(sample);
        System.out.println("-----------------");

        // remove random
        String removed = randomizedQueue.dequeue();
        System.out.println(removed);
        System.out.println(randomizedQueue.size());
        System.out.println("-----------------");

        removed = randomizedQueue.dequeue();
        System.out.println(removed);
        System.out.println(randomizedQueue.size());
        System.out.println("-----------------");

        removed = randomizedQueue.dequeue();
        System.out.println(removed);
        System.out.println(randomizedQueue.size());
        System.out.println("-----------------");

        removed = randomizedQueue.dequeue();
        System.out.println(removed);
        System.out.println(randomizedQueue.size());
        System.out.println("-----------------");
    }
}
