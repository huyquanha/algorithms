package chapter3.part1;

import chapter3.ST;

import java.util.ArrayList;
import java.util.List;

/**
 * Ex3.1.2
 * @param <Key>
 * @param <Value>
 */
public class ArrayST<Key, Value> implements ST<Key, Value> {
    private class Node {
        Key k;
        Value v;

        public Node(Key k, Value v) {
            this.k = k;
            this.v = v;
        }
    }

    private Node[] a;
    private int N;

    public ArrayST() {
        a = (Node[]) new Object[2];
        N = 0;
    }

    public void put(Key key, Value val) {
        checkKey(key);
        if (N == a.length) {
            resize(2 * N);
        }
        for (int i = 0; i < N; i++) {
            if (a[i].k.equals(key)) {
                if (val == null) {
                    delete(key);
                } else {
                    a[i].v = val;
                }
                break;
            }
        }
        a[N++] = new Node(key, val);
    }

    public Value get(Key key) {
        checkKey(key);
        for (int i = 0; i < N; i++) {
            if (a[i].k.equals(key)) {
                return a[i].v;
            }
        }
        return null;
    }

    public void delete(Key key) {
        checkKey(key);
        boolean found = false;
        for (int i = 0 ; i < N && !found; i++) {
            if (a[i].k.equals(key)) {
                Node tmp = a[i];
                a[i] = a[N-1];
                a[N-1] = tmp;
                found = true;
            }
        }
        if (found) {
            a[--N] = null;
            if (N > 0 && N == a.length/4) {
                resize(a.length /2);
            }
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public Iterable<Key> keys() {
        List<Key> keys = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            keys.add(a[i].k);
        }
        return keys;
    }

    private void resize(int sz) {
        Node[] tmp = (Node[]) new Object[sz];
        for (int i = 0; i < N; i++) {
            tmp[i] = a[i];
        }
        a = tmp;
    }
}
