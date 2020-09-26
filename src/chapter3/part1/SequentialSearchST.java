package chapter3.part1;

import chapter3.ST;

import java.util.ArrayList;
import java.util.List;

public class SequentialSearchST<Key, Value> implements ST<Key, Value> {
    private class Node {
        Key k;
        Value v;
        Node next;

        Node(Key k, Value v) {
            this.k = k;
            this.v = v;
            this.next = null;
        }
    }

    // add mostRecent to cache the most received accessed Node
    private Node first, mostRecent;
    private int N, compares;

    public SequentialSearchST() {
        first = null;
        mostRecent = null;
        N = 0;
        compares = 0;
    }

    public void put(Key key, Value val) {
        checkKey(key);
        if (val == null) {
            delete(key);
        } else {
            if (mostRecent != null && mostRecent.k.equals(key)) {
                mostRecent.v = val;
            } else {
                Node cur = first;
                while (cur != null) {
                    if (key.equals(cur.k)) {
                        cur.v = val;
                        print();
                        return;
                    }
                    compares++;
                    cur = cur.next;
                }
                Node oldFirst = first;
                first = new Node(key, val);
                first.next = oldFirst;
                N++;
            }
        }
        print();
    }

    public Value get(Key key) {
        checkKey(key);
        if (mostRecent != null && mostRecent.k.equals(key)) {
            return mostRecent.v;
        }
        Node cur = first;
        while (cur != null) {
            if (key.equals(cur.k)) {
                mostRecent = cur;
                return cur.v;
            }
            cur = cur.next;
        }
        return null;
    }

    public boolean contains(Key key) {
        checkKey(key);
        if (mostRecent != null && mostRecent.k.equals(key)) {
            return true;
        }
        return ST.super.contains(key);
    }

    public void delete(Key key) {
        checkKey(key);
        Node cur = first;
        Node prev = null;
        while (cur != null) {
            if (key.equals(cur.k)) {
                if (prev == null) {
                    first = cur.next;
                } else {
                    prev.next = cur.next;
                }
                N--;
                return;
            }
            prev = cur;
            cur = cur.next;
        }
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0; //or first == null
    }

    public Iterable<Key> keys() {
        List<Key> keys = new ArrayList<>();
        Node cur = first;
        while (cur != null) {
            keys.add(cur.k);
            cur = cur.next;
        }
        return keys;
    }

    public int getCompares() {
        return compares;
    }

    private void print() {
        Node cur = first;
        while (cur != null) {
            System.out.print(cur.k + ";" + cur.v + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    /**
     * Ex3.1.10
     * @param arsg
     */
    public static void main(String[] arsg) {
        SequentialSearchST<Character, Integer> st = new SequentialSearchST<>();
        char[] keys = {'E', 'A','S','Y','Q','U','E','S','T','I','O','N'};
        for (int i = 0; i < keys.length; i++) {
            st.put(keys[i],i);
        }
        System.out.println("Number of compares: " + st.getCompares());
    }
}
