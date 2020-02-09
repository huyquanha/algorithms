package chapter1.part4;

import chapter1.part3.Queue;
import chapter1.part3.Stack;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class StackWithQueue<Item> implements Iterable<Item> {
    private Queue<Item> q = new Queue<>();
    private int N, opCount;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
        q.enqueue(item);
        opCount++;
        N++;
    }

    public Item pop() {
        int remain = N;
        while (remain > 1) {
            q.enqueue(q.dequeue());
            remain--;
        }
        Item item = q.dequeue();
        N--;
        opCount++;
        return item;
    }

    public Iterator<Item> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<Item> {
        private Stack<Item> copy;

        public StackIterator() {
            while (!isEmpty()) {
                copy.push(q.dequeue());
            }
        }

        public boolean hasNext() {
            return copy.iterator().hasNext();
        }

        public Item next() {
            return copy.iterator().next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
