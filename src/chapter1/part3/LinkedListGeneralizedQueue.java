package chapter1.part3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListGeneralizedQueue<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private int N;

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N++;
    }

    public Item delete(int k) {
        if (isEmpty() || k < 1 || k > N) {
            throw new UnsupportedOperationException();
        }
        int count = 1;
        Node cur = first;
        Node prev = null;
        while (count < k) {
            prev = cur;
            cur = cur.next;
            if (cur == null) {
                throw new NoSuchElementException();
            }
            count++;
        }
        Item item = cur.item;
        if (prev == null) { //that means k =1, or first is to be removed
            first = first.next;
        } else {
            prev.next = cur.next;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListGeneralizedIterator();
    }

    private class ListGeneralizedIterator implements Iterator<Item> {
        private Node cur = first;

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            Item item = cur.item;
            cur = cur.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
