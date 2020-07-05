import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node prev, next;
    }

    private Node first, last;
    private int n;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.prev = first;
        } else {
            last = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (oldLast != null) {
            oldLast.next = last;
        } else {
            first = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            // avoid loitering
            first.prev = null;
        } else {
            last = null;
        }
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            // avoid loitering
            last.next = null;
        } else {
            first = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node cur;

        public DequeIterator() {
            cur = first;
        }

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            if (hasNext()) {
                Item item = cur.item;
                cur = cur.next;
                return item;
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        // add first a
        deque.addFirst("a");
        for (String item : deque) {
            System.out.println(item);
        }
        System.out.println(deque.size());
        System.out.println("-------------------------");

        // add first c
        deque.addFirst("c");
        for (String item : deque) {
            System.out.println(item);
        }
        System.out.println(deque.size());
        System.out.println("-------------------------");

        // add last b
        deque.addLast("b");
        for (String item : deque) {
            System.out.println(item);
        }
        System.out.println(deque.size());
        System.out.println("-------------------------");

        // add last d
        deque.addLast("d");
        for (String item : deque) {
            System.out.println(item);
        }
        System.out.println(deque.size());
        System.out.println("-------------------------");

        // remove first
        deque.removeFirst();
        for (String item : deque) {
            System.out.println(item);
        }
        System.out.println(deque.size());
        System.out.println("-------------------------");

        // remove last
        deque.removeLast();
        for (String item : deque) {
            System.out.println(item);
        }
        System.out.println(deque.size());
        System.out.println("-------------------------");

        // remove first
        deque.removeFirst();
        for (String item : deque) {
            System.out.println(item);
        }
        System.out.println(deque.size());
        System.out.println("-------------------------");

        // remove first
        deque.removeFirst();
        for (String item : deque) {
            System.out.println(item);
        }
        System.out.println(deque.size());
        System.out.println("-------------------------");
    }
}
