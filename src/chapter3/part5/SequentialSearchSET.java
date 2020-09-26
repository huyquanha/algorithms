package chapter3.part5;

/**
 * Ex3.5.2
 * @param <Key>
 */
public class SequentialSearchSET<Key> {
    private static class Node<K> {
        K k;
        Node<K> next;

        public Node(K k) {
            this.k = k;
        }
    }

    private Node<Key> first;
    private int N;

    public void add(Key key) {
        if (contains(key)) return;
        Node<Key> oldFirst = first;
        first = new Node<>(key);
        first.next = oldFirst;
        N++;
    }

    public boolean contains(Key key) {
        Node<Key> cur = first;
        while (cur != null) {
            if (cur.k.equals(key)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public void delete(Key key) {
        if (!contains(key)) return;
        Node cur = first;
        while (cur != null) {
            if (cur.k.equals(key)) {
                cur.k = first.k;
                first = first.next;
                return;
            }
            cur = cur.next;
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }
}
