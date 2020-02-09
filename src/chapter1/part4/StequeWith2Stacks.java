package chapter1.part4;

import chapter1.part3.Stack;

import java.util.NoSuchElementException;

public class StequeWith2Stacks<Item> {
    private Stack<Item> s = new Stack<>(); //handle push & pop
    private Stack<Item> q = new Stack<>(); //handle enqueue
    private int N;

    public boolean isEmpty() {
        return N ==0;
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
        s.push(item);
        N++;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item;
        if (!s.isEmpty()) {
            item = s.pop();
        }
        else {
            while (!q.isEmpty()) {
                s.push(q.pop());
            }
            item = s.pop();
        }
        N--;
        return item;
    }

    public void enqueue(Item item) {
        q.push(item);
        N++;
    }
}
