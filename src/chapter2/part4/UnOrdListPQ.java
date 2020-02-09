package chapter2.part4;

public class UnOrdListPQ<Key extends Comparable<Key>> {
    private class Node {
        Key k;
        Node next;
    }

    private Node first = null, cur = null;
    private int N = 0;

    //constant
    public void insert(Key k) {
        Node node = new Node();
        node.k = k;
        node.next = null;
        if (first == null) {
            first = node;
            cur = first;
        } else {
            cur.next = node;
            cur = cur.next;
        }
        N++;
    }

    //~N
    public Key delMax() {
        Node max = first;
        Node itr = first.next;
        while (itr != null) {
            if (itr.k.compareTo(max.k) > 0) {
                max = itr;
            }
            itr = itr.next;
        }
        return max.k;
    }
}
