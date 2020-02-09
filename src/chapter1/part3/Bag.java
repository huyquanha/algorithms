package chapter1.part3;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private int N;

    public boolean isEmpty() {
        //or N == 0
        return first == null;
    }

    public int size() {
        return N;
    }

    public void add(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N++;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node itr = first;

        public boolean hasNext() {
            return itr != null;
        }

        public Item next() {
            Item item = itr.item;
            itr = itr.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
