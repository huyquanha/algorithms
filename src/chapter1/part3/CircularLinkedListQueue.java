package chapter1.part3;

import java.util.Iterator;

public class CircularLinkedListQueue<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private Node last;
    private int N;

    public boolean isEmpty() {
        //or return N ==0
        return last == null;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (isEmpty()) {
            last = new Node();
            last.item = item;
            last.next = last;
        } else {
            Node first = last.next;
            Node oldLast = last;
            last = new Node();
            last.item = item;
            oldLast.next = last;
            last.next = first;
        }
        N++;
    }

    public Item dequeue() {
        Node first = last.next;
        Item item = first.item;
        if (first == last) { //this is the last item in the queue
            last = null;
        } else {
            last.next = first.next;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new CircularListIterator();
    }

    private class CircularListIterator implements Iterator<Item> {
        private Node cur;

        public boolean hasNext() {
            if (last == null) {
                return false;
            }
            else if (cur == null) {
                cur = last.next;
                return true;
            } else {
                return cur != last.next;
            }
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

    public static void main(String[] args) {
        CircularLinkedListQueue<String> q = new CircularLinkedListQueue<>();
        q.enqueue("a");
        for (String item : q) {
            System.out.println(item);
        }
        q.enqueue("b");
        for (String item : q) {
            System.out.println(item);
        }
        q.enqueue("c");
        for (String item : q) {
            System.out.println(item);
        }
        q.dequeue();
        q.dequeue();
        q.enqueue("d");
        q.dequeue();
        for (String item : q) {
            System.out.println(item);
        }

    }
}
