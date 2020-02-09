package chapter1.part3;

import java.util.Iterator;

//Ex32
public class Steque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private Node first, last;
    private int N;

    public boolean isEmpty() {
        // or N ==0
        return first == null;
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst == null) {
            last = first;
        }
        N++;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        N--;
        return item;
    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (oldLast == null) {
            first = last;
        } else {
            oldLast.next = last;
        }
        N++;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
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
