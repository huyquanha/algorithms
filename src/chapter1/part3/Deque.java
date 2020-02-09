package chapter1.part3;

import java.util.Iterator;
import java.util.ListIterator;

public class Deque<Item> implements Iterable<Item> {
    private class DNode {
        Item item;
        DNode left, right;
    }

    private DNode first, last;
    private int N;

    public boolean isEmpty() {
        // N ==0
        return first == null;
    }

    public int size() {
        return N;
    }

    public void pushLeft(Item item) {
        DNode oldFirst = first;
        first = new DNode();
        first.item = item;
        first.right = oldFirst;
        if (oldFirst != null) {
            oldFirst.left = first;
        } else {
            last = first;
        }
        N++;
    }

    public void pushRight(Item item) {
        DNode oldLast = last;
        last = new DNode();
        last.item = item;
        last.left = oldLast;
        if (oldLast != null) {
            oldLast.right = last;
        } else {
            first = last;
        }
        N++;
    }

    public Item popLeft() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        Item item = first.item;
        DNode next = first.right;
        if (next != null) {
            next.left = null;
        } else {
            last = null;
        }
        first = next;
        N--;
        return item;
    }

    public Item popRight() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        Item item = last.item;
        DNode prev = last.left;
        if (prev != null) {
            prev.right = null;
        } else {
            first = null;
        }
        last = prev;
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private DNode cur = first;

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            Item item = cur.item;
            cur = cur.right;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
