package chapter1.part3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    private static class Node<I> {
        I item;
        Node next;
    }

    public Queue() {

    }
    // Ex 1.3.41
    public Queue(Queue<Item> q) {
        int N = q.size();
        while (N > 0) {
            Item item = q.dequeue();
            enqueue(item); //add to this queue
            q.enqueue(item); //add back to q
            N--;
        }
    }

    private Node<Item> first, last;
    private int N;

    public boolean isEmpty() {
        //or first == null
        return N == 0;
    }

    public int size() {
        return N;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node<>();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        N++;
    }

    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * ListIterator needs to be non-static because it accesses the "first" instance variable
     * from the enclosing Queue instance.
     */
    private class ListIterator implements Iterator<Item> {
        private Node<Item> itr = first;

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

    private void print() {
        for (Item item : this) {
            System.out.print(item+ " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Queue<String> q = new Queue<>();
        q.enqueue("a");
        q.enqueue("b");
        q.enqueue("c");
        Queue<String> r = new Queue<>(q);
        q.print();
        r.print();

        q.enqueue("d");
        q.print();
        r.print();

        r.enqueue("e");
        q.print();
        r.print();

        q.dequeue();
        q.print();
        r.print();
    }
}
