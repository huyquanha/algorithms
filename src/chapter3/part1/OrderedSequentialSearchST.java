package chapter3.part1;

import chapter1.part3.Queue;
import chapter3.OrderedST;

import java.util.NoSuchElementException;

/**
 * Ex3.1.3
 * @param <Key>
 * @param <Value>
 */
public class OrderedSequentialSearchST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    private class Node {
        Key k;
        Value v;
        Node next;

        public Node(Key k, Value v) {
            this.k = k;
            this.v = v;
            next = null;
        }
    }

    private Node first = null, last = null;
    private int N = 0;

    public void put(Key key, Value val) {
        checkKey(key);
        if (isEmpty()) {
            first = new Node(key, val);
            last = first;
            N++;
        } else {
            Node cur = first;
            Node prev = null;
            boolean putted = false;
            while (cur != null && !putted) {
                int cmp = key.compareTo(cur.k);
                if (cmp == 0) {
                    if (val == null) {
                        delete(key);
                    } else {
                        cur.v = val;
                    }
                    putted = true;
                } else if (cmp < 0 && val != null) {
                    // key < cur => key is to be inserted before cur, but after prev
                    if (prev == null) {
                        // only true if cur == first
                        Node oldFirst = first;
                        first = new Node(key, val);
                        first.next = oldFirst;
                    } else {
                        Node node = new Node(key, val);
                        node.next = cur;
                        prev.next = node;
                    }
                    putted = true;
                    N++;
                } else {
                    prev = cur;
                    cur = cur.next;
                }
            }
            if (!putted) {
                Node node = new Node(key, val);
                last.next = node;
                last = last.next;
            }
        }
    }

    public Value get(Key key) {
        checkKey(key);
        Node cur = first;
        while (cur != null) {
            int cmp = key.compareTo(cur.k);
            if (cmp == 0) {
                return cur.v;
            } else if (cmp < 0) {
                // key < cur, and cur is only going to get bigger
                return null;
            } else {
                cur = cur.next;
            }
        }
        return null;
    }

    public void delete(Key key) {
        checkKey(key);
        Node cur = first;
        Node prev = null;
        while (cur != null) {
            int cmp = key.compareTo(cur.k);
            if (cmp == 0) {
                if (prev == null) {
                    // cur == first
                    first = first.next;
                    if (first.next == null) {
                        last = first;
                    }
                } else {
                    prev.next = cur.next;
                    if (prev.next == null) {
                        last = prev;
                    }
                }
                N--;
                return;
            } else if (cmp < 0) {
                // key < cur, and cur is only going to get bigger
                return;
            } else {
                prev = cur;
                cur = cur.next;
            }
        }
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return first.k;
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return last.k;
    }

    public Key floor(Key key) {
        checkKey(key);
        Node cur = first;
        Node prev = null;
        while (cur != null) {
            int cmp = key.compareTo(cur.k);
            if (cmp == 0) {
                return key;
            } else if (cmp < 0) {
                // key < cur
                if (prev != null) {
                    return prev.k;
                } else {
                    return null;
                }
            } else {
                // key > cur
                prev = cur;
                cur = cur.next;
            }
        }
        if (prev == null) {
            // this happens if the table is empty
            return null;
        } else {
            // this happens when key is larger than all keys in the table. prev is now pointing at last
            return prev.k;
        }
    }

    public Key ceiling(Key key) {
        checkKey(key);
        Node cur = first;
        while (cur != null) {
            int cmp = key.compareTo(cur.k);
            if (cmp <= 0) {
                return cur.k;
            } else {
                cur = cur.next;
            }
        }
        return null;
    }

    public int rank(Key key) {
        int r = 0;
        Node cur = first;
        while (cur != null && cur.k.compareTo(key) < 0) {
            r++;
            cur = cur.next;
        }
        return r;
    }

    public Key select(int k) {
        if (k < 0 || k >= N) {
            return null;
        }
        Node cur = first;
        int i = 0;
        while (i < k) {
            cur = cur.next;
            i++;
        }
        return cur.k;
    }

    public boolean isEmpty() {
        return first == null; // or N == 0
    }

    public int size() {
        return N;
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new Queue<>();
        if (lo.compareTo(hi) > 0) {
            return q;
        }
        Node cur = first;
        while (cur != null) {
            Key k = cur.k;
            if (k.compareTo(lo) < 0) {
                cur = cur.next;
            }
            else if (k.compareTo(hi) <= 0) {
                q.enqueue(k);
            } else {
                break;
            }
        }
        return q;
    }
}
