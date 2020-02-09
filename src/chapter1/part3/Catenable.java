package chapter1.part3;

import java.util.Iterator;

//Exercise 1.3.47
public class Catenable<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private Node last;
    private int N;

    public boolean isEmpty() {
        return last == null; //or N ==0
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (oldLast != null) {
            last.next = oldLast.next;
            oldLast.next = last;
        }
        else { //meaning last is the only item now
            last.next = last;
        }
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        Node first = last.next;
        Item item = first.item;
        if (first != last) {
            last.next = first.next;
        }
        else {
            last = null;
        }
        N--;
        return item;
    }

    public void push(Item item) {
        Node first = new Node();
        first.item = item;
        if (isEmpty()) {
            last = first;
            last.next = last;
        }
        else {
            Node oldFirst = last.next;
            first.next = oldFirst;
            last.next = first;
        }
        N++;
    }

    public Item pop() {
        return dequeue();
    }

    public void catenate(Queue<Item> q) {
        int size = q.size();
        while (size > 0) {
            Item item = q.dequeue();
            enqueue(item);
            q.enqueue(item);
            size--;
        }
    }

    public void catenate(Stack<Item> s) {
        int size = s.size();
        while (!s.isEmpty()) {
            Item item = s.pop();
            enqueue(item);
            push(item); //this is solely for reconstructing the original stack(below)
        }
        int index = 0;
        while (index < size) { //reconstruct the original stack
            Item item = pop();
            s.push(item);
            index++;
        }
    }

    public void catenate(Steque<Item> stq) {
        int size = stq.size();
        while (size > 0) {
            Item item = stq.pop();
            enqueue(item);
            stq.enqueue(item);
            size--;
        }
    }

    public Iterator<Item> iterator() {
        return new CircularListIterator();
    }

    private class CircularListIterator implements Iterator<Item> {
        private Node cur;

        public boolean hasNext() {
            if (isEmpty()) {
                return false;
            }
            if (cur == null) {
                cur = last.next; //last.next is first, we want to begin at first
                return true;
            }
            else {
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
}
