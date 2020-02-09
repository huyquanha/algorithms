package chapter2.part4;

import chapter1.part3.Queue;
import chapter1.part3.Stack;

import java.util.NoSuchElementException;

public class PQWithLink<Key extends Comparable<Key>> {
    private class Node {
        Key key;
        Node up, left, right;
        int size; //the size of the subtree with this node being the root. 1 for a standalone node
    }
    private Node root = null;
    private Stack<Node> lastInserted = new Stack<>();

    public void insert(Key k) {
        Node node = new Node();
        node.key = k;
        node.size = 1;
        if (root == null) {
            root = node;
            lastInserted.push(node);
        } else {
            insert(root, node);
        }
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return root.key;
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node last = lastInserted.pop();
        exch(root, last);
        Node parent = last.up;
        if (parent != null) {
            if (parent.left == last) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        while (parent != null) {
            parent.size--;
            parent = parent.up;
        }
        sink(root);
        return last.key;
    }

    public Key getLastInserted() {
        return lastInserted.peek().key;
    }

    public int size() {
        return root == null ? 0 : root.size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private void insert(Node r, Node node) {
        if (r.left == null) {
            r.left = node;
            node.up = r;
            lastInserted.push(node);
            swim(node);
        } else if (r.right == null) {
            r.right = node;
            node.up = r;
            lastInserted.push(node);
            swim(node);
        } else {
            //it's wrong here, because considering at the root, the left subtree might be larger, but the
            //next element should still go to the left subtree because it's not complete yet
            if (r.left.size <= r.right.size) {
                insert(r.left, node);
            } else {
                insert(r.right, node);
            }
        }
        r.size++;
    }

    private void swim(Node node) {
        while (node != root && less(node.up, node)) {
            exch(node.up, node);
            node = node.up;
        }
    }

    private void sink(Node node) {
        while(node.left != null) {
            Node child = node.left;
            if (node.right != null && less(child, node.right)) {
                child = node.right;
            }
            if (!less(node, child)) {
                break;
            }
            exch(node, child);
            node = child;
        }
    }

    private boolean less(Node a, Node b) {
        return a.key.compareTo(b.key) < 0;
    }

    private void exch(Node a, Node b) {
        Key tmp = a.key;
        a.key = b.key;
        b.key = tmp;
    }

    public void show() {
        Queue<Node> q = new Queue<>();
        q.enqueue(root);
        while(!q.isEmpty()) {
            Node processed = q.dequeue();
            if (processed != null) {
                System.out.print(processed.key + " ");
                q.enqueue(processed.left);
                q.enqueue(processed.right);
            } else {
                System.out.print("* ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        PQWithLink<Integer> pq = new PQWithLink<>();
        int N = Integer.parseInt(args[0]);
        for (int i = 0; i < N; i++) {
            pq.insert((int) (Math.random() * N));
        }
        pq.show();
        System.out.println(pq.size());
        System.out.println(pq.max());
        System.out.println(pq.getLastInserted());
        System.out.println(pq.delMax());
        pq.show();
    }
}
