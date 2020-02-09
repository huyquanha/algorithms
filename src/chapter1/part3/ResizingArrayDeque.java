package chapter1.part3;

import java.util.Iterator;

public class ResizingArrayDeque<Item> implements Iterable<Item> {
    private Item[] dq = (Item[]) new Object[2];
    private int N, first = 0, last = 1;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void pushLeft(Item item) {
        if (first == -1) {
            resize(dq.length * 2);
        }
        dq[first--] = item;
        N++;
    }

    public Item popLeft() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        Item item = dq[++first];
        dq[first] = null; // avoid loitering
        N--;
        if (N > 0 && N == dq.length / 4) {
            resize(dq.length / 2);
        }
        return item;
    }

    public void pushRight(Item item) {
        if (last == dq.length) {
            resize(dq.length * 2);
        }
        dq[last++] = item;
        N++;
    }

    public Item popRight() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        Item item = dq[--last];
        dq[last] = null; //avoid loitering
        N--;
        if (N > 0 && N == dq.length / 4) {
            resize(dq.length / 2);
        }
        return item;
    }

    private void resize(int newSize) {
        assert newSize >= N;
        Item[] tmp = (Item[]) new Object[newSize];
        for (int i = 0; i < N; i++) {
            tmp[(newSize - N) / 2 + i] = dq[first + 1 + i];
        }
        first = (newSize - N) / 2 - 1; //first is one before the first element in dq
        last = first + N + 1; //last is one after the last element in dq
        dq = tmp;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < N;
        }

        public Item next() {
            Item item = dq[first + 1 + i];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void printStatus() {
        System.out.println("First: " + first);
        System.out.println("Last: " + last);
        System.out.println("Array length: " + dq.length);
        for (Item item : dq) {
            System.out.println(item);
        }
        System.out.println("------------");
    }

    public static void main(String[] args) {
        ResizingArrayDeque<String> deque = new ResizingArrayDeque<>();
        deque.pushLeft("to");
        deque.printStatus();
        deque.pushLeft("be");
        deque.printStatus();
        deque.pushRight("or");
        deque.printStatus();
        deque.pushRight("not");
        deque.printStatus();
        deque.pushRight("to");
        deque.printStatus();
        deque.popLeft();
        deque.printStatus();
        deque.popRight();
        deque.printStatus();
        deque.popRight();
        deque.printStatus();
        deque.popLeft();
        deque.printStatus();
        deque.popLeft();
        deque.printStatus();
        deque.pushRight("Yeah");
        deque.printStatus();
    }
}
