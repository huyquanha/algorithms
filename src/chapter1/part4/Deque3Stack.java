package chapter1.part4;

import chapter1.part3.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Ex 1.4.31
public class Deque3Stack<Item> implements Iterable<Item> {
    private Stack<Item> left, mid, right;
    private int N;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void pushLeft(Item item) {
        left.push(item);
        N++;
    }

    public Item popLeft() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (!left.isEmpty()) {
            Item item = left.pop();
            N--;
            return item;
        } else {
            int idx = 0;
            while (idx < N) {
                Item item = right.pop();
                if (idx < N/2) {
                    mid.push(item);
                } else {
                    left.push(item);
                }
            }
            while (!mid.isEmpty()) {
                right.push(mid.pop());
            }
            Item item = left.pop();
            N--;
            return item;
        }
    }

    public void pushRight(Item item) {
        right.push(item);
        N++;
    }

    public Item popRight() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (!right.isEmpty()) {
            Item item = right.pop();
            N--;
            return item;
        } else {
            int idx = 0;
            while (idx < N) {
                Item item = left.pop();
                if (idx < N/2) {
                    mid.push(item);
                } else {
                    right.push(item);
                }
            }
            while (!mid.isEmpty()) {
                left.push(mid.pop());
            }
            Item item = right.pop();
            N--;
            return item;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new Deque3StackItertor();
    }

    private class Deque3StackItertor implements Iterator<Item> {
        private Iterator<Item> leftItr = left.iterator();
        private Iterator<Item> rightItr = right.iterator();

        public boolean hasNext() {
            return leftItr.hasNext() || rightItr.hasNext();
        }

        public Item next() {
            Item item;
            if (leftItr.hasNext()) {
                item = leftItr.next();
            } else {
                item = rightItr.next();
            }
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
