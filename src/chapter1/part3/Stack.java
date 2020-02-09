package chapter1.part3;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    public Stack() {

    }

    //Ex 1.3.42
    public Stack(Stack<Item> s) {
        Stack<Item> temp = new Stack<>();
        while (!s.isEmpty()) {
            Item item = s.pop();
            temp.push(item);
        }
        while (!temp.isEmpty()) {
            Item item = temp.pop();
            push(item); //push to the current stack
            s.push(item); //push back to old stack
        }
    }

    private Node first;
    private int N, pushPopCount;

    public boolean isEmpty() {
        //alternatively, return N ==0;
        return first == null;
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst; //correct even if first is initially null
        N++;
        pushPopCount++;
    }

    public Item pop() {
        Item item = first.item;
        first = first.next;
        N--;
        pushPopCount++;
        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            return null;
        }
        return first.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    //Exercise 1.3.50 - fail fast iterator
    private class ListIterator implements Iterator<Item> {
        private Node itr;
        private int ppCount;

        public ListIterator() {
            itr = first;
            ppCount = pushPopCount;
        }

        public boolean hasNext() {
            if (ppCount != pushPopCount) {
                throw new ConcurrentModificationException();
            }
            return itr != null;
        }

        public Item next() {
            if (ppCount != pushPopCount) {
                throw new ConcurrentModificationException();
            }
            Item item = itr.item;
            itr = itr.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void print() {
        for(Item item : this) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Stack<String> s = new Stack<>();
        s.push("a");
        s.push("b");
        s.push("c");
        s.print();

        Stack<String> r = new Stack<>(s);
        s.print();
        r.print();

        s.push("d");
        s.print();
        r.print();

        r.push("e");
        s.print();
        r.print();

        s.pop();
        s.print();
        r.print();

        r.pop();
        s.print();
        r.print();
    }
}
