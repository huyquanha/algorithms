package chapter1.part4;

import chapter1.part3.Stack;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueWith2Stacks<Item> implements Iterable<Item> {
    private Stack<Item> enqStack = new Stack<>();
    private Stack<Item> deqStack = new Stack<>();
    private int N;
    private int opCount;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        enqStack.push(item);
        opCount++;
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item;
        if (!deqStack.isEmpty()) {
            item = deqStack.pop();
        }
        else {
            while (!enqStack.isEmpty()) {
                deqStack.push(enqStack.pop());
            }
            item = deqStack.pop();
        }
        N--;
        opCount++;
        return item;
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private int remain = N;
        private int orgOpCount = opCount;

        public boolean hasNext() {
            if (orgOpCount != opCount) {
                throw new ConcurrentModificationException();
            }
            return remain > 0;
        }

        public Item next() {
            if (orgOpCount != opCount) {
                throw new ConcurrentModificationException();
            }
            if (deqStack.isEmpty()) {
                while (!enqStack.isEmpty()) {
                    deqStack.push(enqStack.pop());
                }

            }
            Item item = deqStack.pop();
            enqStack.push(item);
            remain--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        QueueWith2Stacks<Integer> q = new QueueWith2Stacks<>();
        for (int i=0; i< N; i++) {
            q.enqueue(i);
        }
        for (int i=0; i< N/2; i++) {
            System.out.println(q.dequeue());
        }
        System.out.println("-------------");
        for (int i=0; i< N; i++) {
            q.enqueue(i);
        }
        for (Integer i : q) {
            System.out.println(i);
        }
    }
}
